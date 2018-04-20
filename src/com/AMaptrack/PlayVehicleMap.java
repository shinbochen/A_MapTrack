package com.AMaptrack;

import java.util.Timer;

import com.Data.GlobalData;
import com.Language.Language;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class PlayVehicleMap extends Activity {

	public 		Button				m_bthPlay = null;
	public      Button				m_bthPause = null;
	public      Button				m_bthStop = null;

	public  	Timer				m_oPlayViewTime = null;
	public  	PlayViewTask		m_oPlayViewTask = null;
	public 		WebView				m_oWebView = null;
	// 0:	Play
	// 1:	Pause
	// 2:   Stop
	public      int					m_nPlayFlag = 0;			//标志
	public      boolean				m_bFlag = false;
		
	//***************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView( R.layout.playmapview );
		
		this.setTitle( Language.getLangStr( Language.TEXT_VEHICLE_HISTORY_TRACE) );
		initEvent();		
	}
	//*******************************************************
	//
	public void initEvent(){
		
		m_bthPlay = (Button)findViewById( R.id.map_play );
		m_bthPause = (Button)findViewById( R.id.map_pause );
		m_bthStop = (Button)findViewById( R.id.map_stop );
		m_oWebView = (WebView)findViewById(R.id.webview);
			
		m_bthPlay.setEnabled(true);
		m_bthPause.setEnabled(false);
		m_bthStop.setEnabled(true);
		
		m_oWebView.getSettings().setJavaScriptEnabled(true);     	      	      	  
		m_oWebView.setWebViewClient(new WebViewClient());
		m_oWebView.loadUrl( AMapView.MAP_URL);
		
		m_bthPlay.setOnClickListener( clickPlayEvent );
		m_bthPause.setOnClickListener( clickPauseEvent );
		m_bthStop.setOnClickListener( clickStopEvent );
		
		m_bthPlay.setText( Language.getLangStr(Language.TEXT_PLAY) );
		m_bthPause.setText( Language.getLangStr(Language.TEXT_PAUSE) );
		m_bthStop.setText( Language.getLangStr(Language.TEXT_STOP) );
	}
	//*******************************************************
	//
	OnClickListener clickPlayEvent = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			m_nPlayFlag = 0;
			m_bthPlay.setEnabled(false);
			m_bthPause.setEnabled(true);
			m_bthStop.setEnabled(true);
			
			if( m_oPlayViewTime == null ){				
				m_oPlayViewTime = new Timer();		    	      
			    // 一秒检测一次是否登陆成功		
				m_oPlayViewTask = new PlayViewTask();
		        m_oPlayViewTime.schedule(m_oPlayViewTask, 200, 800 );
			}
		}		
	};
	//*******************************************************
	//
	OnClickListener clickPauseEvent = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			m_nPlayFlag = 1;
			m_bthPlay.setEnabled(true);
			m_bthPause.setEnabled(false);
			m_bthStop.setEnabled(true);
		}
	};
	//*******************************************************
	//
	OnClickListener clickStopEvent = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			m_nPlayFlag = 2;
			m_bthPlay.setEnabled(false);
			m_bthPause.setEnabled(false);
			m_bthStop.setEnabled(true);
			
			StopHistory();
		}		
	};
	//*******************************************************
	public  void  PlayHistory(){

		String 				strXML = "";
    	String				strJavascript = "";
    	CarInfo 			oCarInfo = null;
		GPSData				oGPSData = null;
		
		oGPSData = GlobalData.getNextPlayData();
		if( oGPSData != null){
			
			if( m_bFlag == false ){
				
				strJavascript = String.format("javascript:SetZoomin(12)");    				
				m_oWebView.loadUrl(strJavascript); 
				
				strJavascript = String.format("javascript:centerAt(%.6f,%.6f)", oGPSData.getLat(),
																			    oGPSData.getLon());
				m_oWebView.loadUrl(strJavascript);
				m_bFlag = true;
			}
			
			oCarInfo = GlobalData.findDEUIDByCarInfo(oGPSData.getDEUID());
			strXML = String.format( "<div align=left><font size=1> <b>%s</b><br><br>%s: %s<br>%s:%d<br>%s%s %.6f %.6f<br>%s:%s<br>%s:%s<br>%s:%s %s<br>%s:%s<br>%s:%d<br>%s:%s</font></div>",	
									 oCarInfo.GetCarLicense(),
									 Language.getLangStr(Language.TEXT_TIME),
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
			strJavascript = String.format("javascript:history_play(\"({uiid:'%s',lng:%.6f,lat:%.6f,speed:%d,direction:%d,alState:%d,name:'%s',description:'%s',showInfo:true})\")",
									oCarInfo.GetDEUID(),
									oGPSData.getLon(),
									oGPSData.getLat(),
									oGPSData.getSpeed(),
									oGPSData.getDirection(),
									oGPSData.getAlarmState(),
									oCarInfo.GetCarLicense(),
									strXML );
			m_oWebView.loadUrl(strJavascript);  
		}
		else{ 
			Log.e("Play", "回放完成!");		
		}
	}
	//*******************************************************
	//
	public  void StopHistory(){
		
		m_oWebView.loadUrl("javascript:history_stop()");
		finish();
	}	
	//*******************************************************
	//
	@Override
	public void finish() {
		Log.e("play_map", "finish");
		super.finish();
	}
	//*******************************************************
	//
	@Override
	protected void onDestroy() {
		
		if( m_oPlayViewTask != null ){
			m_oPlayViewTask.cancel();
			GlobalData.clearPlayGPSData();
			Log.e("play_map", "destroy");
		}		
		super.onDestroy();
	}
	//********************************************
    // 定时器的引用
    class PlayViewTask extends java.util.TimerTask{

    	
		@Override
		public void run() {
						
			// play
			if( m_nPlayFlag == 0 ){
				PlayHistory();
			}	
		}
    }
}
