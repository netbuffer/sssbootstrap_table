package cn.com.ttblog.sssbootstrap_table;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

/**
 * http://blog.csdn.net/lonely_fireworks/article/details/7962171
 *
 * @author netbuffer
 */
public class TestStringFormat {
    @Test
    public void test() {
        System.out.println(String.format("Hi,%s", "王力"));
        System.out.println(String.format("Hi,%s:%s.%s", "王南", "王力", "王张"));
        System.out.printf("字母a的大写是：%c %n", 'A');//字符类型
        System.out.println(String.format("字母a的大写是：%c", 'A'));
        System.out.printf("3>7的结果是：%b %n", 3 > 7);//布尔类型
        System.out.printf("100的一半是：%d %n", 100 / 2);//整数类型（十进制）
        System.out.printf("100的16进制数是：%x %n", 100);//整数类型（十六进制）
        System.out.printf("100的8进制数是：%o %n", 100);//整数类型（八进制）
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50 * 0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50 * 0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50 * 0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50 * 0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');
    }

    @Test
    public void testStringCompare() {
        String[] str = new String[]{"01", "04", "03", "02"};
        System.out.println("01".compareTo("02"));
        Arrays.sort(str);
        System.out.println(Arrays.deepToString(str));
    }

    @Test
    public void testStringSplit() {
        String filename = "未标题-1.jpg";
        System.out.println("Arrays.deepToString(filename.split(\"\\.\"):" + Arrays.deepToString(filename.split("\\.")));
    }

    @Test
    public void testSubString() {
        String out_trade_no = "100000001-1478594744";
        out_trade_no = out_trade_no.substring(0, out_trade_no.indexOf("-"));
        System.out.println("out_trade_no:" + out_trade_no);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder("test1,test2,");
        System.out.println("sb:" + sb);
        System.out.println("sb.toString:" + sb.toString());
        System.out.println("sb.length:" + sb.length());
        System.out.println("sb.replace(sb.length()-1,sb.length(),\"\"):" + sb.replace(sb.length() - 1, sb.length(), ""));
    }

    @Test
    public void testBase64Image() throws Exception{
        String base64ImgData="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAMAAAC7IEhfAAAA81BMVEX///9VPnxWPXxWPXxWPXxWPXxWPXxWPXz///9hSYT6+vuFc6BXPn37+vz8+/z9/f2LeqWMe6aOfqiTg6uXiK5bQ4BZQX9iS4VdRYFdRYJfSINuWI5vWY9xXJF0YJR3Y5Z4ZZd5ZZd6Z5h9apq0qcW1qsW1q8a6sMqpnLyrn76tocCvpMGwpMJoUoprVYxeRoJjS4abjLGilLemmbrDutDFvdLPx9nX0eDa1OLb1uPd1+Td2OXe2eXh3Ofj3+nk4Orl4evp5u7u7PLv7fPx7/T08vb08/f19Pf29Pj39vn6+fuEcZ9YP35aQn/8/P1ZQH5fR4PINAOdAAAAB3RSTlMAIWWOw/P002ipnAAAAPhJREFUeF6NldWOhEAUBRvtRsfdfd3d3e3/v2ZPmGSWZNPDqScqqaSBSy4CGJbtSi2ubRkiwXRkBo6ZdJIApeEwoWMIS1JYwuZCW7hc6ApJkgrr+T/eW1V9uKXS5I5GXAjW2VAV9KFfSfgJpk+w4yXhwoqwl5AIGwp4RPgdK3XNHD2ETYiwe6nUa18f5jYSxle4vulw7/EtoCdzvqkPv3bn7M0eYbc7xFPXzqCrRCgH0Hsm/IjgTSb04W0i7EGjz+xw+wR6oZ1MnJ9TWrtToEx+4QfcZJ5X6tnhw+nhvqebdVhZUJX/oFcKvaTotUcvUnY188ue/n38AunzPPE8yg7bAAAAAElFTkSuQmCC";
        byte[] bs =Base64Utils.decodeFromString(base64ImgData.substring(base64ImgData.indexOf(',')+1));
        FileOutputStream os = new FileOutputStream("d:/test.png");
        os.write(bs);
        os.close();
    }
}
