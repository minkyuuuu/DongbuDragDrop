package com.dongbusec.customhome;

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
	public CustomTileView(Context context, int type, int moduleType, String itemCode) {
		super(context);
		getTileView(type, moduleType, itemCode);
	}

	private CustomTileView getTileView(int type, int moduleType, String itemCode) {
		switch(type) {
        case Constant.Type.TYPE_1x1:
            return make1x1view(moduleType,itemCode);
            
        case Constant.Type.TYPE_2x1:
        	return make2x1view(moduleType,itemCode);
            
        case Constant.Type.TYPE_2x2:
        	return make2x2view(moduleType,itemCode);
            
        case Constant.Type.TYPE_3x2:
        	return make3x2view(moduleType,itemCode);
            
        case Constant.Type.TYPE_4x2:
        	return make4x2view(moduleType,itemCode);
            
        default:
            break;
		}
		
		return null;
	}

	private CustomTileView make4x2view(int moduleType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private CustomTileView make3x2view(int moduleType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private CustomTileView make2x2view(int moduleType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private CustomTileView make2x1view(int moduleType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private CustomTileView make1x1view(int moduleType, String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
