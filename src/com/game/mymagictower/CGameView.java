package com.game.mymagictower;

import java.lang.ref.WeakReference;
import java.util.Vector;

import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/** ��Ϸ������ */
public class CGameView extends CBaseView {
	// ==================================================================
	// ========================= ��Ա���� =================================
	private WeakReference<Bitmap> 	m_weakBitmapIcon 			= null;
	private Point[]					m_pointHeroState 			= null;	
	private int						m_widthState 				= 0;
	public 	final int 				m_backgColumns_heroState 	= 4; 	// ����״̬������ͼ������
	
	private Point[]			m_pointKeyboardItem		= null;	// ÿ������������꣬�ֱ��ϡ��ҡ��¡���
	private int 	 		m_sizeOfKeyboardItem	= 0; 	// ÿ��������Ĵ�С
	private Vector<Point>	m_vectorPointBackg 		= new Vector<Point>();	// �洢ǽ��ͼƬ����
	private Vector<Bitmap> 	m_vectorBitmapBackg 	= new Vector<Bitmap>(); // �洢ǽ��ͼƬ��Դ
	private float 			m_fFontSize 			= CPublic.retureHeight(32);
	private Point 			m_startPoint_GameView 	= null; // ��Ϸ���ڵ���ʼ����
	private Point 			m_endPoint_GameView 	= null; // ��Ϸ���ڵ���ֹ����
	private CGameData		m_gameData				= null;
	private Vector<Integer>	m_vectorTouch			= null;
	
	// ==================================================================
	// ========================= ��Ա���� =================================
	public CGameView(Context v_context) {
		super(v_context);
		m_gameData = new CGameData(m_context);
		initPoint();
	}
	@Override
	public void DrawView(Canvas v_canvas) {
		// TODO Auto-generated method stub
		synchronized(m_vectorTouch){ // m_vectorTouchͬ����
			// ---- �����ƶ� ----
			if(m_vectorTouch != null
					&& m_vectorTouch.size() != 0){
				int touch = m_vectorTouch.get(0);
				m_vectorTouch.remove(0);
				
				m_gameData.HandleHeroMove(touch);
			}
		}
		// ---- ��ջ��� ----
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		v_canvas.drawPaint(paint);
		
		// ---- ���ƽ��� ----
		if(m_gameData != null)
			m_gameData.ChangeDataOfHeroAndEnemy(); // �ı���ֵ
		drawBackground(v_canvas);
		drawkeyboard(v_canvas);
		drawHeroState(v_canvas);
		drawGameView(v_canvas);
	}

	@Override
	protected boolean HandleTouchEvent(MotionEvent vEvent) {
		// TODO Auto-generated method stub
//		m_gameData.HandleMove(vEvent);
		int t_x = (int)vEvent.getX();
		int t_y = (int)vEvent.getY();
		
		for(int i=0; i<m_pointKeyboardItem.length; i++){
			if(t_x>=m_pointKeyboardItem[i].x && t_x<=(m_pointKeyboardItem[i].x+m_sizeOfKeyboardItem))
				if(t_y>=m_pointKeyboardItem[i].y && t_y<=(m_pointKeyboardItem[i].y+m_sizeOfKeyboardItem)){
					// ---- �ƶ�ͬ���� ----
					synchronized(m_vectorTouch){
						m_vectorTouch.add(i);
					};
					break;
				}
		}
		
		return false;
	}

	@Override
	protected void InitData() {
		// TODO Auto-generated method stub
		m_vectorTouch = new Vector<Integer>();
	}

	@Override
	protected void ReleaseData() {
		// TODO Auto-generated method stub
		m_gameData.releaseData();
		
		if(m_vectorBitmapBackg != null){
			int Size = m_vectorBitmapBackg.size();
			for(int i=0; i<Size; i++){
				if(m_vectorBitmapBackg.get(i)!=null 
						&& m_vectorBitmapBackg.get(i).isRecycled()!=true)
				{	
					m_vectorBitmapBackg.get(i).recycle();
					m_vectorBitmapBackg.set(i, null);
				}
			}
			m_vectorBitmapBackg.clear();
			m_vectorBitmapBackg = null;
		}
		if(m_vectorPointBackg != null)
			m_vectorPointBackg.clear();
		m_vectorPointBackg = null;
		
		if(m_vectorTouch != null)
			m_vectorTouch.clear();
		m_vectorTouch = null;
	}
	/** ��ʼ����Ҫ�����꣬�����ڻ滭ʱ��������*/
	private void initPoint(){
		// ---- �������꣺�������Ϊ������Ե� ---- ----
		if(m_pointKeyboardItem == null)
			m_pointKeyboardItem = new Point[4];
		m_sizeOfKeyboardItem = (int)(CGameData.SIZEUNIT_GAMEVIEW);
		
		int 	t_space = CGameData.SIZEUNIT_BG/4;
		int 	t_startX_left = CGameData.SIZEUNIT_BG/2;
		int 	t_startY_left = CPublic.SCREENSIZE.heightPixels - m_sizeOfKeyboardItem*2 - t_space;
		m_pointKeyboardItem[CGameData.UP] = new Point(t_startX_left+t_space+m_sizeOfKeyboardItem,
				t_startY_left-t_space-m_sizeOfKeyboardItem) ;
		m_pointKeyboardItem[CGameData.RIGHT] = new Point(t_startX_left+t_space*2+m_sizeOfKeyboardItem*2,
				t_startY_left) ;
		m_pointKeyboardItem[CGameData.DOWN] = new Point(t_startX_left+t_space+m_sizeOfKeyboardItem,
				t_startY_left+t_space+m_sizeOfKeyboardItem) ;
		m_pointKeyboardItem[CGameData.LEFT] = new Point(t_startX_left,
				t_startY_left) ;
		
		// ---- ״̬����ֵ���� ----
		m_pointHeroState = new Point[m_gameData.m_hero.m_heroProperty.length + m_gameData.m_hero.m_heroThings.length + 1];
		int 	t_columnsSpace = (m_backgColumns_heroState * CGameData.SIZEUNIT_BG) / 10;
		int 	t_xStart = CGameData.SIZEUNIT_BG / 2 + t_columnsSpace;
		int 	t_yStart = CGameData.SIZEUNIT_BG / 2;
		Paint 	t_paint = new Paint();
		Rect 	t_rect = new Rect();
		String 	t_str = "����";
		m_widthState = m_backgColumns_heroState * CGameData.SIZEUNIT_BG - t_columnsSpace * 2;
		
		t_paint.setTextSize(m_fFontSize);
		t_paint.getTextBounds(t_str, 0, t_str.length(), t_rect);
		t_paint.setColor(Color.WHITE);
		int t_heightRow = t_rect.height();
		int t_rowSpace = t_heightRow / 2; // ÿһ��֮��ļ�������������С�趨
		
		int t_index = 0;
		m_pointHeroState[t_index++] = new Point(t_xStart, t_yStart); // ͼ������
		t_yStart += CPublic.VALUE_ADD; // ����ֵ
		t_yStart += t_rect.height() + t_rowSpace;
		
		int t_size = m_gameData.m_hero.m_mapHeroProperty_Data.size();
		for(int i=0; i<m_gameData.m_hero.m_heroProperty.length && i<t_size; i++){
			m_pointHeroState[t_index++] = new Point(t_xStart, t_yStart);
			t_yStart += t_rect.height() + t_rowSpace;
		}
		t_size = m_gameData.m_hero.m_mapHeroThings_Data.size();
		for(int i=0; i<m_gameData.m_hero.m_heroThings.length && i<t_size; i++){
			m_pointHeroState[t_index++] = new Point(t_xStart, t_yStart);
			t_yStart += t_rect.height() + t_rowSpace;
		}
		
		// ====== ��ʼ��ǽ�������ͼƬ��Դ ======
		// ---- ����ߵı߿� ----
		int start_x = 0;
		WeakReference<Bitmap> t_weak = null;
		for (int t_y = 0; t_y <= CPublic.SCREENSIZE.heightPixels; t_y += CGameData.SIZEUNIT_BG) {
			t_weak = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context,
					R.drawable.t2, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorBitmapBackg.add(Bitmap.createBitmap(t_weak.get(), CGameData.SIZEUNIT_BG / 2, 0, CGameData.SIZEUNIT_BG / 2, CGameData.SIZEUNIT_BG));
			t_weak = null;
			m_vectorPointBackg.add(new Point(start_x, t_y));
		}
		start_x = CGameData.SIZEUNIT_BG / 2;

		// ---- ���״̬����ǽ�棬m_backgColumns_heroStateָ������ ----
		for (int columns = 0; columns < m_backgColumns_heroState; columns++) {
			for (int t_y = 0; t_y <= CPublic.SCREENSIZE.heightPixels; t_y += CGameData.SIZEUNIT_BG) {
				m_vectorBitmapBackg.add(CPublic.CreateBitmap(m_context, R.drawable.t1, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
				m_vectorPointBackg.add(new Point(start_x, t_y));
			}
			start_x += CGameData.SIZEUNIT_BG;
		}

		// ---- ״̬������Ϸ��ķָ�ǽ�� ----
		for (int t_y = 0; t_y <= CPublic.SCREENSIZE.heightPixels; t_y += CGameData.SIZEUNIT_BG) {
			m_vectorBitmapBackg.add(CPublic.CreateBitmap(m_context, R.drawable.t2, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorPointBackg.add(new Point(start_x, t_y));
		}
		start_x += CGameData.SIZEUNIT_BG;

		// ---- �ұߵ���Ϸ���ڣ���߷ָ��� ----
		for (int t_y = 0; t_y <= CPublic.SCREENSIZE.heightPixels; t_y += CGameData.SIZEUNIT_BG) {
			t_weak = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context, R.drawable.t1, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorBitmapBackg.add(Bitmap.createBitmap(t_weak.get(), t_weak.get().getWidth() / 2, 0, t_weak.get().getWidth() / 2,
						t_weak.get().getHeight()));
			t_weak = null;
			m_vectorPointBackg.add(new Point(start_x, t_y));
		}
		start_x += CGameData.SIZEUNIT_BG / 2;
		m_startPoint_GameView = new Point(start_x, CGameData.SIZEUNIT_BG); // ������Ϸ���ڵĻ�����ʼ����
		m_endPoint_GameView = new Point(CPublic.SCREENSIZE.widthPixels - CGameData.SIZEUNIT_BG,
				CPublic.SCREENSIZE.heightPixels - CGameData.SIZEUNIT_BG); // ������Ϸ���ڵĻ�����ֹ����
		if(m_gameData != null){
			m_gameData.m_widthGameView = Math.abs(m_endPoint_GameView.x - m_startPoint_GameView.x);
			m_gameData.m_heightGameView = Math.abs(m_endPoint_GameView.y - m_startPoint_GameView.y);
			m_gameData.m_columnsGameView = Math.round(m_gameData.m_widthGameView/CGameData.SIZEUNIT_GAMEVIEW);
			m_gameData.m_rowGameView = Math.round(m_gameData.m_heightGameView/CGameData.SIZEUNIT_GAMEVIEW);
		}
		
		// ---- ������Ϸ�������¶˵�ǽ�� ----
		for (int t_x = start_x; t_x < (CPublic.SCREENSIZE.widthPixels - CGameData.SIZEUNIT_BG); t_x += CGameData.SIZEUNIT_BG) {
			// ---- �϶� ----
			m_vectorBitmapBackg.add(CPublic.CreateBitmap(m_context, R.drawable.t1, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorPointBackg.add(new Point(t_x, 0));

			// ---- �¶� ----
			m_vectorBitmapBackg.add(CPublic.CreateBitmap(m_context, R.drawable.t1, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			t_weak = null;
			m_vectorPointBackg.add(new Point(t_x, CPublic.SCREENSIZE.heightPixels - CGameData.SIZEUNIT_BG));
		}
		// ---- ���ұ�ǽ�������ͼ����Ϣ ----
		for (int t_y = 0; t_y < CPublic.SCREENSIZE.heightPixels; t_y += CGameData.SIZEUNIT_BG) {
			// ---- ��� ----
			t_weak = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context, R.drawable.t1, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorBitmapBackg.add(Bitmap.createBitmap(t_weak.get(), 0, 0, CGameData.SIZEUNIT_BG / 2, CGameData.SIZEUNIT_BG));
			t_weak = null;
			m_vectorPointBackg.add(new Point(CPublic.SCREENSIZE.widthPixels - CGameData.SIZEUNIT_BG, t_y));

			// ---- �ұ� ----
			t_weak = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context, R.drawable.t2, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			m_vectorBitmapBackg.add(Bitmap.createBitmap(t_weak.get(), 0, 0, CGameData.SIZEUNIT_BG / 2, CGameData.SIZEUNIT_BG));
			t_weak = null;
			m_vectorPointBackg.add(new Point(CPublic.SCREENSIZE.widthPixels - CGameData.SIZEUNIT_BG / 2, t_y));
		}
	}
	/** ���ƽ�ɫ״ֵ̬ */
	private void drawHeroState(Canvas v_canvas) {
		try {
			Paint 	t_paint = new Paint();
			t_paint.setTextSize(m_fFontSize);
			t_paint.setColor(Color.WHITE);
			
			// ---- ����Ӣ�۵�ͼ��͵�ͼ���������� ----
			String 	t_str = String.valueOf(m_gameData.m_map.m_currentMapIndex) + "��";
			int 	t_index = 0;
			
			if(m_weakBitmapIcon == null 
					|| m_weakBitmapIcon.get() == null)
				m_weakBitmapIcon = new WeakReference<Bitmap>(CPublic.CreateBitmap(m_context, m_gameData.m_hero.m_iconId, CGameData.SIZEUNIT_BG, CGameData.SIZEUNIT_BG));
			v_canvas.drawBitmap(m_weakBitmapIcon.get(), m_pointHeroState[t_index].x, m_pointHeroState[t_index].y, null);
			
			Rect t_rect = new Rect();
			t_paint.getTextBounds(t_str, 0, t_str.length(), t_rect);
			v_canvas.drawText(t_str, m_pointHeroState[t_index].x+m_widthState-t_rect.width(), m_pointHeroState[t_index].y+CPublic.VALUE_ADD, t_paint);
			t_index += 1;
			
			// ---- ����Ӣ��״ֵ̬ ----
			for(int i=0; i<m_gameData.m_hero.m_heroProperty.length; i++){
				drawText_HeroState(v_canvas, m_pointHeroState[t_index], m_widthState,
						m_gameData.m_hero.m_heroProperty[i], Color.GREEN,
						m_gameData.m_hero.m_mapHeroProperty_Data.valueAt(i), Color.WHITE);
				t_index += 1;
			}
			
			// ---- ����Ӣ��Կ������ֵ ----
			for(int i=0; i<m_gameData.m_hero.m_heroThings.length; i++){
				drawText_HeroState(v_canvas, m_pointHeroState[t_index], m_widthState,
						m_gameData.m_hero.m_heroThings[i], Color.GREEN,
						m_gameData.m_hero.m_mapHeroThings_Data.valueAt(i), Color.WHITE);
				t_index += 1;
			}
		
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����Ӣ��״̬�����ı�
	 * @param v_canvas ---- ���ƶ���
	 * @param v_point ---- ������ʼ����
	 * @param v_title ---- ����
	 * @param v_titlecolor ---- �����ı���ɫ
	 * @param v_content ---- ����
	 * @param v_contentColor ---- �����ı���ɫ */
	private void drawText_HeroState(Canvas v_canvas, Point v_point,
			int v_width, String v_title, int v_titlecolor, int v_content,
			int v_contentColor) {
		// ---- ���û��� ----
		Paint t_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		t_paint.setTextSize(m_fFontSize);

		// ---- ���Ʊ��⣺����˶��� ----
		t_paint.setColor(v_titlecolor);
		v_canvas.drawText(v_title, v_point.x, v_point.y, t_paint);

		// ---- �������ݣ����Ҷ˶��� ----
		String	t_str = String.valueOf(v_content);
		Rect 	t_rect = new Rect();
		
		t_paint.getTextBounds(t_str, 0, t_str.length(), t_rect);
		t_paint.setColor(v_contentColor);

		int t_textWidth = t_rect.width();
		v_canvas.drawText(t_str, v_point.x + v_width - t_textWidth, v_point.y, t_paint);
	}

	/** ���Ʊ���ͼ */
	private void drawBackground(Canvas v_canvas) {
		if(m_context == null) return;
		if(m_vectorPointBackg == null)	return;
		if(m_vectorBitmapBackg == null) return;
		
		int t_size = m_vectorBitmapBackg.size();
		for(int i=0; i<t_size; i++){
			v_canvas.drawBitmap(m_vectorBitmapBackg.get(i), m_vectorPointBackg.get(i).x, m_vectorPointBackg.get(i).y, null);
		}
	}
	/** ������Ϸ���棬�������ǹ������Ʒ��m_startPoint_GameView �� m_endPoint_GameView�ֱ�Ϊ�ô��ڵ����Ͻ���������½�����*/
	private void drawGameView(Canvas v_canvas) {
		if(m_gameData == null) 			return;
		if(m_gameData.m_map == null)	return;
		
		Bitmap[][]	t_mapBitmap = m_gameData.m_map.getMapBitmap();
		int 		t_rowStart	= m_gameData.m_map.m_yIndex;
		int 		t_colStart 	= m_gameData.m_map.m_xIndex;
		if(t_mapBitmap == null) 	return;
		
		// ������ʵĽ�����ʼ���꣬����Ϸ��ͼС����Ϸ����ʱ���������
		int t_startX = m_startPoint_GameView.x,
			t_startY = m_startPoint_GameView.y;
		int t_temp = ((m_endPoint_GameView.x-m_startPoint_GameView.x) - (CGameData.SIZEUNIT_GAMEVIEW*m_gameData.m_map.m_mapColumns))/2;
		t_startX += (t_temp>0) ? t_temp:0;
		
		t_temp = ((m_endPoint_GameView.y-m_startPoint_GameView.y) - (CGameData.SIZEUNIT_GAMEVIEW*m_gameData.m_map.m_mapRows))/2;
		t_startY += (t_temp>0) ? t_temp:0;
		
		int t_x = t_startX,
			t_y = t_startY;
		
		int 	t_wTemp = 0;
		int		t_hTemp = 0;
		Bitmap 	t_bitmap = null;
		
		// ---- ��ʼ�滭 ----
		for(int t_r=t_rowStart; t_y<m_endPoint_GameView.y && t_r<t_mapBitmap.length; t_r++){
			t_x = t_startX;
			for(int t_c=t_colStart; t_x<m_endPoint_GameView.x && t_c<t_mapBitmap[t_r].length; t_c++){
				
				t_wTemp = ((m_endPoint_GameView.x-t_x)<CGameData.SIZEUNIT_GAMEVIEW)?(m_endPoint_GameView.x-t_x):CGameData.SIZEUNIT_GAMEVIEW;
				t_hTemp = ((m_endPoint_GameView.y-t_y)<CGameData.SIZEUNIT_GAMEVIEW)?(m_endPoint_GameView.y-t_y):CGameData.SIZEUNIT_GAMEVIEW;
				
				if(t_wTemp != CGameData.SIZEUNIT_GAMEVIEW 
						|| t_hTemp != CGameData.SIZEUNIT_GAMEVIEW){
					t_bitmap = Bitmap.createBitmap(t_mapBitmap[t_r][t_c], 0, 0, t_wTemp, t_hTemp);
					v_canvas.drawBitmap(t_bitmap, t_x, t_y, null);
				}
				else
					v_canvas.drawBitmap(t_mapBitmap[t_r][t_c], t_x, t_y, null);
				
				t_x += CGameData.SIZEUNIT_GAMEVIEW;
			}
			t_y += CGameData.SIZEUNIT_GAMEVIEW;
		}
		// ---- �滭���Ǻ͵�ͼ�ϵ����� ----
		drawThing(v_canvas, t_startX, t_startY);
		drawHero(v_canvas, t_startX, t_startY);
	}
	/**
	 * �������� 
	 * @param v_canvas	---	����
	 * @param v_startX	---	��Ϸ�������ʼ���꣨��ͬ����Ϸ�������꣩
	 * @param v_startY	---	��Ϸ�������ʼ���꣨��ͬ����Ϸ�������꣩*/
	private void drawHero(Canvas v_canvas, int v_startX, int v_startY) {
		v_canvas.drawBitmap(m_gameData.m_hero.getHeroStateBitmap(m_gameData.m_hero.m_currentHeroState),
				v_startX+m_gameData.m_hero.m_x*CGameData.SIZEUNIT_GAMEVIEW,
				v_startY+m_gameData.m_hero.m_y*CGameData.SIZEUNIT_GAMEVIEW, null);
	}
	/**
	 * ��������
	 * @param v_canvas	---	����
	 * @param v_startX	---	��Ϸ�������ʼ���꣨��ͬ����Ϸ�������꣩
	 * @param v_startY	---	��Ϸ�������ʼ���꣨��ͬ����Ϸ�������꣩*/
	private void drawThing(Canvas v_canvas, int v_startX, int v_startY){
		int t_size = m_gameData.m_things.m_mapThings.size();
		for(int i=0; i<t_size; i++){
			v_canvas.drawBitmap(m_gameData.m_things.m_mapThings.get(i).m_bitmapThing,
					v_startX+m_gameData.m_things.m_mapThings.get(i).m_pointThing.x*CGameData.SIZEUNIT_GAMEVIEW,
					v_startY+m_gameData.m_things.m_mapThings.get(i).m_pointThing.y*CGameData.SIZEUNIT_GAMEVIEW,
					null);
		}
	}
	/** ���Ʒ���� */
	private void drawkeyboard(Canvas v_canvas) {
		if(m_pointKeyboardItem == null) return;

		Paint t_paint = new Paint();
		t_paint.setColor(Color.argb(170, 0, 200, 0));
		
		int t_left = m_pointKeyboardItem[CGameData.LEFT].x;
		int t_top = m_pointKeyboardItem[CGameData.LEFT].y;
		int t_bottom = m_pointKeyboardItem[CGameData.LEFT].y + m_sizeOfKeyboardItem;
		int t_right = m_pointKeyboardItem[CGameData.LEFT].x + m_sizeOfKeyboardItem;
		
		// ---- �� ----
		v_canvas.drawRect(t_left, t_top, t_right, t_bottom, t_paint);
		
		// ---- �� ----
		t_left = m_pointKeyboardItem[CGameData.UP].x;
		t_top = m_pointKeyboardItem[CGameData.UP].y;
		t_bottom = m_pointKeyboardItem[CGameData.UP].y + m_sizeOfKeyboardItem;
		t_right = m_pointKeyboardItem[CGameData.UP].x + m_sizeOfKeyboardItem;
		v_canvas.drawRect(t_left, t_top, t_right, t_bottom, t_paint);
		
		// ---- �� ----
		t_left = m_pointKeyboardItem[CGameData.RIGHT].x;
		t_top = m_pointKeyboardItem[CGameData.RIGHT].y;
		t_bottom = m_pointKeyboardItem[CGameData.RIGHT].y + m_sizeOfKeyboardItem;
		t_right = m_pointKeyboardItem[CGameData.RIGHT].x + m_sizeOfKeyboardItem;
		v_canvas.drawRect(t_left, t_top, t_right, t_bottom, t_paint);
		
		// ---- �� ----
		t_left = m_pointKeyboardItem[CGameData.DOWN].x;
		t_top = m_pointKeyboardItem[CGameData.DOWN].y;
		t_bottom = m_pointKeyboardItem[CGameData.DOWN].y + m_sizeOfKeyboardItem;
		t_right = m_pointKeyboardItem[CGameData.DOWN].x + m_sizeOfKeyboardItem;
		v_canvas.drawRect(t_left, t_top, t_right, t_bottom, t_paint);
	}
	// ==================================================================
	// ==================================================================
}
