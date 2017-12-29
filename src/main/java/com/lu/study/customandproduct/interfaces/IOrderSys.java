package com.lu.study.customandproduct.interfaces;

import com.lu.study.bean.Order;

import java.util.concurrent.atomic.AtomicInteger;

public interface IOrderSys {

    // 当前系统最多接单数量
    final static int MAX_ORDER_NUMBER = 10;

    // 目前接单数量
    static AtomicInteger currentOrderSize = new AtomicInteger(0);

    // 目前完成的订单数量
    static AtomicInteger currentFinishOrderSize = new AtomicInteger(0);

    // 增加一个订单
    void addOrder(Order addOrder) throws InterruptedException;

    // 完成一个订单
    void removeOrder(Order removeOrder) throws InterruptedException;
}
