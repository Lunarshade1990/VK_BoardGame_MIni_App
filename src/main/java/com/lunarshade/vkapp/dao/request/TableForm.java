package com.lunarshade.vkapp.dao.request;

import com.lunarshade.vkapp.entity.DeskShape;
import lombok.Data;

@Data
public class TableForm {
    String name;
    int width;
    int length;
    int max;
    DeskShape shape;
}
