package com.lu.study.bean;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Student {

    private String name;

    private String id;

    private int age;
}
