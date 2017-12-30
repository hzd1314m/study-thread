package com.lu.study.customandproduct;

import com.lu.study.bean.Order;
import com.lu.study.customandproduct.interfaces.IOrderSys;

import java.util.ArrayList;
import java.util.List;

public class ProductThread implements Runnable {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(0);
        for (int index = 0; index <= 100; index++) {
            list.add("list" + index);
        }

        list.forEach(i -> System.out.println(i));
    }

    private IOrderSys orderSys;

    private Order order;

    public ProductThread(IOrderSys orderSys, Order order) {
        this.orderSys = orderSys;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            orderSys.addOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
