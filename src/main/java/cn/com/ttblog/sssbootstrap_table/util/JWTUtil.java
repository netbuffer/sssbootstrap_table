package cn.com.ttblog.sssbootstrap_table.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(JWTUtil.class);

    public static String createToken(Map param){
        Key key = MacProvider.generateKey();
        String compactJws = Jwts.builder()
                .setSubject(param.get("sub").toString())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        LOGGER.info("sign key:{}",key);
        return compactJws;
    }

    public static String createToken(Map param,String signKey){
        String compactJws = Jwts.builder()
                .setClaims((Map<String, Object>) param.get("claims"))
                .setSubject(param.get("sub").toString())
                .setIssuer(param.get("issuer").toString())
                .setExpiration((Date) param.get("expire"))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
        LOGGER.info("sign key:{}",signKey);
        return compactJws;
    }

    public static Jws<Claims> parseToken(String token, Key key){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    public static Jws<Claims> parseToken(String token, String signKey){
        //密钥错误会抛出SignatureException，说明该token是伪造的
        //如果过期时间exp字段已经早于当前时间，会抛出ExpiredJwtException，说明token已经失效
        return Jwts.parser().setSigningKey(signKey).parseClaimsJws(token);
    }
}
