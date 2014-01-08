package com.dongbusec.customhome;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.dongbusec.corelib.util.LayoutUtil;

/**
 * 기본적으로 커스텀홈에서 사용되는 클래스, 변수, 메소드등의 이름은 아이폰에서 사용한 이름을 사용함.
 *
 */
public class CustomHomeView extends FrameLayout {

	public CustomHomeView(Context context) {
		super(context);
        initChild(context);
        startInitialize(context);
	}
	
    private void initChild(Context context)
    {
    	// CustomHomeManager may be implemented here!  --- no
    }

    private void startInitialize(Context context)
    {
    	makeChild(context);
    }

	private void makeChild(Context context) {
    	LayoutUtil.noCacheAnimation(this);
    	
    	int x, y;
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 4; j++) {
    			if(j == 0) x = j*Constant.SIZE_ONE + 12;
    			else x = j*Constant.SIZE_ONE + j*8 + 12;
    			y = i*Constant.SIZE_ONE + i*8;
    			
//    			View itemView = new CustomTileView(context, this, 11, 0, "지수");
//    			LayoutUtil.addChildRetina(this, itemView, Constant.SIZE_ONE, Constant.SIZE_ONE, x, y);
    		}
    	}
	}

}
