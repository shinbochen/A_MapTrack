package com.AMaptrack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.Data.GlobalData;
import com.Data.Maptrack_ID;
import com.Data.ProgramType;
import com.Language.Language;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.CacheManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class AMapView extends Activity {

	public  final int						ACTIVITY_MAP_VEHICLE = 0x10;
	public  final int						ACTIVITY_HISTORY_TRACE = 0x11;
	public  final int						PROGRESS_DIALOG = 0x12;
	public  static final String  			MAP_URL = "file:///android_asset/map.html";
	//public  static final String  			MAP_URL = "file:///android_asset/10056.html";
	public  boolean	    					m_bFlag = false;
	public  Button							m_bthCar = null;
	public  boolean							m_bCarMove = false;
	
	private WebView							m_oWebView = null;
	public  Timer							m_oAMapViewTime = null;
	private Handler 						m_oProgressHandler = null;  
	public  AMapViewTask					m_oAMapViewTask = null;
	

	public 	Dialog 							m_oPlayPromptDlg = null;
	
	//*******************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
		
		InitMapWebView();
		m_bthCar = (Button)findViewById( R.id.map_car );
	    m_bthCar.setOnClickListener( showCarEvent );
	    m_bthCar.setOnTouchListener( carTouchEvent );
	    
	    m_bthCar.setText( Language.getLangStr(Language.TEXT_CLICK_ME));
	        
		ProgressMessageHandler();
        if( m_oAMapViewTime == null ){
        	m_oAMapViewTime = new Timer();
	    }	      
	    // 一秒检测一次是否登陆成功		
        m_oAMapViewTask = new AMapViewTask();
        m_oAMapViewTime.schedule(m_oAMapViewTask, 200, 1000 );
	}
	//*******************************************************
	// 初始化地图界面
	public  void InitMapWebView(){
		
		m_oWebView = (WebView)findViewById( R.id.webview);
		m_oWebView.getSettings().setJavaScriptEnabled(true);   	      	      	  
		m_oWebView.setWebViewClient(new WebViewClient(){ 
	  	    @Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				
		  	    Log.e("map", "1:"+url);
				super.onPageStarted(view, url, favicon);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
		  	      Log.e("map", "2:"+url);
				return super.shouldOverrideUrlLoading(view, url);
			}
			@Override 
	  	    public void onPageFinished(WebView view, String url){ 
				
	  	      Log.e("map", "3:"+url);
	  	    } 
  	  	}); 
		m_oWebView.loadUrl(MAP_URL);		
	}
	//**************************************************
	//
	OnClickListener	 showCarEvent = new OnClickListener(){

		@Override
		public void onClick(View v) {
						
			if( m_bCarMove  ){		    	
				
				Intent 				intent = new Intent();
				
			    intent.setClass( AMapView.this, MapShowVehicleActivity.class );	   
				startActivityForResult(intent, ACTIVITY_MAP_VEHICLE );	
			}
		}		
	};
	//******************************************************
	//
	@Override
	protected Dialog onCreateDialog(int id) {

		if( id ==  PROGRESS_DIALOG ){
			
			ProgressDialog  dialog = new ProgressDialog(AMapView.this);
		    dialog.setIndeterminate(true);
		    dialog.setMessage(Language.getLangStr(Language.TEXT_RUN_PLEASE_WAIT));
		    dialog.setCancelable(true);
		    dialog.show();
		    return dialog;
		}
		else{
			return super.onCreateDialog(id);
		}
	}
	//*******************************************************
	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if( resultCode == RESULT_OK ){
			if( requestCode == ACTIVITY_MAP_VEHICLE ){
				m_bFlag = false;
				//删除地图车辆 	
				deleteAllMapVehicle();				
				//重新加载车辆
				updateMapVehicle();
			}
			// 轨迹回放
			else if( requestCode == ACTIVITY_HISTORY_TRACE ){
				
				m_oPlayPromptDlg = onCreateDialog( PROGRESS_DIALOG );
				Log.e("Play", "数据");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	//**************************************************
	//
	OnTouchListener carTouchEvent = new OnTouchListener(){
		
		int 	temp[] = new int[]{0, 0};
		long 	lTime = 0;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			int 	x = (int)event.getRawX();
            int 	y = (int)event.getRawY();                        
			int		eventAction = event.getAction();
			
			switch( eventAction ){
			case MotionEvent.ACTION_DOWN: 
				temp[0] = (int)event.getX();
                temp[1] = (int)(y-v.getTop());  
                v.postInvalidate();                    
                lTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_MOVE:
				v.layout( x-temp[0], 
	       				  y-temp[1], 
	       				  x-temp[0]+v.getWidth(), 
	       				  y-temp[1]+v.getHeight()); 
				break;
            case MotionEvent.ACTION_UP:
            	if( (System.currentTimeMillis()-lTime) > 500 ){
            		m_bCarMove = false;
            	}
            	else{
            		m_bCarMove = true;
            	}  
            	break;
			}
			return false;
		}		
	};
	//**************************************************
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				
		if( GlobalData.getCreateMenu() == GlobalData.CREATE_MENU_MAP_WINDOW ){ 
			
			Log.e("pop_Menu", "AMapView");
    		GlobalData.setCreateMenu(GlobalData.CREATE_MENU_NO);
    		menu.add(0, Maptrack_ID.ID_REFRESH, 0, Language.getLangStr(Language.TEXT_REFRESH)).setIcon(R.drawable.refresh);
    		menu.add(0, Maptrack_ID.ID_ZOOMALL, 0, Language.getLangStr(Language.TEXT_ZOOMALL)).setIcon(R.drawable.earth);
        	menu.add(0, Maptrack_ID.ID_GOOGLE_VEHICLE_SHOW, 0, Language.getLangStr(Language.TEXT_VEHICLE_SHOW)).setIcon(R.drawable.car);
	 
	    	menu.add(0, Maptrack_ID.ID_VEHICLE_HISTORY_TRACE, 0, Language.getLangStr(Language.TEXT_VEHICLE_HISTORY_TRACE)).setIcon(R.drawable.history);
	    	menu.add(0, Maptrack_ID.ID_HELP, 0, Language.getLangStr(Language.TEXT_HELP)).setIcon(R.drawable.help);
	    	menu.add(0, Maptrack_ID.ID_EXIT, 0, Language.getLangStr(Language.TEXT_EXIT)).setIcon(R.drawable.cancel);
			
		}
		return super.onCreateOptionsMenu(menu);
	}
	//**************************************************
	//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("弹出", "AMapView");
		switch( item.getItemId() ){
		case Maptrack_ID.ID_ZOOMALL:						//图层管理			
			setZoomAll();
			break;
		case Maptrack_ID.ID_REFRESH:						//刷新
			m_oWebView.loadUrl(MAP_URL);
			break;
		case Maptrack_ID.ID_GOOGLE_VEHICLE_SHOW:			//车辆显示
			setGoogleVehicleShow();
			break;
		case Maptrack_ID.ID_VEHICLE_HISTORY_TRACE:			
			setVehicleHistoryTrack();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//***************************************************
	//
	public void  setZoomAll(){
		
		String  strJavascript = "javascript:mapZoomAll()"; 
		
		m_oWebView.loadUrl(strJavascript); 
				
	}
	//******************************************************
	//
	public void	setGoogleVehicleShow(){
		
		Intent 					intent = new Intent();
		
	    intent.setClass( AMapView.this, MapShowVehicleActivity.class );	   
		startActivityForResult(intent, ACTIVITY_MAP_VEHICLE );
	}
	//*******************************************************
	//
	public void setVehicleHistoryTrack(){
		
		Intent					intent = new Intent();
		
	    intent.setClass( AMapView.this, PlaySetting.class );	   
		startActivityForResult(intent, ACTIVITY_HISTORY_TRACE );
	}
	//********************************************************
	//
	@Override
	protected void onDestroy() {

		if( m_oAMapViewTask != null ){
			m_oAMapViewTask.cancel();
		}
		if( m_oPlayPromptDlg != null ){
			m_oPlayPromptDlg.cancel(); 
		}
		if( m_oWebView != null ){
			
			CacheWebView();
			m_oWebView.destroy();
		}
		super.onDestroy();
	}
	//********************************************************
	// 退出清除
	public  void CacheWebView(){
		
		File file = CacheManager.getCacheFileBaseDir(); 
		if (file != null && file.exists() && file.isDirectory()) { 
			for (File item : file.listFiles()) { 
				item.delete(); 
			} 
			file.delete(); 
		}
		this.getApplicationContext().deleteDatabase("webview.db");
		this.getApplicationContext().deleteDatabase("webviewCache.db");
	}
	//********************************************************
	//
	public  void  deleteAllMapVehicle(){

    	String 				 strJavascript="";
		CarInfo				 oCarInfo = null;
		ArrayList<CarInfo>   lstCarInfo = null;
		
		lstCarInfo = GlobalData.getAllVehicle();
		for( int nCnt = 0; nCnt < lstCarInfo.size(); nCnt++ ){
			
			oCarInfo = (CarInfo)lstCarInfo.get(nCnt);
			if( oCarInfo != null ){
				
				strJavascript = "javascript:mapDeleteMarker('"+ oCarInfo.GetDEUID() +"')";

				Log.e("del_car", strJavascript );
				m_oWebView.loadUrl(strJavascript); 
			}
		}
	}
	//******************************************************
  	// 显示域更新地图
    public  void  DelMapVehilce(){

    	String 				strJavascript="";
    	String				strDEUID = "";
    	   	
    	List<MapVehicle>	lstMapVehicle = null;	
    	
    	lstMapVehicle = GlobalData.getDelMapDEUID();
    	if( lstMapVehicle.size() <= 0 ){   
    		
    		return ;
    	}    	
    	for( int nCnt = 0; nCnt < lstMapVehicle.size(); nCnt++ ){
    		
    		strDEUID = lstMapVehicle.get(nCnt).getDEUID();    
			strJavascript = "javascript:mapDeleteMarker('"+ strDEUID +"')";
    		
			Log.e("del_car", strJavascript );
			m_oWebView.loadUrl(strJavascript);  
    	}
    }    
    //******************************************************
  	//  更新地图车辆
    public  void  updateMapVehicle(){
    	    	
    	
    	String 				strXML = "";
    	String				strJavascript = "";
    	String				strDEUID;
    	CarInfo 			oCarInfo = null;
    	GPSData				oGPSData = null;
    	MapVehicle			oMapVehicle = null;
    	List<MapVehicle>	lstMapVehicle = null;


    	lstMapVehicle = GlobalData.getShowMapDEUID();
    	if( lstMapVehicle.size() <= 0 ){    		
    		return ;
    	}
    	for( int nCnt = 0; nCnt < lstMapVehicle.size(); nCnt++ ){
    		
    		oMapVehicle = lstMapVehicle.get(nCnt);
    		strDEUID = oMapVehicle.getDEUID();    		
    		oCarInfo = GlobalData.findDEUIDByCarInfo(strDEUID);
    		
    		if( oCarInfo == null ){
    			continue;
    		}
    		oGPSData = GlobalData.getRamDBGPSData(strDEUID);  
    		if( oGPSData != null ){ 
    			strXML = String.format( "<div align=left><font size=1> <b>%s</b><br><br>%s: %s<br>%s:%d<br>%s%s %.6f %.6f<br>%s:%s<br>%s:%s<br>%s:%s %s<br>%s:%s<br>%s:%d<br>%s:%s</font></div>",	
						 oCarInfo.GetCarLicense(),
						 Language.getLangStr(Language.TEXT_TIME) ,
						 oGPSData.getTime() ,
						 Language.getLangStr(Language.TEXT_SPEED_KMH),
						 oGPSData.getSpeed(), 
						 Language.getLangStr(Language.TEXT_LATITUDE),
						 Language.getLangStr(Language.TEXT_LONGITUDE),
						 oGPSData.getLat(),
						 oGPSData.getLon(),
						 Language.getLangStr(Language.TEXT_DEUID),
						 oGPSData.getDEUID(),
						 Language.getLangStr(Language.TEXT_TELNO),
						 oCarInfo.GetDESIM(),
						 Language.getLangStr(Language.TEXT_NAME),
						 oCarInfo.GetFName(),
						 oCarInfo.GetLName(),
						 Language.getLangStr(Language.TEXT_TELNO),
						 oCarInfo.GetOwnerTel(),
						 Language.getLangStr(Language.TEXT_DRIVING_TIME) ,
						 oGPSData.getDriverTime(),
						 Language.getLangStr(Language.TEXT_ADDRESS),
						 oGPSData.getAddr() );
    			
    			// 显示到地图中心
    			if( oMapVehicle.isCenterShow() ){
	    			strJavascript = String.format("javascript:mapDisplayGPSObj(\"({uiid:'%s',lng:%.6f,lat:%.6f,speed:%d,direction:%d,alState:%d,name:'%s',description:'%s',showInfo:true})\")",
						    					oCarInfo.GetDEUID(),
						    					oGPSData.getLon(),
						    					oGPSData.getLat(),
						    					oGPSData.getSpeed(),
						    					oGPSData.getDirection(),
						    					oGPSData.getAlarmState(),
						    					oCarInfo.GetCarLicense(),
						    					strXML ); 
	    			oMapVehicle.setCenterShow(false); 
    			}
    			else{
	    			strJavascript = String.format("javascript:mapDisplayGPSObj(\"({uiid:'%s',lng:%.6f,lat:%.6f,speed:%d,direction:%d,alState:%d,name:'%s',description:'%s',showInfo:false})\")",
						    					oCarInfo.GetDEUID(),
						    					oGPSData.getLon(),
						    					oGPSData.getLat(),
						    					oGPSData.getSpeed(),
						    					oGPSData.getDirection(),
						    					oGPSData.getAlarmState(),
						    					oCarInfo.GetCarLicense(),
						    					strXML );  
    			} 
    				
    			Log.e("add car", strJavascript);
    			m_oWebView.loadUrl(strJavascript); 
    			if( m_bFlag == false ){
    				
    				strJavascript = String.format("javascript:SetZoomin(12)");    				
    				m_oWebView.loadUrl(strJavascript); 
    				strJavascript = String.format("javascript:centerAt(%.6f,%.6f)", oGPSData.getLat(),
    																			    oGPSData.getLon());
    				m_oWebView.loadUrl(strJavascript);
    				m_bFlag = true;
    			}
    		}
    	}
    }
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(AMapView.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//******************************************************
	//
	public void  openPlayWindow(){
		
		Intent intent = new Intent();
		
		if( m_oPlayPromptDlg != null ){
			
			m_oPlayPromptDlg.cancel();
			m_oPlayPromptDlg = null;			
			
		    intent.setClass( AMapView.this, PlayVehicleMap.class );
			startActivity( intent );
		}
	}
	//******************************************************
  	//  进程消息处理
  	public  void  ProgressMessageHandler(){
  		
  		m_oProgressHandler = new Handler() {
      		@Override  
              public void handleMessage(Message msg) {
      			int nMessageType = msg.getData().getInt("ProgrameType");
      			switch( nMessageType ){
      			case ProgramType.RECV_GPSDATA:		    //更新GPS数据
      				updateMapVehicle();
      				DelMapVehilce();
      				break;
      			case ProgramType.VEHICLE_SHOWMAP:	    //显示车辆
      				updateMapVehicle();
      				DelMapVehilce();
      				break;  
    			case ProgramType.RECV_LOAD_PLAYGPSDATA:	//加载Play数据
    				if( m_oPlayPromptDlg != null ){
    					((ProgressDialog)m_oPlayPromptDlg).setMessage(Language.getLangStr(Language.TEXT_LOADING_DATA_PLEASE_WAIT));
    				}
    				break;
    			case ProgramType.RECV_PLAYGPSDATA:		//接收Play完成    				
    				openPlayWindow();
    				break;
    			case ProgramType.RECV_NO_PLAYGPSDATA:	//没有Play数据
    				if( m_oPlayPromptDlg != null ){
    					m_oPlayPromptDlg.cancel(); 
    					m_oPlayPromptDlg = null;
    					showWarringDialog(Language.getLangStr(Language.TEXT_WARING_NO_DATA));
    				}
    				break;    
    			case ProgramType.RECV_LOAD_PLAYATA_TIMEOUT:	 //接收回放数据超时
    				if( m_oPlayPromptDlg != null ){
    					m_oPlayPromptDlg.cancel(); 
    					m_oPlayPromptDlg = null;
    					showWarringDialog(Language.getLangStr(Language.TEXT_FAIL));
    				}
    				break;
    		//	case ProgramType.LOGIN_SUCCEED:
    		//		Log.e("MAP", "重新加载地图!");
    		//		m_oWebView.loadUrl(MAP_URL);
    		//		break;
      			}
      		}
      	};
  	}
	//********************************************
    // 定时器的引用
    class AMapViewTask extends java.util.TimerTask{

    	public  int   		m_nCnt = 0;
    	public  boolean 	m_bStartFlag = false;
    	
    	//*************************************************
    	//
		@Override
		public void run() {
			int			    nType = 0;
			Message 		msg = null;		
					
			nType = GlobalData.getRecvAMap();
			switch( nType ){		
			case ProgramType.RECV_GPSDATA:	  //收到GPS数据
			case ProgramType.VEHICLE_SHOWMAP:  //显示车辆
			case ProgramType.RECV_LOAD_PLAYGPSDATA:
			case ProgramType.RECV_LOAD_PLAYATA_TIMEOUT:
			case ProgramType.RECV_PLAYGPSDATA:
			case ProgramType.RECV_NO_PLAYGPSDATA:
			case ProgramType.LOGIN_SUCCEED:
				msg = new Message();	
				msg.getData().putInt("ProgrameType", nType );
				if( m_oProgressHandler != null ){
					m_oProgressHandler.sendMessage(msg);
				}
				break;
			}
			checkLoadMapUrl();
		}
		//****************************************************
		//
		public  void  checkLoadMapUrl(){
			
			if( m_bStartFlag == false ){
				
				if( GlobalData.isLongSucceed() ){
					
					m_nCnt++;
					if( m_nCnt >= 4 ){
						
						m_nCnt = 0;
						Log.e("MAP1", "重新加载地图!");
						m_oWebView.loadUrl(MAP_URL);
						m_bStartFlag = true;
					}
				}
			}
		}
    }
}
