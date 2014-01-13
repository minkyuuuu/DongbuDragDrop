package com.dongbusec.customhome;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.dongbusec.customhome.bean.CustomHome;
import com.dongbusec.customhome.bean.PatternInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


/**
 * 기본적으로 커스텀홈에서 사용되는 클래스, 변수, 메소드등의 이름은 아이폰에서 사용한 이름을 사용함.
 * 
 * mDictionaryPatternData 	: 배치를 위한 메타데이타
 * mArrayData 				: 화면 배치도를 배열. (줄 단위의 배치)
 * 
 * @date 2014. 1. 8.
 */
public class CustomHomeManager {
	private static CustomHomeManager customManager = null;
	Context context;
	
	HashMap<String, PatternInfo> mDictionaryPatternData = null;
	ArrayList<CustomHome> mArrayData;
	
	private CustomHomeManager(Context context) {
		loadData(context);
	}

	public static CustomHomeManager getInstance(Context context) {
		if (customManager == null) {
			customManager = new CustomHomeManager(context);
		}

		return customManager;
	}

	/**
	 * 데이터 로딩 함수
	 * 
	 */
	public void loadData(Context context) {
		this.context = context;
		String contentsData = "";	// get string form local or network
		
		try {
			contentsData = readText("CustomHome.dat");
			if(contentsData.equals("")) {
				Log.e(Constant.TAG, "no data");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 데이타 파싱
        Gson gson = new Gson();
        
        JsonObject object = new JsonParser().parse(contentsData).getAsJsonObject();
        JsonElement patterninfoStr = object.get("patterninfo"); 	// 배치를 위한 메타데이타
        JsonElement customhomeStr = object.get("customhome"); 		// 화면 배치도
        
        Type type = new TypeToken<HashMap<String, PatternInfo>>(){}.getType();
        mDictionaryPatternData = gson.fromJson(patterninfoStr, type);
        //Log.v(Constant.TAG, "mDictionaryPatternData : " + mDictionaryPatternData);
        
        type = new TypeToken<ArrayList<CustomHome>>() {}.getType();
        mArrayData = gson.fromJson(customhomeStr, type);
        //Log.v(Constant.TAG, "mArrayData : " + mArrayData);
        updateData(new ArrayList<CustomHome>(mArrayData));
        
	}

	private String readText(String file) throws IOException {
		InputStream is = context.getAssets().open(file);

		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();

		String text = new String(buffer);
		//Log.v(Constant.TAG, "local data : " + text);

		return text;
	}
	
	/**
	 * 데이터 갱신
	 * 이 함수가 필요하나??? 직접 mArrayData를 조작하면 될것 같은데... 저장 함수만 있으면 될것 같다.
	 * @param arrayData : 변경할 데이터
	 */
	public void updateData(ArrayList<CustomHome> arrayData) {
		mArrayData.clear();
		
		mArrayData = new ArrayList<CustomHome>(arrayData);
		//mArrayData = (ArrayList<CustomHome>)arrayData.clone();
		//mArrayData.addAll(arrayData);
	}
	
	/**
	 * 데이터 저장 함수
	 * @param arrayData
	 */
	public void saveData() {
		CommonUtil.setObjectWithKey(mArrayData, Constant.KEY_CUSTOMHOME);
	}
	
	/**
	 * 화면 배치도 배열 반환
	 */
	public ArrayList<CustomHome> getData() {
		return mArrayData;
	}
	
	/**
	 * 셀 타입에 해당하는 패턴 인덱스 배열 반환
	 * @param type : 셀 크기종류 : 11,21,22,32,42
	 * @param celltype : 셀이 소속될 줄의 높이 : 1:single line, 2:doubleline
	 * @return
	 * 예, sizeType=="11") "patterns1":["1", "2" , "3", "4"] or "patterns2":["1", "2" , "3", "4", "5", "6", "7", "8"])
	 * 예, sizeType=="21") "patterns1":["1,2", "2,3", "3,4"] or "patterns2":["1,2", "2,3", "3,4", "5,6", "6,7", "7,8"])
	 */
	public ArrayList<String> getPatterns(String type, String celltype) {
		PatternInfo patternInfo = mDictionaryPatternData.get(type);
		
		ArrayList<String> patterns = null;
		if(Integer.valueOf(celltype) == Constant.CellType.cellType_Single) patterns = patternInfo.getPatterns1();
		if(Integer.valueOf(celltype) == Constant.CellType.cellType_Double) patterns = patternInfo.getPatterns2();
		
		return patterns;
		
	}
	
	/**
	 * 셀 타입(rect)에 해당하는 패턴 중 특정 위치의 패턴 정보 반환
	 * @param type : 셀 크기종류 : 11,21,22,32,42
	 * @param celltype : 셀이 소속될 줄의 높이 : 1:single line, 2:doubleline
	 * @param rect : "patternrects2": "{{0,82},{74,74}}" (안드로이드에서는 rect를 string type으로 처리, 나중에 계산시 Rect type으로 변환)
	 * @return "5" of "patterns2":["1", "2" , "3", "4", "5", "6", "7", "8"]
	 */
	public String getPatternString(String type, String celltype, String rect) {
		String pattern = "";
		
		ArrayList<String> patternArray = getPatterns(type, celltype);
		ArrayList<String> rectArray = getPatternRects(type, celltype);
		String patternRect = "";
		for(int i = 0; i < rectArray.size(); i++ ) {
			patternRect = rectArray.get(i);	// 배치도에서 가져온 정보를 string type으로 저장했음. 아이폰은 CGRect type으로 저장)
			if(patternRect.equals(rect)) pattern = patternArray.get(i);
		}
		
		return pattern;
	}
	
	/**
	 * 셀타입에 해당하는 패턴 rect 중 point를 포함하는 rect를 반환
	 * @param type
	 * @param celltype
	 * @param point
	 * @param isfirst
	 * @return "5"
	 */
	public String patternForPoint(String type, String celltype, String point, boolean isfirst) {
		String pattern = "";
		
		return pattern;
	}
	
	/**
	 * 셀 타입에 해당하는 패턴 rect 배열 반환
	 * @param type : 셀 크기종류 : 11,21,22,32,42
	 * @param celltype : 셀이 소속될 줄의 높이 : 1:single line, 2:doubleline
	 * @return 
	 * 예, sizeType=="11") 	"patternrects1":["{{0,4},{74,74}}", "{{78,4},{74,74}}", "{{156,4},{74,74}}", "{{234,4},{74,74}}"]
	 *         			  	"patternrects2":["{{0,4},{74,74}}", "{{78,4},{74,74}}", "{{156,4},{74,74}}", "{{234,4},{74,74}}",
	 *                                       "{{0,82},{74,74}}", "{{78,82},{74,74}}", "{{156,82},{74,74}}", "{{234,82},{74,74}}"]
	 */
	public ArrayList<String> getPatternRects(String type, String celltype) {
		PatternInfo patternInfo = mDictionaryPatternData.get(type);
		
		ArrayList<String> patternRects = null;
		if(Integer.valueOf(celltype) == Constant.CellType.cellType_Single) patternRects = patternInfo.getPatternrects1();
		if(Integer.valueOf(celltype) == Constant.CellType.cellType_Double) patternRects = patternInfo.getPatternrects2();
		
		return patternRects;
		
	}
	
	/**
	 * 셀 타입에 해당하는 패턴 rect 중 특정 패턴 rect 반환 (여기서 rect는 string type임)
	 * @param type
	 * @param celltype
	 * @param pattern : "patterns2": "3,4"
	 * @return "{{156,4},{152,74}}" of "patternrects1":["{{0,4},{152,74}}", "{{78,4},{152,74}}", "{{156,4},{152,74}}"],
	 */
	public String getPatternRectString(String type, String celltype, String pattern) {
		String patternRect = "";
		
		ArrayList<String> patternArray = getPatterns(type, celltype);
		ArrayList<String> rectArray = getPatternRects(type, celltype);
		String patternString = "";
		for(int i = 0; i < patternArray.size(); i++ ) {
			patternString = patternArray.get(i);
			if(patternString.equals(pattern)) patternRect = rectArray.get(i);
		}
		
		return patternRect;
	}

	/**
	 * 셀타입에 해당하는 패턴 rect 중 point를 포함하는 rect를 반환
	 * @param type
	 * @param celltype
	 * @param point  	패턴 찾을 위치 정보(반드시 전체 화면이 아닌 row내에서의 위치 값 이어야 함) 예) (203,4)
	 * @param isfirst	isfirst 일치하는 정보가 여러 개일 경우 처음 데이터를 쓸것인지 마지막 데이터를 쓸것인지 여부
	 * @return "{{156,4},{152,74}}"
	 */
	public String patternRectForPoint(String type, String celltype, String point, boolean isfirst) {
		String pattern = "";
		
		return pattern;
	}
	
	
	
	
	
	
	
	
}
