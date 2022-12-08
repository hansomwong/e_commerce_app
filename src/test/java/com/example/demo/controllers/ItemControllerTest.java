package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void testGetItems(){
        Item item0=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        List<Item> items=new ArrayList<>();
        items.add(item0); items.add(item1);
        when(itemRepo.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> obtainedItems=response.getBody();
        assertNotNull(obtainedItems);
        assertEquals(item0, obtainedItems.get(0));
        assertEquals(item1, obtainedItems.get(1));
    }

    @Test
    public void testGetItemById(){
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item obtainedItem= response.getBody();
        assertNotNull(obtainedItem);
        assertEquals(item1, obtainedItem);
    }

    @Test
    public void testGetItemByName(){
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        List<Item> itemList= new ArrayList<>(); itemList.add(item1);
        when(itemRepo.findByName("Square Widget")).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Square Widget");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> obtainedItems= response.getBody();
        assertNotNull(obtainedItems);
        assertEquals(item1, obtainedItems.get(0));
    }
}
