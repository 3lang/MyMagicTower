package com.game.mymagictower;

import com.game.mapeditor.Activity_MapEditor;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class CWelcomeView extends CBaseView {
	// ==================================================================
	// ========================= 成员变量 =================================
	private final String[] 	MENUTITLE 	= { "开始游戏", "读取游戏", "帮助", "地图编辑器", "退出" };
	private final int	 	STARTGAME 	= 0;
	private final int 		LOADGAME 	= 1;
	private final int 		HELP 		= 2;
	private final int 		EDITOR 		= 3;
	private final int 		EXIT 		= 4;

	private float 		m_fFontSize 			= CPublic.retureHeight(35);
	private Point[] 	m_pointStart_MenuItem 	= null;
	private Point[] 	m_pointEnd_MenuItem 	= null;
	private Bitmap 		m_welcomeImage 			= null;
	private int 		m_yStart		 		= 0;

	// ==================================================================
	// ========================= 成员函数 =================================
	public CWelcomeView(Context context) {
		super(context);
	}

	@Override
	public void DrawView(Canvas vCanvas) {
		// TODO Auto-generated method stub
		try {
			vCanvas.drawBitmap(m_welcomeImage, (CPublic.SCREENSIZE.widthPixels - m_welcomeImage.getWidth()) / 2, m_yStart, null);

			Paint t_paint = new Paint();
			t_paint.setColor(Color.WHITE);
			t_paint.setTextSize(m_fFontSize);
			
			for (int i = 0; i < MENUTITLE.length; i++) {
				vCanvas.drawText(MENUTITLE[i], m_pointStart_MenuItem[i].x, m_pointStart_MenuItem[i].y, t_paint);
			}
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	protected boolean HandleTouchEvent(MotionEvent v_event) {
		// TODO Auto-generated method stub
		if(v_event.getAction() == MotionEvent.ACTION_UP){
			int t_x = (int) v_event.getX();
			int t_y = (int) v_event.getY() + CPublic.VALUE_ADD;
	
			for (int i = 0; i < MENUTITLE.length; i++) {
				if ((t_x >= m_pointStart_MenuItem[i].x && t_x <= m_pointEnd_MenuItem[i].x)
						&& (t_y >= m_pointStart_MenuItem[i].y && t_y <= m_pointEnd_MenuItem[i].y)) {
					HandleEvent(i);
					break;
				}
			}
			return false;
		}
		return true;
	}

	private void HandleEvent(int i) {
		switch (i){
			case STARTGAME:
				CPublic.Handler_ChangeView.sendEmptyMessage(CPublic.INDEX_GAMEVIEW);
				break;
			case LOADGAME:
				break;
			case HELP:
				break;
			case EDITOR:{
				Intent t_intent = new Intent(m_context, Activity_MapEditor.class);
				m_context.startActivity(t_intent);
				break;
			}
			case EXIT:
				CPublic.Handler_ChangeView.sendEmptyMessage(CPublic.INDEX_EXIT);
				break;
			default:
				break;
		}
	}

	@Override
	protected void InitData() {
		// TODO Auto-generated method stub
		// ---- 根据屏幕大小计算适当的菜单项高度 ----
		int t_height = CPublic.SCREENSIZE.heightPixels / (MENUTITLE.length + 6);
		int t_space = (CPublic.SCREENSIZE.heightPixels - t_height*(MENUTITLE.length + 2))/ (MENUTITLE.length + 2);
		m_welcomeImage = CPublic.CreateBitmap(m_context, R.drawable.menu, 0, t_height * 2);

		// ---- 初始化菜单项坐标 ----
		m_yStart = t_space;

		m_pointStart_MenuItem = new Point[MENUTITLE.length];
		m_pointEnd_MenuItem = new Point[MENUTITLE.length];

		Paint t_paint = new Paint();
		t_paint.setColor(Color.WHITE);
		t_paint.setTextSize(m_fFontSize);

		Rect t_bounds = new Rect();
		int t_y = m_yStart + m_welcomeImage.getHeight() + t_space;

		for (int i = 0; i < MENUTITLE.length; i++) {
			t_paint.getTextBounds(MENUTITLE[i], 0, MENUTITLE[i].length(), t_bounds);

			if (t_height != 0 && t_height < t_bounds.height()) {
				// ---- 如果原本设定的字体大小形成的文本高度太大，则对字体大小进行调整 ----
				m_fFontSize *= (float) t_height / t_bounds.height();
				t_bounds.bottom = t_bounds.top + t_height;
				t_height = 0; // 只做一次字体大小的调整
			}

			m_pointStart_MenuItem[i] = new Point();
			m_pointStart_MenuItem[i].x = (CPublic.SCREENSIZE.widthPixels - t_bounds.width()) / 2;
			m_pointStart_MenuItem[i].y = t_y;

			m_pointEnd_MenuItem[i] = new Point();
			m_pointEnd_MenuItem[i].x = m_pointStart_MenuItem[i].x + t_bounds.width();
			m_pointEnd_MenuItem[i].y = m_pointStart_MenuItem[i].y + t_bounds.height();

			t_y += (t_bounds.height() + t_space);
		}
	}

	@Override
	protected void ReleaseData() {
		// TODO Auto-generated method stub
		if (m_welcomeImage != null && m_welcomeImage.isRecycled() != true) {
			m_welcomeImage.recycle();
			m_welcomeImage = null;
		}
		m_pointStart_MenuItem = null;
		m_pointEnd_MenuItem = null;
	}
	// ==================================================================
	// ==================================================================
}
