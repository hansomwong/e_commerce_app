package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    public static final String _userName = "hanhuang";
    private CartController cartController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void testAddToCart(){
        User user =new User();
        user.setUsername(_userName);
        Item item=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Cart cart=new Cart();
        cart.setId(0L);
        List<Item> itemList=new ArrayList<>(); itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99")); cart.setUser(user); user.setCart(cart);

        when(userRepo.findByUsername(_userName)).thenReturn(user);
        when(itemRepo.findById(0L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request=new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername(_userName);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        Cart cartAdded=response.getBody();
        assertNotNull(cartAdded);
        assertEquals(0L, cartAdded.getId());
        List<Item> items=cartAdded.getItems();
        assertNotNull(items);
        assertEquals(2,items.size());

        Item item1=items.get(0);
        assertNotNull(item1);
        assertEquals(new BigDecimal("5.98"), cartAdded.getTotal());
        assertEquals(user, cartAdded.getUser());
    }

    @Test
    public void testRemoveFromcart(){
        User user =new User();
        user.setUsername(_userName);
        Item item=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Cart cart=new Cart();
        cart.setId(0L);
        List<Item> itemList=new ArrayList<>(); itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99")); cart.setUser(user); user.setCart(cart);

        when(userRepo.findByUsername(_userName)).thenReturn(user);
        when(itemRepo.findById(0L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request=new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername(_userName);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        Cart cartAdded=response.getBody();
        assertNotNull(cartAdded);
        assertEquals(0L, cartAdded.getId());
        List<Item> items=cartAdded.getItems();
        assertNotNull(items);
        assertEquals(0,items.size());

        assertEquals(new BigDecimal("0.00"), cartAdded.getTotal());
        assertEquals(user, cartAdded.getUser());
    }
}
