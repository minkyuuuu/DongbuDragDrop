package com.dongbusec.customhome;

public interface Constant {
	
	String TAG = "DragDrop";
	
	public interface Type {
		int TYPE_1x1 = 11;
		int TYPE_2x1 = 21;
		int TYPE_2x2 = 22;
		int TYPE_3x2 = 32;
		int TYPE_4x2 = 42;
	}
	
	public interface CellType {
		int cellType_Single = 1;
		int cellType_Double = 2;
	}
	
	Integer SIZE_ONE 	= 148;
	Integer SIZE_TWO 	= 304;
	Integer SIZE_THREE 	= 460;
	Integer SIZE_FOUR 	= 616;
	
	int POSITION_X_0 = 12;
	int POSITION_X_78 = 168;
	int POSITION_X_156 = 324;
	int POSITION_X_234 = 480;
	int POSITION_Y_4 = 0;
	int POSITION_Y_82 = 156;
	
	int POSITION_TOTAL_SINGLE_CELL = 156;
	int POSITION_TOTAL_DOUBLE_CELL = 312;
	
	int CELL_GAP = 8;				// 셀과 셀사이 간격(위와 아래, 양쪽사이)		iPhone : 4
	int CELL_GAP_END = 12;			// 오른쪽과 왼쪽끝의 간격이다.
	int SINGLE_CELL_HEIGHT = 148;	// iPhone : 74
	int DOUBLE_CELL_HEIGHT = 304;	// iPhone : 152
	int SHOWDOW_WIDTH = 20;
	int SHOWDOW_HEIGHT = 20;
	
	String KEY_PATTERNINFO = "patterninfo";
	
	String KEY_CUSTOMHOME = "customhome";
	String KEY_CELLTYPE = "patterninfo";
	String KEY_ITEMS = "patterninfo";
	String KEY_RECT = "patterninfo";
	String KEY_TYPE = "patterninfo";
	String KEY_MODULETYPE = "patterninfo";
	String KEY_ITEMCODE = "patterninfo";
	
}
