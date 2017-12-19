package com.supply.management.auth.util;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.supply.contant.UserType;
import com.supply.entity.po.UserPo;
import com.supply.management.config.contants.ServerConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil
{
	private static final String LOGIN_USER_KEY = "loginUser";

	public static String createJwt(final UserPo loginUser)
	{

		// 指定JWT使用的签名算法
		// SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//
		// // long nowMillis = System.currentTimeMillis();
		// Date now = new Date();

		// We will sign our JWT with our ApiKey secret
		// byte[] apiKeySecretBytes =
		// DatatypeConverter.parseBase64Binary(ServerConfig.JWT_KEY);
		// Key signingKey = new SecretKeySpec(apiKeySecretBytes,
		// signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setSubject(ServerConfig.SYSTEM_NAME).claim(LOGIN_USER_KEY, loginUser)
				.setExpiration(new Date(System.currentTimeMillis() + ServerConfig.TOKEN_EXPIRATION))
				.signWith(SignatureAlgorithm.HS256, ServerConfig.JWT_KEY);

		return builder.compact();
	}

	public static UserPo getLoginUserFromJwt(HttpServletRequest request)
	{
		Claims claims = parseJwt(getJwtStringFromCookie(request));
		if (claims == null)
		{
			return null;
		}
		Map map = (Map) claims.get(LOGIN_USER_KEY);
		if (map == null)
		{
			return null;
		}
		UserPo loginUser = new UserPo();
		loginUser.setId(Long.parseLong(map.get("id").toString()));
		loginUser.setUsername(map.get("username").toString());
		loginUser.setStoreId(Long.parseLong(map.get("storeId").toString()));
		loginUser.setUserType(Enum.valueOf(UserType.class, map.get("userType").toString()));
		return loginUser;
	}

	public static Claims getJwtClaims(HttpServletRequest request)
	{
		return parseJwt(getJwtStringFromCookie(request));
	}

	private static Claims parseJwt(String jwt)
	{
		// This line will throw an exception if it is not a signed JWS (as
		// expected)
		Claims claims = null;
		try
		{
			claims = Jwts.parser().setSigningKey(ServerConfig.JWT_KEY).parseClaimsJws(jwt).getBody();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return claims;
	}

	private static String getJwtStringFromCookie(HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
		{
			return null;
		}

		Stream<Cookie> streamCookies = Stream.of(cookies);
		Optional<Cookie> streamCookie = streamCookies.filter(new Predicate<Cookie>()
		{

			@Override
			public boolean test(Cookie t)
			{
				return ServerConfig.TOKEN_HEADER.equals(t.getName());
			}
		}).findFirst();
		if (streamCookie.isPresent())
		{
			Cookie cookie = streamCookie.get();
			return cookie.getValue();
		}
		return null;
	}
}
