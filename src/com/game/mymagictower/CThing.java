package com.game.mymagictower;

import java.lang.ref.WeakReference;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

/** 地图上的所有物体 */
public class CThing {
	// ==================================================================
	// ========================== 成员变量 ================================
	public static final String 		PRO_ENEMY			= "enemy";				// 敌人
	public static final String		PRO_THING_ATTR		= "thing_attr";			// +/-攻击值
	public static final String		PRO_THING_DEF		= "thing_def";			// +/-防御值
	public static final String		PRO_THING_LIFE		= "thing_life";			// +/-生命值
	public static final String		PRO_THING_REDKEY	= "thing_redkey";		// +红钥匙
	public static final String		PRO_THING_YELLOWKEY	= "thing_yellowkey";	// +黄钥匙
	public static final String		PRO_THING_BLUEKEY	= "thing_bluekey";		// +蓝钥匙
	public static final String		PRO_BUSINESS		= "businessman";		// 商人
	public static final String		PRO_NPC				= "npc";				// NPC
	
	private final int		m_mapId 		= R.drawable.map16;
	private final int		m_beginIndex 	= 18;
	private final int		m_imageColumns 	= 11;
	private final int		m_imgaeRows 	= 12;
	
	public 	Vector<MapThing>		m_mapThings 	= null;	// 地图上的所有物体 
	private WeakReference<Bitmap> 	m_weakBackg 	= null;	// 所有物体的集合图片
	
	// ==================================================================
	// ========================== 成员函数 ================================
	/** 创建地图上的物体
	 * 	@param v_mapIndex ---第几关 */
	public CThing(Context v_context, int v_mapIndex){

		if(m_weakBackg==null || m_weakBackg.get()==null)
			m_weakBackg = new WeakReference<Bitmap>(CPublic.CreateBitmap(v_context, m_mapId,
					m_imageColumns*CGameData.SIZEUNIT_GAMEVIEW, m_imgaeRows*CGameData.SIZEUNIT_GAMEVIEW));
		
		m_mapThings = new Vector<MapThing>();
		
		// ---- 初始化所有物体数据 ----
		m_mapThings.add(new MapThing(11, new Point(3,3), 0, 0, 0, PRO_THING_ATTR, 50));
		m_mapThings.add(new MapThing(14, new Point(4,3), 0, 0, 0, PRO_THING_DEF, 30));
		m_mapThings.add(new MapThing(4, new Point(5,3), 0, 0, 0, PRO_THING_LIFE, 100));
		m_mapThings.add(new MapThing(6, new Point(6,3), 0, 0, 0, PRO_THING_LIFE, -50));
		m_mapThings.add(new MapThing(2, new Point(7,3), 0, 0, 0, PRO_THING_REDKEY, 1));
		m_mapThings.add(new MapThing(0, new Point(8,3), 0, 0, 0, PRO_THING_YELLOWKEY, 1));
		m_mapThings.add(new MapThing(1, new Point(3,4), 0, 0, 0, PRO_THING_BLUEKEY, 1));
		m_mapThings.add(new MapThing(44, new Point(4,4), 300, 50, 30, PRO_ENEMY, 0));
		m_mapThings.add(new MapThing(54, new Point(6,4), 500, 70, 20, PRO_ENEMY, 0));
		m_mapThings.add(new MapThing(55, new Point(7,4), 600, 80, 40, PRO_ENEMY, 0));
		m_mapThings.add(new MapThing(56, new Point(5,4), 1000, 120, 90, PRO_ENEMY, 0));
	}
	
	// ==================================================================
	// ========================== 自定义类 ================================
	public class MapThing{
		public	Bitmap	m_bitmapThing	= null;	// 图片
		public	Point	m_pointThing	= null;	// 坐标
		public	int		m_life			= 0;	// 生命值：怪物
		public	int		m_attr			= 0;	// 攻击值：怪物
		public	int		m_defense		= 0;	// 防御值：怪物
		public	String	m_property		= "";	// 属性：怪物/物品/NPC……
		public	int		m_proValue		= 0;	// 属性加值：+攻击/防御/血量……
		
		public 	MapThing(int v_resId, Point v_point, int v_life, int v_attr, int v_defense,
				String v_property, int v_proValue){
			
			int t_index = m_beginIndex+v_resId;
			int t_col = (t_index)%m_imageColumns;
			int t_row = (t_index)/m_imageColumns;
			m_bitmapThing = Bitmap.createBitmap(m_weakBackg.get(), t_col*CGameData.SIZEUNIT_GAMEVIEW,
					t_row*CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
			
			m_pointThing = v_point;
			m_life = v_life;
			m_attr = v_attr;
			m_defense = v_defense;
			m_property = v_property;
			m_proValue = v_proValue;
		}
	} 
	// ==================================================================
	// ==================================================================
}
