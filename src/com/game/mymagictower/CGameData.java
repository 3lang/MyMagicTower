package com.game.mymagictower;

import java.util.Vector;

import android.content.Context;

/** ��Ϸ��������ݴ����� */
public class CGameData {
	// ==================================================================
	// ========================= ��Ա���� =================================
	public static final int 	UP 		= 0; // �������
	public static final int 	RIGHT 	= 1; // �������
	public static final int		DOWN 	= 2; // �������
	public static final int		LEFT 	= 3; // �������
	
	public final static int 	SIZEUNIT_BG 		= CPublic.SCREENSIZE.heightPixels / 15; // ��Ϸ�����ĵ�Ԫ��С
	public final static int		SIZEUNIT_GAMEVIEW 	= (int)(SIZEUNIT_BG*1.3f);				// ��Ϸ���ڵĵ�Ԫ��С
	
	public CHero 		m_hero				= null; // Ӣ�������
	public CMap 		m_map 				= null; // ��ͼ�����
	public CThing		m_things			= null;	// ���������
	public int			m_widthGameView 	= 0;	// ��Ϸ���ڵĿ��
	public int			m_heightGameView 	= 0;	// ��Ϸ���ڵĸ߶�
	public int			m_columnsGameView 	= 0;	// ��Ϸ���ڵ�����
	public int			m_rowGameView		= 0;	// ��Ϸ���ڵ�����
	
	private int			m_lastStateOfHero	= -1;
	private int			m_indexOfEnemy		= -1;
	private int			m_devalueOfEnemy	= 0;
	private	int			m_devalueOfHero		= 0;
	
	// ==================================================================
	// ========================= ��Ա���� ================================
	public CGameData(Context v_context){
		m_hero = new CHero(v_context);
		m_map = new CMap(v_context, 1);
		m_things = new CThing(v_context, 1);
	}
	/** �ͷ���Դ */
	public void releaseData(){
		m_hero.releaseData();
		m_map.releaseData();
	}
	/** �޸��������� */
	public void ChangeDataOfHeroAndEnemy(){
		if(m_things == null)								return;
		if(m_things.m_mapThings == null)					return;
		if(m_indexOfEnemy == -1)							return;
		if(m_indexOfEnemy >= m_things.m_mapThings.size())	return;
		
		if(m_hero.m_mapHeroProperty_Data.get(CHero.MAPKEY_LIFE) <= 0) {
			// ---- ��������ֵΪ0��ս�� ----
			m_indexOfEnemy = -1;
			m_devalueOfEnemy = 0;
			m_devalueOfHero = 0;
			return;
		} else if(m_things.m_mapThings.get(m_indexOfEnemy).m_life <= 0){
			// ---- ��������ֵΪ0����ʧ ----
			m_hero.m_currentHeroState = m_lastStateOfHero;
			m_things.m_mapThings.remove(m_indexOfEnemy);
			m_indexOfEnemy = -1;
			m_devalueOfEnemy = 0;
			m_devalueOfHero = 0;
			
			// ---- ���Ӿ��� ----
			int t_index = m_hero.m_mapHeroProperty_Data.indexOfKey(CHero.MAPKEY_EXP);
			int t_value = m_hero.m_mapHeroProperty_Data.valueAt(t_index) + 10;
			m_hero.m_mapHeroProperty_Data.setValueAt(t_index, t_value);
			
		} else {
			// ---- ���ݹ����ͷ����������Ǻ͵��˵�����ֵ ----
			int t_index = m_hero.m_mapHeroProperty_Data.indexOfKey(CHero.MAPKEY_LIFE);
			int t_value = m_hero.m_mapHeroProperty_Data.valueAt(t_index) - m_devalueOfHero;
			m_hero.m_mapHeroProperty_Data.setValueAt(t_index, t_value);
			
			m_things.m_mapThings.get(m_indexOfEnemy).m_life -= m_devalueOfEnemy;
		}
	}
	/**	�����ɫ�Լ���ͼ���ƶ�
	 * 	@param v_event : ��������������ֵ
	 	@param CGameData.UP 	---- ������ϵķ����
	 	@param CGameData.RIGHT 	---- ������ҵķ����
	 	@param CGameData.DOWN 	---- ������µķ����
	 	@param CGameData.LEFT 	---- �������ķ���� */
	public void HandleHeroMove(int v_index){
		switch(v_index){
			case CGameData.UP:
				m_hero.m_currentHeroState = CHero.HEROSTATE_GOBACK;
				if(isCanRun(m_hero.m_x, m_hero.m_y-1) == true){
					
					if((m_hero.m_y-m_map.m_yIndex)*SIZEUNIT_GAMEVIEW <= m_heightGameView/2
							&& m_map.m_yIndex > 0)
						m_map.m_yIndex -= 1;
					else
						m_hero.m_y -= 1;
				}
				break;
			case CGameData.RIGHT:
				m_hero.m_currentHeroState = CHero.HEROSTATE_GORIGHT;
				if(isCanRun(m_hero.m_x+1, m_hero.m_y) == true){
					
					if((m_hero.m_x-m_map.m_xIndex)*SIZEUNIT_GAMEVIEW >= m_widthGameView/2
							&& m_map.m_mapColumns-m_map.m_xIndex > m_columnsGameView)
						m_map.m_xIndex += 1;
					else
						m_hero.m_x += 1;
				}
				break;
			case CGameData.DOWN:
				m_hero.m_currentHeroState = CHero.HEROSTATE_FACE;
				if(isCanRun(m_hero.m_x, m_hero.m_y+1) == true){
					
					if((m_hero.m_y-m_map.m_yIndex)*SIZEUNIT_GAMEVIEW >= m_heightGameView/2
							&& m_map.m_mapRows-m_map.m_yIndex > m_rowGameView)
						m_map.m_yIndex += 1;
					else
						m_hero.m_y += 1;
				}
				break;
			case CGameData.LEFT:
				m_hero.m_currentHeroState = CHero.HEROSTATE_GOLEFT;
				if(isCanRun(m_hero.m_x-1, m_hero.m_y) == true){
					if((m_hero.m_x-m_map.m_xIndex)*SIZEUNIT_GAMEVIEW <= m_widthGameView/2
							&& m_map.m_xIndex > 0)
						m_map.m_xIndex -= 1;
					else
						m_hero.m_x -= 1;
				}
				break;
		}
		HandleThingAndHero();
	}
	/** �ж���������Ƿ������ */
	private boolean isCanRun(int v_x, int v_y){
		boolean bResult = false;
		
		// ---- �ж��Ƿ񳬹���ͼ��Χ ----
		int t_xTemp = m_map.m_xIndex + v_x;
		int t_yTemp = m_map.m_yIndex + v_y;
		
		if(t_xTemp < 0)	 	return bResult;
		if(t_yTemp < 0)		return bResult;
		if(t_xTemp >= m_map.m_mapColumns) 	return bResult;
		if(t_yTemp >= m_map.m_mapRows)		return bResult;
		
		// ---- �жϵ�ͼ�ײ����� ----
		int v_type = m_map.m_mapFloor[t_yTemp][t_xTemp];
		
		if(v_type == CPublic.MAPFLOOR_ROAD
				|| v_type == CPublic.MAPFLOOR_DOOR
				|| v_type == CPublic.MAPFLOOR_STAIR_DOWN
				|| v_type == CPublic.MAPFLOOR_STAIR_UP)
			bResult = true;
		
		return bResult;
	}
	/** �ж���������Ʒ/����֮��Ĺ�ϵ*/
	private void HandleThingAndHero(){
		if(m_things == null)				return;	
		if(m_things.m_mapThings == null) 	return;
		
		m_indexOfEnemy = -1;
		m_devalueOfEnemy = 0;
		m_devalueOfHero = 0;
		
		int 			t_size = m_things.m_mapThings.size();
		Vector<Integer>	t_vector_willDeleter = new Vector<Integer>();
		
		for(int i=0; i<t_size; i++){
			if(m_hero.m_x == m_things.m_mapThings.get(i).m_pointThing.x
					&& m_hero.m_y == m_things.m_mapThings.get(i).m_pointThing.y){
				if(m_things.m_mapThings.get(i).m_property == CThing.PRO_ENEMY){
					m_indexOfEnemy = i;
					m_devalueOfEnemy = Math.round(((float)m_hero.m_mapHeroProperty_Data.get(CHero.MAPKEY_ATTR) - m_things.m_mapThings.get(i).m_defense)/4);
					m_devalueOfHero = Math.round(((float)m_things.m_mapThings.get(i).m_attr -m_hero.m_mapHeroProperty_Data.get(CHero.MAPKEY_DEFENSE))/4);
					
					m_devalueOfEnemy = (m_devalueOfEnemy>=0)? m_devalueOfEnemy : 0;
					m_devalueOfHero = (m_devalueOfHero>=0)? m_devalueOfHero : 0;
					
					m_lastStateOfHero = m_hero.m_currentHeroState;
					m_hero.m_currentHeroState = CHero.HEROSTATE_ATTR;
					return;
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_ATTR){
					int t_index = m_hero.m_mapHeroProperty_Data.indexOfKey(CHero.MAPKEY_ATTR);
					int t_value = m_hero.m_mapHeroProperty_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroProperty_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_DEF){
					int t_index = m_hero.m_mapHeroProperty_Data.indexOfKey(CHero.MAPKEY_DEFENSE);
					int t_value = m_hero.m_mapHeroProperty_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroProperty_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_LIFE){
					int t_index = m_hero.m_mapHeroProperty_Data.indexOfKey(CHero.MAPKEY_LIFE);
					int t_value = m_hero.m_mapHeroProperty_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroProperty_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_YELLOWKEY){
					int t_index = m_hero.m_mapHeroThings_Data.indexOfKey(CHero.MAPKEY_YELLOWKEY);
					int t_value = m_hero.m_mapHeroThings_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroThings_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_BLUEKEY){
					int t_index = m_hero.m_mapHeroThings_Data.indexOfKey(CHero.MAPKEY_BLUEKEY);
					int t_value = m_hero.m_mapHeroThings_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroThings_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
				else if(m_things.m_mapThings.get(i).m_property == CThing.PRO_THING_REDKEY){
					int t_index = m_hero.m_mapHeroThings_Data.indexOfKey(CHero.MAPKEY_REDKEY);
					int t_value = m_hero.m_mapHeroThings_Data.valueAt(t_index) + m_things.m_mapThings.get(i).m_proValue;
					m_hero.m_mapHeroThings_Data.setValueAt(t_index, t_value);
					t_vector_willDeleter.add(i);
				}
			}// if(m_hero.m_x
		}// for(int i=0
		t_size = t_vector_willDeleter.size();
		for(int i=0; i<t_size; i++){
			m_things.m_mapThings.remove((int)(t_vector_willDeleter.get(i)));
		}
	}
	// ==================================================================
	// ==================================================================
}
