package com.game.mymagictower;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/** 主界面的Activity窗口 */
public class Activity_Main extends Activity {
	// ==================================================================
	// ========================== 成员变量 ================================
	private CThread_Draw 	m_Thread_Draw 	= null;
	private CBaseView 		m_contentView 	= null;	// 当前界面View
	private int				m_indexView		= 0; 	// 当前界面View的索引值

	// ==================================================================
	// ========================== 成员函数 ================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	protected void onPause(){
		super.onPause();
		m_Thread_Draw.setRunFlag(false);
	}
	@Override
	protected void onResume(){
		super.onResume();
		
		CPublic.InitData(this);

		m_Thread_Draw = new CThread_Draw();
		m_Thread_Draw.setRunFlag(true);
		CPublic.ThreadPool_Draw.execute(m_Thread_Draw);

		CPublic.Handler_ChangeView = new CPublic.MyHandler(this) {
			@Override
			public void handleMessage(Message v_msg) {
				try {
					m_indexView = v_msg.what;
					Activity_Main.this.handleView(m_indexView);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		};
		CPublic.Handler_ChangeView.sendEmptyMessage(CPublic.INDEX_WELCOMEVIEW);
	}
	/**
	 * 根据界面的索引值，设置线程要显示刷新的界面View
	 * @param v_index 界面的索引值：
	 * @param CPublic.INDEX_WELCOMEVIEW（欢迎界面）
	 * @param CPublic.INDEX_GAMEVIEW（游戏界面）
	 * @param CPublic.INDEX_LOADGAME（读取游戏）
	 * @param CPublic.INDEX_HELP（帮助界面）
	 * @param CPublic.INDEX_ABOUNT（关于界面）
	 * @param CPublic.INDEX_EXIT（退出）*/
	private void handleView(int v_index) {
		if (v_index == CPublic.INDEX_WELCOMEVIEW)
			m_contentView = new CWelcomeView(this);
		else if (v_index == CPublic.INDEX_GAMEVIEW)
			m_contentView = new CGameView(this);
		else if (v_index == CPublic.INDEX_EXIT) {
			m_Thread_Draw.setRunFlag(false);
			this.finish();
		}
		if (m_contentView == null) return;

		m_Thread_Draw.setContentView(m_contentView);
		this.setContentView(m_contentView);
	}
	@Override
	public boolean onKeyDown (int v_keyCode, KeyEvent v_event){
		if(v_keyCode == KeyEvent.KEYCODE_BACK){
			// ---- 提示是否退出游戏 ----
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("提示");
			builder.setMessage("你确定要退出游戏？");
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					System.exit(0);
				}
			});
			builder.create().show();
			return false;
		}
		return super.onKeyDown(v_keyCode, v_event);
	}
	// ==================================================================
	// ==================================================================
}
