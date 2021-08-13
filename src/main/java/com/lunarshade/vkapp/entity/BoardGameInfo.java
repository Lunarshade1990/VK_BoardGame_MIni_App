package com.lunarshade.vkapp.entity;


import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.Set;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BoardGameInfo {
    private int minPlayerNumber;
    private int maxPlayerNumber;
    private int minTime;
    private int maxTime;
    @ElementCollection
    private Set<String> genres;
}
