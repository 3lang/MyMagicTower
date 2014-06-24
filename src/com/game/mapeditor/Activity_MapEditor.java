package com.game.mapeditor;

import java.lang.ref.WeakReference;
import java.util.Vector;

import com.game.mymagictower.CPublic;
import com.game.mymagictower.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

/** ��ͼ�༭�� */
public class Activity_MapEditor extends Activity implements OnGestureListener{
	// ==============================================================================
	// ========================== ��Ա���� ============================================
	// ---- ������� ----
	private	RelativeLayout 				m_layoutMapEditView		= null;
	private	CScrollView_MapList			m_scrollListItemView 	= null;
	private CMapListItemView			m_mapListView  			= null;
	private CMapEditorView				m_mapEditorView			= null;
	private GestureDetector 			m_detector 				= null;
	
	// ----- ���ݱ��� ----
	public 	static final int 			SIZEUNIT_IMAGE			= CPublic.SCREENSIZE.heightPixels / 11;
	public 	Point						m_pointListView 		= null;
	public 	Point						m_pointEditView 		= null;
	
	// ---- �ƶ�ͼƬ�ı��� ----
	private boolean						m_bMoveImage_listView	= false;	// �Ƿ������ƶ��б��ͼƬ�������жϻ��������Ƿ񻬶�View
	private WindowManager				m_windowMangager		= null; 
	private WindowManager.LayoutParams	m_lpWindow				= null;
	private ImageView					m_imageViewOfMoving		= null;		// ����ͼƬ��ImageView
	private int							m_indexOfImage			= -1;
	private int							m_indexFloorMap			= -1;		// �����༭��ͼƬʱ��ͼ��Index
	private boolean						m_bMoveImage_editView	= false;	// �Ƿ������ƶ��༭���ͼƬ�������жϻ��������Ƿ񻬶�View
	
	// ---- �༭��ͼ�ı��� ----
//	private int 					m_imageWidth_editorView 	= 0;	// �༭��View����ÿһ����ͼ��Ԫ�Ĵ�С
	public 	Vector<Data_Map>		m_vector_floorMap 			= null;	// �ײ��ͼ������
	private Vector<Data_Map>		m_vector_floorPro 			= null; // �ײ��ͼ���Ե�����
	private Vector<Data_Map>		m_vector_things 			= null;	// ��������� 
	
	/** ��ͼ�༭�������� */
	public class Data_Map{
		public int m_x = 0;
		public int m_y = 0;
		public int m_value = 0;
		public Data_Map(int v_x, int v_y, int v_value){
			m_x = v_x;
			m_y = v_y;
			m_value = v_value;
		}
	}
	// ---- ��ͼԪ�ص��б����� ----
	private final int				m_mapId 				= R.drawable.map16; // ���е�ͼ�ײ����ݵ�ͼƬ
	private final int				m_mapColumns 			= 11;				// m_mapId ��ͼƬ����
	private final int				m_mapRows				= 12;				// m_mapId ��ͼƬ����
	public	final int				m_mapLastIndex 			= 11*7 + 3;			// �� m_mapId ����Ҫ�õ�Ԫ�ظ�����������
	private WeakReference<Bitmap> 	m_weakBackg 			= null;				// ��ͼͼƬ��������
	public	Bitmap[]				m_bitmapArray			= null;				// ���е�ͼԪ�ص�ͼƬ��Դ
	public 	Point[]					m_pointImage			= null;				// ���е�ͼԪ�ص�����ֵ
	public 	int						m_widthImage_listView	= 0;				// �б��ĵ�ͼ��Ԫ�Ĵ�С
	
	// ==============================================================================
	// ================================ ��Ա���� ======================================
	@Override 
	protected void onCreate(Bundle v_saveState){
		super.onCreate(v_saveState);
		super.setContentView(R.layout.activity_mapeditor);
	}
	@Override
	protected void onStart(){
		super.onStart();
	}
	@Override
	protected void onResume(){
		super.onResume();
	}
	@Override
	public void onWindowFocusChanged(boolean v_bHasFocus){
		super.onWindowFocusChanged(v_bHasFocus);
		try{
			initData();
			m_pointListView = new Point(m_scrollListItemView.getLeft(), m_scrollListItemView.getTop());
			m_pointEditView = new Point(m_layoutMapEditView.getLeft(), m_layoutMapEditView.getTop());
			InitDrawData_mapListView(m_mapListView.getWidth());
			InitDrawData_mapEditView();
		}catch(Exception e){}
	}
	private void initData(){
		m_layoutMapEditView = (RelativeLayout)this.findViewById(R.id.id_ScrollMapEdit);
		m_scrollListItemView = (CScrollView_MapList)this.findViewById(R.id.id_ScrollListItem);
		m_mapListView = (CMapListItemView)this.findViewById(R.id.id_CMapListItemView);
		m_mapEditorView = (CMapEditorView)this.findViewById(R.id.id_CMapEditorView);
		m_detector = new GestureDetector(this, this);
		m_windowMangager = this.getWindowManager();
	}
	@Override
	public boolean onTouchEvent(MotionEvent v_event){
		// ---- �������¼����϶�Ԫ�� ----
		if(m_bMoveImage_listView == false
				&& m_bMoveImage_editView == false
				&& m_detector != null)
			return m_detector.onTouchEvent(v_event);
		else{
			if(m_bMoveImage_listView == true
					|| m_bMoveImage_editView == true){
				switch(v_event.getAction()){
					case MotionEvent.ACTION_MOVE:
						moveImage((int)v_event.getX(), (int)v_event.getY());
						break;
					case MotionEvent.ACTION_UP:
						stopMove();
						break;
				}
				
			}
			return super.onTouchEvent(v_event);
		}
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent v_event) {
		
	}
	@Override
	public boolean onScroll(MotionEvent v_e1, MotionEvent v_e2, float v_fdistanceX, float v_fdistanceY) {
		// TODO Auto-generated method stub
		// ====== ���û�����ƶ�ͼƬ���򻬶���Ӧ��View ======
		if(m_bMoveImage_listView == false){
			int x_touch = (int)v_e1.getX();
			if(x_touch >= m_pointListView.x){
				// ====== �����ұߵ��б� ======
				int y_scrollTo = (int)(m_scrollListItemView.getScrollY() + v_fdistanceY*3);
				m_scrollListItemView.scrollTo(0, y_scrollTo);
			}else if(m_bMoveImage_editView == false){
				// ====== ������ߵı༭View ======
				int x_scrollTo = (int)(m_mapEditorView.getScrollX() + v_fdistanceX*(1.3f));
				int y_scrollTo = (int)(m_mapEditorView.getScrollY() + v_fdistanceY*(1.3f));
				// ---- �жϻ����Ƿ�Խ�� ----
				x_scrollTo = (x_scrollTo<=0)? 0:x_scrollTo;
				x_scrollTo = (x_scrollTo+CPublic.SCREENSIZE.widthPixels>=m_mapEditorView.getWidth())?
						m_mapEditorView.getWidth()-CPublic.SCREENSIZE.widthPixels:x_scrollTo;
				y_scrollTo = (y_scrollTo<=0)? 0:y_scrollTo;
				y_scrollTo = (y_scrollTo+CPublic.SCREENSIZE.heightPixels>=m_mapEditorView.getHeight())?
						m_mapEditorView.getHeight()-CPublic.SCREENSIZE.heightPixels:y_scrollTo;
				
				m_mapEditorView.scrollTo(x_scrollTo, y_scrollTo);
			}
		}
		return false;
	}
	@Override
	public void onShowPress(MotionEvent v_event) {
		// TODO Auto-generated method stub
		int x_touch = (int)v_event.getX();
		int y_touch = (int)v_event.getY();
		int y_scroll = m_scrollListItemView.getScrollY();
		if((x_touch>=m_pointListView.x && x_touch<=m_pointListView.x+m_scrollListItemView.getWidth())
				&& (y_touch>=m_pointListView.y && y_touch<=m_pointListView.y+m_scrollListItemView.getHeight())){
			m_indexOfImage =  GetMoveImage(x_touch-m_pointListView.x, y_touch-m_pointListView.y+y_scroll);
			if(m_indexOfImage >= 0
					&& m_indexOfImage < m_bitmapArray.length){
				Bitmap moveImage = m_bitmapArray[m_indexOfImage];
				m_bMoveImage_listView = true;	
				startMove(moveImage);
				moveImage = null;
			}
		}else{
			int x_index = (x_touch)/m_widthImage_listView;
			int y_index = (y_touch)/m_widthImage_listView;
			m_indexFloorMap = getIndexOfFloorMap(x_index, y_index);
			if(m_indexFloorMap != -1){
				Bitmap moveImage = m_bitmapArray[m_vector_floorMap.get(m_indexFloorMap).m_value];
				m_bMoveImage_editView = true;
				m_bMoveImage_listView = false;
				startMove(moveImage);
				moveImage = null;
			}
		}
	}
	@Override
	public boolean onSingleTapUp(MotionEvent v_event) {
		
		return false;
	}
	private int getIndexOfFloorMap(int v_x, int v_y){
		int result = -1;
		if(m_vector_floorMap != null){
			int size = m_vector_floorMap.size();
			for(int i=0; i<size; i++){
				if(m_vector_floorMap.get(i).m_x == v_x
						&& m_vector_floorMap.get(i).m_y == v_y){
					result = i;
					break;
				}
			}
		}
		return result;
	}
	private void startMove(Bitmap v_bitmap){
		try{
			m_lpWindow = new WindowManager.LayoutParams();
			m_lpWindow.gravity = Gravity.TOP|Gravity.LEFT; // ����windowλ��
			m_lpWindow.x = 0;
			m_lpWindow.y = 0;
			m_lpWindow.width = WindowManager.LayoutParams.WRAP_CONTENT;
			m_lpWindow.height = WindowManager.LayoutParams.WRAP_CONTENT;
			m_lpWindow.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
			m_lpWindow.format = PixelFormat.TRANSLUCENT;
			m_lpWindow.windowAnimations = 0;
			m_lpWindow.alpha = 0.8f;
			m_imageViewOfMoving = new ImageView(this);
			m_imageViewOfMoving.setImageBitmap(v_bitmap);
			m_imageViewOfMoving.setScaleType(ScaleType.FIT_XY);
			m_windowMangager.addView(m_imageViewOfMoving, m_lpWindow);
		}catch(Exception e){}
	}
	private void stopMove(){
		try{
			handleMoveImage();
			m_windowMangager.removeView(m_imageViewOfMoving);
			m_imageViewOfMoving = null;
			m_lpWindow = null;
		}catch(Exception e){}
	}
	private void moveImage(int v_x, int v_y){
		try{
			m_lpWindow.x = v_x - m_imageViewOfMoving.getWidth()/2;
			m_lpWindow.y = v_y - m_imageViewOfMoving.getHeight()/2;
			m_windowMangager.updateViewLayout(m_imageViewOfMoving, m_lpWindow);
		}catch(Exception e){
			stopMove(); 
		}
	}
	/** �����б����ϵ��༭�������� */
	private void handleMoveImage(){
		if(m_bMoveImage_listView == true){
			int x_stop = m_lpWindow.x;
			int y_stop = m_lpWindow.y;
			if(x_stop >= m_pointListView.x) return;
			
			int x_index = (x_stop+m_widthImage_listView*3/5)/m_widthImage_listView;
			int y_index = (y_stop+m_widthImage_listView*3/5)/m_widthImage_listView;
			
			if(m_vector_floorMap != null){
				m_vector_floorMap.add(new Data_Map(x_index, y_index, m_indexOfImage));
			}
			if(m_mapEditorView != null)
				m_mapEditorView.invalidate();
			m_indexOfImage = -1;
			m_bMoveImage_listView = false;
		}else if(m_bMoveImage_editView == true){
			int x_stop = m_lpWindow.x;
			int y_stop = m_lpWindow.y;
			
			if(x_stop >= m_pointListView.x){
				if(m_indexFloorMap >= 0
						&& m_indexFloorMap <= m_vector_floorMap.size()){
					m_vector_floorMap.remove(m_indexFloorMap);
				}
			}else{
				int x_index = (x_stop+m_widthImage_listView*3/5)/m_widthImage_listView;
				int y_index = (y_stop+m_widthImage_listView*3/5)/m_widthImage_listView;
				Data_Map object = m_vector_floorMap.get(m_indexFloorMap);
				m_vector_floorMap.remove(m_indexFloorMap);
				object.m_x = x_index;
				object.m_y = y_index;
				m_vector_floorMap.add(object);
			}
			m_indexFloorMap = -1;
			m_bMoveImage_editView = false;
			if(m_mapEditorView != null)
				m_mapEditorView.invalidate();
		}
	}
	private void InitDrawData_mapListView(int v_widthView){
		// ====== ��ʼ��ÿ���б���Ŀ�Ⱥ�����ֵ ======
		// ---- ��ȡͼƬ��Դ ----
		m_widthImage_listView = v_widthView/3;
		
		if(m_bitmapArray == null){
			m_bitmapArray = new Bitmap[m_mapLastIndex];
			if(m_weakBackg == null 
					|| m_weakBackg.get() == null){
				m_weakBackg = new WeakReference<Bitmap>(CPublic.CreateBitmap(this, m_mapId,
						m_mapColumns*m_widthImage_listView, m_mapRows*m_widthImage_listView));
			}
			Bitmap 	t_bitmap 	= m_weakBackg.get();
			int 	t_x			= 0;
			int 	t_y			= 0;
			for(int i=0; i<m_bitmapArray.length; i++){
				t_x = i%m_mapColumns;
				t_y = i/m_mapColumns;
				m_bitmapArray[i] = Bitmap.createBitmap(t_bitmap, t_x*m_widthImage_listView,
						t_y*m_widthImage_listView, m_widthImage_listView, m_widthImage_listView);
			}
			t_bitmap = null;
		}
		// ---- ����ͼƬԪ�صĴ�С������ֵ ----
		if(m_pointImage == null){
			int 	t_space = (v_widthView - m_widthImage_listView*2)/3;
			int 	t_x = t_space;
			int 	t_y = t_space;
			int 	t_n = 0;
			boolean	t_bBreak = false;
			
			m_pointImage = new Point[m_mapLastIndex];
			while(t_bBreak == false){
				t_x = t_space;
				for(int c=0; c<2 && t_bBreak==false; c++){
					if(t_n >= m_mapLastIndex){
						t_bBreak = true;
						break;
					}		
					m_pointImage[t_n++] = new Point(t_x, t_y);
					
					t_x += (m_widthImage_listView+t_space);
				}
				t_y += (m_widthImage_listView + t_space);
			}
			m_mapListView.requestLayout();
			m_mapListView.invalidate();
		}
	}
	private int GetMoveImage(int v_x, int v_y){
		for(int i=0; m_pointImage!=null && i<m_pointImage.length; i++){
			if((v_x >= m_pointImage[i].x && v_x<=m_pointImage[i].x+m_widthImage_listView)
				&& (v_y>=m_pointImage[i].y && v_y<=(m_pointImage[i].y+m_widthImage_listView))){
				return i;
			}
		}
		return -1;
	}
	private void InitDrawData_mapEditView(){
		// ====== ��ʼ���༭������� ======
		m_vector_floorMap = new Vector<Data_Map>();
		m_vector_floorPro = new Vector<Data_Map>();
		m_vector_things = new Vector<Data_Map>();
	}
	// ==============================================================================
	// ==============================================================================
}
