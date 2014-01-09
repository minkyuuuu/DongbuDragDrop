package com.dongbusec.customhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Rect;
import android.widget.FrameLayout;

import com.dongbusec.corelib.util.LayoutUtil;
import com.dongbusec.customhome.bean.CustomHome;
import com.dongbusec.customhome.bean.CustomHome.Item;

/**
 * 기본적으로 커스텀홈에서 사용되는 클래스, 변수, 메소드등의 이름은 아이폰에서 사용한 이름을 사용함.
 *
 */
public class CustomHomeView extends FrameLayout {
	ArrayList<CustomHome> mArrayData;	// 모든 row의 배치 정보
	ArrayList<HashMap<String, Object>> mArrayChildView;
	CustomHomeManager mCustomManager;

	public CustomHomeView(Context context) {
		super(context);
        initChild(context);
        startInitialize(context);
	}
	
    private void initChild(Context context)
    {
    	CustomHomeManager cm = CustomHomeManager.getInstance(context);
    	mArrayData = cm.getData();
    	mArrayChildView = new ArrayList<HashMap<String, Object>>();
    	
    	mCustomManager = CustomHomeManager.getInstance(context); 
    }

    private void startInitialize(Context context)
    {
    	makeChild(context);
    }

	/**
	 * 자식 창 만드는 함수
	 */
	private void makeChild(Context context) {
    	LayoutUtil.noCacheAnimation(this);
    	
		ArrayList<CustomHomeItemView> childArray = new ArrayList<CustomHomeItemView>();
		HashMap<String, Object> childDictionary = new HashMap<String, Object>();
		Integer totalHeight = 0; // tileView가 위치를 y값 
		ArrayList<Integer> cellHeight = new ArrayList<Integer>();
    	for(int i = 0; i < mArrayData.size(); i++) {
    		ArrayList<Item> dataArray = mArrayData.get(i).getItems();	// 하나의 row 정보, 안드로이드에서는 클래스로 처리 getItems(), 아이폰에서는 getItems()대신 맵으로 처리
    		String cellType = mArrayData.get(i).getCelltype();
			if(cellType.equals("1")) cellHeight.add(Constant.POSITION_TOTAL_SINGLE_CELL);
			if(cellType.equals("2")) cellHeight.add(Constant.POSITION_TOTAL_DOUBLE_CELL);
			
    		if(i != 0) {
    			totalHeight += cellHeight.get(i-1);
    		}
    		
    		childArray.clear();			//	하나의 row에 해당하는 CustomHomeItemView를 담는 배열 
    		childDictionary.clear();	//  전체 row에 해당하는 CustomHomeItemView를 담는 배
    		
    		String rectString = "";
    		int width, height, x, y;
    		
    		for(int j = 0; j < dataArray.size(); j++) {
    			Item itemData = dataArray.get(j);	// 각각 row안의 tileview 정보들..., 아이폰에서는 맵으로 처리...
    			
    			// rect에 대한 처리
    			// 아이폰에서는 배치도에서 사용한 {{0,4},{74,74}} 이 포맷을 그대로 사용한다. 아이폰에서 설계했으니 당연하다... CGRect()
    			// 안드로이드에서는 Rect으로 변환하여 사용해야한다. Rect(left, top, right, bottom)
    			// 변환하는 시점은 json 파싱할 때가 아니구 CustomHomeItemView를 생성할 때 내부 필드에 등록하면 될 것 같다.
    			rectString = itemData.getRect();
    			CustomHomeItemView itemView = makeView(context);
    			itemView.setRowIndex(i);	// row 위치 
    			itemView.setTag(j);			// row 안에서의 순서?
    			
    			itemView.setType(itemData.getType());				// 11,21,22,32,42 
    			itemView.setModuleType(itemData.getModuletype());	// 0,1,2,3 (지수, 종목, 매매동향, 링크)
    			itemView.setItemCode(itemData.getItemcode());		// 화면에 보여질 실제 데이타를 위한 code...
    			
    			itemView.setPattern(mCustomManager.getPatternString(itemData.getType(), cellType, rectString));
    			Rect rect = covertToRect(rectString);
    			itemView.setLocalRect(rect);
    			
    			itemView.loadChildChildView(context);	// 바탕이될 CustomTileView를 생성한다.
    			childArray.add(itemView);
    			
    			// 만든 CustomHomeItemView 위치와 크기를 계산하여 화면에 그려준다.
    			width = itemView.getItemViewWidth();
    			height = itemView.getItemViewHeight();
    			
    			x = itemView.getX();
    			y = itemView.getY();
    			LayoutUtil.addChildRetina(this, itemView, width, height, x, totalHeight + y);
    		}
    		
    		childDictionary.put(Constant.KEY_ITEMS, childArray);
    		childDictionary.put(Constant.KEY_RECT, rectString);
    		childDictionary.put(Constant.KEY_CELLTYPE, cellType);
    		
    		mArrayChildView.add(childDictionary);	// childDictionary : 중복되는게 아닌지... CustomHomeItemView안에 rect와 celltype이 있는데... 굳이 가져야하나...
    	}
	}

	private Rect covertToRect(String rectString) {
		String str = rectString.replaceAll("\\{", "");
		str = str.replaceAll("\\}", "");
		str = str.replaceAll(" ", "");
		String [] delimiter = str.split(",");
		
		int left = Integer.parseInt(delimiter[0]);
		int top = Integer.parseInt(delimiter[1]);
		int right = left + Integer.parseInt(delimiter[2]);
		int bottom = top + Integer.parseInt(delimiter[3]);
				
		return new Rect(left, top, right, bottom);
	}

	private CustomHomeItemView makeView(Context context) {
		CustomHomeItemView itemView = new CustomHomeItemView(context);
		
		if(itemView != null) return itemView;
		return null;
	}
	
	/**
	 * celltype 반환 
	 * @param rect
	 * @return "1" or "2"
	 */
	private String cellTypeStringForRect(Rect rect) {
		if(rect.height() <= Constant.SINGLE_CELL_HEIGHT + Constant.CELL_GAP) return String.format("%d", Constant.CellType.cellType_Single);
		return String.format("%d", Constant.CellType.cellType_Single);
	}

}
