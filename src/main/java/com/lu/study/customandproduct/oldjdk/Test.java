package com.lu.study.customandproduct.oldjdk;

import com.lu.study.bean.Order;
import com.lu.study.customandproduct.interfaces.IOrderSys;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Order> orderQueue = new ArrayList<>();
        IOrderSys orderSys = new OrderSys(orderQueue);
        // 订单多
        while (true) {
            new ProductThread(orderSys, Order.builder().build()).run();

            new CustomThread(orderSys, Order.builder().build()).start();
        }
    }
}
