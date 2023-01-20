package com.bluehair.hanghaefinalproject.sse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
@NoArgsConstructor
public class RelatedUrl {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RedirectionType type;
    @Column(nullable = false)
    private Long typeId;

    public RelatedUrl(RedirectionType type, Long typeId){

        this.type = type;
        this.typeId = typeId;
    }
}
