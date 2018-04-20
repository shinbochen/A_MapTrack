package com.AMaptrack;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import com.Data.GlobalData;
import com.Data.GoogleAddr;
import com.Data.Maptrack_ID;
import com.Data.ProgramType;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Protocol.AlarmData;
import com.Protocol.CarInfo;
import com.Protocol.HWState;
import com.Protocol.QueryServerAlarmType;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class AlarmInfo extends Activity {

	public  static	 final  int		ID_DATE_DIALOG = 0x01;		
	public  static	 final  int		ID_TIME_DIALOG = 0x02;
	
	public boolean					m_bQAlarmFlag = false;
	//开始时间
	public boolean					m_bStratFlag = true;	
	private Date					m_oStartTime = null;
	//结束时间
	private Date				    m_oEndTime = null;

	public  Button					m_bthQuery = null;
	public  Spinner					m_cbCarLicense = null;
	public  Spinner					m_cbAlarmType = null;	
	public  CarAdapter				m_oCarAdapter = null;
	public  AlarmTypeAdapter		m_oAlarmTypeAdapter = null;
	
	private	TextView	  			m_txtCarLicense = null;				
	private	TextView				m_txtAlarmType = null;					
	private	TextView				m_txtHeaderCarLicese = null;		
	private	TextView				m_txtHeaderAlarmType = null;			
	private	TextView				m_txtHeaderInfo = null;
	
	// 开始时间 TextView
	private TextView 				m_startTimeName = null;
	private TextView 				m_startTimeDesc = null;	
	// 结束时间 TextView
	private TextView 				m_endTimeName = null; 
	private TextView 				m_endTimeDesc = null;

	public  ListView				m_lvAlTime = null;	
	public  ListView				m_lvAlInfo = null;
	public  DateTimeAdapter			m_oDateTimeAdapter = null;
	public  AlarmInfoAdapter		m_oAlarmInfoAdapter = null;
	
	public  Timer					m_oVehicleTime = null;
	private Handler 				m_oProgressHandler = null; 
	public  AlarmReportTask			m_oAlarmTask = null;
	public 	Dialog 					m_oAlarnPromptDlg = null;
	
	public  int						m_nCarPos = 0;
	public  int						m_nAlarmCondition = 0;
	public  int						m_nAlarmRPos = -1;
	public  boolean					m_bQueryMove = true;
	public  long					m_lTime = 0;	
	public  Calendar 				m_oCalendar = null;

	
	//**************************************************
	//
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		 
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.alarminfo);	
	  
	      initVariable();
	      initEventObject();
	      initEvent();	    
	      initLabel();
	      ProgressMessageHandler();
	      if( m_oVehicleTime == null ){
	    	  m_oVehicleTime = new Timer();
		  }	      
	    //一秒检测一次是否登陆成功		
	      m_oAlarmTask = new AlarmReportTask();
	      m_oVehicleTime.schedule(m_oAlarmTask, 200, 2000 );
	 }
	//******************************************************
	//
	public  void FreeMemory(){
		m_oAlarmInfoAdapter.DelAllAlarmInfo();
		m_oCarAdapter.DelAllCar();		
	}
	//******************************************************
	//
	@Override
	protected void onDestroy() {

		if( m_oAlarmTask != null ){
			m_oAlarmTask.cancel();
		}
		FreeMemory();
		super.onDestroy();
	}
	 //**************************************************
	 //  初始化变量
	 public void  initVariable(){
		 
		 long  lTime = System.currentTimeMillis();
		 m_oCalendar = Calendar.getInstance();
		 m_oStartTime = new Date();
		 m_oStartTime.setTime(lTime);
		 m_oStartTime.setHours(0);
		 m_oStartTime.setMinutes(0);
		 m_oStartTime.setSeconds(1);
		 m_oEndTime = new Date();
		 m_oEndTime.setTime(lTime);
		 m_oEndTime.setHours(23);
		 m_oEndTime.setMinutes(59);
		 m_oEndTime.setSeconds(59);
	 }
	 //**************************************************
	 //  初始化事件对像
	 public void  initEventObject(){
		 
		 m_cbCarLicense = (Spinner)findViewById(R.id.AL_ComBox_CarLicense );
		 m_cbAlarmType = (Spinner)findViewById(R.id.AL_ComBox_AlarmType );
		 m_bthQuery = (Button)findViewById(R.id.AL_QueryAlarm );
		 
		 m_txtCarLicense = (TextView)findViewById( R.id.AL_Label_CarLicense);
		 m_txtAlarmType = (TextView)findViewById( R.id.AL_Label_AlarmType);
		 m_txtHeaderCarLicese = (TextView)findViewById( R.id.AL_Header_CarLicense);
		 m_txtHeaderAlarmType = (TextView)findViewById( R.id.AL_Header_AlarmType);
		 m_txtHeaderInfo = (TextView)findViewById( R.id.AL_Header_Info);
		 
		 m_lvAlTime = (ListView)findViewById( R.id.AL_ListTime );
		 m_lvAlTime.setBackgroundColor(0xFFFFFF);
		 m_lvAlTime.setCacheColorHint(0xFFFFFF);
			
		 m_lvAlInfo = (ListView)findViewById(R.id.AL_ListInfo );
		 m_lvAlInfo.setBackgroundColor(0xFFFFFF);
		 m_lvAlInfo.setCacheColorHint(0xFFFFFF);
	 }
	 //**************************************************
	 //
	 public  void  initLabel(){
		 
		 m_bthQuery.setText( Language.getLangStr(Language.TEXT_QUERY) );
		 m_txtCarLicense.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
		 m_txtAlarmType.setText( Language.getLangStr(Language.TEXT_ALARM_TYPE) );
		 m_txtHeaderCarLicese.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
		 m_txtHeaderAlarmType.setText( Language.getLangStr(Language.TEXT_ALARM_TYPE) );
		 m_txtHeaderInfo.setText( Language.getLangStr(Language.TEXT_REMARK) );
	 }
	 
	 //**************************************************
	 //  初始化事件触发
	 public void  initEvent(){		 
		 
		 m_bthQuery.setOnClickListener( eventQueryListener );
		 m_bthQuery.setOnTouchListener( queryTouchListener );
		
		 // 临控对像 ComBox
		 m_cbCarLicense.setOnItemSelectedListener( eventSelectCarListener );
		 m_oCarAdapter = new CarAdapter(this.getApplicationContext(), null );
	     m_cbCarLicense.setAdapter(m_oCarAdapter);
	     
	     // 报警类型 ComBox
	     m_cbAlarmType.setOnItemSelectedListener( eventSelectAlarmListener );
	     m_oAlarmTypeAdapter = new AlarmTypeAdapter(this.getApplicationContext(), getAlarmType() );
	     m_cbAlarmType.setAdapter(m_oAlarmTypeAdapter);	
	     	     
	     
	     m_lvAlTime.setOnItemClickListener( evemtAlTimeItemListener );
	     m_oDateTimeAdapter = new DateTimeAdapter( this.getApplicationContext() );
	     m_lvAlTime.setAdapter( m_oDateTimeAdapter );
	     
	     //创建上下文菜单
	     m_lvAlInfo.setOnCreateContextMenuListener( oCreateMenuAlInfo );
	     m_lvAlInfo.setOnItemLongClickListener( eventItemLongListener );
	     m_lvAlInfo.setOnItemClickListener( evnetAlInfoItemClickListener );
	     	     
	     m_oAlarmInfoAdapter = new AlarmInfoAdapter(this.getApplicationContext(), null );
	     m_lvAlInfo.setAdapter(m_oAlarmInfoAdapter);
	 }
	//**************************************************
	//
	 OnTouchListener queryTouchListener = new OnTouchListener(){
		 
        int temp[] = new int[]{0, 0};
        public boolean onTouch(View arg0, MotionEvent arg1) {
        	
            int eventAction = arg1.getAction();
            
            int x = (int)arg1.getRawX();
            int y = (int)arg1.getRawY();            
            switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
            		//Log.e("downx-y", x+" "+y+" "+eventAction);            		
            		Log.e("downx-y", "down");
                    temp[0] = (int)arg1.getX();
                    temp[1] = (int)(y-arg0.getTop());  
                    arg0.postInvalidate();                    
                    m_lTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
            	//Log.e("movex-y", x+" "+y+" "+eventAction);
            	//Log.e("pos", (x-temp[0])+" "+(y-temp[1])+" "+(x+arg0.getWidth()-temp[0])+" "+(y-temp[1]+arg0.getHeight()));
            	Log.e("downx-y", "move");
            	arg0.layout( x-temp[0], 
            				 y-temp[1], 
            				 x+arg0.getWidth()-temp[0], 
            				 y-temp[1]+arg0.getHeight()); 
            	
                 break;
            case MotionEvent.ACTION_UP:
            	if( (System.currentTimeMillis()-m_lTime) > 500 ){
            		m_bQueryMove = false;
            	}
            	else{
            		m_bQueryMove = true;
            	}
            	Log.e("downx-y", "up");            	
            	break;
            default:
                break;
            }
            return false;
        }
	};
	//**************************************************
	//
	 OnItemClickListener evnetAlInfoItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			setAlarmRPos( arg2 );
		}
	 };
	//**************************************************
	//   Feekback 响应的菜单事件	 
	 ListView.OnCreateContextMenuListener oCreateMenuAlInfo =new ListView.OnCreateContextMenuListener(){
			
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			
			menu.setHeaderTitle( Language.getLangStr(Language.TEXT_OPERATION_RECORD));
			menu.add( Menu.NONE, Maptrack_ID.ID_VIEW_ALARMREPORT, 0,  Language.getLangStr(Language.TEXT_VIEW) ).setIcon(R.drawable.view);
			menu.add( Menu.NONE, Maptrack_ID.ID_DEL_ALARM_RECORD, 0, Language.getLangStr(Language.TEXT_DEL)).setIcon(R.drawable.remove);
			menu.add( Menu.NONE, Maptrack_ID.ID_DELALL_ALARM_RECORD, 0, Language.getLangStr(Language.TEXT_DEL_ALL)).setIcon(R.drawable.removeall);	    	
			
		}
	};
	//**************************************************
	//
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
		case Maptrack_ID.ID_VIEW_ALARMREPORT: 
			viewAlarmReport();
			break;
		case Maptrack_ID.ID_DEL_ALARM_RECORD:
			delAlarmRecord(); 
			break;
		case Maptrack_ID.ID_DELALL_ALARM_RECORD:
			delAllAlarmRecord();
			break;
		}
		return false;
	}
	//***************************************************
	//
	public  void viewAlarmReport(){
		
		AlarmData					oAlarmData = null;
		Bundle						bundle = null;
		Intent						intent = null;
		
		if( getAlarmRPos() >= 0 ){
			
			intent = new Intent();
			bundle = new Bundle();
			oAlarmData = (AlarmData)m_oAlarmInfoAdapter.getItem( getAlarmRPos());
			bundle.putString( ConfigKey.KEY_DEUID, oAlarmData.getDEUID() );
			bundle.putInt( ConfigKey.KEY_ALARM_TYPE, oAlarmData.getAlarmType() );
			bundle.putInt( ConfigKey.KEY_STARTTIME, oAlarmData.getStartUTC()  );
			bundle.putString( ConfigKey.KEY_STARTADDR, oAlarmData.getBegAddr() );
			bundle.putInt( ConfigKey.KEY_ENDTIME, oAlarmData.getEndUTC() );
			bundle.putString( ConfigKey.KEY_ENDADDR, oAlarmData.getEndAddr() );
			bundle.putInt(ConfigKey.KEY_DURATION, oAlarmData.getDuration() );
			intent.putExtras(bundle );
			intent.setClass(getBaseContext (), ViewActivity.class);
			startActivity( intent );
		}
	}
	//**************************************************
	//
	public  void  setAlarmRPos( int  nPos ){
		
		m_nAlarmRPos = nPos;
	}
	public int   getAlarmRPos( ){
		
		return m_nAlarmRPos;
	}
	 //**************************************************
	 //
	 OnItemLongClickListener  eventItemLongListener = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick( AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
			// 记录选定的行
			m_nAlarmRPos = arg2;
			return false;
		}		 
	 };
	//**************************************************
	//	 
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	     
		 if( GlobalData.getCreateMenu() == GlobalData.CREATE_MENU_ALARM_REPORT ){ 
			Log.e("pop_Menu", "AlarmInfo");
			GlobalData.setCreateMenu(GlobalData.CREATE_MENU_NO);
			
			menu.add(0, Maptrack_ID.ID_VIEW_ALARM_RECORD, 0, Language.getLangStr(Language.TEXT_VIEW)).setIcon(R.drawable.view);
    		menu.add(0, Maptrack_ID.ID_DEL_ALARM_RECORD, 0, Language.getLangStr(Language.TEXT_DEL)).setIcon(R.drawable.remove);
    		menu.add(0, Maptrack_ID.ID_DELALL_ALARM_RECORD, 0, Language.getLangStr(Language.TEXT_DEL_ALL)).setIcon(R.drawable.removeall);
	    	menu.add(0, Maptrack_ID.ID_HELP, 0, Language.getLangStr(Language.TEXT_HELP)).setIcon(R.drawable.help);
	    	menu.add(0, Maptrack_ID.ID_EXIT, 0, Language.getLangStr(Language.TEXT_EXIT)).setIcon(R.drawable.cancel);
	    	
		}
		 return super.onCreateOptionsMenu(menu);
	}
	//**************************************************
	 //
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.e("弹出", "AlarmInfo");
		switch( item.getItemId() ){
		case Maptrack_ID.ID_VIEW_ALARM_RECORD:		//查看报警记录
			viewAlarmReport();
			break;
		case Maptrack_ID.ID_DEL_ALARM_RECORD:		//删除报警记录
			delAlarmRecord();
			break;
		case Maptrack_ID.ID_DELALL_ALARM_RECORD:
			delAllAlarmRecord();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//**************************************************
	//
	public  void 	delAlarmRecord(){
		
		if( getAlarmRPos() >= 0 ){
			m_oAlarmInfoAdapter.DelAlarmInfo( getAlarmRPos() );
		}
	}
	//**************************************************
	//
	public  void 	delAllAlarmRecord(){
		m_oAlarmInfoAdapter.DelAllAlarmInfo();
	}
	 //**************************************************
	 //
	 OnItemClickListener  evemtAlTimeItemListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			case 0:		//设置开始时间
				m_bStratFlag = true;
				Log.e("StartTime", "OK");
				m_oCalendar.setTime(m_oStartTime);
				showDialog(AlarmInfo.ID_TIME_DIALOG);
				showDialog(AlarmInfo.ID_DATE_DIALOG);				
				break;
			case 1:		//设置结束时间
				m_bStratFlag = false;
				Log.e("EndTime", "OK");
				m_oCalendar.setTime(m_oEndTime);				
				showDialog(AlarmInfo.ID_TIME_DIALOG);
				showDialog(AlarmInfo.ID_DATE_DIALOG);
				break;
			}			
		}	
		 
	 };
	 //**************************************************
	 //  获取查询报警状态
	 public List<AlarmType> getAlarmType(){
		 
		 AlarmType		   oAlarmType = null;
		 List<AlarmType>   lstAlarmType = new ArrayList<AlarmType>();
	     lstAlarmType.clear();
	     
	     int  nCol = 0;
	     while( true ){
	    	 
	    	 if( QueryServerAlarmType.m_nAlarmTitle[nCol][1] == -1 ){
	    		 
	    		 break;
	    	 }
	    	 oAlarmType = new AlarmType();
	    	 oAlarmType.setAlarmName( Language.getLangStr( QueryServerAlarmType.m_nAlarmTitle[nCol][1]) );
	    	 oAlarmType.setAlarmType( QueryServerAlarmType.m_nAlarmTitle[nCol][0]);
	    	 nCol++;
	    	 lstAlarmType.add(oAlarmType);
	     }
	     
	     return lstAlarmType;
	 }
	 //**************************************************
	 //  车辆ComBox 选定事件
	 OnItemSelectedListener eventSelectCarListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			m_nCarPos = arg2; //记录选车的车辆
			Log.e("SelectCarItem", ((CarInfo)m_oCarAdapter.getItem(arg2)).GetDEUID()  );
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	 };
	//**************************************************
	//  报警ComBox 选定事件
	 OnItemSelectedListener eventSelectAlarmListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			// 查询报警条件
			m_nAlarmCondition = ((AlarmType)m_oAlarmTypeAdapter.getItem(arg2)).getAlarmType();
			Log.e("AlarmInfoItem", ((AlarmType)m_oAlarmTypeAdapter.getItem(arg2)).getAlarmName()  );
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	 };
	//**************************************************
	 //  查询报警记录
	 OnClickListener  eventQueryListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			int  				nStartTime = 0;
			int  				nEndTime = 0;
			ServerCommand		oCmd =null;
			
			if( m_bQueryMove  ){				
				
				oCmd = new ServerCommand();
				nStartTime = (int)(m_oStartTime.getTime()/1000);
				nEndTime   = (int)(m_oEndTime.getTime()/1000); 
				oCmd.setQueryAlarmCondition( GlobalData.getUserInfo().getUserName(),
											 ((CarInfo)m_oCarAdapter.getItem(m_nCarPos)).GetDEUID(), 
											  nStartTime,
											  nEndTime, 
											  m_nAlarmCondition );
				GlobalData.addSendServerData(oCmd);
				m_oAlarnPromptDlg = CreateDialog("正在查询,请稍后!");
				// 记录录标志
				m_bQAlarmFlag = true;
				// 清除查询报警数据
				GlobalData.clearAlarmData();
				m_oAlarmInfoAdapter.DelAllAlarmInfo();
				setAlarmRPos(-1);
			}
		}		 
	 };
	 //**************************************************
	 // 
	 @Override
	 protected Dialog onCreateDialog(int id) {

		 switch (id) {
		// 显示日期对话框
		case AlarmInfo.ID_DATE_DIALOG:			
				return new DatePickerDialog(this, mDateSetListener, 
												m_oCalendar.get(Calendar.YEAR), 
												m_oCalendar.get(Calendar.MONTH),
												m_oCalendar.get(Calendar.DAY_OF_MONTH));
		// 显示时间对话框
		case AlarmInfo.ID_TIME_DIALOG:
			return new TimePickerDialog(this, mTimeSetListener, 
											  m_oCalendar.get(Calendar.HOUR),
											  m_oCalendar.get(Calendar.MINUTE), 
											  true);
		
		}	
		return super.onCreateDialog(id);
	}
	//**************************************************
	// 时间选择对话框
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
			new TimePickerDialog.OnTimeSetListener() {		

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			if( m_bStratFlag ){	
				m_oStartTime.setHours(hourOfDay);
				m_oStartTime.setMinutes(minute);
			}
			else{
				m_oEndTime.setHours(hourOfDay);
				m_oEndTime.setMinutes(minute);
			}
			m_oDateTimeAdapter.notifyDataSetChanged();
			
		}
	};
	//**************************************************
	// 日期选择对话框
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			if( m_bStratFlag ){	
				m_oStartTime.setYear(year-1900);
				m_oStartTime.setMonth(monthOfYear);
				m_oStartTime.setDate(dayOfMonth);
			}
			else{
				m_oEndTime.setYear(year-1900);
				m_oEndTime.setMonth(monthOfYear);
				m_oEndTime.setDate(dayOfMonth);
			}	
			m_oDateTimeAdapter.notifyDataSetChanged();
		}		
	};
	
	//*******************************************************
	//  监控对像扩充类
	class CarAdapter extends BaseAdapter{		
		
		private List<CarInfo> 		m_oCarList = null;
		private Context 			m_oContext = null;
		    		

	    //*********************************************************
	    //
	    public CarAdapter(Context pContext, List<CarInfo> inData) {
	
	    	m_oContext = pContext;	
	    	if( m_oCarList == null ){
	    		m_oCarList = new ArrayList<CarInfo>();
	    		m_oCarList.clear();
	    	}
	    	if( inData != null ){
	    		AddCar( inData );
	    	}	    			
	    }
	    //*********************************************************
	    //
	    public void updateCar( List<CarInfo> inData ){
	    	
	    	CarInfo    oCarInfo = null;

	    	m_oCarList.clear();
	    	Iterator<CarInfo>	it = inData.iterator();
	    	while( it.hasNext() ){
	    		oCarInfo = it.next();
	    		AddCar(oCarInfo );
	    	}
	    	this.notifyDataSetChanged();
	    }
	    //*********************************************************
	    //
	    public void AddCar( List<CarInfo>  inData ){
	    	
	    	CarInfo    oCarInfo = null;
	    		    	
	    	Iterator<CarInfo>	it = inData.iterator();
	    	while( it.hasNext() ){
	    		oCarInfo = it.next();
	    		AddCar(oCarInfo );
	    	}
	    }
	    //*********************************************************
	    //
	    public void AddCar( CarInfo  oData ){
	    	
	    	m_oCarList.add(oData);
	    	this.notifyDataSetChanged();
	    }
	    //*********************************************************
	    //
	    public void  DelCar( int position ){
	    
	    	if( m_oCarList.size() > position)
	    		m_oCarList.remove(position);
	    	this.notifyDataSetChanged();
	    }
	    //*********************************************************
	    //
	    public  void  DelCar( String  strDEUID ){
	    	
	    	CarInfo 	oCarInfo = null;
	    	
	    	Iterator<CarInfo>		it = m_oCarList.iterator();
	    	while(it.hasNext() ){
	    		
	    		oCarInfo = it.next();
	    		if( oCarInfo.GetDEUID().equals(strDEUID) ){	    			
	    			m_oCarList.remove(oCarInfo);
	    			this.notifyDataSetChanged();
	    			break;
	    		}
	    	}
	    }
	    //*********************************************************
	    //
	    public void  DelAllCar(){
	    	
	    	m_oCarList.clear();
			this.notifyDataSetChanged();
	    	
	    }
	    //*********************************************************
	    //
		@Override
		public int getCount() {
			return m_oCarList.size();
		}
	    //*********************************************************
	    //
		@Override
		public Object getItem(int position) {
			 
			return m_oCarList.get(position);
		}
	    //*********************************************************
	    //
		@Override
		public long getItemId(int position) {
			return position;
		}
	    //*********************************************************
	    //
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			 LayoutInflater  _LayoutInflater=LayoutInflater.from(m_oContext);
			 convertView = _LayoutInflater.inflate(R.layout.combox, null);
			 if(convertView!=null)
			 {
			     TextView text=(TextView)convertView.findViewById(R.id.CarCombox_CarLicense);
			     text.setText(m_oCarList.get(position).GetCarLicense() );
			  }
			  return convertView;
		}	
	}
	
	//*******************************************************
	//  报警类型扩充类
	class AlarmTypeAdapter extends BaseAdapter{

		public List<AlarmType>		m_lstAlarmType = null;
		public Context				m_oContext = null;
		
		//*********************************************************
	    //
	    public AlarmTypeAdapter(Context pContext, List<AlarmType> inData) {
	
	    	m_oContext = pContext;	
	    	if( m_lstAlarmType == null ){
	    		m_lstAlarmType = new ArrayList<AlarmType>();
	    		m_lstAlarmType.clear();
	    	}
	    	if( inData != null ){
	    		AddAlarmType( inData );
	    	}	    			
	    }
		//*********************************************************
	    //
	    public  void  AddAlarmType( List<AlarmType>  inData ){
	    	
	    	AlarmType		oAlarm = null;
	    	
	    	Iterator<AlarmType>		it = inData.iterator();
	    	while( it.hasNext() ){
	    		
	    		oAlarm = it.next();
	    		AddAlarmType(oAlarm);
	    	}
	    }
		//*********************************************************
	    //
	    public  void AddAlarmType( AlarmType  oData ){
	    	
	    	m_lstAlarmType.add(oData);
	    	this.notifyDataSetChanged();
	    }
		//*********************************************************
	    //
		@Override
		public int getCount() {
			
			return m_lstAlarmType.size();
		}
		//*********************************************************
	    //
		@Override
		public Object getItem(int position) {
			 
			return m_lstAlarmType.get(position);
		}
		//*********************************************************
	    //
		@Override
		public long getItemId(int position) {
			
			return position;
		}
		//*********************************************************
	    //
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater  _LayoutInflater=LayoutInflater.from(m_oContext);
			 convertView = _LayoutInflater.inflate(R.layout.combox, null);
			 if(convertView!=null)
			 {
			     TextView text=(TextView)convertView.findViewById(R.id.CarCombox_CarLicense);
			     text.setText(m_lstAlarmType.get(position).getAlarmName() );
			  }
			  return convertView;
		}		
		
	}
				
	//*******************************************************
	//  设置时间的扩充类
	class DateTimeAdapter extends BaseAdapter{

		public  Context  		 m_oContext = null;		
		public  SimpleDateFormat m_oFormatTime = null;
		
		// 列表显示内容
		String[] strs = {  Language.getLangStr(Language.TEXT_STARTTIME),
						   Language.getLangStr(Language.TEXT_ENDTIME) };
		
		//*********************************************************
	    //
	    public DateTimeAdapter(Context pContext ) {
	
	    	m_oContext = pContext;
	    	m_oFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.notifyDataSetChanged();
	    }
		//*********************************************************
	    //
		@Override
		public int getCount() {
			
			return strs.length;
		}
		//*********************************************************
	    //
		@Override
		public Object getItem(int position) {

			return position;
		}
		//*********************************************************
	    //
		@Override
		public long getItemId(int position){
			
			return position;
		}
		//*********************************************************
	    //
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater  _LayoutInflater=LayoutInflater.from(m_oContext);			
			View v = _LayoutInflater.inflate(R.layout.setuptime, null);
			switch (position) {
			// 提醒日期
			case 0:				
				m_startTimeName = (TextView) v.findViewById(R.id.name);
				m_startTimeDesc = (TextView) v.findViewById(R.id.desc);
				m_startTimeName.setText( strs[position] );	
				m_startTimeDesc.setText( m_oFormatTime.format( m_oStartTime ) );
				return v;
			// 提醒时间
			case 1:				
				m_endTimeName = (TextView) v.findViewById(R.id.name);
				m_endTimeDesc = (TextView) v.findViewById(R.id.desc);
				m_endTimeName.setText(strs[position]);
				m_endTimeDesc.setText( m_oFormatTime.format( m_oEndTime ) );
				return v;
			default:
				break;
			}
			return null;
		}		
	}

	
	//*******************************************************
	//  显示查询报警信息
	class AlarmInfoAdapter extends BaseAdapter{

		private List<AlarmData>			m_lstData = null;
	    public  HashMap<Integer,View> 	m_oMapObj = null;
		public  Context  				m_oContext = null;
		
		//*******************************************************
		//
		public AlarmInfoAdapter( Context  oContext, List<AlarmData> inData ){
						
			m_oContext = oContext;
			if( m_lstData == null ){
				m_lstData = new ArrayList<AlarmData>();
				m_lstData.clear();
			}
			if( m_oMapObj == null ){
				m_oMapObj = new HashMap<Integer,View>();
				m_oMapObj.clear();
			}
			if( inData != null ){
				AddAlarmInfo( inData );
			}
		}
		//*******************************************************
		//
		public void  AddAlarmInfo( List<AlarmData> inData ){
			
			AlarmData		oData = null;
			
			Iterator<AlarmData>		it = inData.iterator();
			while( it.hasNext() ){
				
				oData = it.next();
				AddAlarmInfo( oData );
			}
		}
		//*******************************************************
		//
		public void  AddAlarmInfo( AlarmData  oData ){
			
			m_lstData.add( oData);
			this.notifyDataSetChanged();
		}
		//*******************************************************
		//
		public  void DelAlarmInfo( int  pos ){
			
			if( m_lstData.size() > pos ){
				
				m_lstData.remove(pos);
				m_oMapObj.clear();
				this.notifyDataSetChanged();
			}
		}
		//*******************************************************
		//
		public  void DelAllAlarmInfo(){
			
			m_lstData.clear();
			m_oMapObj.clear();
			this.notifyDataSetChanged();
		}
		//*******************************************************
		//
		@Override
		public int getCount() {
			return m_lstData.size();
		}
		//*******************************************************
		//
		@Override
		public Object getItem(int arg0) {
			
			return m_lstData.get(arg0);
		}
		//*******************************************************
		//
		@Override
		public long getItemId(int position) {
			
			return position;
		}
		//*******************************************************
		//
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View   				oView = null;
			ShowAlarmInfoView   oShowAlarm = null;
			
			if( m_oMapObj.get(position) == null ){
				
				oShowAlarm = new ShowAlarmInfoView();
				LayoutInflater mInflater = (LayoutInflater) m_oContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				oView = mInflater.inflate(R.layout.show_alarm_info, null);
			
				oShowAlarm.Text_CarLicense = (TextView)oView.findViewById(R.id.AL_CarLicense );
				oShowAlarm.Text_AlarmType = (TextView)oView.findViewById(R.id.AL_AlarmType );				
				oShowAlarm.Text_Info = (TextView)oView.findViewById(R.id.AL_Info );
				
				oShowAlarm.Image_Car = (ImageView)oView.findViewById(R.id.AL_Car_img);	            
				oShowAlarm.Image_Car.setScaleType(ImageView.ScaleType.CENTER_CROP);
				oShowAlarm.Image_Car.setPadding(8, 8, 8, 8);
	            
	            
				final int p = position;
				oView.setTag(oShowAlarm);
				m_oMapObj.put(position, oView );
				
				oShowAlarm.Text_CarLicense.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
						setAlarmRPos(p);
					}					
				});
				oShowAlarm.Text_AlarmType.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
						setAlarmRPos(p);
					}					
				});
				oShowAlarm.Text_Info.setOnClickListener( new OnClickListener(){

					@Override
					public void onClick(View v) {
						setAlarmRPos(p);
					}					
				});
			}	
			else{
				oView = m_oMapObj.get( position );
				oShowAlarm = (ShowAlarmInfoView)oView.getTag();
			}
			oShowAlarm.Image_Car.setImageResource( R.drawable.car );
			String   strDEUID = m_lstData.get(position).getDEUID();			
			oShowAlarm.Text_CarLicense.setText(GlobalData.findDEUIDByCarInfo(strDEUID).GetCarLicense() );
			oShowAlarm.Text_AlarmType.setText( HWState.GetAlarmString( m_lstData.get(position).m_nALState) );
			oShowAlarm.Text_Info.setText( m_lstData.get(position).getStartTime()+" "+m_lstData.get(position).getBegAddr() );
			return oView;
		}		
	}
	//******************************************************
	//	查询提示框
	protected Dialog	CreateDialog( String  strPrompt ) {
					
		ProgressDialog  dialog = new ProgressDialog(AlarmInfo.this);
	    dialog.setIndeterminate(true);
	    dialog.setMessage( strPrompt );
	    dialog.setCancelable(true);
	    dialog.show();
	    return dialog;
	}
	//*****************************************************
	//	更新报表数据报表
	public void updateAlarmData(){
		
		List<AlarmData>		lstAlarm = new ArrayList<AlarmData>();
		Iterator<AlarmData>   it = null;
		
		lstAlarm.clear();
		lstAlarm = GlobalData.getQAlarmData();
		
		it = lstAlarm.iterator();
		while( it.hasNext() ){
			m_oAlarmInfoAdapter.AddAlarmInfo( it.next() );	
		}
		// 开启线程查地址
		new Thread( new GoogleAddr( getApplicationContext(),1 )).start();
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
    				m_oCarAdapter.AddCar(GlobalData.getAllVehicle() );
    				break;
    			case ProgramType.UPDATE_VEHICLE:	
    				m_oCarAdapter.updateCar(GlobalData.getAllVehicle() );
    				break;
    			case ProgramType.QUERY_DATA_TIMEOUT://查询数据超时
    				m_oAlarnPromptDlg.cancel();
    				Toast.makeText( getApplicationContext(),
		    						"查询超时!", 
									Toast.LENGTH_LONG).show();
    				break;
    			case ProgramType.RECV_QUERYALARM:	//收到报警数据
    				m_bQAlarmFlag = false;
    				m_oAlarnPromptDlg.cancel();
    				updateAlarmData();
    				break;
    			case ProgramType.RECV_NO_QUERYALARM:	//没有报警数据
    				m_bQAlarmFlag = false;
    				m_oAlarnPromptDlg.cancel();
    				Toast.makeText( getApplicationContext(),
							Language.getLangStr(Language.TEXT_WARING_NO_DATA), 
							Toast.LENGTH_LONG).show();
    				break;
    			// 更新数据,加载地址信息	
    			case ProgramType.UPDATE_ALARM_REPORT_LIST_ADDR:
    				m_oAlarmInfoAdapter.notifyDataSetChanged();
    				break;
    			}
    		}
    	};
	}
	
	//********************************************
    // 定时器的引用
    class AlarmReportTask extends java.util.TimerTask{

    	public  int   		m_nCnt = 0;
    	
    	
    	//****************************************
    	//
    	public void checkQAlarmData(){
    		
    		Message  msg = null;
    		if( m_bQAlarmFlag ){
    			
    			if( m_nCnt++ > 30 ){
    				m_nCnt = 0;
    				m_bQAlarmFlag = false;
    				msg = new Message();	
    				msg.getData().putInt("ProgrameType", ProgramType.QUERY_DATA_TIMEOUT );
    				if( m_oProgressHandler != null ){
    					m_oProgressHandler.sendMessage(msg);
    				}
    			}
    		}
    		else{
    			m_nCnt = 0;
    		}
    	}
    	//****************************************
    	//
		@Override
		public void run() {
			int				nCmdType = 0;
			Message 		msg = null;		
			
			nCmdType = GlobalData.getRecvAlarmReport();
			switch( nCmdType ){
			case ProgramType.LOGIN_SUCCEED:		//登陆成功
			case ProgramType.RECV_QUERYALARM:
			case ProgramType.RECV_NO_QUERYALARM:	
			case ProgramType.UPDATE_ALARM_REPORT_LIST_ADDR:
			case ProgramType.UPDATE_VEHICLE:				
				msg = new Message();	
				msg.getData().putInt("ProgrameType", nCmdType );
				if( m_oProgressHandler != null ){
					m_oProgressHandler.sendMessage(msg);
				}
				break;
			}			
			checkQAlarmData();
		}
    }
}
