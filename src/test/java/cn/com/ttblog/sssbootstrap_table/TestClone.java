package cn.com.ttblog.sssbootstrap_table;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestClone implements Cloneable{

    private Integer age;
    private String name;

    public static void main(String[] args) {
        TestClone test=new TestClone();
        test.age=20;
        test.name="test";
        System.out.println("test.clone():"+test.clone());
    }

    public TestClone clone(){
        try {
            return (TestClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
