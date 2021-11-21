package com.lunarshade.vkapp.other;


import com.lunarshade.vkapp.dao.request.gameevent.PlayRqDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class PlayRqDtoTest extends Assertions {

    static PlayRqDto playRqDto;

    @Test
    void createObjWithoutException() {
        assertDoesNotThrow(() -> {
                    new PlayRqDto(
                            1L,
                            1L,
                            new ArrayList<Long>(),
                            new Date(),
                            new Date(),
                            2,
                            5,
                            null,
                            1L,
                            new ArrayList<String>());
                }
        );
    }

    @Test
    void createObjWithException() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new PlayRqDto(
                            1L,
                            null,
                            new ArrayList<Long>(),
                            new Date(),
                            new Date(),
                            2,
                            5,
                            null,
                            1L,
                            new ArrayList<String>());
                }
        );
    }



}
