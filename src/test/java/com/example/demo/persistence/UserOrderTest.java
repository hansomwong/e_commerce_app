package com.example.demo.persistence;

import com.example.demo.model.persistence.UserOrder;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserOrderTest {

    @Test
    public void testGetId() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(25L);
        assertEquals(25L, userOrder.getId());
    }
}
