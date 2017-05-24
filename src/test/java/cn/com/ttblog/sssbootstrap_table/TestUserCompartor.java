package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.model.User;
import cn.com.ttblog.sssbootstrap_table.model.UserCompartor;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import java.util.Collections;
import java.util.List;

public class TestUserCompartor {

    @Test
    public void test(){
        List<User> users= Lists.newArrayList();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setName(RandomStringUtils.random(5,true,true));
            user.setAge(RandomUtils.nextInt(1,99));
            users.add(user);
        }
        System.out.printf("比较前list:%s\n\n",users);
        Collections.sort(users,new UserCompartor());
        System.out.printf("比较后list:%s\n\n",users);
        Collections.reverse(users);
        System.out.printf("反转后list:%s\n\n",users);
    }
}
