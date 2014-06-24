package com.game.mapeditor;

import com.game.mymagictower.CPublic;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/** ��ͼ�ı༭�� */
@SuppressLint("DrawAllocation")
public class CMapEditorView extends View {
	// ==================================================================
	// ========================== ��Ա���� ================================	
	private	Activity_MapEditor		m_activity		= null;
	
	// ==================================================================
	// ========================== ��Ա���� ================================
	public CMapEditorView(Context v_context){
		super(v_context);
		setWillNotDraw(false);
		initData(v_context);
	}
	public CMapEditorView(Context v_context, AttributeSet v_attr){
		super(v_context, v_attr);
		setWillNotDraw(false);
		initData(v_context);
	}
	public CMapEditorView(Context v_context, AttributeSet v_attr, int v_style){
		super(v_context, v_attr, v_style);
		setWillNotDraw(false);
		initData(v_context);
	}
	private void initData(Context v_context){
		try{
			m_activity = (Activity_MapEditor)v_context;
		}catch(Exception e){}
	}
	@Override
	public void onMeasure(int v_width, int v_heiht){
		
		v_width = CPublic.SCREENSIZE.widthPixels*2;
		v_heiht = CPublic.SCREENSIZE.heightPixels*2;
		
		setMeasuredDimension(v_width, v_heiht);
	} 
	@Override
	public void onDraw(Canvas v_canvas){
		v_canvas.drawText("����", this.getLeft(), this.getTop(), new Paint());
		v_canvas.drawText("����", this.getRight()-30, this.getTop(), new Paint());
		v_canvas.drawText("����", this.getLeft(), this.getBottom()-30, new Paint());
		v_canvas.drawText("����", this.getRight()-30, this.getBottom()-30, new Paint());
		v_canvas.drawText("�м䷳�ú�ˬ��ˮ�ľ��ѺͿ��ԵĽɷѺ󾡿����", this.getRight()/2, this.getBottom()/2, new Paint());
		
		if(m_activity != null){
			int size_floorMap = (m_activity.m_vector_floorMap==null)? 0:m_activity.m_vector_floorMap.size();
			int index_bitmap = 0;
			for(int i=0; i<size_floorMap; i++){
				index_bitmap = m_activity.m_vector_floorMap.get(i).m_value;
				if(index_bitmap < m_activity.m_bitmapArray.length)
					v_canvas.drawBitmap(m_activity.m_bitmapArray[index_bitmap],
							m_activity.m_vector_floorMap.get(i).m_x*m_activity.m_widthImage_listView,
							m_activity.m_vector_floorMap.get(i).m_y*m_activity.m_widthImage_listView, new Paint());
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent v_event){
		// ---- �������¼����϶�Ԫ�� ----
		return false;
	}
	
	// ==================================================================
	// ==================================================================

}
