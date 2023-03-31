package org.interviewmate.domain.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Job {
    SERVER, CLIENT, DATA_SCIENTIST, DATA_ANALYST, AI_ENGINEER, AI_RESEARCHER;


}
