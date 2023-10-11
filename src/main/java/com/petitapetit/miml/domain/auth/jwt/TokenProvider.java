// package com.petitapetit.miml.domain.auth.jwt;
//
// import java.security.Key;
// import java.util.Arrays;
// import java.util.Base64;
// import java.util.Collection;
// import java.util.stream.Collectors;
//
// import javax.servlet.http.HttpServletRequest;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
//
// import com.petitapetit.miml.domain.member.model.RoleType;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @RequiredArgsConstructor
// public class TokenProvider {
//
// 	private static final String AUTHORITIES_KEY = "role";
// 	private final Key accessKey;
// 	private final Key refreshKey;
// 	@Value("${access-token.expiration}")
// 	private Long ACCESS_TOKEN_EXPIRATION;
// 	@Value("${refresh-token.expiration}")
// 	private Long REFRESH_TOKEN_EXPIRATION;
//
// 	public TokenProvider(String accessTokenSecret, String refreshTokenSecret) {
// 		this.accessKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(accessTokenSecret.getBytes()));
// 		this.refreshKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(refreshTokenSecret.getBytes()));
// 	}
//
// 	public AuthToken convertAccessToken(String token) {
// 		return new AuthToken(token, accessKey);
// 	}
//
// 	public AuthToken convertRefreshToken(String token) {
// 		return new AuthToken(token, refreshKey);
// 	}
//
// 	public AuthToken generateToken(String providerId, String role, boolean isAccessToken) {
//
// 		Claims claims = Jwts.claims().setSubject(providerId);
// 		claims.put(AUTHORITIES_KEY, role);
//
// 		AuthToken authToken;
// 		if (isAccessToken) {
// 			authToken = AuthToken.builder()
// 				.claims(claims)
// 				.key(accessKey)
// 				.duration(ACCESS_TOKEN_EXPIRATION)
// 				.build();
// 		} else { // refresh token 인 경우
// 			authToken = AuthToken.builder()
// 				.claims(claims)
// 				.key(refreshKey)
// 				.duration(REFRESH_TOKEN_EXPIRATION)
// 				.build();
// 		}
// 		return authToken;
// 	}
//
// 	public Authentication getAuthentication(AuthToken authToken) {
//
// 		if (!authToken.validateToken()) {
// 			throw new RuntimeException("access token invalid");
// 			// throw new BusinessException(ACCESS_TOKEN_INVALID);
// 		}
//
// 		Claims claims = authToken.getTokenClaims();
// 		Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(claims);
// 		User principal = new User(claims.getSubject(), "", authorities);
//
// 		return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
// 	}
//
// 	private Collection<? extends GrantedAuthority> getGrantedAuthorities(Claims claims) {
//
// 		RoleType role = RoleType.valueOf((String)claims.get("role"));
//
// 		return Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
// 			.map(c -> new SimpleGrantedAuthority(role.toString()))
// 			.collect(Collectors.toList());
// 	}
//
// 	/**
// 	 * http 헤더로부터 bearer 토큰을 가져옴.
// 	 */
// 	public String resolveToken(HttpServletRequest req) {
// 		String bearerToken = req.getHeader("Authorization");
// 		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
// 			return bearerToken.substring(7);
// 		}
// 		return null;
// 	}
// }
