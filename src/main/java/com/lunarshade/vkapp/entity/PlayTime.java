package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PlayTime {
    private Date timeStart;
    private Date timeEnd;
}
