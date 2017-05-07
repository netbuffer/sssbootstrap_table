package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * JWT易于辨识，是三段由小数点组成的字符串：
 * aaaaaaaaaa.bbbbbbbbbbb.cccccccccccc
 * 这三部分含义分别是header，payload, signature
 * https://github.com/jwtk/jjwt
 */
public class TestJWT {

    @Test
    public void testCreateToken() {
        Map param = new HashedMap();
        param.put("sub", "netbuffer");
        param.put("issuer", "aaa");
        Map claims = new HashedMap();
        DateTime exp=new DateTime();
        claims.put("create_time",exp.toString("yyyy-MM-dd HH:mm:ss") );
        param.put("claims", claims);
        param.put("expire", exp.toDate());
        System.out.printf("token:%s", JWTUtil.createToken(param, "netbuffer"));
    }

    @Test
    public void testParseToken() {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXRidWZmZXIifQ.kEnVEN4lyhdJn0JlbeFGw7iOv7Ido9iJQQoyPS6NAlts_Ph2fU2xK15FKjjmrRjPx8I06Pgzzwzo8seos1BTEw";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE0OTQxNTQ2NjEsImlzcyI6ImFhYSIsImNyZWF0ZV90aW1lIjoiMjAxNy0wNS0wNyAxODo1Nzo0MSIsInN1YiI6Im5ldGJ1ZmZlciJ9.u4CYN4PyR42E51zPUD6CGEj3STp3fknI03OOL04lONeoB1q0vsQOa63SvC-gxY3tho9cjrXda6WmtJuMd5bOVQ";
        //无效的token
        String invalidToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXRidWZmZXIifQ.kEnVEN4lyhdJn0JlbeFGw7iOv7Ido9iJQQoyPS6NAlts_Ph2fU2xK15FKjjmrRjPx8I06Pgzzwzo8seos1BTEwfsdafsd";
        try {
            System.out.printf("parse valid result:%s\n", JWTUtil.parseToken(token, "netbuffer"));
        } catch (Exception e) {
            //过期
            System.out.println("e:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            System.out.printf("parse invalidToken result:%s\n", JWTUtil.parseToken(invalidToken, "netbuffer"));
        } catch (ExpiredJwtException e) {
            System.err.printf("parse ExpiredJwtException error:" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.printf("parse UnsupportedJwtException error:" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.printf("parse MalformedJwtException error:" + e.getMessage());
        } catch (SignatureException e) {
            System.err.printf("签名错误 parse SignatureException error:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.printf("parse IllegalArgumentException error:" + e.getMessage());
        } catch (Exception e) {
            System.err.printf("parse Exception error:" + e.getMessage());
        }

    }
}
