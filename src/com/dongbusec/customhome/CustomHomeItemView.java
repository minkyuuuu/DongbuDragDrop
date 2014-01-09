package com.dongbusec.customhome;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class CustomHomeItemView extends View {

	String type = "";
	String moduleType = "";
	String itemCode = "";

	String pattern = "";	
	int rowIndex = -1;
	Rect originalRect = new Rect();
	Rect localRect = new Rect();
	boolean isDragging = false;
	
	int width;
	int height;
	int x;
	int y;

	public CustomHomeItemView(Context context) {
		super(context);
		initChild(context);
	}

	private void initChild(Context context) {
	}

	/**
	 * 자식 창 만드는 함수
	 * 먼저 배경에 해당하는 tileView를 만들어 이것을 배경으로 설정한다.
	 * CustomHomeView에서 기본 생성자를 통해 만든 이 객체에 여러가지 정보를 셋팅한다. (화면에서의 위치관련 값(rowIndex), type, moduletype, ...)
	 */
	public void loadChildChildView(Context context) {

		View itemView = new CustomTileView(context, Integer.parseInt(type), moduleType, itemCode);
		Drawable drawable = itemView.getBackground();
		this.setBackgroundDrawable(drawable);

//		itemView.buildDrawingCache();
//		Bitmap bm = itemView.getDrawingCache();
	}
	
	public void setWidth(int type) {
		switch(type) {
        case Constant.Type.TYPE_1x1:
            width = Constant.SIZE_ONE;
            break;
            
        case Constant.Type.TYPE_2x1:
        	width = Constant.SIZE_TWO;
        	break;
            
        case Constant.Type.TYPE_2x2:
        	width = Constant.SIZE_TWO;
        	break;
            
        case Constant.Type.TYPE_3x2:
        	width = Constant.SIZE_THREE;
        	break;
            
        case Constant.Type.TYPE_4x2:
        	width = Constant.SIZE_FOUR;
        	break;
            
        default:
            break;
		}
	}
	
	public void setHeight(int type) {
		switch(type) {
		case Constant.Type.TYPE_1x1:
		case Constant.Type.TYPE_2x1:
			height = Constant.SIZE_ONE;
			break;
			
		case Constant.Type.TYPE_2x2:
		case Constant.Type.TYPE_3x2:
		case Constant.Type.TYPE_4x2:
			height = Constant.SIZE_TWO;
			break;
			
		default:
			break;
		}
	}
	
	public int getX() {
		int x = 12;
		
		if(localRect.left == 0) x = Constant.POSITION_X_0;
		if(localRect.left == 78) x = Constant.POSITION_X_78;
		if(localRect.left == 156) x = Constant.POSITION_X_156;
		if(localRect.left == 234) x = Constant.POSITION_X_234;
			
		return x;
	}
	
	public int getY() {
		int y = 0;
		
		if(localRect.top == 4) y = Constant.POSITION_Y_4;
		if(localRect.top == 82) y = Constant.POSITION_Y_82;
			
		return y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		setWidth(Integer.parseInt(type));
		setHeight(Integer.parseInt(type));
	}
	
	public int getItemViewWidth() {
		return width;
	}
	public int getItemViewHeight() {
		return height;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Rect getOriginalRect() {
		return originalRect;
	}

	public void setOriginalRect(Rect originalRect) {
		this.originalRect = originalRect;
	}

	public Rect getLocalRect() {
		return localRect;
	}

	public void setLocalRect(Rect localRect) {
		this.localRect = localRect;
	}

	public boolean isDragging() {
		return isDragging;
	}

	public void setDragging(boolean isDragging) {
		this.isDragging = isDragging;
	}

}
