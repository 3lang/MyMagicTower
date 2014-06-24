package com.game.mymagictower;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;

/** 地图类 */
public class CMap {
	// ==================================================================
	// ========================== 成员变量 ================================
	private final int				m_mapId = R.drawable.map16;
	private WeakReference<Bitmap> 	m_weakBackg = null;
	
	private Context		m_context 			= null;	
	public 	int 		m_currentMapIndex 	= 1; 	// 当前关卡数
	public 	int 		m_xIndex 			= 0;	// 当前X坐标的索引号
	public 	int 		m_yIndex 			= 0;	// 当前Y坐标的索引号
	private	Bitmap[][]	m_currentMapBitmap	= null;	// 当前地图的图像bitmap，从m_currentMap解析出来
	
	public 	final int	m_mapColumns = 11;
	public 	final int	m_mapRows = 11;
	private int[][]		m_currentMap 		= {	{9,1,1,1,1,11,1,1,1,1,9},
												{9,1,1,1,1,1,1,1,1,1,9},
												{9,1,1,1,1,1,1,1,1,1,9},
												{9,6,1,1,1,1,1,1,1,1,9},
												{9,6,6,1,1,1,1,1,1,6,9},
												{9,6,6,1,1,1,1,1,6,6,9},
												{9,9,6,6,6,1,6,6,6,9,9},
												{9,9,9,9,9,13,9,9,9,9,9},
												{8,9,8,9,1,1,1,9,8,9,8},
												{8,8,8,8,8,1,8,8,8,8,8},
												{8,8,8,8,8,1,8,8,8,8,8}};
	
	public int[][]		m_mapFloor			= { {0,1,1,1,1,3,1,1,1,1,0},
												{0,1,1,1,1,1,1,1,1,1,0},
												{0,1,1,1,1,1,1,1,1,1,0},
												{0,0,1,1,1,1,1,1,1,1,0},
												{0,0,0,1,1,1,1,1,1,0,0},
												{0,0,0,1,1,1,1,1,0,0,0},
												{0,0,0,0,0,1,0,0,0,0,0},
												{0,0,0,0,0,4,0,0,0,0,0},
												{0,0,0,0,1,1,1,0,0,0,0},
												{0,0,0,0,0,1,0,0,0,0,0},
												{0,0,0,0,0,1,0,0,0,0,0}};
	// ==================================================================
	// ========================== 成员变量 ================================
	public CMap(Context v_context,int v_mapIndex){
		try{
			m_context = v_context; 
			m_currentMapIndex = v_mapIndex;
	
			if(m_weakBackg==null || m_weakBackg.get()==null)
				m_weakBackg = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context, m_mapId,
						11*CGameData.SIZEUNIT_GAMEVIEW, 12*CGameData.SIZEUNIT_GAMEVIEW));
			
			if(m_currentMap == null) return;
			
			Bitmap 	t_bitmap = m_weakBackg.get();
			int 	t_index = 0;
			int 	t_x = 0;
			int 	t_y = 0;
			
			m_currentMapBitmap = new Bitmap[m_mapRows][m_mapColumns];
			
			for(int t_row=0; t_row<m_currentMap.length; t_row++){
				for(int t_col=0; t_col<m_currentMap[t_row].length; t_col++){
					t_index = m_currentMap[t_row][t_col];
					t_y = t_index/m_mapColumns;
					t_x = t_index%m_mapColumns;
					
					m_currentMapBitmap[t_row][t_col] = Bitmap.createBitmap(t_bitmap, t_x*CGameData.SIZEUNIT_GAMEVIEW,
							t_y*CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/** 返回地图图像数据 */
	public Bitmap[][] getMapBitmap(){
		return m_currentMapBitmap;
	}
	/** 释放资源 */
	public void releaseData(){
		for(int r=0; m_currentMapBitmap!=null && r<m_currentMapBitmap.length; r++){
			for(int c=0; m_currentMapBitmap[r]!=null && c<m_currentMapBitmap[r].length; c++){
				if(m_currentMapBitmap[r][c] == null) continue;
				if(m_currentMapBitmap[r][c].isRecycled() == true) continue;
				
				m_currentMapBitmap[r][c].recycle();
			}
		}
	}
	// ==================================================================
	// ==================================================================
}
