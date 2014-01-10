package com.dongbusec.customhome;

import com.dongbusec.corelib.util.ResourceManager;

import android.content.Context;
import android.view.View;

public class CustomTileView extends View {

	/**
	 * @param context
	 * @param type
	 *            셀 크기종류 : 11,21,22,32,42
	 * @param moduleType
	 * @param itemCode
	 */
	public CustomTileView(Context context, int type, String moduleType, String itemCode) {
		super(context);
		getTileView(type, moduleType, itemCode);
	}

	private CustomTileView getTileView(int type, String moduleType, String itemCode) {
		switch (type) {
		case Constant.Type.TYPE_1x1:
			make1x1view(moduleType, itemCode);
			break;

		case Constant.Type.TYPE_2x1:
			make2x1view(moduleType, itemCode);
			break;

		case Constant.Type.TYPE_2x2:
			make2x2view(moduleType, itemCode);
			break;
			
		case Constant.Type.TYPE_3x2:
			make3x2view(moduleType, itemCode);
			break;

		case Constant.Type.TYPE_4x2:
			make4x2view(moduleType, itemCode);
			break;

		default:
			break;
		}
		
		return this;
	}

	private void make1x1view(String moduleType, String itemCode) {
		setBack(moduleType);
	}

	private void make2x1view(String moduleType, String itemCode) {
		setBack(moduleType);
	}

	private void make2x2view(String moduleType, String itemCode) {
		setBack(moduleType);
	}

	private void make3x2view(String moduleType, String itemCode) {
		setBack(moduleType);
	}

	private void make4x2view(String moduleType, String itemCode) {
		setBack(moduleType);
	}
	
	private void setBack(String moduleType) {
		if(moduleType.equals("0")) {
			setBackgroundDrawable(ResourceManager.getSingleImage("frame_box_red.9"));
		}
		if(moduleType.equals("1")) {
			setBackgroundDrawable(ResourceManager.getSingleImage("frame_box_red.9"));
		}
		if(moduleType.equals("2")) {
			setBackgroundDrawable(ResourceManager.getSingleImage("frame_box_orange.9"));
		}
		if(moduleType.equals("3")) {
			setBackgroundDrawable(ResourceManager.getSingleImage("frame_box_green01.9"));
		}
	}

}
