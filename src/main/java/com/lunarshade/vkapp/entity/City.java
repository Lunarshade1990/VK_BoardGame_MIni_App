package com.lunarshade.vkapp.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class City {
    @Id
    private long Id;
    private String name;
}
