package com.lu.study.customandproduct.newjdk;

import com.lu.study.bean.Order;
import com.lu.study.customandproduct.interfaces.IOrderSys;
import com.lu.study.customandproduct.oldjdk.CustomThread;
import com.lu.study.customandproduct.oldjdk.ProductThread;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<Order> orderQueue = new ArrayList<>();
        IOrderSys orderSys = new OrderSysImplByNewWay(orderQueue);
        // 订单多
        while (true) {
            new ProductThread(orderSys, Order.builder().build()).run();

            new CustomThread(orderSys, Order.builder().build()).start();
        }
    }
}
