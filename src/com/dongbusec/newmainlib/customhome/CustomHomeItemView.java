package com.dongbusec.newmainlib.customhome;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.dongbusec.corelib.util.LayoutUtil;
import com.dongbusec.corelib.util.ResourceManager;

public class CustomHomeItemView extends BaseView {
	
	ImageView searchIcon, changeboxIcon, deleteIcon, palmIcon;

	public CustomHomeItemView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		searchIcon = new ImageView(context);
		changeboxIcon = new ImageView(context);
		deleteIcon = new ImageView(context);
		palmIcon = new ImageView(context);
		
		searchIcon.setBackgroundDrawable(ResourceManager.getImage("btn_search02"));
		changeboxIcon.setBackgroundDrawable(ResourceManager.getImage("btn_change"));
		
		deleteIcon.setBackgroundDrawable(ResourceManager.getImage("btn_del03"));
		palmIcon.setBackgroundDrawable(ResourceManager.getImage("btn_move"));
		
		setOnLongClickListener(this);
	}

	/**
	 * 자식 창 만드는 함수
	 * 먼저 배경에 해당하는 tileView를 만들어 이것을 배경으로 설정한다.
	 * CustomHomeView에서 기본 생성자를 통해 만든 이 객체에 여러가지 정보를 셋팅한다. (화면에서의 위치관련 값(rowIndex), type, moduletype, ...)
	 */
	public void loadTileView(Context context) {

		View itemView = new CustomTileView(context, Integer.parseInt(type), moduleType, itemCode);
		Drawable drawable = itemView.getBackground();
		this.setBackgroundDrawable(drawable);

//		itemView.buildDrawingCache();
//		Bitmap bm = itemView.getDrawingCache();
	}
	
	/**
	 * 화면에 배치될 때 한 번만 처리된다. 그 이후는 내부 정보만 달라진다.???
	 * 아니다. 같은 type일  moduleType이 변경될 수 있다.
	 * 
	 * 
	 * 화면에 보여줄 데이타를 가져와서 적당한 위치에 보여준다. (type, moduletype에 따라 배치가 달라진다.)
	 * type, moduletype, itemcode에 따른 각각의 위치가 있을것임...
	 * 
	 * 1) 기본아이콘(돋보기 아이콘, 박스변경아이콘, 삭제아이콘, 손바다아이콘) 배치
	 * 2) get data from network
	 * 3) deploy data
	 * 
	 * moduleType : 0(지수), 1(종목), 2(매매동향), 3(링크)
	 */
	public void loadData(Context context) {
		// 기본요소 배치
		if(type.equals("11")) deployIcon_11();
		if(type.equals("21")) deployIcon_21();
		if(type.equals("22")) deployIcon_22();
		if(type.equals("32")) deployIcon_32();
		if(type.equals("42")) deployIcon_42();
		
		if(isEditMode) {
			searchIcon.setVisibility(INVISIBLE);
			changeboxIcon.setVisibility(INVISIBLE);
			deleteIcon.setVisibility(INVISIBLE);
			palmIcon.setVisibility(INVISIBLE);
			
			// test
			if(!type.equals("11")) {
				if(moduleType.equals("2") || moduleType.equals("3")) {
					changeboxIcon.setVisibility(VISIBLE);
				}
				else {
					searchIcon.setVisibility(VISIBLE);
					deleteIcon.setVisibility(VISIBLE);
				}
				
				palmIcon.setVisibility(VISIBLE);
			}
				
		}

		if(moduleType.equals("0") || moduleType.equals("1")) {	// 지수, 종목 
			if(type.equals("11")) deployData_0_11();
			if(type.equals("21")) deployData_0_21();
			if(type.equals("22")) deployData_0_22();
			if(type.equals("32")) deployData_0_32();
			if(type.equals("42")) deployData_0_42();
		}
		
		if(moduleType.equals("2")) {							// 매매동향 
			if(type.equals("11")) deployData_2_11();
			if(type.equals("22")) deployData_2_22();
			if(type.equals("42")) deployData_2_42();
		}
		
		if(moduleType.equals("3")) {							// 링크
			if(type.equals("11")) deployData_3_11();
		}
	}
	
	// ------------------------------------------
	// Basic Icon
	// 돋보기 아이콘, 박스변경아이콘, 삭제아이콘, 손바다아이콘
	// ------------------------------------------
	private void deployIcon_11() {
		LayoutUtil.addChildRetina(this, searchIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 4, 0);
		//LayoutUtil.addChildRetina(this, changeboxIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 20, 8, 0, Gravity.TOP | Gravity.RIGHT);
		
		LayoutUtil.addChildRetina(this, deleteIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 74, 0);
		LayoutUtil.addChildRetina(this, palmIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 6, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
	}
	private void deployIcon_21() {
		LayoutUtil.addChildRetina(this, searchIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 74, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, deleteIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 4, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, palmIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 6, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
	}
	private void deployIcon_22() {
		LayoutUtil.addChildRetina(this, searchIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 74, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, changeboxIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 10, 8, 0, Gravity.TOP | Gravity.RIGHT);
		
		LayoutUtil.addChildRetina(this, deleteIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 4, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, palmIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 0, Gravity.CENTER);
	}
	private void deployIcon_32() {
		LayoutUtil.addChildRetina(this, searchIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 74, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, deleteIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 4, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, palmIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 0, Gravity.CENTER);
	}
	private void deployIcon_42() {
		LayoutUtil.addChildRetina(this, searchIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 74, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, changeboxIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 20, 8, 0, Gravity.TOP | Gravity.RIGHT);
		
		LayoutUtil.addChildRetina(this, deleteIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 4, 0, Gravity.TOP | Gravity.RIGHT);
		LayoutUtil.addChildRetina(this, palmIcon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, 0, Gravity.CENTER);
	}

	// ---------------
	// 지수, 종목
	// ---------------
	private void deployData_0_11() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 20, 100, "ffffff", Gravity.CENTER);
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 30, 0, 0, Gravity.CENTER_HORIZONTAL | Gravity.TOP);
	}
	private void deployData_0_21() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 20, 100, "ffffff", Gravity.CENTER);
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 20, 14, 0, 0);
	}
	private void deployData_0_22() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 20, 100, "ffffff", Gravity.CENTER);
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 26, 20, 0, 0);
	}
	private void deployData_0_32() {
		ImageView logo = new ImageView(mContext);
		logo.setBackgroundDrawable(ResourceManager.getSingleImage("ic_icon"));
		LayoutUtil.addChildRetina(this, logo, 104, 104, 28, 30, 0, 0);
		
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 20, 100, "ffffff", Gravity.CENTER);
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 170, 26, 0, 0);
	}
	private void deployData_0_42() {
		ImageView logo = new ImageView(mContext);
		logo.setBackgroundDrawable(ResourceManager.getSingleImage("ic_icon"));
		LayoutUtil.addChildRetina(this, logo, 104, 104, 28, 30, 0, 0);
		
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 22, 100, "ffffff", Gravity.CENTER);	// 22 size
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 170, 26, 0, 0);
	}
	
	// ---------------
	// 매매동향 
	// ---------------
	private void deployData_2_11() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 18, 100, "ffffff", Gravity.CENTER);	// 18 size
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 18, 0, 0, Gravity.CENTER_HORIZONTAL | Gravity.TOP);
	}
	private void deployData_2_22() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 26, 100, "ffffff", Gravity.CENTER);	// 26 size
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 20, 24, 0, 0);
	}
	private void deployData_2_42() {
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 36, 100, "ffffff", Gravity.CENTER);	// 36 size
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30, 28, 0, 0);
	}

	// ---------------
	// 링크
	// ---------------
	private void deployData_3_11() {
		String str = "";
		if(itemCode.equalsIgnoreCase("주식잔고")) str = "ico_link01";
		if(itemCode.equalsIgnoreCase("가판뉴스")) str = "ico_link02";
		if(itemCode.equalsIgnoreCase("리서치")) str = "ico_link03";
		if(itemCode.equalsIgnoreCase("테마&섹터")) str = "ico_link04";
		if(itemCode.equalsIgnoreCase("DOMA")) str = "ico_link05";
			
		ImageView icon = new ImageView(mContext);
		icon.setBackgroundDrawable(ResourceManager.getImage(str));
		LayoutUtil.addChildRetina(this, icon, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 18, 0, 0, Gravity.CENTER_HORIZONTAL | Gravity.TOP);
		
        TextView tv = new TextView(mContext);
        LayoutUtil.setFont(tv, 1, Typeface.BOLD, 22, 100, "ffffff", Gravity.CENTER);	// 22 size
        tv.setText(itemCode);
        LayoutUtil.addChildRetina(this, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 104, 0, 0, Gravity.CENTER_HORIZONTAL | Gravity.TOP);
	}

}
