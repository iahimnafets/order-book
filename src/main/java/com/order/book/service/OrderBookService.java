package com.order.book.service;

import com.order.book.dto.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@org.springframework.stereotype.Service
@Slf4j
public class OrderBookService {

    // In this class I have NOT done any checks
    // regarding the input data due to lack of time.
    // In case you use swagger for testing enter
    // the DATA that is requested please

    private TreeMap<Double, TreeMap<Long, Order>> bidMap;
    private TreeMap<Double, TreeMap<Long, Order>> askMap;

    public OrderBookService() {
        bidMap = new TreeMap<>(Collections.reverseOrder());
        askMap = new TreeMap<>();
    }

    public void addOrder(Order order) {
        TreeMap<Double, TreeMap<Long, Order>> map = order.getSide() == 'B' ? bidMap : askMap;
        TreeMap<Long, Order> orders = map.getOrDefault(order.getPrice(), new TreeMap<>());
        orders.put(order.getId(), order);
        map.put(order.getPrice(), orders);
    }

    public synchronized void removeOrder(long id) {
        log.info("removeOrder-RUN id: {} ", id);
        removeOrderFromMap(id, bidMap);
        removeOrderFromMap(id, askMap);
    }

    public Order getOrder(long id) {
        log.info("getOrder-RUN id: {} ", id);
        Order order = findOrderFromMap(id, bidMap);
        if (order == null) {
            order = findOrderFromMap(id, askMap);
        }
        return order;
    }

    private Order findOrderFromMap(long id, TreeMap<Double, TreeMap<Long, Order>> map) {
        log.info("findOrderFromMap-RUN id: {} ", id);
        Order result = null;
        for (TreeMap<Long, Order> orders : map.values()) {
            result = orders.get(id);
        }
        return result;
    }

    private void removeOrderFromMap(long id, TreeMap<Double, TreeMap<Long, Order>> map) {
        log.info("removeOrderFromMap-RUN id: {} ", id);
        for (TreeMap<Long, Order> orders : map.values()) {
            orders.remove(id);
        }
    }

    public synchronized void modifyOrderSize(long id, long newSize) {
        log.info("modifyOrderSize-RUN id: {}  newSize: {}", id, newSize);
        modifyOrderInMap(id, newSize, bidMap);
        modifyOrderInMap(id, newSize, askMap);
    }

    private synchronized void modifyOrderInMap(long id, long newSize, TreeMap<Double, TreeMap<Long, Order>> map) {
        log.info("modifyOrderInMap-RUN id: {}  newSize: {}", id, newSize);

        for (TreeMap<Long, Order> orders : map.values()) {
            Order order = orders.get(id);
            if (order != null) {
                // order.setSize(newSize); <-- This method NOT exist in: Order
                // I don't want to modify the class originally since I want to keep it immutable
                //  id,  price,  side,  size
                Order newPriceForOrder = new Order(order.getId(), order.getPrice(), order.getSide(), newSize);
                orders.remove(id);
                orders.put(id, newPriceForOrder);
                break;
            }
        }
    }

    public Double getPrice(char side, int level) {
        log.info("getPrice-RUN side: {}  level: {}", side, level);

        TreeMap<Double, TreeMap<Long, Order>> map = side == 'B' ? bidMap : askMap;
        int i = 1;
        for (Double price : map.keySet()) {
            if (i == level) {
                return price;
            }
            i++;
        }
        return Double.NaN;
    }

    public long getTotalSize(char side, int level) {
        log.info("getTotalSize-RUN side: {}  level: {}", side, level);

        TreeMap<Double, TreeMap<Long, Order>> map = side == 'B' ? bidMap : askMap;
        int i = 1;
        for (TreeMap<Long, Order> orders : map.values()) {
            long totalSize = 0;
            for (Order order : orders.values()) {
                totalSize += order.getSize();
            }
            if (i == level) {
                return totalSize;
            }
            i++;
        }
        return 0;
    }

    public List<Order> getOrders(char side) {
        log.info("getOrders-RUN side: {} ", side);

        List<Order> orders = new ArrayList<>();
        TreeMap<Double, TreeMap<Long, Order>> map = side == 'B' ? bidMap : askMap;
        for (TreeMap<Long, Order> ordersAtPrice : map.values()) {
            orders.addAll(ordersAtPrice.values());
        }
        return orders;
    }


    public TreeMap<Double, TreeMap<Long, Order>> getBidMap() {
        return bidMap;
    }
}
