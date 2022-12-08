package com.example.demo.persistence;

import com.example.demo.model.persistence.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void testEquals() {
        Item item0=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        assertNotEquals(item0, null);
        assertNotEquals(item0, item1);
    }


}
