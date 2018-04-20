package com.AMaptrack;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import com.Data.CLCmdCode;
import com.Data.GlobalData;
import com.Data.GoogleAddr;
import com.Data.Maptrack_ID;
import com.Data.ProgramType;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class VehicleList extends Activity {	
	
	public  final	int			ACTIVITY_ADD = 0x01;
	public  final   int			ACTIVITY_MODIFY = 0x02;
		
	public   TextView			m_labelVehicleNumber = null;
	
	public 	 ListView			m_lstViewVehicle = null;
	public 	 VehicleAdapter		m_oVehicleAdapter = null;
	
	public 	 GridView			m_oTECmdView = null;
	public   TECmdAdapter		m_oTECmdAdapter = null;
		
	public   Timer				m_oVehicleTime = null;
	private  Handler 			m_oProgressHandler = null;  
	public   VehicleTask		m_oVehicleTask = null;
	
	public  LayoutInflater		m_oPromptInflater = null;
	public  View				m_oPromptView = null;
	public  EditText			m_edPsd = null;
	
	public  int					m_nCarPosition = 0; //记录车辆的最后光标
		
	//**************************************************
	//
	@Override  
	public void onCreate(Bundle savedInstanceState) {  

		  super.onCreate(savedInstanceState);  
	      setContentView(R.layout.vehiclelist);      
	      initEventObj();
	      initLabel();
	      initWindow();
	      initEvent();	
	      ProgressMessageHandler();
	      if( m_oVehicleTime == null ){
	    	  m_oVehicleTime = new Timer();
		  }	      
		  // 一秒检测一次是否登陆成功		
	      m_oVehicleTask = new VehicleTask();
	      m_oVehicleTime.schedule(m_oVehicleTask, 200, 1000 );
	 }
	//******************************************************
	//
	public  void FreeMemory(){
		
		m_oVehicleAdapter.DelAllVehicle();
	}
	//**************************************************
	//
	@Override
	protected void onDestroy() {
		
		FreeMemory();
		if( m_oVehicleTask != null){
			m_oVehicleTask.cancel();
		}
		super.onDestroy();
	}
	//**************************************************
	//  初始化事件对像
	public  void    initEventObj( ){
		
	    m_labelVehicleNumber = (TextView)findViewById(R.id.Label_VehicleList );
	    	    
	    m_lstViewVehicle = (ListView)findViewById(R.id.List_Vehicle);
	    m_lstViewVehicle.setBackgroundColor(0xFFFFFF);
		m_lstViewVehicle.setCacheColorHint(0xFFFFFF);
		
		m_oTECmdView = (GridView)findViewById( R.id.TECmd_GridView );
	}
	//*************************************************
	//
	public  void   initWindow(){
		
	 	int			nRetention = 0; 
		int 		nWidth = 0;
		int			nHeight = 0;
		Display 	oDisplay = null;		
				
		oDisplay = getWindowManager().getDefaultDisplay();		
		DisplayMetrics metrics = new DisplayMetrics();
		oDisplay.getMetrics(metrics);
		nWidth = oDisplay.getWidth();
		nHeight = oDisplay.getHeight();
		Log.e("window-w-h", Integer.toString(nWidth)+ " "+Integer.toString(nHeight) );
        
		nRetention = GlobalData.getTabTitleHeight();
		nHeight = GlobalData.getTabHostHeight();
		
		// 车辆标签 height和width
        LinearLayout.LayoutParams	linearLabel = (LinearLayout.LayoutParams)m_labelVehicleNumber.getLayoutParams();
        Log.e("m_labelVehicleNumber-w-h", Integer.toString(linearLabel.width)+ " "+Integer.toString(linearLabel.height) );
        
        // 车辆标签 height和width
        TableRow  tableRow = (TableRow) findViewById( R.id.tableRow0);
        LinearLayout.LayoutParams	linearTable = (LinearLayout.LayoutParams)tableRow.getLayoutParams();
        Log.e("linearTable-w-h", Integer.toString(linearTable.width)+ " "+Integer.toString(linearTable.height) );
        
        // 原始车辆列表height和width
        LinearLayout.LayoutParams linearVehicleSize = (LinearLayout.LayoutParams)m_lstViewVehicle.getLayoutParams();
        Log.e("m_lstViewVehicle-w-h", Integer.toString(linearVehicleSize.width)+ " "+Integer.toString(linearVehicleSize.height) );
        
        // 原始控制表格 height和width
        LinearLayout.LayoutParams linearTECmdSize = (LinearLayout.LayoutParams)m_oTECmdView.getLayoutParams();
        Log.e("m_oTECmdView-w-h", Integer.toString(linearTECmdSize.width)+ " "+Integer.toString(linearTECmdSize.height) );
        
        // 更改车辆列表的高度
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) m_lstViewVehicle.getLayoutParams(); // 取控件mGrid当前的布局参数        
        linearParams.height = nHeight-linearTable.height-linearLabel.height-linearTECmdSize.height-nRetention;        
        m_lstViewVehicle.setLayoutParams(linearParams);
	}
	//*************************************************
	//
	public void  	initLabel(){
		
		m_labelVehicleNumber.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
	}
	//**************************************************
	//  事件初始化
	public  void  	initEvent( ){
		
		//设置点击选择监听器
		m_lstViewVehicle.setOnItemClickListener(eventVehicleListener);
		//设置选中监听器
		m_lstViewVehicle.setOnItemSelectedListener(eventSelectVehicleListener);
				
		// 加载容器
		m_oVehicleAdapter = new VehicleAdapter( getApplicationContext(), null );
		m_lstViewVehicle.setAdapter(m_oVehicleAdapter );
		
		m_oTECmdAdapter  = new TECmdAdapter( this.getApplicationContext() ); 
        m_oTECmdView.setAdapter( m_oTECmdAdapter );//调用GridViewAdapter
        m_oTECmdView.setOnItemClickListener( TeCmdItemClickEvent );        
	}
	//*************************************************************
	//
	OnItemClickListener	TeCmdItemClickEvent = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			switch(arg2){
			case 0:		//定位呼叫
				TrackOnce();
				break;
			case 1:		//最后位置
				TrackLast();
				break;
			case 2:		//远程监听
				CarListen();
				break;	
			case 3:		//远程通话
				CarTalk();
				break;
			case 4:		//远程断油
				StopEngin();
				break;
			case 5:		//恢复油路
				ResumeEngin();
				break;
			case 6:		//远程锁门
				RemoteLock();
				break;
			case 7:		//远程开门
				RemoteUnLock();
				break;
			}
			Log.e("Pos", Integer.toString(arg2));
		}		
	};
	//**************************************************
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		if( GlobalData.getCreateMenu() == GlobalData.CREATE_MENU_MONITOR_OBJECT ){ 

			Log.e("pop_Menu", "VehicleList");
    		GlobalData.setCreateMenu(GlobalData.CREATE_MENU_NO);
    		
    		menu.add(0, Maptrack_ID.ID_ADD_VEHICLE, 0, Language.getLangStr(Language.TEXT_ADD)).setIcon(R.drawable.add);
    		menu.add(0, Maptrack_ID.ID_MODIFY_VEHICLE, 0, Language.getLangStr(Language.TEXT_MODIFY)).setIcon(R.drawable.modify);
	    	menu.add(0, Maptrack_ID.ID_DEL_VEHICLE, 0, Language.getLangStr(Language.TEXT_DEL)).setIcon(R.drawable.remove);
	    	menu.add(0, Maptrack_ID.ID_VIEW_VEHICLE, 0, Language.getLangStr(Language.TEXT_VIEW)).setIcon(R.drawable.view);
	    	menu.add(0, Maptrack_ID.ID_HELP, 0, Language.getLangStr(Language.TEXT_HELP)).setIcon(R.drawable.help);
	    	menu.add(0, Maptrack_ID.ID_EXIT, 0, Language.getLangStr(Language.TEXT_EXIT)).setIcon(R.drawable.cancel);
	    	
		}
		return super.onCreateOptionsMenu(menu);
	}
	//**************************************************
	//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.e("弹出", "VehicleList");
		switch( item.getItemId() ){
		case Maptrack_ID.ID_ADD_VEHICLE:
			addVehicle();
			break;
		case Maptrack_ID.ID_MODIFY_VEHICLE:
			modifyVehicle();
			break;
		case Maptrack_ID.ID_DEL_VEHICLE:
			delVehicle();
			break;
		case Maptrack_ID.ID_VIEW_VEHICLE:
			VehicleView();
			break;
		case Maptrack_ID.ID_HELP:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//*************************************************
    // 
    public String queryAddress( double dbLat, double dbLng) {
    	    
    	float []		 dbDistance = null;
    	List<Address> 	 lstAddr = null;
        StringBuilder 	 strResult = null; 
        Geocoder 		 geoCode = null;
        
		try {			
			strResult = new StringBuilder(); 
	        geoCode =new Geocoder( getApplicationContext(), Locale.getDefault());
			lstAddr = geoCode.getFromLocation(dbLat, dbLng, 2);

			if( lstAddr != null){	
				
	            Address address = lstAddr.get(0);
	            for(int i=0;i<address.getMaxAddressLineIndex();i++){
	            	strResult.append(address.getAddressLine(i));
	            }
	            
	            dbDistance =new float[3];
	            Arrays.fill(dbDistance, 0);
	            Location.distanceBetween( dbLat, 
	            						dbLng, 
	            					    address.getLatitude(), 
	            					    address.getLongitude(),
	            					    dbDistance );
	            
	            strResult.append( (int)(dbDistance[0])+ 
     				   Language.getLangStr(Language.TEXT_M) + " " );
		        Log.e("addr", strResult.toString() );
	        }
		} 
		catch (IOException e) {
			Log.e("地址", "错误");
			e.printStackTrace();
		}
		return  strResult.toString();
    }
	//*************************************************
	//
	public void     addVehicle(){
		
		Intent intent = new Intent();
		Bundle  bundle = new Bundle();
		
		bundle.putInt( ConfigKey.KEY_WORKMODE, 0 );
	    intent.setClass( VehicleList.this, CarInfoActivity.class );
		intent.putExtras( bundle );
		startActivityForResult(intent, ACTIVITY_ADD ); 
	}
	//*************************************************
	//
	public void delVehicle(){
		
		AlertDialog.Builder  builder = null;
		
		
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog("请选择车辆!");
			return ;
		}		
		builder = new AlertDialog.Builder(this);
		
		builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(Language.getLangStr(Language.TEXT_DEL) ).
			setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					delVehicleEx();
				}
		}).setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
					
				return ;
			}
		});	
		AlertDialog dlg = builder.create();
		dlg.show();
		
		
	}
	//*************************************************
	//
	public void delVehicleEx(){
		
		List<CarInfo>	lstCarInfo = null;
		List<String>	lstDEUID = null;
		ServerCommand	oCmd = null;
				
		lstCarInfo = new ArrayList<CarInfo>();
		lstCarInfo.clear();
		lstDEUID = new ArrayList<String>();
		m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
		for( int nCnt = 0; nCnt <lstCarInfo.size(); nCnt++ ){
			
			lstDEUID.add( lstCarInfo.get(nCnt).GetDEUID() );
		}
		oCmd = new ServerCommand();
		oCmd.DelVehicleCmd( GlobalData.getUserInfo().getUserName(), lstDEUID );		
		GlobalData.addSendServerData( oCmd );
		
		// 删除报警车辆列表?	
		m_oVehicleAdapter.DelVehicleData(lstDEUID );
	}
	//*************************************************
	//
	public void modifyVehicle(){
		
		List<CarInfo>	lstCarInfo = null;
		CarInfo			oCarInfo = null;
		Intent 			intent = null;
		Bundle  		bundle = null;
		
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		lstCarInfo = new ArrayList<CarInfo>();
		lstCarInfo.clear();
		m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
		if( lstCarInfo.size() >= 2 ){			
			showWarringDialog(Language.getLangStr(Language.TEXT_ONLY_TICK_CAR));
			return ;
		}
		oCarInfo = lstCarInfo.get(0);
		
		intent = new Intent();
		bundle = new Bundle();
		
		bundle.putInt( ConfigKey.KEY_WORKMODE, 1 );
		setBundleData( oCarInfo, bundle );		
		
	    intent.setClass( VehicleList.this, CarInfoActivity.class );
		intent.putExtras( bundle );
		startActivityForResult(intent, ACTIVITY_MODIFY ); 
	}
	//*************************************************
	//
	public void VehicleView(){
		List<CarInfo>	lstCarInfo = null;
		CarInfo			oCarInfo = null;
		Intent 			intent = null;
		Bundle  		bundle = null;
		
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		lstCarInfo = new ArrayList<CarInfo>();
		lstCarInfo.clear();
		m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
		if( lstCarInfo.size() >= 2 ){			
			showWarringDialog(Language.getLangStr(Language.TEXT_ONLY_TICK_CAR));
			return ;
		}
		oCarInfo = lstCarInfo.get(0);
		
		intent = new Intent();
		bundle = new Bundle();
		
		bundle.putInt( ConfigKey.KEY_WORKMODE, 2 );
		setBundleData( oCarInfo, bundle );
		
	    intent.setClass( VehicleList.this, CarInfoActivity.class );
		intent.putExtras( bundle );
		startActivity(intent ); 
	}
	//*************************************************
	//
	public  void  setBundleData( CarInfo  oCarInfo, Bundle  bundle ){
		
		bundle.putString(ConfigKey.KEY_CARLICENSE, oCarInfo.GetCarLicense() );
		bundle.putInt(ConfigKey.KEY_DETYPE, oCarInfo.GetTEType() );
		bundle.putString(ConfigKey.KEY_DEUID, oCarInfo.GetDEUID() );
		bundle.putString(ConfigKey.KEY_DESIM, oCarInfo.GetDESIM() );
		bundle.putString(ConfigKey.KEY_FNAME, oCarInfo.GetFName() );
		bundle.putString(ConfigKey.KEY_LNAME, oCarInfo.GetLName() );
		bundle.putString(ConfigKey.KEY_ADDR, oCarInfo.GetOwnerAddr() );
		bundle.putString(ConfigKey.KEY_REMARK, oCarInfo.GetRemark() );
		bundle.putString(ConfigKey.KEY_TEL, oCarInfo.GetOwnerTel() );
	}
	//*************************************************
	//
	public  void  getBundleData( CarInfo  oCarInfo, Bundle  bundle ){
		
		oCarInfo.SetCarLicense( bundle.getString(ConfigKey.KEY_CARLICENSE) );
		oCarInfo.SetTEType(bundle.getInt(ConfigKey.KEY_DETYPE) );
		oCarInfo.SetDEUID(bundle.getString(ConfigKey.KEY_DEUID ) );
		oCarInfo.SetDESIM(bundle.getString(ConfigKey.KEY_DESIM ) );
		oCarInfo.SetFName(bundle.getString(ConfigKey.KEY_FNAME ) );
		oCarInfo.SetLName(bundle.getString(ConfigKey.KEY_LNAME ) );
		oCarInfo.SetOwnerAddr(bundle.getString(ConfigKey.KEY_ADDR ) );
		oCarInfo.SetRemark(bundle.getString(ConfigKey.KEY_REMARK ) );
		oCarInfo.SetOwnerTel(bundle.getString(ConfigKey.KEY_TEL) );
	}
	//*************************************************
	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if( resultCode == RESULT_OK ){
			switch( requestCode ){
			case ACTIVITY_ADD:
				addVehicleResult( data.getExtras() );
				break;
			case ACTIVITY_MODIFY:
				modifyVehicleResult( data.getExtras() );
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	//*************************************************
	//
	public  void addVehicleResult( Bundle  bundle ){
		
		ServerCommand		oCmd = new ServerCommand();
		CarInfo				oCarInfo = new CarInfo();
		
		getBundleData( oCarInfo, bundle );
		GlobalData.AddCarInfo( oCarInfo );
		m_oVehicleAdapter.AddVehicleData(oCarInfo);
		oCmd.AddVehicleCmd(GlobalData.getUserInfo().getUserName(), oCarInfo);
		GlobalData.addSendServerData(oCmd);
	}
	 
	//*************************************************
	//
	public void modifyVehicleResult( Bundle  bundle ){		
		
		CarInfo				oCarInfo = null;
		ServerCommand		oCmd = new ServerCommand();
		
		oCarInfo = GlobalData.findDEUIDByCarInfo( bundle.getString(ConfigKey.KEY_DEUID) );
		if( oCarInfo == null ){
			return ;
		}
		getBundleData( oCarInfo, bundle );
		GlobalData.AddCarInfo( oCarInfo );

		m_oVehicleAdapter.ModifyVehicleData( oCarInfo );
		
		oCmd.ModifyVehicleCmd(GlobalData.getUserInfo().getUserName(), oCarInfo);
		GlobalData.addSendServerData(oCmd);
	}
	//**************************************************
	//  设置点击选择监听器
	OnItemClickListener	 eventVehicleListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Log.e("VehicleList", Integer.toString(arg2));
			m_nCarPosition = arg2;
			m_oVehicleAdapter.setSelectedPosition( arg2 );
		}
	};
	//**************************************************
	//  设置选中监听器
	OnItemSelectedListener eventSelectVehicleListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}		
	};	
	//**************************************************
	//  定位
	public  void TrackOnce(){
		
		List<CarInfo>	lstCarInfo = null;
		
		lstCarInfo = new ArrayList<CarInfo>();
		lstCarInfo.clear();
		if( m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo) == false ){
			
			showWarringDialog( Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		addTECmdSend(lstCarInfo, CLCmdCode.TECMD_TRACKONCE );
	}
	//**************************************************
	//  断油
	public void  StopEngin(){
		
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPromptObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_AGREE), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			str;
				List<CarInfo>	lstCarInfo = null;
				
				// 密码是否相同
				str = m_edPsd.getText().toString();
				
				if( GlobalData.getUserInfo().getUserPsd().equals(str) ){
					lstCarInfo = new ArrayList<CarInfo>();
					lstCarInfo.clear();
					m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
					addTECmdSend(lstCarInfo, CLCmdCode.TECMD_STOP_ENGINE );
				}
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_DENY), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}	 
	//**************************************************
	//  断油
	public  void ResumeEngin(){
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPromptObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_AGREE), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			str;
				List<CarInfo>	lstCarInfo = null;
				
				// 密码是否相同
				str = m_edPsd.getText().toString();
				
				if( GlobalData.getUserInfo().getUserPsd().equals(str) ){
					lstCarInfo = new ArrayList<CarInfo>();
					lstCarInfo.clear();
					m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
					addTECmdSend(lstCarInfo, CLCmdCode.TECMD_RESUME_ENGINE );
				}
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_DENY), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}
	//*************************************************
    //  提示框始化
	public void  initPromptObj(){				
		
		m_oPromptInflater = LayoutInflater.from(this);
		m_oPromptView = m_oPromptInflater.inflate(R.layout.prompt, null);
		
		TextView tv1  = (TextView)m_oPromptView.findViewById(R.id.propt_text1 );
		TextView tv2  = (TextView)m_oPromptView.findViewById(R.id.propt_text2 );
		TextView tv3  = (TextView)m_oPromptView.findViewById(R.id.propt_text3 );
		TextView tv4  = (TextView)m_oPromptView.findViewById(R.id.propt_text4 );
		TextView tv5  = (TextView)m_oPromptView.findViewById(R.id.propt_text5 );
		TextView tv6  = (TextView)m_oPromptView.findViewById(R.id.propt_text6 );
		TextView tv7  = (TextView)m_oPromptView.findViewById(R.id.propt_text7 );
		
    	TextView tvpsd  = (TextView)m_oPromptView.findViewById(R.id.propt_text_psd );
    	tvpsd.setText( Language.getLangStr( Language.TEXT_PASSWORD) );
    	m_edPsd   = (EditText)m_oPromptView.findViewById(R.id.propt_edit_psd );

    	tv1.setText( Language.getLangStr(Language.TEXT_YOUR_CAUSE_FOLLOWING));
    	tv2.setText( Language.getLangStr(Language.TEXT_MSG_HEARD));
    	tv3.setText( Language.getLangStr(Language.TEXT_MSG_ONE));
    	tv4.setText( Language.getLangStr(Language.TEXT_MSG_TWO));
    	tv5.setText( Language.getLangStr(Language.TEXT_MSG_THREE));
    	tv6.setText( Language.getLangStr(Language.TEXT_MSG_FOUR));
    	tv7.setText( Language.getLangStr(Language.TEXT_MSG_DESCRIPTION));
    	
	}	 	 
	//**************************************************
	//  锁门
	public	void RemoteLock(){
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPromptObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_AGREE), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			str;
				List<CarInfo>	lstCarInfo = null;
				
				// 密码是否相同
				str = m_edPsd.getText().toString();
				
				if( GlobalData.getUserInfo().getUserPsd().equals(str) ){
					lstCarInfo = new ArrayList<CarInfo>();
					lstCarInfo.clear();
					m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
					addTECmdSend(lstCarInfo, CLCmdCode.TECMD_LOCK );
				}
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_DENY), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}
	//**************************************************
	//  开门
	public  void	RemoteUnLock(){
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPromptObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_AGREE), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			str;
				List<CarInfo>	lstCarInfo = null;
				
				// 密码是否相同
				str = m_edPsd.getText().toString();
				
				if( GlobalData.getUserInfo().getUserPsd().equals(str) ){
					lstCarInfo = new ArrayList<CarInfo>();
					lstCarInfo.clear();
					m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);
					addTECmdSend(lstCarInfo, CLCmdCode.TECMD_UNLOCK );
				}
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_DENY), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}
	//*************************************************
   //  提示框始化
	public void  initPhoneObj(){				
				
		m_oPromptInflater = LayoutInflater.from(this);
		m_oPromptView = m_oPromptInflater.inflate(R.layout.phone, null);
		
		m_edPsd = (EditText)m_oPromptView.findViewById(R.id.edit_phone);
		TextView tv = (TextView)m_oPromptView.findViewById(R.id.text_phone);
		tv.setText( Language.getLangStr(Language.TEXT_TELNO));
	}
	//**************************************************
	//  监听
	public  void  CarListen(){
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPhoneObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setTitle(Language.getLangStr(Language.TEXT_LISTEN_IN))
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			strPhone;
				List<CarInfo>	lstCarInfo = null;					
					
				strPhone = m_edPsd.getText().toString();
				if( strPhone.length() <= 3 ){
				
					showWarringDialog(Language.getLangStr(Language.TEXT_NUMBER_SUB_RULE));
					return ;
				}
				lstCarInfo = new ArrayList<CarInfo>();
				lstCarInfo.clear();
				m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);	
				addTECmdSend(lstCarInfo, strPhone, CLCmdCode.TECMD_CAR_LISTEN );
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_CANCEL), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}
	//**************************************************
	//  通话
	public  void 	CarTalk(){
		if( m_oVehicleAdapter.isSelectedVehicle() == false ){				
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		initPhoneObj();
		// 提示
		AlertDialog.Builder  bulider = new AlertDialog.Builder(VehicleList.this);
		bulider
		.setTitle(Language.getLangStr(Language.TEXT_TALK))
		.setView(m_oPromptView)
		.setPositiveButton( Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String			strPhone;
				List<CarInfo>	lstCarInfo = null;					
					
				strPhone = m_edPsd.getText().toString();
				if( strPhone.length() <= 3 ){
				
					showWarringDialog(Language.getLangStr(Language.TEXT_NUMBER_SUB_RULE));
					return ;
				}
				lstCarInfo = new ArrayList<CarInfo>();
				lstCarInfo.clear();
				m_oVehicleAdapter.getSelectedCarInfo(lstCarInfo);	
				addTECmdSend(lstCarInfo, strPhone, CLCmdCode.TECMD_CAR_TALK );
			}
		})
		.setNegativeButton( Language.getLangStr(Language.TEXT_CANCEL), null );
		AlertDialog alert = bulider.create();
		alert.show();
	}
	//**************************************************
	//  增加发送指令
	public  void  addTECmdSend(List<CarInfo>  inData, int  nCmdType ){
			
		CarInfo			oCarInfo = null;		
		ServerCommand   oCmd = null;
					
		Iterator<CarInfo>		it = inData.iterator();
		while( it.hasNext() ){
			oCmd = new ServerCommand();
			oCarInfo = it.next();
			oCmd.setGeneralCmd(oCarInfo,  nCmdType );
			GlobalData.addSendServerData(oCmd);
			GlobalData.setOperating(oCarInfo.GetDEUID(), nCmdType );
			
			GlobalData.setCmdTimingCheck( oCarInfo.GetDEUID() );
		}		 
	}
	//**************************************************
	//  增加发送指令
	public  void  addTECmdSend(List<CarInfo>  inData, String strPara, int  nCmdType ){
			
		CarInfo			oCarInfo = null;		
		ServerCommand   oCmd = null;
					
		Iterator<CarInfo>		it = inData.iterator();
		while( it.hasNext() ){
			oCmd = new ServerCommand();
			oCarInfo = it.next();
			oCmd.setGeneralCmd(oCarInfo, nCmdType, strPara );
			GlobalData.addSendServerData(oCmd);
			GlobalData.setOperating( oCarInfo.GetDEUID(), nCmdType);
			GlobalData.setCmdTimingCheck( oCarInfo.GetDEUID() );
		}
	}
	//**************************************************
	//  查询最后位置
	public  void  TrackLast(){
		
		List<String>	lstDEUID = null;
		ServerCommand   oCmd = null;
		
		lstDEUID = new ArrayList<String>();
		lstDEUID.clear();
		if( m_oVehicleAdapter.getSelectedVehicle(lstDEUID) == false ){
			
			showWarringDialog(Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}			
		oCmd = new ServerCommand();			
		oCmd.findTrackLast( GlobalData.getUserInfo().getUserName(), lstDEUID );
		GlobalData.addSendServerData(oCmd);
	}
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(VehicleList.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING) );
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	} 
    //*************************************************
	//
	public  String  getCurrentTime(  ){
		
		Date				de = null;
		SimpleDateFormat 	oFormat= null;
		
		de = new Date();		
		de.setTime( System.currentTimeMillis() );
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return oFormat.format(de);
	}	
	//***********************************************************
	//
	public class VehicleAdapter extends BaseAdapter{
		
		private List<VehicleListData> 		m_lstData = null;    
	    public  HashMap<Integer,View> 		m_oMapObj = null;
		public 	Context 					m_oContext = null;
		
		
		//***********************************************************
		//	
	    public VehicleAdapter(Context context, List<VehicleListData> inData) {    

	    	m_oContext = context;
	    	m_lstData = new ArrayList<VehicleListData>();
	    	m_lstData.clear();
	    	m_oMapObj = new HashMap<Integer,View>();
	    	m_oMapObj.clear();
	    	if( inData != null ){
	    		AddVehiclData( inData );
	    	}
	    }
	    //***********************************************************
	  	//
	    public void  DelAllVehicle(){
	    	
	    	m_lstData.clear();
	    	m_oMapObj.clear();
	    	this.notifyDataSetChanged();
	    }
	    //**********************************************************
	    //
	    public  void AddVehiclData( List<VehicleListData>  inData ){
	    	
	    	VehicleListData   oVehicleData = null;
	    	
	    	for( int nCnt = 0; nCnt < inData.size(); nCnt++ ){
	    		
	    		oVehicleData = inData.get(nCnt);
	    		m_lstData.add(oVehicleData);
	    	}	    	
	    	this.notifyDataSetChanged();
	    	setSelectedCarNumber();
	    }
	    //***********************************************************
	    public  void  AddVehicleData( CarInfo  oCarInfo ){
	    	
	    	VehicleListData		oVehicleData = null;
	    	
	    	oVehicleData = new VehicleListData( oCarInfo, null );
	    	m_lstData.add(oVehicleData);
	    	this.notifyDataSetChanged();
	    	setSelectedCarNumber();
	    }
	   //***********************************************************
	    public  void  AddVehicleData( List<CarInfo>  inData ){
	    	
	    	VehicleListData		oVehicleData = null;
	    	
	    	for( int nCnt = 0; nCnt < inData.size(); nCnt++ ){
	    		
	    		oVehicleData = new VehicleListData( inData.get(nCnt), null );
	    		m_lstData.add(oVehicleData);
	    	}
	    	this.notifyDataSetChanged();
	    	setSelectedCarNumber();
	    }
	    //***********************************************************
	    //
	    public  void  ModifyVehicleData( CarInfo  oCarInfo ){
	    	
	    	VehicleListData		oVehicleData = null;
	    	
	    	for( int  nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){
	    		
	    		oVehicleData = m_lstData.get(nCnt);
	    		if( oVehicleData.getCarInfo().GetDEUID().equals(oCarInfo.GetDEUID()) ){
	    			
	    			oVehicleData.setCarInfo(oCarInfo);
	    			break;
	    		}
	    	}
	    	this.notifyDataSetChanged();
	    }
	    //***********************************************************
	    //
	    public  void   DelVehicleData( List<String>  inData ){
	    	
	    	String 				strDEUID;
	    	
	    	for ( int nCnt = 0; nCnt < inData.size(); nCnt++ ){
	    		
	    		strDEUID = inData.get(nCnt);
	    		DelVehicleData( strDEUID );
	    	}
		    this.notifyDataSetChanged();		    
	    }
	    //***********************************************************
	    //
	    public  void   DelVehicleData( String  strDEUID ){
	    	
	    	VehicleListData		oVehicleData = null;
	    	
	    	for( int  nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){
	    		
	    		oVehicleData = m_lstData.get(nCnt);
	    		if( oVehicleData.getCarInfo().GetDEUID().equals(strDEUID) ){
	    			
	    			GlobalData.addCarToMap(strDEUID, false, false );
	    			m_lstData.remove(oVehicleData);
	    			m_oMapObj.clear();
	    			break;
	    		}
	    	}
	    	this.notifyDataSetChanged();
	    	setSelectedCarNumber();
	    }
	    //***********************************************************
	    //	列新GPS数据
	    public  void  updataGPSData( int  nPos ){

	    	GPSData			 oGPSData = null;
	    	VehicleListData  oVehicleData = null; 
	   	
	    	if( nPos >= 0 ){
	    		oVehicleData = m_lstData.get(nPos);
	    		oGPSData = oVehicleData.getGPSData();
	    		if( oGPSData == null ){
		    		oGPSData = GlobalData.getRamDBGPSData( oVehicleData.getCarInfo().GetDEUID() );
					if( oGPSData != null ){		
						ShowGPSData(oGPSData);
					}
	    		}
	    	}
	    	this.notifyDataSetChanged();
	    }
	    //***********************************************************
	    //
	    public  void   ShowGPSData( GPSData  oGPSData ){
	    		    	
	    	GPSData				oldGPSData = null;
	    	VehicleListData		oVehicleData = null;
	    	
	    	for( int  nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){
	    		
	    		oVehicleData = m_lstData.get(nCnt);
	    		if( oVehicleData.getCarInfo().GetDEUID().equals(oGPSData.getDEUID()) ){
	    			
	    			oldGPSData = oVehicleData.getGPSData();
	    			if( oldGPSData != null ){
	    				if( oGPSData.getGPSTime() > oldGPSData.getGPSTime() ){
	    					oVehicleData.setGPSData(oGPSData);
	    				}
	    			}    			
	    			else{
	    				oVehicleData.setGPSData(oGPSData);
	    			}
	    			break;
	    		}
	    	}
	    	//this.notifyDataSetChanged();	    	
	    }
	    //***********************************************************
	  	// 
	    public boolean isSelectedVehicle(){
	    	
	    	boolean 							bResult = false;
	    	
	    	for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){		    		
	    		if( m_lstData.get(nCnt).getSelected() ){	    			
					bResult = true;
					break;
	    		}
	    	}
	    	return bResult;
	    }
	    //***********************************************************
	  	// 
	    public boolean getSelectedCarInfo( List<CarInfo>  outData ){
	    	
	    	boolean 							bResult = false;
	    	
	    	for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){	
	    		
	    		if( m_lstData.get(nCnt).getSelected() ){	    			
	    			outData.add( m_lstData.get(nCnt).getCarInfo() );
					bResult = true;
	    		}
	    	}
	    	return bResult;
	    }
	    //***********************************************************
	  	// 
	    public boolean getSelectedVehicle( List<String>  outData ){
	    	
	    	boolean 							bResult = false;
	    	
	    	for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){	
	    		
	    		if( m_lstData.get(nCnt).getSelected() ){	    			
	    			outData.add( m_lstData.get(nCnt).getCarInfo().GetDEUID() );
					bResult = true;
	    		}
	    	}
	    	return bResult;
	    }
	    //***********************************************************
	  	//   
	    public void  setSelectedPosition( int  nPosition ){
	    	
	    	boolean		 	 bFlag = false;	
	    	ViewHolder	  	 oHolder = null;
			View		  	 oView = null;
			
			if( m_lstData.size() > nPosition){
				
				if( m_lstData.get(nPosition).getSelected() ){
					bFlag = false;
				}
				else{
					bFlag = true;
				}
				m_lstData.get(nPosition).setSelected(bFlag);
				
				oView = m_oMapObj.get(nPosition );
	    		if( oView != null ){	    			
	    			oHolder = (ViewHolder)oView.getTag();
	    			oHolder.setSelected( m_lstData.get(nPosition).getSelected() );
	    		}
	    		setSelectedCarNumber();
			}
	    }

	    //***********************************************************
	  	//   车辆的选择数据
	    public  void	 setSelectedCarNumber(){
	    	
	    	int									nResultCnt = 0;
	    	
			for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){				
				if( m_lstData.get(nCnt).getSelected() ){
					nResultCnt++;
				}
			}
			
			if( m_lstData.size() <= 0 ){				
				m_labelVehicleNumber.setTextSize(15);
				m_labelVehicleNumber.setTextColor( Color.rgb(255, 0, 0)  );
				m_labelVehicleNumber.setText( Language.getLangStr(Language.TEXT_ADD_VEHICLE_NOW) );
			}
			else{
				m_labelVehicleNumber.setTextSize(12);
				m_labelVehicleNumber.setTextColor( 0xFFFFFFFF  );
				m_labelVehicleNumber.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT)+":"
											  +Integer.toString(nResultCnt)+"/"
											  +Integer.toString(m_lstData.size()) );
			}
	    	return ;
	    }
	    //***********************************************************
		//
		@Override
		public int getCount() {

			return m_lstData.size();
		}
		//***********************************************************
		//
		@Override
		public Object getItem(int position) {

			return m_lstData.get(position);
		}
		//***********************************************************
		//
		@Override
		public long getItemId(int position) {
			
			return position;
		}
		//***********************************************************
		//
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder	  oHolder = null;
			View		  oView = null;
			
			if( m_oMapObj.get(position) == null ){
				oHolder = new ViewHolder();
				LayoutInflater mInflater = (LayoutInflater) m_oContext  
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				oView = mInflater.inflate(R.layout.vehicle, null);
				
				oHolder.m_CheckCar = (CheckBox)oView.findViewById(R.id.check_car );
	            oHolder.m_TextCar = (TextView)oView.findViewById(R.id.car_name);
	            oHolder.m_TextGPSDesc = (TextView)oView.findViewById(R.id.gps_desc);
	            oHolder.m_ImageCar = (ImageView)oView.findViewById(R.id.car_img);	            
	            oHolder.m_ImageCar.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            oHolder.m_ImageCar.setPadding(8, 8, 8, 8);
	            oHolder.setDEUID( m_lstData.get(position).getCarInfo().GetDEUID() );
	            
				oView.setTag(oHolder);
				final  int  p = position;
				m_oMapObj.put(position, oView );
				// 扩展写触发事件
				oHolder.m_CheckCar.setOnCheckedChangeListener( new OnCheckedChangeListener(){					
					@Override
	            	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						Log.e("CheckCar", "0");
						m_nCarPosition = p;
						m_lstData.get(m_nCarPosition).setSelected(isChecked);
						setSelectedCarNumber();
					}
				});
				oHolder.m_TextCar.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
						
						Log.e("Car", "0");
						m_nCarPosition = p;
						setSelectedPosition( m_nCarPosition );
					}
				});
				oHolder.m_TextGPSDesc.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
										
						Log.e("GPSDesc", "0");
						m_nCarPosition = p;		
						setSelectedPosition( m_nCarPosition );					}
				});
				oHolder.m_ImageCar.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
						
						Log.e("ImageCar", "0");
						m_nCarPosition = p;
						setSelectedPosition( m_nCarPosition );
					}
				});
			}
			else{
				oView = m_oMapObj.get(position);
				oHolder = (ViewHolder)oView.getTag();
			}	
			oHolder.m_ImageCar.setImageResource( R.drawable.car );
			oHolder.m_TextCar.setText( m_lstData.get(position).getCarInfo().GetCarLicense() );			
			
			ShowGPSTextShow( oHolder, m_lstData.get(position).getGPSData(), m_lstData.get(position).getSelected() );
			
			return oView;
		}
		//***********************************************************
		//
		void  ShowGPSTextShow( ViewHolder oHolder, GPSData oGPSData, boolean  bSelected ){
			
			if( oGPSData != null ){
				if( oGPSData.getAlarmState() > 0 ){
					oHolder.m_TextGPSDesc.setTextColor( Color.rgb(255, 0, 0) );
					
				}
				else{
					oHolder.m_TextGPSDesc.setTextColor( Color.rgb(255, 255, 255) );
				}
				oHolder.m_TextGPSDesc.setText( oGPSData.getTime()+" "+
											   oGPSData.getAddr()+" "+
											   oGPSData.getGeneralInfo( ));				
			}
			else{
				oHolder.m_TextGPSDesc.setText( Language.getLangStr( Language.TEXT_WARING_NO_DATA));
			}
			oHolder.setSelected( bSelected );
		}
	}	

    //********************************************
    //
    public class TECmdAdapter extends BaseAdapter {
    	
	    private Context  m_oContext = null;
	    public  Map<Integer, View>  m_oMap = null;

	    public int[][] mimages = {	    		
	    	{R.drawable.track_once,   Language.TEXT_TRACK_ONCE},
	    	{R.drawable.track_last,   Language.TEXT_LAST_POSITION},
	    	{R.drawable.phone,		  Language.TEXT_LISTEN_IN},
	    	{R.drawable.phone,		  Language.TEXT_TALK},
	    	{R.drawable.stop_engine,  Language.TEXT_FUEL_OFF},
	    	{R.drawable.resume_engine,Language.TEXT_FUEL_ON},
	    	{R.drawable.lock,		  Language.TEXT_LOCK},
	    	{R.drawable.unlock,		  Language.TEXT_UNLOCK},
	    };
		
	    public  TECmdAdapter( Context  oContext ){
	    	
	    	m_oContext = oContext;
	    	m_oMap = new HashMap<Integer, View>();
	    }
		@Override
		public int getCount() {

			return mimages.length;
		}

		@Override
		public Object getItem(int arg0) {
			
			return arg0;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		class ImageShowText{
			
			public  ImageView   m_mimageView = null;
			public  TextView    m_textInfo = null;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ImageShowText	oImageShow;
			View		   oView;

	       if(m_oMap.get(position)==null){
	    	
	    	   oImageShow = new ImageShowText();
	    	   LayoutInflater mInflater = (LayoutInflater) m_oContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	           oView = mInflater.inflate(R.layout.tecmd, null);
	         
	            oImageShow.m_mimageView = (ImageView)oView.findViewById(R.id.tecmd_Image);
	            oImageShow.m_textInfo = (TextView)oView.findViewById(R.id.tecmd_name);
	        
	            oImageShow.m_mimageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            oImageShow.m_mimageView.setPadding(8, 8, 8, 8);
	            m_oMap.put( position, oView );
	            oView.setTag(oImageShow);
	       }
	       else{
	    	   oView = m_oMap.get(position); 
	    	   oImageShow = (ImageShowText)oView.getTag();
	       }
	       oImageShow.m_mimageView.setImageResource(mimages[position][0]);
	       oImageShow.m_textInfo.setText( Language.getLangStr( mimages[position][1] ) );
	       
	       return oView;

		}
    }
    public int		nStartViewPos = 0;
	//******************************************************
	//  更新GPS数据
	public void	UpdateGPSData(){

		boolean				bFlag = false;
		GPSData				oGPSData = null;
		List<GPSData>		lstGPSData = new ArrayList<GPSData>();
		Iterator<GPSData>   it = null;
		
		nStartViewPos = -1;
		lstGPSData.clear();
		lstGPSData = GlobalData.findNewGPSData();
		it = lstGPSData.iterator();
		while( it.hasNext() ){	
			bFlag = true;
			oGPSData = (GPSData)it.next();
			if( oGPSData != null ){
				GlobalData.delCmdTimingCheck( oGPSData );
				m_oVehicleAdapter.ShowGPSData( oGPSData );
			}
		}
		if( bFlag == true ){
			m_oVehicleAdapter.notifyDataSetChanged();
		}
		// 开启线程查地址
		new Thread( new GoogleAddr( getApplicationContext(),0 )).start();
	}
	//******************************************************
	//  进程消息处理
	public  void  ProgressMessageHandler(){
		
		m_oProgressHandler = new Handler() {
    		@Override  
            public void handleMessage(Message msg) {
    			int nMessageType = msg.getData().getInt("ProgrameType");
    			switch( nMessageType ){
    			case ProgramType.LOGIN_SUCCEED:		//登陆成功
    				m_oVehicleAdapter.AddVehicleData(GlobalData.getAllVehicle() );
    				break;
    			case ProgramType.RECV_GPSDATA:		//更新GPS数据
    				UpdateGPSData();
    				break;
    			case ProgramType.RECV_NO_GPSDATA:		//没有GPS数据
    				Toast.makeText( getApplicationContext(),
    								Language.getLangStr(Language.TEXT_WARING_NO_DATA), 
    								Toast.LENGTH_LONG).show();
    				break;
    			// 更新数据,加载地址信息		
    			case ProgramType.UPDATE_VEHICLE_LIST_ADDR:
    				nStartViewPos = -1;
    				m_oVehicleAdapter.notifyDataSetChanged(); 
    				m_lstViewVehicle.requestFocus();
    				m_lstViewVehicle.setSelection( nStartViewPos );
    				m_lstViewVehicle.setFocusable(true);
    				break;
    			case ProgramType.ADD_VEHICLE_FAIL:
    			case ProgramType.MODIFY_VEHICLE_FAIL:
    			case ProgramType.DEL_VEHICLE_FAIL:	
    				Toast.makeText( getApplicationContext(),
							Language.getLangStr(Language.TEXT_FAIL), 
							Toast.LENGTH_LONG).show();
    				break;
    			case ProgramType.ADD_VEHICLE_SUCCEED:
    			case ProgramType.MODIFY_VEHICLE_SUCCEED:
    			case ProgramType.DEL_VEHICLE_SUCCEED:
    				Toast.makeText( getApplicationContext(),
							Language.getLangStr(Language.TEXT_SUCCEED), 
							Toast.LENGTH_LONG).show();
    				break;
    			}
    		}
    	};
	}	
	//********************************************
    // 定时器的引用
    class VehicleTask extends java.util.TimerTask{

    	public  int   m_nCnt = 0;
    	
    	//*************************************************
    	//
		@Override
		public void run() {
			
			int				nCmdType = 0;
			Message 		msg = null;		
					
			nCmdType = GlobalData.getRecvMonitor();
			switch( nCmdType ){
			case ProgramType.LOGIN_SUCCEED:		//登陆成功
			case ProgramType.RECV_GPSDATA:	  	//收到GPS数据	
			case ProgramType.RECV_NO_GPSDATA: 	//没有GPS数据	
			case ProgramType.ADD_VEHICLE_SUCCEED:
			case ProgramType.ADD_VEHICLE_FAIL:
			case ProgramType.MODIFY_VEHICLE_SUCCEED:
			case ProgramType.MODIFY_VEHICLE_FAIL:
			case ProgramType.DEL_VEHICLE_SUCCEED:
			case ProgramType.DEL_VEHICLE_FAIL:	
			case ProgramType.UPDATE_VEHICLE_LIST_ADDR:
				msg = new Message();	
				msg.getData().putInt("ProgrameType", nCmdType );
				if( m_oProgressHandler != null ){
					m_oProgressHandler.sendMessage(msg);
				}
				break;
			}
		}
    }
}
