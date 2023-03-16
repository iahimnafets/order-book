package com.order.book;

import com.order.book.dto.Order;
import com.order.book.service.OrderBookService;

import java.util.List;

public class JavaMainTest {

    public static void main(String[] args) {

        OrderBookService orderBook = new OrderBookService();

        // Add some orders
        orderBook.addOrder(new Order(1, 100.0, 'B', 10));
        orderBook.addOrder(new Order(2, 99.5, 'O', 5));
        orderBook.addOrder(new Order(3, 101.0, 'B', 7));
        orderBook.addOrder(new Order(4, 98.0, 'O', 3));
        orderBook.addOrder(new Order(5, 100.5, 'B', 2));

        // get some result from methods
        System.out.println("Best bid price: " + orderBook.getPrice('B', 1));
        System.out.println("Total size available at best bid: " + orderBook.getTotalSize('B', 1));
        System.out.println("Second best offer price: " + orderBook.getPrice('O', 2));
        System.out.println("Total size available at third best bid: " + orderBook.getTotalSize('B', 3));

        List<Order> bids = orderBook.getOrders('B');
        List<Order> offer = orderBook.getOrders('O');
        printOrders(bids, "bids");
        printOrders(offer, "offer");

        // Modify an existing order
        orderBook.modifyOrderSize(1, 5);
        System.out.println("Size of order 1 after modification: " + orderBook.getOrder(1).getSize());

        // Remove an order
        orderBook.removeOrder(2);
        System.out.println("Order with id 2 removed");

        System.out.println(" After some updates in the system  \n");

        bids = orderBook.getOrders('B');
        offer = orderBook.getOrders('O');
        printOrders(bids, "bids");
        printOrders(offer, "offer");

    }

    private static void printOrders(List<Order> orders, String type  ){
        System.out.println("\nAll " + type + " in the book:");
        for (Order bid : orders) {
            System.out.println("id: "+bid.getId() + " Price: " + bid.getPrice() + " Size: " + bid.getSize());
        }
    }
}
