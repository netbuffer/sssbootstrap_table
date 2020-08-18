package cn.netbuffer.sssbootstrap_table.model;

import java.util.Comparator;

/**
 * User类型比较器
 */
public class UserCompartor implements Comparator<User>{

    /**
     * 根据年龄大小来比较，可以在这里定制加入更多的比较策略
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(User o1, User o2) {
        return o1.getAge().compareTo(o2.getAge());
    }

}
