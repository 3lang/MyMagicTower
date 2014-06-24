package com.game.mymagictower;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;

/** 所有界面的基类 */
public abstract class CBaseView extends SurfaceView implements SurfaceHolder.Callback {
	protected Context 	m_context = null;
	
	// ==================================================================
	// ========================= 成员函数 =================================
	public CBaseView(Context v_context) {
		super(v_context);
		m_context = v_context;
		this.getHolder().addCallback(this);
	}
	public CBaseView(Context v_context, AttributeSet v_attr){
		super(v_context, v_attr);
		m_context = v_context;
		this.getHolder().addCallback(this);
	}
	public CBaseView(Context v_context, AttributeSet v_attr, int v_style){
		super(v_context, v_attr, v_style);
		m_context = v_context;
		this.getHolder().addCallback(this);
	}
	@Override
	public boolean onTouchEvent(MotionEvent v_event) {
		return HandleTouchEvent(v_event);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		InitData();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ReleaseData();
	}

	// ==================================================================
	// ========================== 虚函数 =================================
	/**
	 * 绘画View
	 * @param v_canvas ---- onDraw的参数  */
	public abstract void DrawView(Canvas v_canvas);

	/**
	 * 处理触碰界面的事件
	 * @param v_event ---- onTouchEvent的参数 */
	protected abstract boolean HandleTouchEvent(MotionEvent v_event);

	/** 初始化资源变量 */
	protected abstract void InitData();

	/** 释放资源变量 */
	protected abstract void ReleaseData();
	// ==================================================================
	// ==================================================================
}
