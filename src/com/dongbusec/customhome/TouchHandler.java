/*
 * Copyright (C) 2009 Google Inc.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongbusec.customhome;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ScrollView;

public final class TouchHandler implements View.OnTouchListener {
	
	FrameLayout mDraggingView;
	FrameLayout.LayoutParams params;
	ScrollView sv;
	CustomHomeView chv;
	CustomHomeItemView itemView;
	
	int width, height;

	public TouchHandler(View view, Context context) {
		sv = (ScrollView) view;
		view.setOnTouchListener(this);
		
	}
	
	public void setDraggingView(CustomHomeView parent, View itemView, FrameLayout view) {
		chv = (CustomHomeView) parent;
		this.itemView = (CustomHomeItemView) itemView;
		
		mDraggingView = view;
		params = (LayoutParams) mDraggingView.getLayoutParams();
		width = params.width;
		height = params.height;
	}
	
	private int xx(int x) {
		return x - width / 2;
	}

	private int yy(int y) {
		return y - height/2;	// 살짝 위로 올려주어야 손가락 위로 보인다.
	}

	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();
		long timestamp = event.getEventTime();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			anim.setVisibility(View.VISIBLE);
			Log.v(Constant.TAG, "ACTION_DOWN : x ---> " + x);
			Log.v(Constant.TAG, "ACTION_DOWN : y ---> " + y);
			params.leftMargin = xx(x);
			params.topMargin = yy(sv.getScrollY() + y);
			mDraggingView.setLayoutParams(params);
//
//			if (constrain(x, y)) {
//				params.leftMargin = xx(x);
//				params.topMargin = yy(y);
//				anim.setLayoutParams(params);
//				//params.setMargins(x, y, 0, 0);
//			}
//
//			state = new Sequence(x, y, timestamp);
			return true;

		case MotionEvent.ACTION_CANCEL:
//			state = null;
//			anim.setVisibility(View.INVISIBLE);
			sv.setOnTouchListener(null);
			return true;

		case MotionEvent.ACTION_UP:
//			boolean handled = state != null && state.handleUp(x, y, timestamp);
//			state = null;
//			anim.setVisibility(View.INVISIBLE);
//			return handled;
			sv.setOnTouchListener(null);
			chv.removeView(mDraggingView);
			itemView.setVisibility(View.VISIBLE);
			return true;

		case MotionEvent.ACTION_MOVE:
			Log.v(Constant.TAG, "ACTION_MOVE : x ---> " + x);
			//Log.v(Constant.TAG, "ACTION_MOVE : y ---> " + y);
			params.leftMargin = xx(x);
			params.topMargin = yy(sv.getScrollY() + y);
			mDraggingView.setLayoutParams(params);
			
			//sv.scrollBy(0, 10);
//			if (constrain(x, y)) {
//				params.leftMargin = xx(x);
//				params.topMargin = yy(y);
//				anim.setLayoutParams(params);
//			}
//			
//			return state != null && state.handleMove(x, y, timestamp);
			return true;

		default:
			return false;
		}
	}


}
