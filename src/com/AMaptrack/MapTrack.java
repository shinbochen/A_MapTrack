package com.AMaptrack;

//"0sLMkpuGqn9ci5_DYNE71hvHBflrWfWJZzhwsnw" (公司)
//0XdL4qum36L_WbR1ks7F__92Sh_VUG2HoI7otHw	(家)

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.Data.GlobalData;
import com.Data.Maptrack_ID;
import com.Data.ProgramType;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;
import com.Protocol.UserInfo;
import com.Socket.ServerSocket;

import com.AMaptrack.AMapView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log ;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.app.AlertDialog;
import android.app.TabActivity;  
import android.content.Context;  
import android.content.DialogInterface;
import android.content.Intent;  

import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TabHost;  
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;  



public class MapTrack extends TabActivity {
	
	public  static  final	String  m_strVersion = "V2.2_20131103";

	
	public 	UserSharedPreferences	m_oSharedpre = null;
	
	public 	boolean					m_bRecordFlag = false;
	public 	String					m_strLoginUser="";
	public 	String 	    			m_strLoginPsd="";
	public  TabHost 				m_tabHost = null;
    public 	TabSpec 				m_tabMap = null;
    public 	TabSpec 				m_tabAlarmReport = null;
    public 	TabSpec 				m_tabVehicle = null;
	
	private Handler 				m_oProgressHandler = null;
	public  Timer					m_oMapTrackTimer = null;
	public  MapTrackTask			m_oMapTrackTask = null;
	public  List<Thread>			m_lstThread = null;	
	public  ListView				m_lstLog = null;
	public  LogInfoAdapter			m_logInfoAdapter = null;
	
    //*************************************************
    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);        
        this.setTitle( R.string.app_name );
        
        GetInitData();    
        // 初始化全局数据变量
        GlobalData.init( this.getApplicationContext() );
        initCreateTabSpec();
        initEvent();
        ProgressMessageHandler();
        if( m_oMapTrackTimer == null ){
    	  m_oMapTrackTimer = new Timer();
	    }	      
        //一秒检测一次是否登陆成功		
        m_oMapTrackTask = new MapTrackTask();
        m_oMapTrackTimer.schedule(m_oMapTrackTask, 200, 1000 );
		// 开启登陆窗口
		startLogin();	
    }  
    //*************************************************
    //   获取配置参数
    public 	void    GetInitData(){
    	
    	String		strVersion = "";
    	String		strTmp = "";
    	
    	
    	m_oSharedpre = new UserSharedPreferences(this, "maptrack");
    	strVersion = m_oSharedpre.getValue( ConfigKey.KEY_VERSION, "");
    	    	
    	if( m_strVersion.equals(strVersion) ){
    		restoreInitData();
    	}
    	else{
    		strTmp = m_strVersion.substring(0, 3 );
    		if( strVersion.equals(strTmp) ){
    			restoreInitData();
    		}
    		else{
    			m_oSharedpre.setValue( ConfigKey.KEY_VERSION, m_strVersion );
    			saveInitData();
    		}
    	}
    }
    //*************************************************
    //
    public  void restoreInitData(){
    	
    	GlobalData.m_strIP = m_oSharedpre.getValue( ConfigKey.KEY_IP, "203.86.9.28");
    	GlobalData.m_nPort = m_oSharedpre.getValue( ConfigKey.KEY_PORT, 9990 );		
		m_bRecordFlag = m_oSharedpre.getValue( ConfigKey.KEY_RECORDPSD, false );		
		m_strLoginUser = m_oSharedpre.getValue( ConfigKey.KEY_LOGIN_USER, "");
		m_strLoginPsd = m_oSharedpre.getValue( ConfigKey.KEY_LOGIN_PSD, "");
		Language.setLang( m_oSharedpre.getValue( ConfigKey.KEY_LANG, 0 ) );
		
		Log.e("Read IP", GlobalData.m_strIP );
		Log.e("Read Port", String.valueOf(GlobalData.m_nPort));
    }
    //*************************************************
    //
    public  void  saveInitData(){
    	
    	m_oSharedpre.setValue( ConfigKey.KEY_IP, GlobalData.m_strIP );
		m_oSharedpre.setValue( ConfigKey.KEY_PORT, GlobalData.m_nPort );		
		m_oSharedpre.setValue( ConfigKey.KEY_RECORDPSD, m_bRecordFlag );		
		m_oSharedpre.setValue( ConfigKey.KEY_LOGIN_USER, m_strLoginUser);
		m_oSharedpre.setValue( ConfigKey.KEY_LOGIN_PSD, m_strLoginPsd);
		m_oSharedpre.setValue( ConfigKey.KEY_LANG, Language.getLang() );
    } 
    //*************************************************
    // 
    public  void  initLable(){
    	   
    	m_tabMap.setIndicator( Language.getLangStr(Language.TEXT_MAP_WINDOW) , getResources().getDrawable(R.drawable.map) );
    	
    	m_tabVehicle.setIndicator(Language.getLangStr(Language.TEXT_MONITOR_OBJECT), getResources().getDrawable(R.drawable.monitor) );
    	m_tabAlarmReport.setIndicator(Language.getLangStr(Language.TEXT_ALARM_REPORT), getResources().getDrawable(R.drawable.alarminfo) );
    	saveInitData();
   }
    //*************************************************
    // 创建Tab初始化状态
    public void initCreateTabSpec(){

    	m_tabHost = getTabHost();
        
    	m_tabMap = m_tabHost.newTabSpec("tab_MapWindow");
    	m_tabMap.setIndicator( Language.getLangStr(Language.TEXT_MAP_WINDOW) , getResources().getDrawable(R.drawable.map) );
    	Context ctxMap = this.getApplicationContext();
        Intent iMap = new Intent(ctxMap, AMapView.class);
        m_tabMap.setContent(iMap);
        m_tabHost.addTab(m_tabMap);      
        
        m_tabVehicle = m_tabHost.newTabSpec("tab_MonitorObject");
        m_tabVehicle.setIndicator(Language.getLangStr(Language.TEXT_MONITOR_OBJECT), getResources().getDrawable(R.drawable.monitor) );
        Context ctxVehicle = this.getApplicationContext();
        Intent iVehicle = new Intent(ctxVehicle, VehicleList.class);
        m_tabVehicle.setContent(iVehicle);  
        m_tabHost.addTab(m_tabVehicle);
                
        m_tabAlarmReport = m_tabHost.newTabSpec("tab_AlarmReoprt");
        m_tabAlarmReport.setIndicator( Language.getLangStr(Language.TEXT_ALARM_REPORT), getResources().getDrawable(R.drawable.alarmreport) );
        Context ctxAlarmInfo = this.getApplicationContext();
        Intent iAlarmInfo = new Intent(ctxAlarmInfo, AlarmInfo.class);
        m_tabAlarmReport.setContent(iAlarmInfo);  
        m_tabHost.addTab(m_tabAlarmReport);
        
        TabSpec tabLogInfo = m_tabHost.newTabSpec("tab_Log");
        tabLogInfo.setIndicator(Language.getLangStr(Language.TEXT_OTHER), getResources().getDrawable(R.drawable.alarminfo) );
        tabLogInfo.setContent( R.id.list_Loginfo );    
        m_tabHost.addTab( tabLogInfo );
        
        m_tabHost.setCurrentTab(0);
        GlobalData.setCreateMenu( GlobalData.CREATE_MENU_MAP_WINDOW );
        m_tabHost.setOnTabChangedListener( eventTab );
     }
    //*************************************************
    // 
    public  void initEvent(){
    	
    	m_lstLog = (ListView)findViewById( R.id.list_Loginfo );
    	
    	m_logInfoAdapter = new LogInfoAdapter( getApplicationContext(), null );		
    	m_lstLog.setAdapter(m_logInfoAdapter );
    }
    //*************************************************
    // 
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		    	
    	if( GlobalData.getCreateMenu() == GlobalData.CREATE_MENU_ALARM_INFO ){
    		
    		Log.e("pop_Menu", "MapTrack");
    		GlobalData.setCreateMenu(GlobalData.CREATE_MENU_NO);
    		
	    	menu.add(0, Maptrack_ID.ID_USER_MANAGE, 0, Language.getLangStr(Language.TEXT_USER_MANAGE)).setIcon(R.drawable.user);
	    	menu.add(0, Maptrack_ID.ID_MODIFY_PASD, 0, Language.getLangStr(Language.TEXT_MODIFY_PASD)).setIcon(R.drawable.psd);
	    	menu.add(0, Maptrack_ID.ID_HELP, 0, Language.getLangStr(Language.TEXT_HELP)).setIcon(R.drawable.help);
	    	menu.add(0, Maptrack_ID.ID_EXIT, 0, Language.getLangStr(Language.TEXT_EXIT)).setIcon(R.drawable.cancel);
		}
    	return super.onCreateOptionsMenu(menu);
	}
    //*************************************************
    // 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
				
		Log.e("弹出", "MapView");
		switch( item.getItemId() ){
		case Maptrack_ID.ID_USER_MANAGE:					//用户管理
			UserManage();
			break;
		case Maptrack_ID.ID_MODIFY_PASD:					//修改密码
			ModifyUserPsd();
			break;
		case Maptrack_ID.ID_HELP:							//关于
			about();
			break;
		case Maptrack_ID.ID_EXIT:							//退出
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//*******************************************************
	//
	public  void  about(){
		AlertDialog.Builder  builder = new AlertDialog.Builder(this);
		//V1.4_20120711
		builder.setTitle(Language.getLangStr(Language.TEXT_HELP));
		builder.setMessage( "Version:"+m_strVersion+" \r\nCopyRight(C) 2014(C1W2)").
			setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.e("exit", "finish");
					return ;
				}
		});	

		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//*******************************************************
	//
	public void UserManage(){
		
		UserInfo	oUserInfo = null;
		
		oUserInfo = GlobalData.getUserInfo();
		if( oUserInfo == null ){
			return ;
		}
		Intent intent = new Intent();
		Bundle		bundle = new Bundle();
		
		bundle.putInt(ConfigKey.KEY_WORKMODE, 1 );
		bundle.putString(ConfigKey.KEY_USER, oUserInfo.getUserName() );
		bundle.putString(ConfigKey.KEY_PSD, oUserInfo.getUserPsd() );
		bundle.putString(ConfigKey.KEY_FNAME, oUserInfo.getFName() );
		bundle.putString(ConfigKey.KEY_LNAME, oUserInfo.getLName() );
		bundle.putString(ConfigKey.KEY_COMPANY, oUserInfo.getCoName() );
		bundle.putString(ConfigKey.KEY_EMAIL, oUserInfo.getEmail() );
		bundle.putString(ConfigKey.KEY_TEL, oUserInfo.getTelNum() );
		
		intent.setClass( MapTrack.this, UserInfoActivity.class );
		intent.putExtras( bundle );
 		startActivityForResult(intent, Maptrack_ID.ID_ACTIVITY_USERINFO ); 		
	}
    //*************************************************
    //
	public void ModifyUserPsd(){
		
		Intent intent = new Intent();
		
		intent.setClass( MapTrack.this, ModifyPsdActivity.class );
 		startActivityForResult(intent, Maptrack_ID.ID_ACTIVITY_USERPSD );
	}
    //*************************************************
    // tab切换事件处理
    OnTabChangeListener  eventTab = new OnTabChangeListener(){    	
    	@Override
        public void onTabChanged(String tabId) {
    			
    		if( tabId.equals("tab_Log") ){		//报警记录
    	//		Log.e("Tab", "tab_Log");
    			GlobalData.setCreateMenu( GlobalData.CREATE_MENU_ALARM_INFO );
    		}
    		else if( tabId.equals("tab_MapWindow") ){	//地图窗口
    	//		Log.e("Tab", "tab_MapWindow");
    			GlobalData.setCreateMenu( GlobalData.CREATE_MENU_MAP_WINDOW );
    		}
    		else if( tabId.equals("tab_MonitorObject")  ){	//监控对像
    //			Log.e("Tab", "tab_MonitorObject");
    			GlobalData.setCreateMenu( GlobalData.CREATE_MENU_MONITOR_OBJECT );
    		}
    		else if( tabId.equals("tab_AlarmReoprt")  ){	//报警统计
    	//		Log.e("Tab", "tab_AlarmReoprt");
    			GlobalData.setCreateMenu( GlobalData.CREATE_MENU_ALARM_REPORT );
    		}
    		else{
    	//		Log.e("Tab", "no_tab");
    			GlobalData.setCreateMenu(GlobalData.CREATE_MENU_NO);
    		}
        }      
    };
       
    //*************************************************
    //  开启登陆窗口
    public void startLogin(){
    	
    	Intent intent = new Intent();
		Bundle  bundle = new Bundle();
		
		bundle.putString( ConfigKey.KEY_IP, GlobalData.m_strIP );
		bundle.putInt( ConfigKey.KEY_PORT, GlobalData.m_nPort );		
		bundle.putBoolean( ConfigKey.KEY_RECORDPSD, m_bRecordFlag );		
		bundle.putString( ConfigKey.KEY_LOGIN_USER, m_strLoginUser);
		bundle.putString( ConfigKey.KEY_LOGIN_PSD, m_strLoginPsd);
		bundle.putInt( ConfigKey.KEY_LANG, Language.getLang() );
	    intent.setClass( MapTrack.this, Login.class );
		intent.putExtras( bundle );
		startActivityForResult(intent, Maptrack_ID.ID_REQUESTCODE_LOGIN ); 
    }
    //*************************************************
    // 返回状态码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        if( resultCode == RESULT_OK ){
        	Bundle bundle = data.getExtras();
        	switch( requestCode ){
        	case Maptrack_ID.ID_REQUESTCODE_LOGIN: 
        		if( bundle.getBoolean(ConfigKey.KEY_EXIT) == true ){
        			onClose();
        		}
        		else{        			
	        		GlobalData.m_strIP = bundle.getString(ConfigKey.KEY_IP);
	        		GlobalData.m_nPort = bundle.getInt(ConfigKey.KEY_PORT);
	        		m_bRecordFlag = bundle.getBoolean( ConfigKey.KEY_RECORDPSD );
	        		m_strLoginUser = bundle.getString(ConfigKey.KEY_LOGIN_USER );
	        		m_strLoginPsd = bundle.getString(ConfigKey.KEY_LOGIN_PSD );
	        		
	        		saveInitData();
        		}
        		break;
        	case  Maptrack_ID.ID_ACTIVITY_USERINFO:
        		ModifyfUser( bundle);
        		break;
        	case Maptrack_ID.ID_ACTIVITY_USERPSD:
        		ModifyUserPsd( bundle );
        		break;
        	}
        }
    }
    //*************************************************
    //  修改用户密码
    public void ModifyUserPsd( Bundle  bundle ){
    	    	
    	ServerCommand		oCmd = new ServerCommand();
    	UserInfo 			oUserInfo = GlobalData.getUserInfo();
    	
    	oUserInfo.setUserPsd( bundle.getString(ConfigKey.KEY_NEWPSD) );
    	oCmd.ModifyUserCmd(oUserInfo);
    	GlobalData.addSendServerData(oCmd);
    }
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
				
		AlertDialog.Builder  builder = new AlertDialog.Builder(MapTrack.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
    //*************************************************
    //  修改用户信息
    public  void ModifyfUser( Bundle  bundle ){
    	
    	ServerCommand		oCmd = new ServerCommand();
    	UserInfo 			oUserInfo = GlobalData.getUserInfo();
    	    	
    	oUserInfo.setUserName(  bundle.getString(ConfigKey.KEY_USER) );
    	oUserInfo.setUserPsd( bundle.getString(ConfigKey.KEY_PSD) );
    	oUserInfo.setFName( bundle.getString(ConfigKey.KEY_FNAME) );
    	oUserInfo.setLName(bundle.getString(ConfigKey.KEY_LNAME) );
    	oUserInfo.setCoName( bundle.getString(ConfigKey.KEY_COMPANY) );
    	oUserInfo.setEmail( bundle.getString(ConfigKey.KEY_EMAIL) );
    	oUserInfo.setTelNum(bundle.getString(ConfigKey.KEY_TEL) );
    	oCmd.ModifyUserCmd(oUserInfo);
    	GlobalData.addSendServerData(oCmd);
    }
    //*************************************************
    //
	@Override
	public void finish() {
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(this);
		
		builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(Language.getLangStr(Language.TEXT_ARE_YOU_SURE)).
			setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.e("exit", "finish");
					onClose();
				}
		}).setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
					
				Log.e("exit", "result");
				return ;
			}
		});	
		AlertDialog dlg = builder.create();
		dlg.show();
	} 
	//**************************************************
	// 退出
	public  void onClose(){
		
		m_oMapTrackTask.cancel();
		GlobalData.FreeMemory();
		
		super.finish();
	}
	//*************************************************
    // 检测是否有车辆报警
	public  void  chkAlarmGPSData(){
		
		LogInfo			oLogInfo = null;
		List<GPSData>	lstGPSData = null;
		
		lstGPSData = GlobalData.findNewAlarmGPSData();
		for( int nCnt = 0; nCnt < lstGPSData.size(); nCnt++ ){
			
			oLogInfo = new LogInfo( lstGPSData.get(nCnt).getDEUID(), 
									lstGPSData.get(nCnt) );
			m_logInfoAdapter.addLogInfo(oLogInfo);
		}
	}
	//*************************************************
	//
	public void  chkControlGPSData(){
		
		LogInfo			oLogInfo = null;
		List<GPSData>	lstGPSData = null;
		
		lstGPSData = GlobalData.findNewControlGPSData();
		for( int nCnt = 0; nCnt < lstGPSData.size(); nCnt++ ){
			
			oLogInfo = new LogInfo( lstGPSData.get(nCnt).getDEUID(), 
									lstGPSData.get(nCnt) );
			m_logInfoAdapter.addLogInfo(oLogInfo);
		}
	}
	//*************************************************
    //
	public  void  chkOperatingLog(){
		
		LogInfo			oLogInfo = null;
		
		oLogInfo = GlobalData.getOperation();
		if( oLogInfo != null ){
			m_logInfoAdapter.addLogInfo(oLogInfo);
		}
	}
	//*************************************************
    //  处理进程序信息
    public void  ProgressMessageHandler(){    	
    
    	m_oProgressHandler = new Handler() {
    		@Override  
            public void handleMessage(Message msg) {
    			int nMessageType = msg.getData().getInt("ProgrameType");
    			switch( nMessageType ){
    			case ProgramType.LOGIN_SUCCEED:		//登陆成功
    				break;
    			case ProgramType.MODIFY_USER_SUCCEED:
    				Toast.makeText( getApplicationContext(),
						Language.getLangStr(Language.TEXT_SUCCEED), 
						Toast.LENGTH_LONG).show();
    				break;
    			case ProgramType.MODIFY_USER_FAIL:
    				Toast.makeText( getApplicationContext(),
							Language.getLangStr(Language.TEXT_FAIL), 
							Toast.LENGTH_LONG).show();
    				break;
    			case ProgramType.RECV_GPSDATA:	    //收到GPS数据
    				chkAlarmGPSData();
    				chkControlGPSData();
    				break;
    			case ProgramType.LOG_INFO:
    				chkOperatingLog();
    				break;
    			case ProgramType.UPDATE_VEHICLE_LIST_ADDR:
    				if( m_logInfoAdapter.getCount() > 0 ){
    					m_logInfoAdapter.notifyDataSetChanged();
    				}
    				break;
    			case ProgramType.UPDATE_LANGUAGE:
    				initLable();
    				break;
    			}
    		}
    	};
    }
	//********************************************
    // 定时器的引用
    class MapTrackTask extends java.util.TimerTask{

    	public 	int		m_nCnt = 0;
    	
    	//*******************************************
    	//
		@Override
		public void run() {
			
			int				nCmdType = 0; 
			Thread			oThread = null;;
			Message 		msg = null;
			
			//**测试是否有指令发送**************************************
			if( GlobalData.isSendServerData() ){
				
				// 开启新线程
				oThread =	new Thread( new ServerSocket()); 
				oThread.start();
			}
			//(96-884)
			if( ( GlobalData.getTabTitleHeight() == 0) && 
				( GlobalData.getTabHostHeight() == 0) ){
				
				GlobalData.setTabTitleHeight( getTabWidget().getChildAt(1).getHeight() );
				GlobalData.setTabHostHeight( m_tabHost.getHeight() );
			}
			
			nCmdType = GlobalData.getRecvLogInfo();
			switch( nCmdType ){
			case ProgramType.LOGIN_SUCCEED:		//登陆成功				
				m_nCnt = 26;
				msg = new Message();	
				msg.getData().putInt("ProgrameType", nCmdType );
				if( m_oProgressHandler != null ){
					m_oProgressHandler.sendMessage(msg);
				}
				break;
			case ProgramType.RECV_GPSDATA:	    //收到GPS数据	
			case ProgramType.MODIFY_USER_FAIL:
			case ProgramType.LOG_INFO:
			case ProgramType.UPDATE_VEHICLE_LIST_ADDR:
			case ProgramType.UPDATE_LANGUAGE:
			case ProgramType.MODIFY_USER_SUCCEED:	//修改
				msg = new Message();	
				msg.getData().putInt("ProgrameType", nCmdType );
				if( m_oProgressHandler != null ){
					m_oProgressHandler.sendMessage(msg);
				}
				break;
			}
			// 定时查询GPS内存数据
			if( m_nCnt++ >= 30 ){
				if( GlobalData.isLongSucceed() ){
					m_nCnt = 0;
					Log.e("登陆成功", "查询GPS数据!");					
					checkGPSRamData();
				}
			}
			//  定时读取GPS数据
			if( m_nCnt % 5 == 0 ){
				readControlCmdGPSData();
			}
		}
		//************************************************
		//  定时查询GPS内存数据
		public  void 	checkGPSRamData(){
			
			ServerCommand		oCmd = null;
			List<CarInfo>		lstCarInfo = null;
			List<String>		lstDEUID = new ArrayList<String>();
						
			lstCarInfo = GlobalData.getAllVehicle();
			for( int nCnt = 0; nCnt < lstCarInfo.size(); nCnt++ ){
				
				lstDEUID.add( lstCarInfo.get(nCnt).GetDEUID() );
			}
			if( lstDEUID.size() > 0 ){
				oCmd = new ServerCommand();
				oCmd.findRamData( GlobalData.getUserInfo().getUserName(),
							     lstDEUID );
				GlobalData.addSendServerData(oCmd);
			}
		}
		//************************************************
		//  查询控制命令数据
		public  void  readControlCmdGPSData(){
			
			ServerCommand		oCmd = null;
			List<String>		lstDEUID = null;
			
			lstDEUID =  GlobalData.getCmdTimingCheck();
			if( lstDEUID.size() > 0 ){
				
				Log.e("查询控制GPS数据", "1");				
				oCmd = new ServerCommand();
				oCmd.findRamData( GlobalData.getUserInfo().getUserName(),
							     lstDEUID );
				GlobalData.addSendServerData(oCmd);
			}
		}		
    }
    
    //****************************************************
    //   报警/操作日记,适配器
    //
    //
    public class LogInfoAdapter extends BaseAdapter {

    	public 	Context					m_oContext = null ;
    	public  List<LogInfo>			m_lstData = null;
    	public  Map<Integer, View>		m_mapObj = null;
    	
    	//***************************************************
    	// 
    	public LogInfoAdapter( Context  oContext, List<LogInfo>  inData ){
    		
    		m_oContext = oContext;
    		m_lstData = new ArrayList<LogInfo>();
    		m_lstData.clear();
    		m_mapObj = new HashMap<Integer, View>();
    		m_mapObj.clear();
    		if( inData != null ){
    			addLogInfo( inData );
    		}
    	}
        //****************************************************
        //
    	public  void addLogInfo( List<LogInfo>  inData ){
    		
    		
    		for( int nCnt = 0; nCnt < inData.size(); nCnt++ ){
    			
    			m_lstData.add( inData.get(nCnt) );
    		}    		
    		this.notifyDataSetChanged();
    	}
        //****************************************************
        //
    	public  void  addLogInfo( LogInfo  oData ){
    		
    		m_lstData.add(oData);
    		this.notifyDataSetChanged();
    	}
    	//****************************************************
    	//
    	public  void  delLogInfo( int  nPos ){
    		
    		m_lstData.remove(nPos);
    		m_mapObj.clear();
    		this.notifyDataSetChanged();
    	}
    	//****************************************************
    	//
    	public  void  delAllLogInfo(){
    		
    		m_lstData.clear();
    		m_mapObj.clear();
    		this.notifyDataSetChanged();
    	}
        //****************************************************
        //
		@Override
		public int getCount() {
			
			return m_lstData.size();
		}
        //****************************************************
        //
		@Override
		public Object getItem(int position) {

			return m_lstData.get(position);
		}
	    //****************************************************
	    //
		@Override
		public long getItemId(int position) {

			return position;
		}
	    //****************************************************
	    //
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				
			View 				oView = null;
			FeekbackView		feekView = null;
			
			if( m_mapObj.get( position ) == null) {
				
				feekView = new FeekbackView();
				LayoutInflater mInflater = (LayoutInflater) m_oContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
	            oView = mInflater.inflate(R.layout.feekback, null);
	            feekView.Feek_DEUID = (TextView)oView.findViewById(R.id.Feek_DEUID);
	            feekView.Feek_CarLicense = (TextView)oView.findViewById(R.id.Feek_CarLicense);
	            feekView.Feek_Time = (TextView)oView.findViewById(R.id.Feek_Time);
	            feekView.Feek_Info = (TextView)oView.findViewById(R.id.Feek_Info);
	            m_mapObj.put( position, oView );
	            oView.setTag(feekView);
			}
			else{
				oView = m_mapObj.get(position); 
				feekView = (FeekbackView)oView.getTag();
			}
			String		strDEUID;
			strDEUID = m_lstData.get(position).getDEUID();
			CarInfo  oCar = GlobalData.findDEUIDByCarInfo(strDEUID);
			feekView.Feek_DEUID.setText(  strDEUID );
			if( oCar != null ){
				feekView.Feek_CarLicense.setText( oCar.GetCarLicense() );
				
				if( m_lstData.get(position).isGPSData() ){
					
					if( m_lstData.get(position).getGPSData().getAlarmState() == 0 ){
						feekView.Feek_CarLicense.setTextColor( Color.rgb(59, 165, 239) );
						feekView.Feek_Time.setTextColor( Color.rgb(59, 165, 239) );
						feekView.Feek_Info.setTextColor( Color.rgb(59, 165, 239) );						
					}
					else{
						feekView.Feek_CarLicense.setTextColor( Color.rgb(255, 0, 0) );
						feekView.Feek_Time.setTextColor( Color.rgb(255, 0, 0) );
						feekView.Feek_Info.setTextColor( Color.rgb(255, 0, 0) );
					}
					feekView.Feek_Time.setText( m_lstData.get(position).getGPSData().getTime() );
					feekView.Feek_Info.setText( m_lstData.get(position).getGPSData().getAddr() +
												m_lstData.get(position).getGPSData().getGeneralInfo() );
					
				}
				else{
					feekView.Feek_Time.setText( getCurrentTime( m_lstData.get(position).getOperatTime()) );
					feekView.Feek_Info.setText( GlobalData.getOperaCmdToStr( m_lstData.get(position).getOperating()) );
				}
			}
			return oView;
		}  
    }    
    //*************************************************
	//
	public  String  getCurrentTime( long  nMillis ){
		
		Date				de = null;
		SimpleDateFormat 	oFormat= null;
		
		de = new Date();		
		de.setTime( nMillis );
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return oFormat.format(de);
	}
}
