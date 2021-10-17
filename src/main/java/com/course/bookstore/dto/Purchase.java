package com.course.bookstore.dto;

import com.course.bookstore.entity.Address;
import com.course.bookstore.entity.Customer;
import com.course.bookstore.entity.Order;
import com.course.bookstore.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
