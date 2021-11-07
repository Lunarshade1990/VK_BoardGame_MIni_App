package com.lunarshade.vkapp.dao.request;

import com.lunarshade.vkapp.entity.DeskShape;
import lombok.Data;

@Data
public class TableForm {
    private final Long id;
    private final String name;
    private final int width;
    private final int length;
    private final int max;
    private final DeskShape shape;
    private final boolean byDefault;
}
