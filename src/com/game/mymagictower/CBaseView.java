package com.game.mymagictower;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;

/** ���н���Ļ��� */
public abstract class CBaseView extends SurfaceView implements SurfaceHolder.Callback {
	protected Context 	m_context = null;
	
	// ==================================================================
	// ========================= ��Ա���� =================================
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
	// ========================== �麯�� =================================
	/**
	 * �滭View
	 * @param v_canvas ---- onDraw�Ĳ���  */
	public abstract void DrawView(Canvas v_canvas);

	/**
	 * ������������¼�
	 * @param v_event ---- onTouchEvent�Ĳ��� */
	protected abstract boolean HandleTouchEvent(MotionEvent v_event);

	/** ��ʼ����Դ���� */
	protected abstract void InitData();

	/** �ͷ���Դ���� */
	protected abstract void ReleaseData();
	// ==================================================================
	// ==================================================================
}
