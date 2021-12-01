package com.lunarshade.vkapp.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class BoardGameCollectionId implements Serializable {
    Long collection;
    Long boardGame;
}
