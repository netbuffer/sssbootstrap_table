package cn.netbuffer.sssbootstrap_table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.Test;

import java.io.IOException;

public class TestGetRandomUserApi {

    @Test
    public void testGetUser() {
        String api = "https://randomuser.me/api/";
        Request request = Request.Get(api).connectTimeout(5000).socketTimeout(5000);
        Response response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            System.out.printf("\nIO错误:" + e.getMessage());
        } catch (Exception e) {
            System.out.printf("\n错误:" + e.getMessage());
        }
        if (response != null) {
            String content = null;
            try {
                content = response.returnContent().asString();
            } catch (IOException e) {
                System.out.printf("\n服务器响应IO错误:%s", e.getMessage());
            } catch (Exception e) {
                System.out.printf("\n服务器响应错误:%s", e.getMessage());
            }
            if (StringUtils.isNotBlank(content)) {
                JSONObject jsonObject = JSON.parseObject(content);
                System.out.printf("\n服务器端响应内容:%s", jsonObject);
                JSONArray jarr = (JSONArray) jsonObject.get("results");
                JSONObject obj = (JSONObject) jarr.get(0);
                System.out.printf("\nname:%s,sex:%s,phone:%s,pic:%s", obj.getString("name"), obj.getString("gender"), obj.getString("phone"), obj.getString("picture"));
            }
        }
    }
}
