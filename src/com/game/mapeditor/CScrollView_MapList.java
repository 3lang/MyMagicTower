package com.game.mapeditor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CScrollView_MapList extends ScrollView{
	// ==============================================================================
	// ========================== ��Ա���� ============================================

	// ==============================================================================
	// ========================== ��Ա���� ============================================
	public CScrollView_MapList(Context v_context) {
		super(v_context);
		this.InitData(v_context);
	}
	public CScrollView_MapList(Context v_context, AttributeSet v_attrs) {
		super(v_context, v_attrs);
		this.InitData(v_context);
	}
	public CScrollView_MapList(Context v_context, AttributeSet v_attrs, int v_defStyle) {
		super(v_context, v_attrs, v_defStyle);
		this.InitData(v_context);
	}
	private void InitData(Context v_context){
		// ====== ��ʼ������ ======
	}
	@Override
	public boolean onTouchEvent(MotionEvent v_event){
		return false;
	}
	
	// ==============================================================================
	// ==============================================================================
}
