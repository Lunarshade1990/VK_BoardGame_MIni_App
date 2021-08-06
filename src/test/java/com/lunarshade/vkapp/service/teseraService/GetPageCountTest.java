package com.lunarshade.vkapp.service.teseraService;

import com.lunarshade.vkapp.service.TeseraService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
public class GetPageCountTest extends Assertions {

    @Autowired
    TeseraService teseraService;
    private static Method method;

    @SneakyThrows
    @BeforeAll
    static void initMethod() {
        method = TeseraService.class.getDeclaredMethod("getPageCount", int.class);
        method.setAccessible(true);
    }
    
    private int invoke(int gameCount) {
        try {
            return (int) method.invoke(teseraService, gameCount);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Test
    void AssertGameCountEqualLimit() {
        assertEquals(1, invoke(15));
    }

    @Test
    void AssertGameCountLessLimit() {
        assertEquals(1, invoke(5));
    }

    @Test
    void AssertGameCountMoreLimit() {
        assertEquals(2, invoke(30));
    }

    @Test
    void AssertGameCountMoreLimit2() {
        assertEquals(3, invoke(38));
    }

    @Test
    void AssertGameCountEquil0() {
        assertEquals(0, invoke(0));
    }



}
