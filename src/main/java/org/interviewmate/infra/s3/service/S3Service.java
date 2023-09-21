package org.interviewmate.infra.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.infra.redis.repository.RedisInterviewVideoRepository;
import org.interviewmate.infra.s3.model.dto.response.GetPreSignedUrlRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final Long EXPIRATION_TIME = Duration.ofMinutes(60).toMillis();
    private final AmazonS3 amazonS3;
    private final RedisInterviewVideoRepository redisInterviewVideoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //todo : portfolio setUrl 해서 update (이때 해당 user의 portfolio가 없다면 create)

    /**
     * 파일 저장 1. Pre-Signed Url 생성 -> 2. object-key를 해당하는 객체 테이블에 저장
     */
    public GetPreSignedUrlRes saveObject(Long userId, Long number, String directory) {

        log.info("-----Start To Create Pre-signed Url-----");
        String path = directory + "/" + userId;

        // object-key 생성
        List<String> objectKeys = createObjectKeys(number, path);

        // pre-signed URL 생성
        List<String> preSignedUrls = createPreSignedUrls(objectKeys);

        //setObjectInRedis(path, preSignedUrls);

        log.info("-----Complete To Create Pre-signed Url-----");

        return GetPreSignedUrlRes.of(preSignedUrls);

    }

    private void setObjectInRedis(String path, List<String> preSignedUrls) {

        List<String> removeHeaderUrls = new ArrayList<>();
        preSignedUrls.stream().forEach(
                preSignedUrl -> {
                    String removeHeaderUrl = preSignedUrl.substring(0, preSignedUrl.lastIndexOf("?"));
                    removeHeaderUrls.add(removeHeaderUrl);
                    log.info(removeHeaderUrl);
                });

        redisInterviewVideoRepository.save(path, removeHeaderUrls);

    }

    private Date getExpiration(Long expirationTime) {

        Date now = new Date();

        return new Date(now.getTime() + expirationTime);

    }


    private List<String> createObjectKeys(Long number, String path) {

        List<String> objectKeys = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            objectKeys.add(path + "/" + UUID.randomUUID());
        }

        return objectKeys;

    }

    private List<String> createPreSignedUrls(List<String> objectKeys) {

        List<String> preSignedUrls = new ArrayList<>();
        Date expirationDate = getExpiration(EXPIRATION_TIME);

        for (String objectKey : objectKeys) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest
                    = new GeneratePresignedUrlRequest(bucket, objectKey)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expirationDate);

            generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString());
            URL preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            preSignedUrls.add(preSignedUrl.toString());
        }

        return preSignedUrls;

    }

    /**
     * 객체 주소 조회
     */
    public String retrieveObject(String objectKey) {

        log.info("-----Start To Create Object Url-----");

        Date expirationDate = getExpiration(EXPIRATION_TIME);
        log.info("Object Url Expiration : " + expirationDate);

        String objectUrl = retrieveObject(objectKey, expirationDate);
        log.info("Object Url : " + objectUrl);

        log.info("-----Complete To Create Object Url-----");

        return objectUrl;

    }

    private String retrieveObject(String objectKey, Date expirationDate) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, objectKey).withMethod(HttpMethod.GET).withExpiration(
                expirationDate);

        String objectUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return objectUrl.toString().substring(0, objectUrl.lastIndexOf("?"));
    }

}