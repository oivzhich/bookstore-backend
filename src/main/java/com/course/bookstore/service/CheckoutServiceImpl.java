package com.course.bookstore.service;

import com.course.bookstore.dao.CustomerRepository;
import com.course.bookstore.dto.Purchase;
import com.course.bookstore.dto.PurchaseResponse;
import com.course.bookstore.entity.Customer;
import com.course.bookstore.entity.Order;
import com.course.bookstore.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Получение объекта Order из dto обхекта
        Order order = purchase.getOrder();

        // Генерация уникального номера заказа
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Добавление объектов OrderItems в объект Order
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // Добавление адреса доставки и платежного адреса в объект Order
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // Сохранеие объекта Customer
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            customer = customerFromDB;
        }
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // генерация рандомного UUID номера
        return UUID.randomUUID().toString();
    }
}









