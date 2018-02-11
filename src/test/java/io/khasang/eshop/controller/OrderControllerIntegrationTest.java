package io.khasang.eshop.controller;

import io.khasang.eshop.entity.Order;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.Assert.*;

public class OrderControllerIntegrationTest {
    private final String ROOT = "http://localhost:8085/order";
    private final String ADD = "/add";
    private final String GET = "/get";
    private final String UPDATE = "/update";
    private final String DELETE = "/delete";

    @Test
    public void addOrder() {
        Order order = createOrder();

        Order receivedOrder = getOrder(order.getId());
        assertNotNull(receivedOrder);
        assertEquals(order.getId(), receivedOrder.getId());
    }

    @Test
    public void updateOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Order order = createOrder();
        BigDecimal bigDecimal = new BigDecimal("22221.45");
        order.setAmount(bigDecimal);

        HttpEntity<Order> httpEntity = new HttpEntity<>(order, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Order> responseEntity = template.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                Order.class
        );
        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());

        Order receivedOrder = getOrder(order.getId());
        assertNotNull(receivedOrder);
        assertEquals(order.getId(), receivedOrder.getId());
        assertEquals(order.getAmount(), receivedOrder.getAmount());
    }

    @Test
    public void deleteOrder() {
        Order order = createOrder();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order> deletedEntiry = restTemplate.exchange(
                ROOT + DELETE + "?id={id}",
                HttpMethod.DELETE,
                null,
                Order.class,
                order.getId()
        );

        assertEquals("OK", deletedEntiry.getStatusCode().getReasonPhrase());

        Order receivedOrder = getOrder(order.getId());
        assertNull(receivedOrder);
    }

    private Order createOrder() {
        Order order = fillOrder();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<Order> httpEntity = new HttpEntity<>(order, headers);
        RestTemplate template = new RestTemplate();
        Order createdOrder = template.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                Order.class
        ).getBody();

        assertNotNull(createdOrder);
        assertEquals(order.getNumber(), createdOrder.getNumber());
        return createdOrder;
    }

    private Order fillOrder() {
        Order order= new Order();
        order.setNumber("зак-0003");
        Date date = new Date(1);
        order.setDate(date);
        BigDecimal bigDecimal = new BigDecimal(123456.88);
        order.setAmount(bigDecimal);
        return order;
    }

    private Order getOrder(Long id) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<Order> responseEntity = template.exchange(
                ROOT + GET + "?id={id}",
                HttpMethod.GET,
                null,
                Order.class,
                id
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        return responseEntity.getBody();
    }
}
