package com.AMaptrack;

import java.util.HashMap;

import com.Data.GlobalData;
import com.Language.Language;
import com.Protocol.AlarmData;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewActivity extends Activity {

	public  ListView		m_lstAlarm = null;
	public  ViewAdapter		m_oViewAdapter = null;
	public  AlarmData   	m_oAlarmData = null;
	
	//*****************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView( R.layout.view );

		initEventObj();
		getIntentData();
		initEvent();
	}
	//*****************************************************
	//
	public  void getIntentData(){

		Bundle		bundle = this.getIntent().getExtras();
		
		m_oAlarmData = new AlarmData();
		m_oAlarmData.setDEUID( bundle.getString( ConfigKey.KEY_DEUID) );
		m_oAlarmData.setAlarmType( bundle.getInt( ConfigKey.KEY_ALARM_TYPE) );
		m_oAlarmData.setStartUTC( bundle.getInt( ConfigKey.KEY_STARTTIME) );
		m_oAlarmData.setBegAddr(bundle.getString( ConfigKey.KEY_STARTADDR) );
		m_oAlarmData.setEndUTC(bundle.getInt( ConfigKey.KEY_ENDTIME) );
		m_oAlarmData.setEndAddr(bundle.getString( ConfigKey.KEY_ENDADDR) );
		m_oAlarmData.setDuration(bundle.getInt(ConfigKey.KEY_DURATION) );
	}
	//*****************************************************
	//
	public  void  initEventObj(){
		
		m_lstAlarm = (ListView)findViewById( R.id.list_view );
	}
	//*****************************************************
	//
	public void  initEvent(){
		
		m_oViewAdapter = new ViewAdapter( this.getApplicationContext(), m_oAlarmData );
		m_lstAlarm.setAdapter(m_oViewAdapter);
	}
	//*****************************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	//*****************************************************
	//
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	//***********************************************************
	//  车辆列表的显示 适配器
	//
	public class ViewAdapter extends BaseAdapter {

		public 	  Context					m_oContext = null;
		public    AlarmData					m_oData = null;
	    public    HashMap<Integer,View> 	m_oMapObj = null;
		
		public  ViewAdapter( Context  oContext, AlarmData  oData ){
			
			m_oContext = oContext;
			m_oMapObj = new HashMap<Integer,View>();
			m_oMapObj.clear();
			if( oData == null ){
				return ;
			}		
			Log.e("ViewAdapter", "0");
			m_oData = oData;		
			this.notifyDataSetChanged();
		}
		//*****************************************************
		//
		@Override
		public int getCount() {

			return 7;
		}
		//*****************************************************
		//
		@Override
		public Object getItem(int arg0) {

			return null;
		}
		//*****************************************************
		//
		@Override
		public long getItemId(int arg0) {

			return 0;
		}
		//*****************************************************
		//
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {

			View		vw = null;
			ViewClass   oView = null;

			if( m_oMapObj.get(arg0 ) == null ){
				oView =  new ViewClass();
				LayoutInflater  _LayoutInflater=LayoutInflater.from(m_oContext);			
				vw = _LayoutInflater.inflate(R.layout.setuptime, null);
				oView.m_txtName = (TextView)vw.findViewById(R.id.name);
				oView.m_txtDesc = (TextView)vw.findViewById(R.id.desc);				
				m_oMapObj.put(arg0, vw );
				vw.setTag(oView);
			}
			else{
				vw = m_oMapObj.get(arg0 );
				oView = (ViewClass)vw.getTag();
			}
			if( arg0 == 0 ){
				oView.setName( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
				oView.setDesc( GlobalData.findDEUIDByCarInfo(m_oData.getDEUID()).GetCarLicense() );				
			}
			else{				
				oView.setName( m_oData.getPositionTitle(arg0) );
				oView.setDesc( m_oData.getPositionValues(arg0) );
			}
			oView.m_txtName.setText( oView.getName() );
			oView.m_txtDesc.setText( oView.getDesc() );
			return vw;
		} 
	}
	//*****************************************************
	//
	class ViewClass{
		
		public   TextView	m_txtName = null;
		public   TextView   m_txtDesc = null;
		public   String   	m_strName ="";
		public   String	  	m_strDesc = "";
		
		public  void  setName( String strName ){
			m_strName =  strName;
			
		}
		public String  getName( ){
			return m_strName;
		}
		public void  setDesc( String  strName ){
			
			m_strDesc = strName;
			
		}
		public String  getDesc(){
			
			return m_strDesc;
		}
	}
}