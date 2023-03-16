package com.order.book.controller;


import com.order.book.dto.Order;
import com.order.book.dto.Response;
import com.order.book.service.OrderBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping( "/orders" )
@Slf4j
public class OrderBookController
{

    private final OrderBookService orderBookService;

    @Autowired
    public OrderBookController(final OrderBookService orderBookService )
    {
        this.orderBookService = orderBookService;
    }


    @Operation( summary =  "Add a new Order " )
    @PutMapping( value = "/add" )
    public ResponseEntity<Response> addOrder(@RequestBody final Order order)
    {
        orderBookService.addOrder( order );

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .message( " The order was added ")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @Operation( summary =  "Remove a order by Id " )
    @DeleteMapping( value = "/delete" )
    public ResponseEntity<Response> removeOrder(
                       @RequestParam( name = "idOrder",  required = true ) final Long idOrder )
    {
        orderBookService.removeOrder( idOrder  );
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .message( " The order was removed ")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @Operation( summary =  "Return a order by ID" )
    @GetMapping( value = "/get" )
    public ResponseEntity<Response> getOrder(
            @RequestParam( name = "idOrder",  required = true ) final Long idOrder )
    {

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .data(Map.of( "order ",  orderBookService.getOrder(idOrder) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @Operation( summary =  "Modify a order, change the size" )
    @PostMapping( value = "/change-size" )
    public ResponseEntity<Response> modifyOrder(
            @RequestParam( name = "idOrder",  required = true ) final Long idOrder,
            @RequestParam( name = "size",  required = true ) final Long size)
    {
        orderBookService.modifyOrderSize(idOrder, size );

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .message(" The size was modified")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @Operation( summary =  "Return the price per side and level SIDE: B=Bid or O=Offer" )
    @PostMapping( value = "/price" )
    public ResponseEntity<Response> getPrice(
            @RequestParam( name = "side",  required = true ) final String side,
            @RequestParam( name = "level",  required = true ) final Integer level)
    {

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .data(Map.of( "price",  orderBookService.getPrice(side.charAt(0), level) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @Operation( summary =  "Return the total size per side and level: SIDE: B=Bid or O=Offer " )
    @PostMapping( value = "/size" )
    public ResponseEntity<Response> getTotalSize(
            @RequestParam( name = "side",  required = true ) final String side,
            @RequestParam( name = "level",  required = true ) final Integer level)
    {

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .data(Map.of( "totalSize",  orderBookService.getTotalSize (side.charAt(0), level) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @Operation( summary =  "Return the orders by side  SIDE: B=Bid or O=Offer " )
    @GetMapping( value = "/orders" )
    public ResponseEntity<Response> getOrders(
            @RequestParam( name = "side",  required = true ) final String side)
    {

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .data(Map.of( "orders-by-side",  orderBookService.getOrders (side.charAt(0)) ))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }





}
