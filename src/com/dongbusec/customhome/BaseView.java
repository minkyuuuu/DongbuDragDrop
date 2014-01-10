package com.dongbusec.customhome;

import android.content.Context;
import android.graphics.Rect;
import android.widget.FrameLayout;

public class BaseView extends FrameLayout{
	Context mContext;
	
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
	
	boolean isEditMode = true;
	
	public BaseView(Context context) {
		super(context);
		mContext = context;
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
	
	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}


}
