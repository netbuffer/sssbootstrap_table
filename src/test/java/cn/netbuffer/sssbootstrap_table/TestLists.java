package cn.netbuffer.sssbootstrap_table;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class TestLists {
    @Test
    public void testPartition(){
        List<String> lists= Lists.newArrayList();
        lists.add("a");
        lists.add("b");
        lists.add("c");
        lists.add("d");
        lists.add("e");
        lists.add("f");
        lists.add("g");
        lists.add("h");
        List<List<String>> listList=Lists.partition(lists,3);
        System.out.printf("lists:%s,分组后:%s",lists,listList);
    }
}
