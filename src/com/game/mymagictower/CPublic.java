package com.game.mymagictower;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.content.Context;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/** ȫ�ֱ����������� */
public class CPublic {
	// ==================================================================
	// ========================= ȫ�ֱ��� =================================
	public static final ExecutorService ThreadPool_Draw = Executors.newSingleThreadExecutor(); // �̳߳�
				
	public static MyHandler 	Handler_ChangeView 	= null;
	public static int			VALUE_ADD 			= 40;	// ��Y�����ϵĲ���ֵ
	
	public static final int 	INDEX_WELCOMEVIEW 	= 1;
	public static final int 	INDEX_GAMEVIEW 		= 2;
	public static final int 	INDEX_LOADGAME 		= 3;
	public static final int 	INDEX_HELP 			= 4;
	public static final int 	INDEX_ABOUNT 		= 5;
	public static final int 	INDEX_EXIT 			= 6;
	
	public static final DisplayMetrics SCREENSIZE = new DisplayMetrics();

	// ---- ��ͼ�ײ����� ----
	public static final int		MAPFLOOR_ROAD 		= 1; 	// ��·
	public static final int		MAPFLOOR_DOOR	 	= 4; 	// ��
	public static final int 	MAPFLOOR_STAIR_DOWN = 2;	// ¥��-����
	public static final int 	MAPFLOOR_STAIR_UP 	= 3;	// ¥��-����
	// ==================================================================
	// ========================= ȫ�ֺ��� =================================
	/**
	 * ��ʼ����������
	 * @param v_Context ---- Activity����ָ��*/
	public static void InitData(Context v_Context) {
		if (v_Context == null) return;

		((Activity) v_Context).getWindowManager().getDefaultDisplay().getMetrics(SCREENSIZE);
		VALUE_ADD = (int)(40*(float)CPublic.SCREENSIZE.heightPixels/720);
	}
	/**
	 * ���ض�Ӧ��ֱ����µ�����ֵ
	 * @param v_width ԭ����ֵ
	 * @return ���ض�Ӧֵ  */
	public static int retureWidth(int v_width){
		return (int)(v_width*(float)CPublic.SCREENSIZE.widthPixels/1184);
	}
	/**
	 * ���ض�Ӧ���ֱ����µ�����ֵ
	 * @param v_width ԭ����ֵ
	 * @return ���ض�Ӧֵ  */
	public static int retureHeight(int v_height){
		return (int)(v_height*(float)CPublic.SCREENSIZE.heightPixels/720);
	}
	/**
	 * ��ȡָ����С��ͼƬBitmap
	 * @param v_Context ---- Activity����ָ��
	 * @param v_SrcID ---- ͼƬ��ԴID
	 * @param v_DesWidth ---- ָ��ͼƬ�Ŀ��
	 * @param v_DesHeight ---- ָ��ͼƬ�ĸ߶� */
	public static Bitmap CreateBitmap(Context v_Context, int v_SrcID, int v_DesWidth, int v_DesHeight) {
		try {
			Options t_option = new Options();
			t_option.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(v_Context.getResources(), v_SrcID, t_option);

			int t_Width = t_option.outWidth;
			int t_Height = t_option.outHeight;

			boolean t_bWrap = false;
			if (v_DesWidth == 0 || v_DesHeight == 0)
				t_bWrap = true;

			v_DesWidth = (v_DesWidth == 0) ? t_Width : v_DesWidth;
			v_DesHeight = (v_DesHeight == 0) ? t_Height : v_DesHeight;

			float t_ScaleW = (float) (t_Width) / v_DesWidth;
			float t_ScaleH = (float) (t_Height) / v_DesHeight;
			float t_Scale = Math.max(t_ScaleW, t_ScaleH);

			t_option.inJustDecodeBounds = false;
			t_option.inSampleSize = (int) t_Scale;
			// ---- ������ ----
			WeakReference<Bitmap> t_Weak = new WeakReference<Bitmap>(
					BitmapFactory.decodeResource(v_Context.getResources(), v_SrcID, t_option));
			if (t_bWrap == true)
				return t_Weak.get();
			else
				return Bitmap.createScaledBitmap(t_Weak.get(), v_DesWidth,v_DesHeight, true);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ==================================================================
	// ========================= �Զ����� =================================
	/** �Զ�����Ϣ������ */
	public static class MyHandler extends Handler {
		private WeakReference<Context> m_Weak = null;

		/** ��Activity�����ָ����Σ����뵽�����ã�ͨ��getContext()������ȡ */
		public MyHandler(Context v_context) {
			m_Weak = new WeakReference<Context>(v_context);
		}

		public Context getContext() {
			return (m_Weak == null) ? null : m_Weak.get();
		}
	}

	// ==================================================================
	// ==================================================================
}
