package com.lu.study.bean;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    // 顾客手机号
    private String accountPhone;
    // 顾客姓名
    private String accountName;
    // 顾客桌号
    private String accountTableID;
    // 顾客本次订单总价
    private double totalMoney;
    // 顾客本次订单打折
    private double range;
}
