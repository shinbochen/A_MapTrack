package com.Protocol;


import java.util.ArrayList;
import java.util.Iterator;
import  java.util.Map;
import  java.util.HashMap;

import android.util.Log;


public class MemoryGPSData {

	Map<String, GPSData>		m_mapDEUIDToObj;
	
	//**************************************************
	//
	public MemoryGPSData(){
		
		m_mapDEUIDToObj = new HashMap<String, GPSData>();
		m_mapDEUIDToObj.clear();
	}
	//**************************************************
	//
	public  void SetGPSData( String  strKey,  GPSData  inObj ){
		
		GPSData 	oOldGPSData = null;
		
		oOldGPSData = m_mapDEUIDToObj.get(strKey);

		if( oOldGPSData == null ){			
			m_mapDEUIDToObj.put(strKey, inObj );
		}
		else{
			if ( (inObj.m_nTime > oOldGPSData.m_nTime) &&
				 (inObj.m_bGPSValid == 1 )	){
				
				oOldGPSData.m_nTime = inObj.m_nTime;
				oOldGPSData.m_dbLon = inObj.m_dbLon;
				oOldGPSData.m_dbLat = inObj.m_dbLat;  
				oOldGPSData.m_nSpeed = inObj.m_nSpeed;				// KM/H
				oOldGPSData.m_nDirection = inObj.m_nDirection;		// 0~360
				oOldGPSData.m_nMileage = inObj.m_nMileage;			// KM
				oOldGPSData.m_nDriverTime = inObj.m_nDriverTime;	// 0~255 minute
				oOldGPSData.m_nAlarmState = inObj.m_nAlarmState;
				oOldGPSData.m_nCodeState = inObj.m_nCodeState;
				oOldGPSData.m_nHWState = inObj.m_nHWState;
				oOldGPSData.m_nReadFlag = 0;						// 标志
			//	oOldGPSData.m_strAddr = "";							// 地址
				oOldGPSData.m_ReadAlarmFlag = 0;
				oOldGPSData.m_ReadCodeStateFlag = 0;
			}
			else{
				oOldGPSData.m_nAlarmState = inObj.m_nAlarmState;
				oOldGPSData.m_nCodeState = inObj.m_nCodeState;
				oOldGPSData.m_nHWState = inObj.m_nHWState;
				oOldGPSData.m_nDriverTime = inObj.m_nDriverTime;
			}
		}
	}
	//**************************************************
	//
	public ArrayList<GPSData>	getNewAlarmGPSData(){
		
		GPSData				 					oGPSData = null;
		Iterator<Map.Entry<String, GPSData>>	it = null;		
		ArrayList<GPSData>	 					lstResult = null;
		
		
		lstResult = new ArrayList<GPSData>();
		lstResult.clear();
		it = m_mapDEUIDToObj.entrySet().iterator();
		while( it.hasNext() ){
			
			Map.Entry<String, GPSData> entry = it.next();
			oGPSData = entry.getValue();
			if( (oGPSData.getAlarmReadFlag() == 0x00)
			     && (oGPSData.getAlarmState() > 0 ) ){
				
				oGPSData.setAlarmReadFlag();
				lstResult.add(oGPSData);
			}
		}
		return lstResult;
	}
	//**************************************************
	//
	public	 ArrayList<GPSData>	 getNewControlGPSData(){
	
		GPSData				 					oGPSData = null;
		Iterator<Map.Entry<String, GPSData>>	it = null;		
		ArrayList<GPSData>	 					lstResult = null;
		
		
		lstResult = new ArrayList<GPSData>();
		lstResult.clear();
		it = m_mapDEUIDToObj.entrySet().iterator();
		while( it.hasNext() ){
			
			Map.Entry<String, GPSData> entry = it.next();
			oGPSData = entry.getValue();
			if( ( oGPSData.getReadCodeStateFlag() == 0x00 )
			     && (oGPSData.getCodeState() > 0 ) ){
				
				Log.e("GPS命令数据", Integer.toString(oGPSData.getCodeState()));
				oGPSData.setReadCodeStateFlag();
				lstResult.add(oGPSData);
			}
		}
		return lstResult;
	}
	//**************************************************
	//
	public ArrayList<GPSData>	getNewGPSData(){
		
		GPSData				 					oGPSData = null;
		Iterator<Map.Entry<String, GPSData>>	it = null;		
		ArrayList<GPSData>	 					lstResult = null;
		
		
		lstResult = new ArrayList<GPSData>();
		lstResult.clear();
		it = m_mapDEUIDToObj.entrySet().iterator();
		while( it.hasNext() ){
			
			Map.Entry<String, GPSData> entry = it.next();
			oGPSData = entry.getValue();
			if( oGPSData.getReadFlag() == 0x00 ){
				
				oGPSData.setReadFlag();
				lstResult.add(oGPSData);
			}
		}
		return lstResult;
	}
	//**************************************************
	//
	public ArrayList<GPSData>	getAllGPSData(){
		
		GPSData				 					oGPSData = null;
		Iterator<Map.Entry<String, GPSData>>	it = null;		
		ArrayList<GPSData>	 					lstResult = null;
		
		
		lstResult = new ArrayList<GPSData>();
		lstResult.clear();
		it = m_mapDEUIDToObj.entrySet().iterator();
		while( it.hasNext() ){
			
			Map.Entry<String, GPSData> entry = it.next();
			oGPSData = entry.getValue();
			lstResult.add(oGPSData);
		}
		return lstResult;
	}
	//**************************************************
	//
	public   GPSData GetGPSData( String  strKey ){
				
		return	m_mapDEUIDToObj.get(strKey);
	}
	//**************************************************
	//
	public  void   RemoveGPSData( String   strKey ){
		
		m_mapDEUIDToObj.remove(strKey);
	}
	public void  RecmoveAllGPSData(){
		m_mapDEUIDToObj.clear();
	}
	//**************************************************
	//
	public  int  GetSize( ){
		
		return m_mapDEUIDToObj.size();
	}	
}
