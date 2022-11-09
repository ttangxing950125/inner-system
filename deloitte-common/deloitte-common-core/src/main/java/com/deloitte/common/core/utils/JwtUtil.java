package com.deloitte.common.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
	/**
	 * 30分钟过期
	 */
	private static final long EXPIRE_TIME=1440*60*1000;
	/**
	 * token私钥
	 */
	private static final String TOKEN_SECRET="B8C29C536555C1266D9BBBC57AE5B339";
	/***
	 * 校验token是否正确
	 * @param token 密钥
	 * @return 是否正确
	 */
	public static boolean verify(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier =JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static String sign(String userid){
		try {
			//过期时间
			Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
			//私钥及加密算法
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			//设置头部信息
			Map<String,Object> header = new HashMap<String,Object>();
			header.put("typ", "JWT");
		    header.put("alg", "HS256");
		    return JWT.create()
		    		.withHeader(header)
		    		.withClaim("userid", userid)
		    		.withExpiresAt(date)
		    		.sign(algorithm);
		} catch (Exception e) {
			return null;
		}
	}
	/***
	 * 获取token中用户id
	 * @param token
	 * @return
	 */
	public static String getuserid(String token){
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userid").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
}
