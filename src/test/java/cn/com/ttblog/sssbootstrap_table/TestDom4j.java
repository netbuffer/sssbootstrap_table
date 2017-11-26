package cn.com.ttblog.sssbootstrap_table;

import org.apache.commons.io.IOUtils;
import org.dom4j.*;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultCDATA;
import org.junit.Test;
import java.io.*;

public class TestDom4j {

    @Test
    public void testWrite() {
        writeXml();
    }

    @Test
    public void testRead() {
        readXml();
    }


    private void readXml() {
        StringBuilder stringBuilder=null;
        try {
            stringBuilder=new StringBuilder(IOUtils.toString(new FileReader("d:/users.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stringBuilder!=null){
            try {
                Document document=DocumentHelper.parseText(stringBuilder.toString());
                System.out.printf("解析xml文件:%s\n,xml:%s\n",document,document.asXML());
                System.out.printf("root:%s\n",document.getRootElement().asXML());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeXml() {
        try {
            //DocumentHelper提供了创建Document对象的方法
            Document document = DocumentHelper.createDocument();
            //添加节点信息
            Element rootElement = document.addElement("users");
            //这里可以继续添加子节点，也可以指定内容
            rootElement.setText("这个是users标签的文本信息");
            Element element = rootElement.addElement("user");
            Element nameElement = element.addElement("name");
            Element valueElement = element.addElement("value");
            Element descriptionElement = element.addElement("description");
            nameElement.setText("名称");
            nameElement.addAttribute("language", "java");//为节点添加属性值
            valueElement.setText("值");
            valueElement.addAttribute("language", "java");
            descriptionElement.setText("描述");
            descriptionElement.addAttribute("language", "mysql");
            //将document文档对象直接转换成字符串输出
            System.out.println(document.asXML());
            Writer fileWriter = new FileWriter("d:\\users.xml");
            //dom4j提供了专门写入文件的对象XMLWriter
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
            System.out.println("xml文档添加成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseWeixinMsg(){
        String notifyMsg="<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml> ";
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(notifyMsg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if(doc==null){
            return;
        }
        Element root = doc.getRootElement();
        String appid = root.elementText("appid");
        String attach=root.elementText("attach");
        System.out.printf("appid:%s,attach:%s\n",appid,attach);
    }

    @Test
    public void testCreateWeixinMsg(){
        Document document=DocumentHelper.createDocument();
        Element root=document.addElement("xml");
        Element returnCode=root.addElement("return_code");
        returnCode.add(new DefaultCDATA("SUCCESS"));
        Element returnMsg=root.addElement("return_msg");
        returnMsg.add(new DefaultCDATA("OK"));
        System.out.printf("document.asXML():\n%s\n",document.asXML());
        //移除xml头部后的xml片段
        System.out.printf("document.getRootElement().asXML():\n%s\n",document.getRootElement().asXML());
    }
}
