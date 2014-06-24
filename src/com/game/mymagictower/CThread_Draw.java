package com.game.mymagictower;

import android.view.SurfaceHolder;
import android.graphics.Canvas;

/** ���ƽ�����߳��� */
public class CThread_Draw extends Thread {
	// ==================================================================
	// ========================== ��Ա���� ================================
	private final int 	SLEEPTIME 		= 200;
	private CBaseView 	m_contentView 	= null;
	private boolean 	m_bRunFlag 		= false;

	// ==================================================================
	// ========================== ��Ա���� ================================
	/** ���߳����񿪹� */
	public void setRunFlag(boolean vbRunFlag) {
		m_bRunFlag = vbRunFlag;
	}
	
	/**
	 * ���ݽ��������ֵ�������߳�Ҫ��ʾˢ�µĽ���View
	 * 	@param vIndex  ���������ֵ��
	 	@param CPublic.INDEX_WELCOMEVIEW����ӭ���棩
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
