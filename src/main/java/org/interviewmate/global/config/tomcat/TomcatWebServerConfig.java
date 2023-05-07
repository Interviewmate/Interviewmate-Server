package org.interviewmate.global.config.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatWebServerConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    /**
     * tomcat에 옵션 추가
     * @param factory the web server factory to customize
     */
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(
                    connector -> connector.setProperty("relaxedQueryChars", "\\"));
//        factory.addConnectorCustomizers((TomcatConnectorCustomizer)
//                connector -> connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}"));
    }
}
