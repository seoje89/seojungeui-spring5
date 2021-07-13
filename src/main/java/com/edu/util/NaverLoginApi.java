package com.edu.util;

import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginApi extends DefaultApi20 {

	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//싱글톤 인스턴스 객체를 생성하기 위해서 인스턴스홀더 클래스를 생성
	private static class InstanceHolder {
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	}
	public static NaverLoginApi instance() {
		// 싱글톤으로 인스턴스 객체를 생성하는 방식: 클래스 실행을 한번만 하기 위해서(메모리에 올라가는 객체를 1회만 생성하기 위해서)
		return InstanceHolder.INSTANCE;
	}

}
