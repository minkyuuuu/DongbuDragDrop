package com.dongbusec.newmainlib.customhome;

import android.os.Bundle;

import com.dongbusec.corelib.base.BaseActivity;
import com.dongbusec.corelib.util.FileIOUtil;
import com.dongbusec.corelib.util.ResourceManager;
import com.dongbusec.corelib.util.Util;
import com.dongbusec.newmainlib.info.CommonInfo;

public class MainActivity extends BaseActivity {

	CustomHomeMain mCustomHomeMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();

		mCustomHomeMain = new CustomHomeMain(this);
		setContentView(mCustomHomeMain);
	}

	private void init() {
		FileIOUtil.initialize(this);

		Util.initDisplayInfo(this);
		Util.setFormScaleInfo(320, 568, 568, 320); // iphone5 디자인 시안 기준.

		// 폰트,칼라 로딩
		ResourceManager.initInstance(this);

		// 폰트를 외부에서 설정 하도록 기존 로직 변경
		ResourceManager.createFont(CommonInfo.FONT_PATH);

	}

}
