package com.mc.filters;

import com.alibaba.fastjson.JSONObject;
import com.mc.entity.FormMap;
import com.mc.enums.ConstantEnums;
import com.mc.enums.StatusCode;
import com.mc.utils.JWTUtils;
import com.mc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.core.io.buffer.DataBuffer;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class Filter implements GlobalFilter, Ordered {

    @Resource
    RedisUtils redisUtils;
    @Value("#{'${router.path}'.trim().split(',')}")
    private List<String> router;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        if (!checkRouter(uri)) {
            log.info("-----------coming in filter uri is illegal------------- {}", uri);
            return setMessage(StatusCode.ILLEGAL_ACCESS.statusCode(), HttpStatus.NOT_FOUND, "非法访问资源！", exchange.getResponse());
        }
        if(uri.indexOf("/user/login") >= 0) { // 登录接口无需验证token
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst((String) ConstantEnums.TOKEN_KEY.getConstant());
        if (StringUtils.isBlank(authorization)) {
            log.error("Filter : the token is empty");
            return setMessage(StatusCode.AUTHENTICATION_ERROR.statusCode(), HttpStatus.UNAUTHORIZED, "认证失败，无权限访问！", exchange.getResponse());
        }
        if (!checkToken(authorization)) {
            return setMessage(StatusCode.AUTHENTICATION_ERROR.statusCode(), HttpStatus.UNAUTHORIZED, "认证失败，无权限访问！", exchange.getResponse());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


    private Mono<Void> setMessage(int code, HttpStatus httpStatusCode, String message, ServerHttpResponse response) {
        JSONObject msg = new JSONObject();
        msg.put("code", code);
        msg.put("date", null);
        msg.put("message", message);
        byte[] bytes = msg.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(httpStatusCode);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private boolean checkRouter(String uri) {
        if (StringUtils.isBlank(uri)) {
            log.error("gateway filter: the url is null");
            return false;
        }
        String route = uri.substring(1, uri.indexOf("/", 1));
        if (router.contains(route)) {
            return true;
        }
        return false;
    }

    private boolean checkToken(String authorization) {
        if (!redisUtils.hasKey(authorization) || redisUtils.getCacheObject(authorization) == null) {
            log.info("Filter : the token does not exist redis,open jwt validationToken");
            return checkJWTToken(authorization);
        }
        FormMap<String, Object> userMap = redisUtils.getCacheObject(authorization);
        String currentUserToken = (String) userMap.get("token");
        if (!authorization.equals(currentUserToken)) { // header中token与当前登录用户token不一致
            log.error("Filter : the header token is error!");
            return false;
        }
        // token 验证ok 刷新缓存时间
        redisUtils.expire(authorization, Long.parseLong(ConstantEnums.TOKEN_VALIDITY_TIME.getConstant() + ""));
        return true;
    }

    private boolean checkJWTToken(String authorization) {
        return JWTUtils.validationToken(authorization);
    }
}
