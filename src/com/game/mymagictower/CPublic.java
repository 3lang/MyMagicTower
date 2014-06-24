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

/** 全局变量、函数类 */
public class CPublic {
	// ==================================================================
	// ========================= 全局变量 =================================
	public static final ExecutorService ThreadPool_Draw = Executors.newSingleThreadExecutor(); // 线程池
				
	public static MyHandler 	Handler_ChangeView 	= null;
	public static int			VALUE_ADD 			= 40;	// 在Y坐标上的补偿值
	
	public static final int 	INDEX_WELCOMEVIEW 	= 1;
	public static final int 	INDEX_GAMEVIEW 		= 2;
	public static final int 	INDEX_LOADGAME 		= 3;
	public static final int 	INDEX_HELP 			= 4;
	public static final int 	INDEX_ABOUNT 		= 5;
	public static final int 	INDEX_EXIT 			= 6;
	
	public static final DisplayMetrics SCREENSIZE = new DisplayMetrics();

	// ---- 地图底层属性 ----
	public static final int		MAPFLOOR_ROAD 		= 1; 	// 道路
	public static final int		MAPFLOOR_DOOR	 	= 4; 	// 门
	public static final int 	MAPFLOOR_STAIR_DOWN = 2;	// 楼梯-向下
	public static final int 	MAPFLOOR_STAIR_UP 	= 3;	// 楼梯-向上
	// ==================================================================
	// ========================= 全局函数 =================================
	/**
	 * 初始化必须数据
	 * @param v_Context ---- Activity对象指针*/
	public static void InitData(Context v_Context) {
		if (v_Context == null) return;

		((Activity) v_Context).getWindowManager().getDefaultDisplay().getMetrics(SCREENSIZE);
		VALUE_ADD = (int)(40*(float)CPublic.SCREENSIZE.heightPixels/720);
	}
	/**
	 * 返回对应横分辨率下的像素值
	 * @param v_width 原像素值
	 * @return 返回对应值  */
	public static int retureWidth(int v_width){
		return (int)(v_width*(float)CPublic.SCREENSIZE.widthPixels/1184);
	}
	/**
	 * 返回对应竖分辨率下的像素值
	 * @param v_width 原像素值
	 * @return 返回对应值  */
	public static int retureHeight(int v_height){
		return (int)(v_height*(float)CPublic.SCREENSIZE.heightPixels/720);
	}
	/**
	 * 获取指定大小的图片Bitmap
	 * @param v_Context ---- Activity对象指针
	 * @param v_SrcID ---- 图片资源ID
	 * @param v_DesWidth ---- 指定图片的宽度
	 * @param v_DesHeight ---- 指定图片的高度 */
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
			// ---- 弱引用 ----
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
	// ========================= 自定义类 =================================
	/** 自定义消息处理类 */
	public static class MyHandler extends Handler {
		private WeakReference<Context> m_Weak = null;

		/** 将Activity类对象指针入参，加入到弱引用，通过getContext()函数获取 */
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
