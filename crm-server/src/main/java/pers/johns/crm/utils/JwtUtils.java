package pers.johns.crm.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName    : JwtUtils
 * <br/>
 * Description  : JWT 相关工具类
 * <br/>
 * CreateTime   : 2024/6/27 15:55
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

public class JwtUtils {
    private JwtUtils() {}

    private static final String JWT_SECRET = "bGdqIksBYQ1sHqaE580P";

    public static String createJWT(String jsonData) {
        // 默认签反过期时间为 30 分钟
        return createJWT(jsonData, 1000 * 60 * 30);
    }

    public static String createJWT(String jsonData, long expireTime) {
        Map<String, Object> header = new HashMap<>();

        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Date now = new Date();

        return JWT.create()
                .withHeader(header)
                .withClaim("data", jsonData)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + expireTime))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public static Boolean verifyJWT(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();

            verifier.verify(jwt);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String parsePayloadData(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();

            DecodedJWT decodedJWT = verifier.verify(jwt);

            return decodedJWT.getClaim("data").asString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
