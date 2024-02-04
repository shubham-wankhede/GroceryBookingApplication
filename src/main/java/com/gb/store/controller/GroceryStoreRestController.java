package com.gb.store.controller;

import com.gb.store.constant.Constant;
import com.gb.store.model.request.GroceryOrderRequest;
import com.gb.store.model.response.GroceryItemResponse;
import com.gb.store.model.response.GroceryOrderResponse;
import com.gb.store.service.GroceryService;
import com.gb.store.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Grocery View and Order Rest Api Handler
 */
@RestController
@RequestMapping("/api/v1/groceries")
@Tag(name = "Grocery Store Api")
public class GroceryStoreRestController {

    private final OrderService orderService;
    private final GroceryService groceryService;

    @Autowired
    public GroceryStoreRestController(OrderService orderService, GroceryService groceryService) {
        this.orderService = orderService;
        this.groceryService = groceryService;
    }

    /**
     * Rest API to fetch all grocery items from store db
     * @return all available grocery items
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = "Fetch all groceries from grocery store.")
    public ResponseEntity<List<GroceryItemResponse>> getAllGroceries() {
        List<GroceryItemResponse> groceryItemResponses = groceryService.getAllGroceryItems();
        return new ResponseEntity<>(groceryItemResponses, HttpStatus.OK);
    }

    /**
     * Rest API to place an order for order with quntity details
     * @param groceryOrderRequest grocery order representing items and quantity to purchase
     * @return grocery purchase status
     */
    @PostMapping("/order")
    @PreAuthorize("hasAuthority('user:write')")
    @Operation(description = "Place an order or groceries.")
    public ResponseEntity<GroceryOrderResponse> placeOrder(
            @RequestBody @Valid List<GroceryOrderRequest> groceryOrderRequest) {
        orderService.placeOrder(groceryOrderRequest);
        return new ResponseEntity<>(GroceryOrderResponse.builder().status(Constant.SUCCESS).build(), HttpStatus.CREATED);
    }

}
