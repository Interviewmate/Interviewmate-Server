package org.interviewmate.debug.baseentity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.global.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class TestEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @Builder
    public TestEntity(String name) {
        this.name = name;
    }
}
