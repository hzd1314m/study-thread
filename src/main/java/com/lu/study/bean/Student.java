package com.lu.study.bean;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private String name;

    private String id;

    private int age;
}
