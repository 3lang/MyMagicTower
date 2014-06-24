package com.game.mapeditor;

import com.game.mymagictower.CPublic;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

/** 地图元素的选择列表 */
public class CMapListItemView extends View{
	// ==================================================================
	// ========================== 成员变量 ================================
	private final String			TAG 			= "CMapListItemView";
	private	Activity_MapEditor		m_activity		= null;
	
	// ==================================================================
	// ========================== 成员函数 ================================
	public CMapListItemView(Context v_context){
		super(v_context);
		try{
			m_activity = (Activity_MapEditor)v_context;
		}catch(Exception e){}
	}
	public CMapListItemView(Context v_context, AttributeSet v_attr){
		super(v_context, v_attr);
		try{
			m_activity = (Activity_MapEditor)v_context;
		}catch(Exception e){}
	}
	public CMapListItemView(Context v_context, AttributeSet v_attr, int v_style){
		super(v_context, v_attr, v_style);
		try{
			m_activity = (Activity_MapEditor)v_context;
		}catch(Exception e){}
	}
	@Override
	protected void onDraw(Canvas v_canvas) {
		Log.i(TAG, "Width="+String.valueOf(this.getWidth()) +
				"   Height="+String.valueOf(this.getHeight()));
		Log.i(TAG, "Measure Width="+String.valueOf(this.getMeasuredWidth()) +
				"   Measure Height="+String.valueOf(this.getMeasuredHeight()));
		try{
			for(int i=0; i<m_activity.m_mapLastIndex; i++){
				v_canvas.drawBitmap(m_activity.m_bitmapArray[i], m_activity.m_pointImage[i].x, m_activity.m_pointImage[i].y, null);
			}
		}catch(Exception e){}
	}
	@Override
	public void onMeasure(int v_width, int v_heiht){
		
		v_width = CPublic.SCREENSIZE.widthPixels/4;
		if(m_activity != null){
			if(m_activity.m_pointImage == null)
				v_heiht = CPublic.SCREENSIZE.heightPixels*2;
			else
				v_heiht = m_activity.m_pointImage[m_activity.m_pointImage.length-1].y + m_activity.m_widthImage_listView*3/2;
		}
		setMeasuredDimension(v_width, v_heiht);
	}
	
	@Override
	protected void onDetachedFromWindow (){
		super.onDetachedFromWindow();
	}
	// ==================================================================
	// ==================================================================
}
