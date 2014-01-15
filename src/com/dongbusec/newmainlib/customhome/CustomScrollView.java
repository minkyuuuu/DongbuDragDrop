package com.dongbusec.newmainlib.customhome;

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
	
	int originX, originY;
	
	int startX, startY;
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
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
//        Log.v("DragDrop", "CustomScrollView : onScrollChanged() : y --> " + y);
//        Log.v("DragDrop", "CustomScrollView : onScrollChanged() : oldy --> " + oldy);
    }
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : w --> " + w);
//		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : h --> " + h);
//		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : oldw --> " + oldw);
//		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : oldh --> " + oldh);
		super.onSizeChanged(w, h, oldw, oldh);
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
//		startX = itemView.getX();
//		startY = itemView.getY();
		
		if(itemView != null) makeDraggingView();
		if(itemView != null) startDrag();
	}

	private void makeDraggingView() {
//		Log.i("DragDrop", "itemView width : ---> " + itemView.getWidth());			// (first : 249
//		Log.i("DragDrop", "itemView height : ---> " + itemView.getHeight());		// (first : 250
//		
//		Log.i("DragDrop", "itemView : getLeft : ---> " + itemView.getLeft());		// (first : 20
//		Log.i("DragDrop", "itemView : getTop : ---> " + itemView.getTop());			// (first : 0
//		Log.i("DragDrop", "itemView : getRight : ---> " + itemView.getRight());		// (first : 269
//		Log.i("DragDrop", "itemView : getBottom : ---> " + itemView.getBottom());	// (first : 250
//		
//		itemView.getLocationOnScreen(location);
//		Log.i("DragDrop", "itemView : location[0] : x : ---> " + location[0]);		// (first : 20
//		Log.i("DragDrop", "itemView : location[1] : y : ---> " + location[1]);		// (first : 226
		
		itemView.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(itemView.getDrawingCache());
		ImageView above = new ImageView(mContext);
		above.setImageBitmap(bitmap);
		
		int width = ((BaseView) itemView).getItemViewWidth();
		int height = ((BaseView) itemView).getItemViewHeight();
		startX = ((BaseView) itemView).getX();
		startY = ((BaseView) itemView).getRealY();
		Log.v("DragDrop", "startX : before : " + startX);	// (first : 12)
		Log.v("DragDrop", "startY : before : " + startY);	// (first : 0)
		
		draggingView = new FrameLayout(mContext);
		draggingView.setBackgroundDrawable(ResourceManager.getSingleImage("frame_edit_shadow"));
		LayoutUtil.addChildRetina(draggingView, above, width, height, 0,0,0,0, Gravity.CENTER);
		chv = (CustomHomeView) itemView.getParent();
		int x = startX - Constant.FRAME_SHADOW_SIZE/2;
		int y = startY - Constant.FRAME_SHADOW_SIZE/2;
        LayoutUtil.addChildRetina(chv, draggingView, width+Constant.FRAME_SHADOW_SIZE, height+Constant.FRAME_SHADOW_SIZE, x, y);
        draggingView.bringToFront();
        params = (LayoutParams) draggingView.getLayoutParams();
		Log.v("DragDrop", "startX : after : " + params.leftMargin);			// (first : -3)
		Log.v("DragDrop", "startY : after : " + params.topMargin);			// (first : -23)
		Log.v("DragDrop", "draggingView : x : " + draggingView.getLeft());	// (first : 0)
		Log.v("DragDrop", "draggingView : y : " + draggingView.getTop());	// (first : 0)
	}

	private void startDrag() {
		if(draggingView != null) {
			itemView.setVisibility(INVISIBLE);
			
//			params.leftMargin = originX;
//			params.topMargin = getScrollY() + originY;
//			draggingView.setLayoutParams(params);
			
			limitBottomY = computeVerticalScrollRange() - getHeight(); 
		}
	}
	
	private void drag(int x, int y) {
		params.leftMargin = x - draggingView.getWidth() / 2;
		params.topMargin = getScrollY() + y - draggingView.getHeight() / 2;
		draggingView.setLayoutParams(params);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// computeVerticalScrollRange() = 0 ??? 
		//limitBottomY = computeVerticalScrollRange() - getHeight(); // 2702 = 4204 - 1502, 크기가 변하면 다시 계산해주어야한다.
		screenLimitTopY = getTop();
		screenLimitBottomY = getBottom();
		super.onLayout(changed, l, t, r, b);
	}

	int dy = 0;
	private void scroll() {
		// 먼저 scroll의 보이는 위치가 스크롤의 처음과 마지막이면 return
		if( !(getScrollY() >= 0 && getScrollY() <= limitBottomY) ) return;
		
		if(draggingView != null) {
			// draggingView의 위치가 보이는 스크롤영역을 벗어나면...
			draggingView.getLocationOnScreen(draggingLoc);
			Log.e("DragDrop", "draggingView : location[1] : y : ---> " + draggingLoc[1]);	
			
			if (draggingLoc[1] < screenLimitTopY) {
				dy = screenLimitTopY - draggingLoc[1];
				post(new Runnable() {
					@Override
					public void run() {
						scrollBy(0, -10);
					}
				});
			}
			if ((draggingLoc[1] + draggingView.getHeight()) > screenLimitBottomY) {
				dy = screenLimitBottomY - (draggingLoc[1] + draggingView.getHeight());
				post(new Runnable() {
					@Override
					public void run() {
						scrollBy(0, 10);
					}
				});
			}
		    
		}

	}
	
    final Runnable r = new Runnable()
    {
        public void run() 
        {
        }
    };
	
	@Override
	public void scrollTo(int x, int y) {
		Log.v("DragDrop", "CustomScrollView : scrollTo() : x --> " + x);
		Log.v("DragDrop", "CustomScrollView : scrollTo() : y --> " + y);
		
//		if(draggingView != null) {
//			
//			//Log.v("DragDrop", "draggingView : getLeft : ---> " + draggingView.getLeft());								// (first : -3
//			Log.v("DragDrop", "draggingView : getTop : ---> " + draggingView.getTop());									// (first : -23
//			//Log.v("DragDrop", "draggingView : getRight : ---> " + draggingView.getRight());						 		// (first : 297
//			Log.v("DragDrop", "draggingView : getBottom : ---> " + draggingView.getBottom());							// (first : 277
//			
//			draggingView.getLocationOnScreen(draggingLoc);
//			//Log.v("DragDrop", "draggingView : location[0] : x : ---> " + location[0]);									// (first : -2
//			Log.e("DragDrop", "draggingView : location[1] : y : ---> " + draggingLoc[1]);									// (first : 203
//			
//			Log.v("DragDrop", "CustomScrollView : computeVerticalScrollRange() : ---> " + computeVerticalScrollRange());
//		}
		
//		
//		// test
//		Log.v("DragDrop", "CustomScrollView : getMaxScrollAmount() : ---> " + getMaxScrollAmount());					// 751, no meaning
//		Log.v("DragDrop", "CustomScrollView : isFillViewport() : ---> " + isFillViewport());							// false
//		Log.v("DragDrop", "CustomScrollView : isSmoothScrollingEnabled() : ---> " + isSmoothScrollingEnabled());		// true
//				
//		// protected	
//		Log.v("DragDrop", "CustomScrollView : computeVerticalScrollOffset() : ---> " + computeVerticalScrollOffset());	// 0
//		Log.v("DragDrop", "CustomScrollView : computeVerticalScrollRange() : ---> " + computeVerticalScrollRange());	// 4204
//		Log.v("DragDrop", "CustomScrollView : getBottomFadingEdgeStrength() : ---> " + getBottomFadingEdgeStrength());	// 1.0
//		Log.v("DragDrop", "CustomScrollView : getTopFadingEdgeStrength() : ---> " + getTopFadingEdgeStrength());		// 1.0
//		
//		setFillViewport(true);																							// no meaning
//		Log.v("DragDrop", "CustomScrollView : isFillViewport() : ---> " + isFillViewport());							// true
//		
//		Log.v("DragDrop", "CustomScrollView : getChildCount : ---> " + getChildCount());								// 1
//		Log.v("DragDrop", "CustomScrollView : getParent : ---> " + getParent());										// CustomHomeMain
//		Log.v("DragDrop", "CustomScrollView : getRootView : ---> " + getRootView());									// 
//		
//		// check display size
//		Log.v("DragDrop", "CustomScrollView : scrollview width : ---> " + getWidth());									// 1080
//		Log.v("DragDrop", "CustomScrollView : scrollview height : ---> " + getHeight());								// 1502
//		
//		// scrollview itself
//		Log.v("DragDrop", "CustomScrollView : getLeft : ---> " + getLeft());											// 0
//		Log.v("DragDrop", "CustomScrollView : getTop : ---> " + getTop());												// 226
//		Log.v("DragDrop", "CustomScrollView : getRight : ---> " + getRight());						 					// 1080
//		Log.v("DragDrop", "CustomScrollView : getBottom : ---> " + getBottom());										// 1728
//		Log.v("DragDrop", "CustomScrollView : getBaseline : ---> " + getBaseline());									// -1
//		
//		getLocationOnScreen(location);
//		Log.v("DragDrop", "CustomScrollView : location[0] : x : ---> " + location[0]);									// 0
//		Log.v("DragDrop", "CustomScrollView : location[1] : y : ---> " + location[1]);									// 226
//		
//		
//		// CustomHomeView : check size of contents (chileview)
//		CustomHomeView chv = (CustomHomeView) getChildAt(0);
//		Log.i("DragDrop", "CustomHomeView : getChildCount : ---> " + chv.getChildCount());								// 1
//		int totalWidth = getChildAt(0).getWidth();
//		int totalHeight = getChildAt(0).getHeight();
//		Log.i("DragDrop", "CustomHomeView : width : ---> " + totalWidth);												// 1080
//		Log.i("DragDrop", "CustomHomeView : height : ---> " + totalHeight);												// 4204 ( bottom(2702) + 1502 )
//		
//		Log.i("DragDrop", "CustomHomeView : getLeft : ---> " + chv.getLeft());											// 0
//		Log.i("DragDrop", "CustomHomeView : getTop : ---> " + chv.getTop());											// 0
//		Log.i("DragDrop", "CustomHomeView : getRight : ---> " + chv.getRight());						 				// 1080
//		Log.i("DragDrop", "CustomHomeView : getBottom : ---> " + chv.getBottom());										// 4204
//		Log.i("DragDrop", "CustomHomeView : getBaseline : ---> " + chv.getBaseline());									// -1
//		
//		getLocationOnScreen(location);
//		Log.i("DragDrop", "CustomScrollView : location[0] : x : ---> " + location[0]);									// 0
//		Log.i("DragDrop", "CustomScrollView : location[1] : y : ---> " + location[1]);									// 226
//		
//		// Computes the coordinates of this view on the screen.
//		if(draggingView != null) {
//			Log.v("DragDrop", "draggingView width : ---> " + draggingView.getWidth());									// (first : 300
//			Log.v("DragDrop", "draggingView height : ---> " + draggingView.getHeight());								// (first : 300
//			
//			Log.v("DragDrop", "draggingView : getLeft : ---> " + draggingView.getLeft());								// (first : -3
//			Log.v("DragDrop", "draggingView : getTop : ---> " + draggingView.getTop());									// (first : -23
//			Log.v("DragDrop", "draggingView : getRight : ---> " + draggingView.getRight());						 		// (first : 297
//			Log.v("DragDrop", "draggingView : getBottom : ---> " + draggingView.getBottom());							// (first : 277
//			Log.v("DragDrop", "draggingView : getBaseline : ---> " + draggingView.getBaseline());						// -1
//			
//			draggingView.getLocationOnScreen(location);
//			Log.v("DragDrop", "draggingView : location[0] : x : ---> " + location[0]);									// (first : -2
//			Log.v("DragDrop", "draggingView : location[1] : y : ---> " + location[1]);									// (first : 203
//			
//		}
		
		super.scrollTo(x, y);
	}
	
	

    
    
    
    
}
