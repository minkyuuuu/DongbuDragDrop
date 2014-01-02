
package com.example.draganddrop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dongbusec.corelib.util.LayoutUtil;
import com.dongbusec.corelib.util.ResourceManager;

public class CustomHome extends LinearLayout
{

    public CustomHome(Context context)
    {
        super(context);
        init(context);
        initView(context);
    }

    private void init(Context context)
    {
        setBackgroundDrawable(ResourceManager.getSingleImage("ico_mainbg01_b"));
        this.setOrientation(LinearLayout.VERTICAL);
    }

    private void initView(Context context)
    {
        LayoutUtil.noCacheAnimation(this);
        
        makeDummyView(context);
        //addView(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    
    LinearLayout below;
    private void makeDummyView(Context context){
    	// 상단영역
        LinearLayout above = new LinearLayout(context);
        above.setGravity(Gravity.CENTER);
        above.setBackgroundColor(Color.parseColor("#30FF0000"));
        above.setOrientation(LinearLayout.HORIZONTAL);
        
    	// Title
    	TextView tvTitle = new TextView(context);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setText("Default Home");
    	LayoutUtil.setFont(tvTitle, 2, Typeface.BOLD, 24, 100, "FFFFFF", Gravity.CENTER);
    	LayoutUtil.addChildRetina(above, tvTitle, LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
    	tvTitle.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		if (below != null && below.getVisibility() == View.GONE)
        		{
        			below.setVisibility(View.VISIBLE);
        		}
        	}
        });
    	
    	LayoutUtil.addChildRetina(this, above, LayoutParams.MATCH_PARENT , 134);
    	        
    	// 중간영역 (control region)
    	ScrollView sv = new ScrollView(context);
    	sv.setBackgroundColor(LayoutUtil.getHexaColor(60, "1f273a"));
    	RelativeLayout subLayout  = new RelativeLayout(context);
    	
    	sv.addView(subLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    	LayoutUtil.addChildWeightRetina(this, sv, LayoutParams.MATCH_PARENT , LayoutParams.WRAP_CONTENT, 1f);
    	
    	// 하단영역
        below = new LinearLayout(context);
        below.setGravity(Gravity.CENTER);
        below.setBackgroundColor(Color.parseColor("#300000FF"));
        below.setOrientation(LinearLayout.HORIZONTAL);
        
        // + button
        Button btnPlus = new Button(context);
        btnPlus.setBackgroundDrawable(ResourceManager.getImage("btn_plus02"));
        //LayoutUtil.addChildRetina(below, btnPlus, LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
        LayoutUtil.addChildRetina(below, btnPlus, 54, 54);
        btnPlus.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		if (below.getVisibility() == View.VISIBLE)
        		{
        			below.setVisibility(View.GONE);
        		}
        		else
        		{
        			below.setVisibility(View.VISIBLE);
        		}
        	}
        });
        
        LayoutUtil.addChildRetina(this, below, LayoutParams.MATCH_PARENT , 114, 0,0,0,0, Gravity.BOTTOM);
    	
    }

    private void testDisplayMatrics(Context context) {
    	DisplayMetrics metrics = new DisplayMetrics();
    	((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	int screenWidth = metrics.widthPixels;
    	int screenHeight = metrics.heightPixels;
    	
    	// Full HD (1080 x 1920)
//    	int screenWidth = Util.GetScreenWidth(false);	// 320
//    	int screenHeight = Util.GetScreenHeight(false);	// 469
//    	int screenWidth = Util.GetActivityScreenSize((Activity) context, false, false);	// 1032
//    	int screenHeight = Util.GetActivityScreenSize((Activity) context, true, false);	// 1968
//    	int screenWidth = Util.GetHandsetWidth();	// not work
//    	int screenHeight = Util.GetHandsetHeight();	// not work
    	
    	Log.v("DragDrop", "screenWidth : " + screenWidth);
    	Log.v("DragDrop", "screenHeight : " + screenHeight);
    }

}
