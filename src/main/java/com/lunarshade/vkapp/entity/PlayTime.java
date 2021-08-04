package com.lunarshade.vkapp.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PlayTime {
    private Calendar timeStart;
    private Calendar timeEnd;
}
