package com.dongbusec.customhome;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.dongbusec.corelib.util.LayoutUtil;
import com.dongbusec.corelib.util.ResourceManager;


public class CustomScrollView extends ScrollView {
	Context mContext;
	
	int originX, originY;
	int startX, startY;
	BaseView itemView = null;
	FrameLayout draggingView = null;
	FrameLayout.LayoutParams params;
	CustomHomeView chv = null;
	
	public CustomScrollView(Context context) {
		super(context);
		mContext = context;
	}
	
	// -----------------------------------------------------------------------------------
	// onInterceptTouchEvent()
	// ACTION_DOWN 과 ACTION_MOVE 이벤트 발생함.
	// ACTION_MOVE 3~4번 발생하고 onTouchEvent.ACTION_MOVE 무수히 발생.
	// ACTION_UP 이벤트는 발생하지 않음.
	// dragging 할 때 ACTION_MOVE 이벤트가 20번 정도 발생하고 onTouchEvent.ACTION_MOVE 무수히 발생.
	// ACTION_DOWN 이벤트가 필요하지 않으면 구현할 이유가 없음.
	// ACTION_MOVE 이벤트에 기능구현하면 안된다. 계속 발생하지 않는다.
	// -----------------------------------------------------------------------------------
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        final int action = event.getAction();
//		final int x = (int) event.getX();
//		final int y = (int) event.getY();	
//
//		switch (action) {
//		case MotionEvent.ACTION_DOWN:
//			Log.e("DragDrop", "CustomScrollView : onInterceptTouchEvent : ACTION_DOWN");
//			originX = x;
//			originY = y;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			Log.e("DragDrop", "CustomScrollView : onInterceptTouchEvent : ACTION_MOVE");
//			break;
//		case MotionEvent.ACTION_CANCEL:
//			Log.e("DragDrop", "CustomScrollView : onInterceptTouchEvent : ACTION_CANCEL");
//			break;
//		case MotionEvent.ACTION_UP:
//			Log.e("DragDrop", "CustomScrollView : onInterceptTouchEvent : ACTION_UP");
//			break;
//		}
//        return super.onInterceptTouchEvent(event);
//    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final int x = (int) event.getX();
		final int y = (int) event.getY();	
		
		switch (action) {
			case MotionEvent.ACTION_DOWN:	// CustomHomeView에 빈공간일때는 down 이벤트가 발생한다.
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_DOWN");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_MOVE");
				if(draggingView != null) {
					drag(x, y);
					return true;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_CANCEL");
				break;
			case MotionEvent.ACTION_UP:
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_UP");
				if(chv != null) {
					chv.removeView(draggingView);
					itemView.setVisibility(View.VISIBLE);
					chv = null;
					draggingView = null;
					return true;
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}
	
	public void setItemView(BaseView itemView) {
		this.itemView = itemView;
		//startX = itemView.getX();
		//startY = itemView.getY();
		
		if(itemView != null) makeDraggingView();
		if(itemView != null) startDrag();
	}

	private void makeDraggingView() {
		itemView.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(itemView.getDrawingCache());
		ImageView above = new ImageView(mContext);
		above.setImageBitmap(bitmap);
		
		int width = ((BaseView) itemView).getItemViewWidth();
		int height = ((BaseView) itemView).getItemViewHeight();
		startX = ((BaseView) itemView).getX();
		startY = ((BaseView) itemView).getRealY();
		Log.v("DragDrop", "startX : before : " + startX);
		Log.v("DragDrop", "startY : before : " + startY);
		
		draggingView = new FrameLayout(mContext);
		draggingView.setBackgroundDrawable(ResourceManager.getSingleImage("frame_edit_shadow"));
		LayoutUtil.addChildRetina(draggingView, above, width, height, 0,0,0,0, Gravity.CENTER);
		chv = (CustomHomeView) itemView.getParent();
		int x = startX - Constant.FRAME_SHADOW_SIZE/2;
		int y = startY - Constant.FRAME_SHADOW_SIZE/2;
        LayoutUtil.addChildRetina(chv, draggingView, width+Constant.FRAME_SHADOW_SIZE, height+Constant.FRAME_SHADOW_SIZE, x, y);
        draggingView.bringToFront();
        params = (LayoutParams) draggingView.getLayoutParams();
		Log.v("DragDrop", "startX : after : " + params.leftMargin);
		Log.v("DragDrop", "startY : after : " + params.topMargin);
		Log.v("DragDrop", "draggingView : x : " + draggingView.getLeft());
		Log.v("DragDrop", "draggingView : y : " + draggingView.getTop());
	}

	private void startDrag() {
		if(draggingView != null) {
			itemView.setVisibility(INVISIBLE);
			
//			params.leftMargin = originX;
//			params.topMargin = getScrollY() + originY;
//			draggingView.setLayoutParams(params);
		}
	}
	
	private void drag(int x, int y) {
		params.leftMargin = x - draggingView.getWidth() / 2;
		params.topMargin = getScrollY() + y - draggingView.getHeight() / 2;
		draggingView.setLayoutParams(params);
	}
	
	
}
