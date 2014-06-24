package com.game.mymagictower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

/** ��ɫ�� */
@SuppressLint("UseSparseArrays")
public class CHero {
	// ==================================================================
	// ========================== ��Ա���� ================================
	public final String[] 	m_heroProperty	= { "�ȼ�", "����", "����", "����", "����", "���" };
	public final String[] 	m_heroThings	= { "��Կ��", "��Կ��", "��Կ��", "����Կ��" };
	
	public 	final int 		m_iconId 		= R.drawable.icon; 	 // ״̬������ͼƬ
	private final int		m_ImageId 		= R.drawable.hero16; // ����ͼƬ������ͼƬ
	private final int		m_numState 		= 14;	// �����ܹ��Ķ���״̬��
	private int				m_currentState 	= 0;		
	
	public int					m_currentHeroState 	= 0;	// ��ǰ���ǵ�״ֵ̬
	public static final int		HEROSTATE_FACE 		= 0;	// ����
	public static final int		HEROSTATE_GOLEFT 	= 1;	// ����
	public static final int		HEROSTATE_GORIGHT 	= 2;	// ����
	public static final int		HEROSTATE_GOBACK 	= 3;	// ���
	public static final int		HEROSTATE_ATTR 		= 4;	// ����
	
	private  	Bitmap[]	m_heroBitmap_face 	= null;		// ���������ͼƬ
	private	 	Bitmap[] 	m_heroBitmap_left 	= null;		// ���������ͼƬ
	private  	Bitmap[] 	m_heroBitmap_right 	= null;		// �������ҵ�ͼƬ
	private  	Bitmap[] 	m_heroBitmap_back 	= null;		// ���Ǳ����ͼƬ
	private  	Bitmap[] 	m_heroBitmap_attr 	= null;		// ���Ǵ򶷵�ͼƬ

	public 	int 	m_heroState 	= 0; 	// ��ǰ�����ͼƬ
	private int 	m_grade			= 1; 	// ����ȼ�
	private int 	m_life 			= 550; 	// ��������ֵ
	private int 	m_attr 			= 60; 	// ���﹥��ֵ
	private int 	m_defense 		= 30; 	// �������ֵ
//	private int	 	m_MagicDefense 	= 1; 	// ����ħ��ֵ
	private int 	m_Exp 			= 0; 	// ����ֵ
	private int 	m_money 		= 100; 	// ��Ǯ
	private int 	m_keyNum_yellow = 0; 	// ��ɫԿ����
	private int 	m_keyNum_blue 	= 0; 	// ��ɫԿ����
	private int 	m_keyNum_red 	= 0; 	// ��ɫԿ����
	private int 	m_keyNum_All 	= 0; 	// ����Կ����
	public 	int		m_x 			= 5;	// ����X����
	public 	int		m_y				= 6; 	// ����Y����

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
	// ========================== ��Ա���� ================================
	public CHero(Context v_context){
		createHeroBitmap(v_context);
		m_currentHeroState = HEROSTATE_FACE;
		
		// ---- ��ʼ��״ֵ̬ ----
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
		// ---- ����ͼƬ ----
		m_heroBitmap_face = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_face[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- ����ͼƬ ----
		m_heroBitmap_left = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_left[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- ����ͼƬ ----
		m_heroBitmap_right = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_right[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- ���ͼƬ ----
		m_heroBitmap_back = new Bitmap[3];
		for(int i=0; i<3; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_back[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- ��ͼƬ ----
		m_heroBitmap_attr = new Bitmap[2];
		for(int i=0; i<2; i++, t_x+=CGameData.SIZEUNIT_GAMEVIEW){
			m_heroBitmap_attr[i] = Bitmap.createBitmap(t_bitmap, t_x, 0, CGameData.SIZEUNIT_GAMEVIEW, CGameData.SIZEUNIT_GAMEVIEW);
		}
		t_bitmap.recycle();
		t_bitmap = null;
	}
	/**
	 * ���ݽ�ɫ״̬������ӦͼƬ
	 * 	@param 	v_state ��
	   	@param 	CHero.HEROSTATE_FACE ����
		@param 	CHero.HEROSTATE_GOLEFT ����
		@param	CHero.HEROSTATE_GORIGHT ����
		@param	CHero.HEROSTATE_GOBACK ���
		@param	CHero.HEROSTATE_ATTR ����
	 * @return ������ӦͼƬBitmap */
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
	/** �ͷ���Դ */
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
