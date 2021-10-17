package com.course.bookstore.service;

import com.course.bookstore.dto.Purchase;
import com.course.bookstore.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
