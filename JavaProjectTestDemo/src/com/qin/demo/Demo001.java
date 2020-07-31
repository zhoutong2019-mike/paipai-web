package com.qin.demo;

import java.io.InputStream;

public class Demo001 {

    public static void main(String [] args){

        InputStream resourceAsStream = Demo001.class.getResourceAsStream("123");
        System.out.println("resourceAsStream="+resourceAsStream);


        System.out.println("1212");

    }
}
