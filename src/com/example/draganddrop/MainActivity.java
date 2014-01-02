package com.example.draganddrop;

import android.os.Bundle;

import com.dongbusec.corelib.base.BaseActivity;
import com.dongbusec.corelib.util.FileIOUtil;
import com.dongbusec.corelib.util.ResourceManager;
import com.dongbusec.corelib.util.Util;
import com.dongbusec.newmainlib.info.CommonInfo;

public class MainActivity extends BaseActivity {

	CustomHome customHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();

		customHome = new CustomHome(this);
		setContentView(customHome);
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
