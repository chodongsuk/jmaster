package com.kr.jmaster;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


public class WebApplication extends MultiDexApplication {
	/**
	 * 이미지 로더, 이미지 캐시, 요청 큐를 초기화한다.
	 */
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
