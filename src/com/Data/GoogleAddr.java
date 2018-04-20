package com.Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.Language.Language;
import com.Protocol.AlarmData;
import com.Protocol.GPSData;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

public class GoogleAddr implements Runnable{

	public  Context		m_oContext = null;
	//	0: 监控对像
	//  1: 报警统计
	//  2: LOG
	public 	int			m_nParentMsg = 0;
	

	//******************************************************
	//
	public GoogleAddr( Context  oContext, int ParentMsg ){
		
		m_oContext = oContext;
		m_nParentMsg = ParentMsg;
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
	        geoCode =new Geocoder( m_oContext, Locale.getDefault());
	        lstAddr = geoCode.getFromLocation(dbLat, dbLng, 2);
			if( lstAddr != null){	

				if( lstAddr.size() <= 0 ){
					return "";
				}
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
	//******************************************************
	//
    public  boolean  queryGPSDataAddr(){

    	boolean			bResult = false;
    	GPSData			oGPSData = null;
		List<GPSData>	lstGPSData = null;
		
		lstGPSData = GlobalData.getAllDataAddr();
		for( int nCnt = 0;  nCnt < lstGPSData.size(); nCnt++ ){
			
			oGPSData = lstGPSData.get(nCnt);
			if( oGPSData.m_bGPSValid == 1 ){
				//if( oGPSData.getAddr().isEmpty() ){
				if( oGPSData.getAddr().length() <= 0 ){ 	
					oGPSData.setAddr( queryAddress( oGPSData.m_dbLat, oGPSData.m_dbLon ) );
				}
				bResult = true;
			}
		}
		return  bResult;
    }
	//******************************************************
	//
    public  boolean	queryAlarmDataAddr(){
    	
    	boolean				bResult = false;
    	AlarmData			oAlarmData = null;
    	List<AlarmData>		lstAlarmData = null;
    	
    	lstAlarmData = GlobalData.getQAlarmData();
    	
    	for( int  nCnt = 0; nCnt < lstAlarmData.size(); nCnt++ ){
    		oAlarmData = lstAlarmData.get(nCnt);
    		oAlarmData.setBegAddr( queryAddress(oAlarmData.m_dbBegLat, oAlarmData.m_dbBegLon) );
    		if( oAlarmData.m_dbEndLat > 0 &&
    				oAlarmData.m_dbEndLon > 0 ){
    			oAlarmData.setEndAddr( queryAddress(oAlarmData.m_dbEndLat, oAlarmData.m_dbEndLon) );
    		}
    		bResult = true;
    	}
    	return bResult;
    }
	//******************************************************
	//
	@Override
	public void run() {
		
		switch( m_nParentMsg ){
		case 0:
			if( queryGPSDataAddr() ){
				GlobalData.addRecvProgramType( ProgramType.UPDATE_VEHICLE_LIST_ADDR );
			}
			break;
		case 1:
			if( queryAlarmDataAddr() ){
				GlobalData.addRecvProgramType( ProgramType.UPDATE_ALARM_REPORT_LIST_ADDR );
			}
			break;
		case 2:
			if( queryGPSDataAddr() ){
				GlobalData.addRecvProgramType( ProgramType.UPDATE_LOG_LIST_ADDR );
			}
			break;
		}
	}
}
