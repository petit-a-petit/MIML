package com.petitapetit.miml.domain.auth.oauth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.petitapetit.miml.global.util.SessionUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OriginalUriFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		SessionUtil.setOriginalRequestUri(request.getSession(), request.getRequestURI());
		filterChain.doFilter(request, response);
	}
}
