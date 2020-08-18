package cn.netbuffer.sssbootstrap_table;

import cn.netbuffer.sssbootstrap_table.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestMarshaller {
    private static final Logger LOG = LoggerFactory.getLogger(TestMarshaller.class);
    private Marshaller marshaller=new XStreamMarshaller();
    private Unmarshaller unmarshaller=new XStreamMarshaller();

    @Test
    public void testWrite(){
        User user=new User("name","男",22,"13928383434","sss",(int)(System.currentTimeMillis()/1000),"remark");
        try {
            writeObjectToXml(user,"d:/user.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRead(){
        try {
            LOG.debug("readObjectFromXml(\"d:/user.xml\"):{}",readObjectFromXml("d:/user.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObjectToXml(Object object, String filename) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            marshaller.marshal(object, new StreamResult(fos));
        } catch (XmlMappingException e) {
            LOG.error("Xml-Serialization failed due to an XmlMappingException.", e);
        } catch (IOException e) {
            LOG.error("Xml-Serialization failed due to an IOException.", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public Object readObjectFromXml(String filename) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            return unmarshaller.unmarshal(new StreamSource(fis));
        } catch (IOException e) {
            LOG.error("Xml-Deserialization failed due to an IOException.", e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return null;
    }
}
