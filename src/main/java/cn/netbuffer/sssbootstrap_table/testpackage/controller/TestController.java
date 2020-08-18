package cn.netbuffer.sssbootstrap_table.testpackage.controller;

import org.springframework.stereotype.Controller;

@Controller(value = "testpackage_controller")
public class TestController {
    public TestController(){
        System.out.println("init testcontroller");
    }
}
