package com.lu.study.customandproduct;

import com.lu.study.bean.Order;
import com.lu.study.customandproduct.interfaces.IOrderSys;

public class CustomThread extends Thread {

    private IOrderSys orderSys;

    private Order order;

    public CustomThread(IOrderSys orderSys,Order order) {
        this.orderSys = orderSys;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            orderSys.removeOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
