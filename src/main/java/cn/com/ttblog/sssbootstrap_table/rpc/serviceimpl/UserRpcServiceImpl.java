package cn.com.ttblog.sssbootstrap_table.rpc.serviceimpl;

import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.rpc.service.UserRpcService;
import cn.com.ttblog.sssbootstrap_table.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRpcServiceImpl implements UserRpcService{

    private static final Logger LOGGER= LoggerFactory.getLogger(UserRpcServiceImpl.class);

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private IUserService userService;

    @Override
    public User getUserById(long userId) {
//        User user=userService.findById(userId);
//        LOGGER.info("rpc调用获取用户信息:{}",user);
//        return user;
        User user=new User();
        user.setName("test-rpc");
        return user;
    }
}
