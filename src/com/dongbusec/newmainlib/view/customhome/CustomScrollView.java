package com.dongbusec.newmainlib.view.customhome;

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


/**
 * ScrollView extends FrameLayout 
 */
public class CustomScrollView extends ScrollView {
	Context mContext;
	
	BaseView itemView = null;
	FrameLayout draggingView = null;
	FrameLayout.LayoutParams params;
	CustomHomeView chv = null;
	
	// draggingView 이동시 화면을 벗어날 때 scrollview의 이동을 처리하기 위해서...
	Runnable mScrollingRunnable;
	int [] draggingLoc = {0,0};	// draggingView의 화면위치 
	int limitBottomY = 0;		// ScrollView의 bottom 위치
	int screenLimitTopY = 0;	// draggingView의 움직이는 화면범위.
	int screenLimitBottomY = 0;	// draggingView의 움직이는 화면범위.
	
	public CustomScrollView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		mContext = context;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getPointerCount() > 1) {
	        return super.onTouchEvent(event);
	    }
		
		final int action = event.getAction();
		final int x = (int) event.getX();
		final int y = (int) event.getY();	
		
		switch (action) {
			case MotionEvent.ACTION_DOWN:	// CustomHomeView에 빈공간일때는 down 이벤트가 발생한다.
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_DOWN");
				break;
			case MotionEvent.ACTION_MOVE:
				//Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_MOVE");
				if(draggingView != null) {
					drag(x, y);
					scroll();
					return true;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_CANCEL");
				if(chv != null) {
					resetAll();
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.v("DragDrop", "CustomScrollView : onTouchEvent : ACTION_UP");
				if(chv != null) {
					resetAll();
					return true;
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	private void resetAll() {
		chv.removeView(draggingView);
		itemView.setVisibility(View.VISIBLE);
		chv = null;
		draggingView = null;
	}
	
	public void setItemView(BaseView itemView) {
		this.itemView = itemView;
		
		if(itemView != null) {
			buildDraggingView();
			startDrag();
		}
	}

	private void buildDraggingView() {
		itemView.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(itemView.getDrawingCache());
		ImageView above = new ImageView(mContext);
		above.setImageBitmap(bitmap);

		// draggingView의 위치설정은 design guide의 수치 기준으로 배치해야한다. 즉 객체에 저장된 수치기준으로 배치해야한다.
		// 단 배치할 때 그림자 크기를 고려해야한다.
		draggingView = new FrameLayout(mContext);
		draggingView.setBackgroundDrawable(ResourceManager.getSingleImage("frame_edit_shadow"));
		int designWidth = ((BaseView) itemView).getItemViewWidth();		// desing guide 수치, 즉 객체에 저장된 수치이다.
		int designHeight = ((BaseView) itemView).getItemViewHeight();
		LayoutUtil.addChildRetina(draggingView, above, designWidth, designHeight, 0, 0, 0, 0, Gravity.CENTER);
		
		chv = (CustomHomeView) itemView.getParent();
		int designX = ((BaseView) itemView).getX();						// desing guide 수치, 즉 객체에 저장된 수치이다.
		int designY = ((BaseView) itemView).getRealY();
		int w = designWidth + Constant.FRAME_SHADOW_SIZE;
		int h = designHeight + Constant.FRAME_SHADOW_SIZE;
		int left = designX - Constant.FRAME_SHADOW_SIZE / 2;
		int top = designY - Constant.FRAME_SHADOW_SIZE / 2;
		
		LayoutUtil.addChildRetina(chv, draggingView, w, h, left, top);
		draggingView.bringToFront();
		
		params = (LayoutParams) draggingView.getLayoutParams();
	}

	private void startDrag() {
		if(draggingView != null) {
			itemView.setVisibility(INVISIBLE);
			
			limitBottomY = computeVerticalScrollRange() - getHeight(); 
			screenLimitTopY = getTop();
			screenLimitBottomY = getBottom();
		}
	}
	
	private void drag(int x, int y) {
		params.leftMargin = x - draggingView.getWidth() / 2;
		params.topMargin = getScrollY() + y - draggingView.getHeight() / 2;
		draggingView.setLayoutParams(params);
	}
	
	private void scroll() {
		// 먼저 scroll의 보이는 위치가 스크롤의 처음과 마지막이면 return
		if( !(getScrollY() >= 0 && getScrollY() <= limitBottomY) ) return;
		
		if(draggingView != null) {
			// draggingView의 위치가 보이는 스크롤영역을 벗어나면...
			draggingView.getLocationOnScreen(draggingLoc);
			
			if (draggingLoc[1] < screenLimitTopY) {
				//dy = screenLimitTopY - draggingLoc[1];
				post(new Runnable() {
					@Override
					public void run() {
						scrollBy(0, -Constant.SCROLL_SPEED);
					}
				});
			}
			if ((draggingLoc[1] + draggingView.getHeight()) > screenLimitBottomY) {
				//dy = screenLimitBottomY - (draggingLoc[1] + draggingView.getHeight());
				post(new Runnable() {
					@Override
					public void run() {
						scrollBy(0, Constant.SCROLL_SPEED);
					}
				});
			}
		    
		}

	}
}
