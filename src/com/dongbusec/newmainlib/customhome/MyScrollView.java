package com.dongbusec.newmainlib.customhome;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


public class MyScrollView extends ScrollView {
	
	GestureDetector gdv;

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.setOnTouchListener(gestureListener2);
		

		//onScrollChanged(l, t, oldl, oldt)
		GestureDetector gdv = new GestureDetector(vert);
		
		
		
	}
	
//	@Override
//	public void onScrollChanged() {
//
//	}
	
	OnTouchListener gestureListener2 = new View.OnTouchListener()
	{
	    public boolean onTouch(View v, MotionEvent event)
	    {
	        return gdv.onTouchEvent(event);
	    }
	};
	

	
	SimpleOnGestureListener vert = new SimpleOnGestureListener()
	{
	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	    {
	    	return false;
	    }
	};
	
}
