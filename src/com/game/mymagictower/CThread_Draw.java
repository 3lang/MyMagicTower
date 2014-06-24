package com.game.mymagictower;

import android.view.SurfaceHolder;
import android.graphics.Canvas;

/** 绘制界面的线程类 */
public class CThread_Draw extends Thread {
	// ==================================================================
	// ========================== 成员函数 ================================
	private final int 	SLEEPTIME 		= 200;
	private CBaseView 	m_contentView 	= null;
	private boolean 	m_bRunFlag 		= false;

	// ==================================================================
	// ========================== 成员函数 ================================
	/** 打开线程任务开关 */
	public void setRunFlag(boolean vbRunFlag) {
		m_bRunFlag = vbRunFlag;
	}
	
	/**
	 * 根据界面的索引值，设置线程要显示刷新的界面View
	 * 	@param vIndex  界面的索引值：
	 	@param CPublic.INDEX_WELCOMEVIEW（欢迎界面）
	 */
	public void setContentView(CBaseView v_contentView) {
		m_contentView = v_contentView;
	}

	@Override
	public void run() {
		SurfaceHolder t_holder = null;
		Canvas t_CanvasDraw = null;
		while (m_bRunFlag == true) {
			try {
				t_holder = m_contentView.getHolder();
				t_CanvasDraw = t_holder.lockCanvas();
				synchronized (t_holder) {
					m_contentView.DrawView(t_CanvasDraw);
				}
				Thread.sleep(SLEEPTIME);
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (t_CanvasDraw != null)
					t_holder.unlockCanvasAndPost(t_CanvasDraw);
			}
		}
	}
	// ==================================================================
	// ==================================================================
}
