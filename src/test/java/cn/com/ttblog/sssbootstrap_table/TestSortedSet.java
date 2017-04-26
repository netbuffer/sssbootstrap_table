package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

public class TestSortedSet {
    @Test
    public void testSortedSet(){
        SortedSet set=new TreeSet();
        set.add("z");
        set.add("c");
        set.add("b");
        set.add("m");
        //输出顺序:[b, c, m, z]
        System.out.printf("set:%s，first:%s,last:%s",set,set.first(),set.last());
    }
}
