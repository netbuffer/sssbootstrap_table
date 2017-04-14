package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;

public class TestArrayToString {

    @Test
    public void test(){
        int[] arr=new int[]{1,2};
        System.out.println("int arr:"+arr);
        Integer[] arrObj=new Integer[]{1,2};
        System.out.println("int obj arr:"+arrObj);
        long[] arrl=new long[]{1,2};
        System.out.println("long arr:"+arrl);
        float[] floats=new float[]{1.0f,1.1f};
        System.out.println("float arr:"+floats);
        double[] arrd=new double[]{1,2};
        System.out.println("double arr:"+arrd);
        byte[] bytes=new byte[]{1,2};
        System.out.println("byte arr:"+bytes);
    }

}
