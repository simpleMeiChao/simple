package com.mc.utils;

import com.mc.entity.FormMap;
import com.mc.enums.ConstantEnums;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtils {

    /**
     * 签发token
     *
     * @return token
     */
    public static String buildToken(String userName) {
        Date buildTime = new Date();
        Date expirationTime = new Date(buildTime.getTime() +  Long.parseLong(ConstantEnums.TOKEN_VALIDITY_TIME.getConstant() + ""));
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setIssuer("adminSimple")
                .setSubject("build-token")
                .setId(UUIDUtils.getUUID())
                .setIssuedAt(buildTime)
                .setExpiration(expirationTime)
                .claim("username", userName)
                .signWith(SignatureAlgorithm.HS256, (String) ConstantEnums.TOKEN_SIGNATURE.getConstant())
                .compact();
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public static Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parser();
        Claims claims;
        try {
            Jws<Claims> claimsJws = jwtParser
                    .setSigningKey((String) ConstantEnums.TOKEN_SIGNATURE.getConstant())
                    .parseClaimsJws(token);
            claims = claimsJws.getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 校验token
     *
     * @param token token
     * @return true or false
     */
    public static boolean validationToken(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return false;
        }
        return true;
    }
}
