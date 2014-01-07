package com.dongbusec.customhome;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.dongbusec.customhome.bean.CustomHomeInfo;
import com.dongbusec.customhome.bean.CustomHomeInfo.CustomHome;
import com.dongbusec.customhome.bean.CustomHomeInfo.Item;
import com.dongbusec.customhome.bean.PatternInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CustomHomeManager {
	Context context;

	public CustomHomeManager(Context context) {
		this.context = context;
		loadData();
	}

	private void loadData() {
		String contentsData = "";
		try {
			contentsData = readText("CustomHome.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Gson gson = new Gson();
//        Type type = new TypeToken<CustomHomeData>() {}.getType();
//        CustomHomeData contentDictionary = gson.fromJson(contentsData, type);
//        
//        ArrayList<CustomHomeInfo> mArrayData = contentDictionary.getCustomhome();
//        PatternInfo mDictionaryPatternData = contentDictionary.getPatterninfo();
        
        
        // success : test1.dat
//        Type type = new TypeToken<HashMap<String, DictionaryPatternData>>(){}.getType();
//        HashMap<String, DictionaryPatternData> mDictionaryPatternData = gson.fromJson(contentsData, type);
//        DictionaryPatternData a = mDictionaryPatternData.get("11");
//        ArrayList<String> s = a.getPatterns1();
//        ArrayList<String> s2 = a.getPatterns2();
        
        // success : test1.dat
        JsonObject object = new JsonParser().parse(contentsData).getAsJsonObject();
        JsonElement p = object.get("patterninfo"); // application 
        JsonElement c = object.get("customhome"); // 0.1.0
        
        Type type = new TypeToken<HashMap<String, PatternInfo>>(){}.getType();
        HashMap<String, PatternInfo> mPatternInfo = gson.fromJson(p, type);
        PatternInfo aa = mPatternInfo.get("11");
        ArrayList<String> s = aa.getPatterns1();
        ArrayList<String> s2 = aa.getPatterns2();
        
        type = new TypeToken<ArrayList<CustomHome>>() {}.getType();
        //CustomHomeInfo customHomeInfo = gson.fromJson(c, type);
        ArrayList<CustomHome> customHome = gson.fromJson(c, type);
        CustomHome cc = customHome.get(0);
        ArrayList<Item> item = cc.getItems();
        String celltype = cc.getCelltype();
        
        Item ii = item.get(0);
        String rect = ii.getRect();
        
        
        
        
        
        
        
        // fail : test1.dat
//        Type type = new TypeToken<PatternInfo>(){}.getType();
//        PatternInfo mDictionaryPatternData = gson.fromJson(contentsData, type);
//        HashMap<String, DictionaryPatternData> hh = mDictionaryPatternData.getmDictionaryPatternData();
//        DictionaryPatternData a = hh.get("11");
//        ArrayList<String> s = a.getPatterns1();
//        ArrayList<String> s2 = a.getPatterns2();
        
        
        
		
	}

	private String readText(String file) throws IOException {
		InputStream is = context.getAssets().open(file);

		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();

		String text = new String(buffer);
		Log.v("DragDrop", "test data : " + text);

		return text;
	}
}
