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
public class CustomScrollViewTest extends ScrollView {
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
	
	public CustomScrollViewTest(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		mContext = context;
	}
	
  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
      final int action = event.getAction();
		final int x = (int) event.getX();
		final int y = (int) event.getY();	

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e("DragDrop", "onInterceptTouchEvent : ACTION_DOWN : y --> " + y);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
      return super.onInterceptTouchEvent(event);
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
		//itemView.cancelLongPress();

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
//		int left = designX;
//		int top = designY;
		int left = designX - Constant.FRAME_SHADOW_SIZE / 2;
		int top = designY - Constant.FRAME_SHADOW_SIZE / 2;
		
		LayoutUtil.addChildRetina(chv, draggingView, w, h, left, top);
		draggingView.bringToFront();
		
		Log.v("DragDrop", "itemView designX : before layout : " + designX);	// (first : 12), design 수치
		Log.v("DragDrop", "itemView designY : before layout : " + designY);	// (first : 0),  design 수치
		Log.v("DragDrop", "itemView getLeft() : " + itemView.getLeft());	// (first : 20), 상대적 위치
		Log.v("DragDrop", "itemView getTop() : " + itemView.getTop());		// (first : 0),  상대적 위치
		Log.v("DragDrop", "draggingView : left : " + left);					// (first : -3)
		Log.v("DragDrop", "draggingView : top : " + top);					// (first : -15)
		
		Log.v("DragDrop", "itemView : getWidth() : " + itemView.getWidth());	// (first : 249
		Log.v("DragDrop", "itemView : getHeight() : " + itemView.getHeight());	// (first : 250
		Log.v("DragDrop", "draggingView : getWidth() : " + draggingView.getWidth());
		Log.v("DragDrop", "draggingView : getHeight() : " + draggingView.getHeight());
        
        params = (LayoutParams) draggingView.getLayoutParams();
		Log.v("DragDrop", "draggingView leftMargin : after layout : " + params.leftMargin);	// (first : -3)
		Log.v("DragDrop", "draggingView topMargin  : after layout : " + params.topMargin);	// (first : -23)
		Log.v("DragDrop", "draggingView : getLeft() : " + draggingView.getLeft());			// (first : 0) 이유는 아직...
		Log.v("DragDrop", "draggingView : getTop() : " + draggingView.getTop());			// (first : 0)
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
	
	//int dy = 0;
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
	
    final Runnable r = new Runnable()
    {
        public void run() 
        {
        }
    };
    
	/* (non-Javadoc)
	 * 단순한 flicking에 의한 scroll시에는 실행되지 않구...
	 * draggingView가 drag될 때, 즉 ScrollView의 자식뷰의 위치나 크기가 변할 때 발생된다.
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.w("DragDrop", "onLayout");
//		//limitBottomY = computeVerticalScrollRange() - getHeight(); // 2702 = 4204 - 1502, 크기가 변하면 다시 계산해주어야한다.
//		screenLimitTopY = getTop();
//		screenLimitBottomY = getBottom();
		if(draggingView != null) {
			Log.i("DragDrop", "draggingView : getWidth() : " + draggingView.getWidth());	// 300
			Log.i("DragDrop", "draggingView : getHeight() : " + draggingView.getHeight());	// 300
			Log.v("DragDrop", "draggingView : getLeft() : " + draggingView.getLeft());		// -3
			Log.v("DragDrop", "draggingView : getTop() : " + draggingView.getTop());		// -23
		}
		
		super.onLayout(changed, l, t, r, b);
	}

	/* (non-Javadoc)
	 * 스크롤이 끝나는 시점을 알수 있는 이벤트 콜백 함수 
	 */
//	@Override
//    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
//        super.onScrollChanged(x, y, oldx, oldy);
////        Log.w("DragDrop", "CustomScrollView : onScrollChanged() : y --> " + y);
////        Log.v("DragDrop", "CustomScrollView : onScrollChanged() : oldy --> " + oldy);
//    }
	
//	@Override
//	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		Log.w("DragDrop", "CustomScrollView : onSizeChanged() : w --> " + w);
////		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : h --> " + h);
////		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : oldw --> " + oldw);
////		Log.v("DragDrop", "CustomScrollView : onSizeChanged() : oldh --> " + oldh);
//		super.onSizeChanged(w, h, oldw, oldh);
//	}
	
	/* (non-Javadoc)
	 * 단순한 flicking에 의한 scroll시에는 실행되지 않구...
	 * draggingView가 drag될 때 scrollBy()에 의한 scroll시에의해 실행된다.
	 */
//	@Override
//	public void scrollTo(int x, int y) {
//		Log.v("DragDrop", "CustomScrollView : scrollTo() : x --> " + x);
//		Log.e("DragDrop", "CustomScrollView : scrollTo() : y --> " + y);
		
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
//		
//		super.scrollTo(x, y);
//	}
	
	

    
    
    
    
}
