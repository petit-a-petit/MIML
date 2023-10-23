package com.petitapetit.miml.global.util;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	private static final String ORIGINAL_REQUEST_URI = "ORIGINAL_REQUEST_URI";

	// 인스턴스화 방지
	private SessionUtil() {
	}

	/**
	 * 로그인을 시도한 유저가 접속하고자 하는 ORIGINAL URI 를 세션에 저장한다.
	 */
	public static void setOriginalRequestUri(HttpSession session, String originalRequestUri) {
		session.setAttribute(ORIGINAL_REQUEST_URI, originalRequestUri);
	}

	/**
	 * 로그인한 유저가 접속하고자 했던 ORIGINAL URI 를 세션에서 꺼낸다.
	 */
	public static String getOriginalRequestUri(HttpSession session) {
		return (String) session.getAttribute(ORIGINAL_REQUEST_URI);
	}

}
