package com.game.mymagictower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

/** 角色类 */
@SuppressLint("UseSparseArrays")
public class CHero {
	// ==================================================================
	// ========================== 成员变量 ================================
	public final String[] 	m_heroProperty	= { "等级", "生命", "攻击", "防御", "经验", "金币" };
	public final String[] 	m_heroThings	= { "黄钥匙", "蓝钥匙", "红钥匙", "万能钥匙" };
	
	public 	final int 		m_iconId 		= R.drawable.icon; 	 // 状态栏人物图片
	private final int		m_ImageId 		= R.drawable.hero16; // 人物图片的整合图片
	private final int		m_numState 		= 14;	// 人物总共的动作状态数
	private int				m_currentState 	= 0;		
	
	public int					m_currentHeroState 	= 0;	// 当前主角的状态值
	public static final int		HEROSTATE_FACE 		= 0;	// 正面
	public static final int		HEROSTATE_GOLEFT 	= 1;	// 向左
	public static final int		HEROSTATE_GORIGHT 	= 2;	// 向右
	public static final int		HEROSTATE_GOBACK 	= 3;	// 向后
	public static final int		HEROSTATE_ATTR 		= 4;	// 攻击
	
	private  	Bitmap[]	m_heroBitmap_face 	= null;		// 主角正面的图片
	private	 	Bitmap[] 	m_heroBitmap_left 	= null;		// 主角向左的图片
	private  	Bitmap[] 	m_heroBitmap_right 	= null;		// 主角向右的图片
	private  	Bitmap[] 	m_heroBitmap_back 	= null;		// 主角背面的图片
	private  	Bitmap[] 	m_heroBitmap_attr 	= null;		// 主角打斗的图片

	public 	int 	m_heroState 	= 0; 	// 当前人物的图片
	private int 	m_grade			= 1; 	// 人物等级
	private int 	m_life 			= 550; 	// 人物生命值
	private int 	m_attr 			= 60; 	// 人物攻击值
	private int 	m_defense 		= 30; 	// 人物防御值
//	private int	 	m_MagicDefense 	= 1; 	// 人物魔防值
	private int 	m_Exp 			= 0; 	// 经验值
	private int 	m_money 		= 100; 	// 金钱
	private int 	m_keyNum_yellow = 0; 	// 黄色钥匙数
	private int 	m_keyNum_blue 	= 0; 	// 蓝色钥匙数
	private int 	m_keyNum_red 	= 0; 	// 红色钥匙数
	private int 	m_keyNum_All 	= 0; 	// 万能钥匙数
	public 	int		m_x 			= 5;	// 人物X坐标
	public 	int		m_y				= 6; 	// 人物Y坐标

	public SparseArray<Integer> 	m_mapHeroProperty_Data 	= new SparseArray<Integer>();
	public SparseArray<Integer> 	m_mapHeroThings_Data 	= new SparseArray<Integer>();
	public static final int MAPKEY_GRADE 		= 1;
	public static final int MAPKEY_LIFE 		= 2;
	public static final int MAPKEY_ATTR 		= 3;
	public static final int MAPKEY_DEFENSE 		= 4;
	public static final int MAPKEY_MAGICDEFEN 	= 5;
	public static final int MAPKEY_EXP 			= 6;
	public static final int MAPKEY_MONEY 		= 7;
	public static final int MAPKEY_YELLOWKEY 	= 8;
	public static final int MAPKEY_BLUEKEY 		= 9;
	public static final int MAPKEY_REDKEY 		= 10;
	public static final int MAPKEY_ALLKEY 		= 11;

	// ==================================================================
	// ========================== 成员函数 ================================
	public CHero(Context v_context){
		createHeroBitmap(v_context);
		m_currentHeroState = HEROSTATE_FACE;
		
		// ---- 初始化状态值 ----
		m_mapHeroProperty_Data.put(MAPKEY_GRADE, m_grade);
		m_mapHeroProperty_Data.put(MAPKEY_LIFE, m_life);
		m_mapHeroProperty_Data.put(MAPKEY_ATTR, m_attr);
		m_mapHeroProperty_Data.put(MAPKEY_DEFENSE, m_defense);
		m_mapHeroProperty_Data.put(MAPKEY_EXP, m_Exp);
		m_mapHeroProperty_Data.put(MAPKEY_MONEY, m_money);
		
		m_mapHeroThings_Data.put(MAPKEY_YELLOWKEY, m_keyNum_yellow);
		m_mapHeroThings_Data.put(MAPKEY_BLUEKEY, m_keyNum_blue);
		m_mapHeroThings_Data.put(MAPKEY_REDKEY, m_keyNum_red);
		m_mapHeroThings_Data.put(MAPKEY_ALLKEY, m_keyNum_All);
	}
	private void createHeroBitmap(Context v_context){
		Bitmap t_bitmap = CPublic.CreateBitmap(v_context, m_ImageId, CGameData.SIZEUNIT_GAMEVIEW*m_numState, CGameData.SIZEUNIT_GAMEVIEW);
		
		int t_x = 0;
		// ---- 正面图片 ----
		m_heroBitmap_face = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_face[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- 向左图片 ----
		m_heroBitmap_left = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_left[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- 向右图片 ----
		m_heroBitmap_right = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_right[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- 向后图片 ----
		m_heroBitmap_back = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_back[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- 打斗图片 ----
		m_heroBitmap_attr = new Bitmap[2];
		for(int i=0; i<2; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_attr[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		t_bitmap.recycle();
		t_bitmap = null;
	}
	/**
	 * 根据角色状态返回相应图片
	 * 	@param 	v_state ：
	   	@param 	CHero.HEROSTATE_FACE 正面
		@param 	CHero.HEROSTATE_GOLEFT 向左
		@param	CHero.HEROSTATE_GORIGHT 向右
		@param	CHero.HEROSTATE_GOBACK 向后
		@param	CHero.HEROSTATE_ATTR 攻击
	 * @return 返回相应图片Bitmap */
	public Bitmap getHeroStateBitmap(int v_state){
		Bitmap t_resultBitmap = null;
		switch(v_state){
			case HEROSTATE_FACE:
				t_resultBitmap = m_heroBitmap_face[(m_currentState++) % m_heroBitmap_face.length];
				break;
			case HEROSTATE_GOLEFT:
				t_resultBitmap = m_heroBitmap_left[(m_currentState++) % m_heroBitmap_left.length];
				break;
			case HEROSTATE_GORIGHT:
				t_resultBitmap = m_heroBitmap_right[(m_currentState++) % m_heroBitmap_right.length];
				break;
			case HEROSTATE_GOBACK:
				t_resultBitmap = m_heroBitmap_back[(m_currentState++) % m_heroBitmap_back.length];
				break;
			case HEROSTATE_ATTR:
				t_resultBitmap = m_heroBitmap_attr[(m_currentState++) % m_heroBitmap_attr.length];
				break;
		}
		return t_resultBitmap;
	}
	/** 释放资源 */
	public void releaseData(){
		if(m_heroBitmap_face != null)
			for(int i=0; i<m_heroBitmap_face.length; i++){
				if(m_heroBitmap_face[i] == null) continue;
				if(m_heroBitmap_face[i].isRecycled() == true) continue;
				
				m_heroBitmap_face[i].recycle();
			}
		if(m_heroBitmap_left != null)
			for(int i=0; i<m_heroBitmap_left.length; i++){
				if(m_heroBitmap_left[i] == null) continue;
				if(m_heroBitmap_left[i].isRecycled() == true) continue;
				
				m_heroBitmap_left[i].recycle();
			}
		if(m_heroBitmap_right != null)
			for(int i=0; i<m_heroBitmap_right.length; i++){
				if(m_heroBitmap_right[i] == null) continue;
				if(m_heroBitmap_right[i].isRecycled() == true) continue;
				
				m_heroBitmap_right[i].recycle();
			}
		if(m_heroBitmap_back != null)
			for(int i=0; i<m_heroBitmap_back.length; i++){
				if(m_heroBitmap_back[i] == null) continue;
				if(m_heroBitmap_back[i].isRecycled() == true) continue;
				
				m_heroBitmap_back[i].recycle();
			}
		if(m_heroBitmap_attr != null)
			for(int i=0; i<m_heroBitmap_attr.length; i++){
				if(m_heroBitmap_attr[i] == null) continue;
				if(m_heroBitmap_attr[i].isRecycled() == true) continue;
				
				m_heroBitmap_attr[i].recycle();
			}
	}
	// ==================================================================
	// ==================================================================
}
