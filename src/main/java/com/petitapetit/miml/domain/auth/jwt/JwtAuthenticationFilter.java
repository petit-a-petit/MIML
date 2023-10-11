// package com.petitapetit.miml.domain.auth.jwt;
//
// import java.io.IOException;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// /*
//  * 클라이언트에게 받은 JWT 가 유효한 토큰인지 확인하는 "인가 필터"
//  */
// @Slf4j
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
// 	private final TokenProvider tokenProvider;
//
// 	@Override
// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
// 		throws ServletException, IOException {
// 		String accessToken = tokenProvider.resolveToken(request);
// 		try {
// 			if (accessToken != null) {
// 				AuthToken authToken = tokenProvider.convertAccessToken(accessToken);
// 				if (authToken.validateToken()) {
// 					Authentication authentication = tokenProvider.getAuthentication(authToken);
// 					SecurityContextHolder.getContext().setAuthentication(authentication);
// 				}
// 			}
// 		} catch (RuntimeException e) {
// 		// } catch (BusinessException e) {
// 			SecurityContextHolder.clearContext();
// 			response.sendError(500, e.getMessage()); // 임시로 500
// 			// response.sendError(e.getErrorCode().getHttpStatus().value(), e.getMessage());
// 			return;
// 		}
//
// 		filterChain.doFilter(request, response);
// 	}
// }
