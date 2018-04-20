package com.AMaptrack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.Data.GlobalData;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Protocol.CarInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


public class PlaySetting extends Activity {

	public  static final  int   	PLAY_MAXTIME = 86400;
	public   TextView				m_txtCarLicense = null;
	public   Button					m_bthPlay = null;
	public   Button					m_bthCancel = null;
	public   ListView				m_listVehicle = null;
	public   ShowVehicleAdapter		m_oShowVehicleAdapter = null;
		
	//开始时间
	public boolean					m_bStratFlag = true;	
	private Date					m_oStartTime = null;
	//结束时间
	private Date				    m_oEndTime = null;
	// 开始时间 TextView
	private TextView 				m_startTimeName = null;
	private TextView 				m_startTimeDesc = null;	
	// 结束时间 TextView
	private TextView 				m_endTimeName = null; 
	private TextView 				m_endTimeDesc = null;
	public  DateTimeAdapter			m_oDateTimeAdapter = null;
	public  ListView				m_lvPlayTime = null;	
	public  Calendar 				m_oCalendar = null;
	
	
	//******************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
		this.setContentView( R.layout.playsetting );		
		this.setTitle( Language.getLangStr( Language.TEXT_VEHICLE_HISTORY_TRACE) );
		initEvent();		
		initVariable();
		initLabel();
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
	//******************************************************
	//
	public  void  initEvent(){
		
		m_bthPlay  = (Button)findViewById( R.id.OkButton);
		m_bthCancel = (Button)findViewById( R.id.cancelButton );
		
		m_txtCarLicense = (TextView)findViewById( R.id.Label_CarLicense);
		
		m_listVehicle = (ListView)findViewById( R.id.List_Vehicle);
		m_lvPlayTime = (ListView)findViewById( R.id.Play_Time );
		
		m_bthPlay.setOnClickListener( playClickEvent );
		m_bthCancel.setOnClickListener( cancelClickEvent );
		
		//设置点击选择监听器
		m_listVehicle.setOnItemClickListener(eventVehicleListener);
		//设置选中监听器
		m_listVehicle.setOnItemSelectedListener(eventSelectVehicleListener);				
		// 加载容器
		m_oShowVehicleAdapter = new ShowVehicleAdapter( getApplicationContext(), 
														GlobalData.getAllVehicle()  );
		m_listVehicle.setAdapter(m_oShowVehicleAdapter );	
		
		
		m_lvPlayTime.setBackgroundColor(0xFFFFFF);
		m_lvPlayTime.setCacheColorHint(0xFFFFFF);		
		m_lvPlayTime.setOnItemClickListener( evemtPlayTimeItemListener );
	    m_oDateTimeAdapter = new DateTimeAdapter( this.getApplicationContext() );
	    m_lvPlayTime.setAdapter( m_oDateTimeAdapter );
	    
	    m_bthPlay.setText( Language.getLangStr( Language.TEXT_OK));
	    m_bthCancel.setText( Language.getLangStr( Language.TEXT_CANCEL));
	    m_txtCarLicense.setText( Language.getLangStr( Language.TEXT_MONITOR_OBJECT));
	}
	//**************************************************
	// 
	public void  initLabel(){
		
		m_txtCarLicense.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
	}
	//**************************************************
	//
	OnItemClickListener  evemtPlayTimeItemListener = new OnItemClickListener(){

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
	//**************************************************
	//  设置点击选择监听器
	OnItemClickListener	 eventVehicleListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Log.e("VehicleList", Integer.toString(arg2));
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
	//******************************************************
	//
	OnClickListener   playClickEvent = new OnClickListener(){

		@Override
		public void onClick(View v) {

			Intent		intent = null;
			
			queryPlayVehicle();			
			intent = new Intent();
			intent.setClass( PlaySetting.this, AMapView.class);  
			setResult(RESULT_OK, intent );
			finishExit();
		}		
	};
	//******************************************************
	// 
	public  void  queryPlayVehicle(){

		int  				nStartTime = 0;
		int  				nEndTime = 0;
		ServerCommand		oCmd =null;
		List<String>		lstDEUID = null;
		
		lstDEUID = m_oShowVehicleAdapter.getSelecttedCar();
		if( lstDEUID.size() <= 0 ){
			showWarringDialog( Language.getLangStr(Language.TEXT_SELECT_VEHICLE));
			return ;
		}
		oCmd = new ServerCommand();
		nStartTime = (int)(m_oStartTime.getTime()/1000);
		nEndTime   = (int)(m_oEndTime.getTime()/1000); 
		if( (nEndTime - nStartTime) > PLAY_MAXTIME  ){
			
			showWarringDialog( Language.getLangStr(Language.TEXT_RANGE_EXCEED_DAYS));
			return ;
		}
		// 查询有速度的数据
		oCmd.setQueryPlayGPSData( GlobalData.getUserInfo().getUserName(),
								  lstDEUID.get(0), 
								  nStartTime,
								  nEndTime, 
								  1 );
		GlobalData.clearPlayGPSData();
		GlobalData.addSendServerData(oCmd);
	}
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(PlaySetting.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING) );
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	} 
	//******************************************************
	//  退出
	OnClickListener   cancelClickEvent = new OnClickListener(){

		@Override
		public void onClick(View v) {
			finish();
		}		
	};
	//******************************************************
	//
	public  void  finishExit(){
		
		super.finish();
	}
	//******************************************************
	//
	@Override
	protected void onDestroy() {

		super.onDestroy();
		m_oShowVehicleAdapter.delAllData();
	}
	//*************************************************
	//
	//
	public class ShowVehicleAdapter extends BaseAdapter {
		
		public 	Context 				m_oContext = null;
		public  List<CarInfo>			m_lstData = null;  
	    public  HashMap<Integer,View>  	m_oMapObj = null;

		//*************************************************
		//
		public  ShowVehicleAdapter( Context  oContext,  List<CarInfo>	inData ){
			
			m_oContext = oContext;
			if( inData == null ){
				return;
			}
			m_lstData = new ArrayList<CarInfo>();
			m_lstData.clear();
			m_oMapObj = new HashMap<Integer, View>();
			m_oMapObj.clear();
			LoadData( inData );
		}
		//*************************************************
		//
		public void delAllData(){
			m_lstData.clear();
			m_oMapObj.clear();
			this.notifyDataSetChanged();
		}
		//*************************************************
		//
		public void  LoadData( List<CarInfo>  inData ){
			
			for( int nCnt = 0; nCnt < inData.size(); nCnt++ ){				
				m_lstData.add(inData.get(nCnt));
			}
			this.notifyDataSetChanged();
		}
		//*************************************************
		//
		@Override
		public int getCount() {

			return m_lstData.size();
		}
		//*************************************************
		//
		@Override
		public Object getItem(int arg0) {

			return m_lstData.get(arg0);
		}
		//*************************************************
		//
		@Override
		public long getItemId(int arg0) {

			return arg0;
		}
		//************************************************
		//
		public List<String>  getSelecttedCar(){
			
			View				view = null;
			VehicleViewHolder		holder = null;
			List<String>		lstDEUID = new ArrayList<String>();
			
			
			lstDEUID.clear();
			for( int nCnt = 0; nCnt < m_oMapObj.size(); nCnt++ ){
				
				view = m_oMapObj.get(nCnt);
				holder = (VehicleViewHolder)view.getTag();
				if( holder.getSelected() ){
					lstDEUID.add( holder.getDEUID() );
				}
			}
			return lstDEUID;
		}
		//*************************************************
		//
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {

			View 					oView = null;
	    	VehicleViewHolder 		holder = null; 
	        
	        if( m_oMapObj.get(position) == null ){
	        	holder = new VehicleViewHolder();
	            LayoutInflater mInflater = (LayoutInflater) m_oContext  
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            oView = mInflater.inflate(R.layout.vehicle2, null);
	            holder.m_chkCarlicense = (CheckedTextView)oView.findViewById( R.id.Check_CarLicense);

	            holder.setDEUID( m_lstData.get(position).GetDEUID() );
	            final int p = position;
	            m_oMapObj.put( position, oView );
	            oView.setTag(holder);
	            
	            holder.m_chkCarlicense.setOnClickListener(new OnClickListener(){
	            	
					@Override
					public void onClick(View v) {
						
						VehicleViewHolder		h  = null;
						View 	   				Vw = null;      
						
						Vw = m_oMapObj.get(p);
						if( Vw == null ){
							Log.e("Car", "error" );
							return;
						}
						h = (VehicleViewHolder)Vw.getTag();
						h.m_chkCarlicense.toggle();
						h.setSelectedEx( h.m_chkCarlicense.isChecked() );
						updateSelected( m_lstData.get(p).GetDEUID(), h.getSelected() );
						Log.e("Car", m_lstData.get(p).GetCarLicense() );
					}	            	
	            });
	        }
	        else{
	        	oView = m_oMapObj.get(position);  
                holder = (VehicleViewHolder)oView.getTag(); 
	        }
	        holder.m_chkCarlicense.setText( m_lstData.get(position).GetCarLicense() );
	        holder.m_chkCarlicense.setChecked( holder.getSelected() );
			return oView;
		}
		//*****************************************************************
		//
		public  void  updateSelected( String  strDEUID,  boolean  bFlag ){
			
			View						oView = null;
			VehicleViewHolder 			holder = null; 
						
			for( int nCnt = 0;  nCnt < m_oMapObj.size(); nCnt++ ){
				
				oView = m_oMapObj.get( nCnt );
				holder = (VehicleViewHolder)oView.getTag();
				if( holder.getDEUID().equals( strDEUID ) == false ){
					holder.setSelected( false );
				}
			}
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
}
