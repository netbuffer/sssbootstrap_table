package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.rpc.service.UserRpcService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/rpc")
public class RpcController {

    @Resource
    private UserRpcService userRpcService;

    @RequestMapping("get/{id}")
    public User getUser(@PathVariable(value = "id") Long id){
        return userRpcService.getUserById(id);
    }
}
