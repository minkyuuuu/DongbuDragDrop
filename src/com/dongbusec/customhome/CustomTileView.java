package com.dongbusec.customhome;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dongbusec.corelib.util.LayoutUtil;
import com.dongbusec.corelib.util.ResourceManager;

public class CustomTileView extends FrameLayout {
	int SIZE_ONE 	= 148;
	int SIZE_TWO 	= 304;
	int SIZE_THREE 	= 406;
	int SIZE_FOUR 	= 616;

	public CustomTileView(Context context, FrameLayout customHomeView, int viewType, int moduleType, String itemCode) {
		super(context);
        init(context, customHomeView, viewType, moduleType);
        initView(context, moduleType, itemCode);
	}
	
    private void init(Context context, FrameLayout customHomeView, int viewType, int moduleType)
    {
    	LayoutParams params = new LayoutParams(SIZE_ONE, SIZE_ONE);
    	this.setLayoutParams(params);
    	
        setBackgroundDrawable(ResourceManager.getSingleImage("frame_box_green01.9"));
        //LayoutUtil.addChildRetina(customHomeView, this, SIZE_ONE , SIZE_ONE);
    }

    private void initView(Context context, int moduleType, String itemCode)
    {
        LayoutUtil.noCacheAnimation(this);

        TextView tv = new TextView(context);

        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 34, 100, "000000", Gravity.CENTER);
        tv.setText(itemCode);

        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0,0,0,0, Gravity.CENTER);

    }

}
