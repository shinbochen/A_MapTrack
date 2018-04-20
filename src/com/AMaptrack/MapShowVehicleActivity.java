package com.AMaptrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.Data.GlobalData;
import com.Language.Language;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MapShowVehicleActivity extends Activity {

	public   TextView			m_labelVehicleNumber = null;
	public  Button				m_bthOK = null;
	public  Button				m_bthCancel =null;

	public  ListView			m_listVehicle = null;
	public  ShowVehicleAdapter	m_oShowVehicleAdapter = null;
	public  List<String>		m_lstDEUID = null;
	
	public  int					m_nCarPosition = 0; //记录车辆的最后光标
	
	//*************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mapshowvehicle );

		initEventObj();
		initEvent();
		initData();
		this.setTitle( Language.getLangStr( Language.TEXT_VEHICLE_SHOW) );
	}
	//*************************************************
	//
	public void  initEventObj(){
						
		m_labelVehicleNumber = (TextView)findViewById(R.id.Label_Vehicle );
		m_listVehicle = (ListView)findViewById( R.id.List_Vehicle );
		m_bthOK = (Button)findViewById( R.id.OkButton );
		m_bthCancel = (Button)findViewById( R.id.cancelButton );
				
		m_bthOK.setText(Language.getLangStr(Language.TEXT_OK) );
		m_bthCancel.setText(Language.getLangStr(Language.TEXT_CANCEL) );
	}
	//*************************************************
	//
	public void  initEvent(){
		
		boolean						bFlag = false;
		CarInfo						oCarInfo;
		GPSData						oGPSData;
		VehicleListData				oVehicleListData;
		ArrayList<GPSData>			lstGPSData;
		ArrayList<CarInfo>			lstCarInfo;
		ArrayList<VehicleListData>	lstVehicleListData;
		
		m_bthOK.setOnClickListener( eventOK );
		m_bthCancel.setOnClickListener( eventCancel );
		
		//设置点击选择监听器
		m_listVehicle.setOnItemClickListener(eventVehicleListener);
		//设置选中监听器
		m_listVehicle.setOnItemSelectedListener(eventSelectVehicleListener);	
		
		// 加载容器
		lstCarInfo = GlobalData.getAllVehicle();
		lstGPSData = GlobalData.getAllDataAddr();
		lstVehicleListData = new ArrayList<VehicleListData>();
		for( int nCnt = 0; nCnt < lstCarInfo.size(); nCnt++ ){
			
			bFlag = false;
			oGPSData = null;			
			oCarInfo = lstCarInfo.get(nCnt);
			for( int nCnt2 = 0; nCnt2 < lstGPSData.size(); nCnt2++ ){
				oGPSData = lstGPSData.get(nCnt2);
				if( oCarInfo.GetDEUID().equals(oGPSData.getDEUID()) ){					
					bFlag = true;
				}
			}
			if( bFlag == true ){
				oVehicleListData = new VehicleListData( oCarInfo, oGPSData );
			}
			else{
				oVehicleListData = new VehicleListData( oCarInfo, null );
			}
			lstVehicleListData.add( oVehicleListData );
		}
		m_oShowVehicleAdapter = new ShowVehicleAdapter( getApplicationContext(), 
														lstVehicleListData );
		
		m_listVehicle.setAdapter(m_oShowVehicleAdapter );
		
	}
	//*************************************************
	//
	public void initData(){
		
		MapVehicle			oMapVehicle = null;
		List<MapVehicle>	lstMapVehicle = null;	
		
		
		m_lstDEUID = new ArrayList<String>();
		m_lstDEUID.clear();
		lstMapVehicle = GlobalData.getAllShowMapDEUID();	
		
		Iterator<MapVehicle> it= lstMapVehicle.iterator();
		while( it.hasNext() ){
			oMapVehicle = it.next();
			if( oMapVehicle.isShow() ){	
				
				m_oShowVehicleAdapter.setSelectedCar( oMapVehicle.getDEUID() );
			}
		}
	}
	//**************************************************
	//  设置点击选择监听器
	OnItemClickListener	 eventVehicleListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Log.e("VehicleList", Integer.toString(arg2));
			m_nCarPosition = arg2;
			m_oShowVehicleAdapter.setSelectedPosition( arg2 );
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
	//*************************************************
	//
	OnClickListener eventOK = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			Bundle		bunde  = new Bundle();	
			Intent		intent = new Intent();
			
			getShowMapCar();
			intent.setClass( MapShowVehicleActivity.this, AMapView.class);   
			intent.putExtras( bunde );			
			setResult(RESULT_OK, intent );
			finish();
		}		
	};
	//***************************************************
	//
	public  void  getShowMapCar(){
		
		List<String>		lstDEUID = null;
		
		GlobalData.delAllMapDEUID();
		lstDEUID = m_oShowVehicleAdapter.getSelecttedCar();		
		Log.e("显示的车辆数", Integer.toString( lstDEUID.size()+m_lstDEUID.size()));
		for( int nCnt = 0; nCnt < lstDEUID.size(); nCnt++ ){			
			GlobalData.addCarToMap( lstDEUID.get(nCnt), true, false );
		}	
		for( int nCnt = 0; nCnt < m_lstDEUID.size(); nCnt++ ){			
			GlobalData.addCarToMap( m_lstDEUID.get(nCnt), true, false );
		}
	}
	//*************************************************
	//
	OnClickListener eventCancel = new OnClickListener(){

		@Override
		public void onClick(View v) {
			finish();
		}		
	};
	//*************************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	//*************************************************
	//
	@Override
	protected void onDestroy() {
		
		m_lstDEUID.clear();
		m_oShowVehicleAdapter.delAllData();
		super.onDestroy();
	}	
	//*************************************************
	//
	//
	public class ShowVehicleAdapter extends BaseAdapter {
		
		public  int							m_nSelectNumber = 0;
		public 	Context 					m_oContext = null;
		public  ArrayList<VehicleListData>	m_lstData = null;
	    public  HashMap<Integer,View>  		m_oMapObj = null;

	    	    
		//*************************************************
		//
	    public  ShowVehicleAdapter( Context  oContext, ArrayList<VehicleListData> inData ){
	    	
	    	m_oContext = oContext;
			if( inData == null ){
				return;
			}			
			m_lstData = new ArrayList<VehicleListData>();
	    	m_lstData.clear();
	    	m_oMapObj = new HashMap<Integer,View>();
	    	m_oMapObj.clear();
	    	if( inData != null ){
	    		AddVehiclData( inData );
	    	}
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
		//*************************************************
		//
		public void setSelectedCar( String  strDEUID ){
			
			for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){	    		
	    		if( m_lstData.get(nCnt).getCarInfo().GetDEUID().equals( strDEUID) ){	    			
	    			m_lstData.get(nCnt).setSelected(true);
	    		}
	    	}	
			this.notifyDataSetChanged();
		}
		//************************************************
		//
		public List<String>  getSelecttedCar(){
			
			List<String>		lstDEUID = new ArrayList<String>();			
			
			lstDEUID.clear();	    	
	    	for( int nCnt = 0; nCnt < m_lstData.size(); nCnt++ ){	
	    		
	    		if( m_lstData.get(nCnt).getSelected() ){	    	
	    			lstDEUID.add( m_lstData.get(nCnt).getCarInfo().GetDEUID() );
	    		}
	    	}	    	
			return lstDEUID;
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
			
			if( nPosition < m_lstData.size() ){
				
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
	    	
	    	int				nResultCnt = 0;
	    	
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
							setSelectedPosition( m_nCarPosition );					
						}
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
}
