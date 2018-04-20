package com.Protocol;


import java.util.ArrayList;
import   java.util.Map;
import   java.util.HashMap;
import   java.util.Iterator;

import android.util.Log;

public class VehicleManage {
	
	public Map<String,CarInfo>   		m_mapDEUIDToObj;
	public Map<String,DeviceSetup>		m_mapSetupDEUIDToObj;
	
	//***************************************************
	//
	public VehicleManage(){
		
		m_mapDEUIDToObj = new HashMap<String, CarInfo>();
		m_mapDEUIDToObj.clear();
		
		m_mapSetupDEUIDToObj = new HashMap<String, DeviceSetup>();
		m_mapSetupDEUIDToObj.clear();
	}
	//***************************************************
	//
	public  void  SetVehicleData( String  strKey,  CarInfo  inObj ){
		
		CarInfo  oldCar = null;
		
		if( m_mapDEUIDToObj.get(strKey) == null) {
			
			m_mapDEUIDToObj.put(strKey, inObj);
		}
		else{
			oldCar = m_mapDEUIDToObj.get(strKey);
			oldCar.setCarInfo(inObj);
		}
	}
	//***************************************************
	//
	public  String  GetLicenseByDEUID( String  strDEUID ){
		
		CarInfo		oCarInfo = null;
		
		oCarInfo = m_mapDEUIDToObj.get( strDEUID );
		if( oCarInfo != null ){
			return oCarInfo.GetCarLicense();
		}
		else{
			return  "";
		}
	}
	//***************************************************
	//
	public  String  GetDEUIDByLicense( String  strLicense ){
		
		CarInfo									oCarInfo = null;		
		Iterator<Map.Entry<String, CarInfo>>	it = null;
		
		Log.e("VehicleManage", "GetDEUIDByLicense" );
		it = m_mapDEUIDToObj.entrySet().iterator();		
		while( it.hasNext() ){
		
			Map.Entry<String, CarInfo> entry = it.next();
			oCarInfo = entry.getValue();
			if( strLicense == oCarInfo.GetCarLicense() ){
				
				return oCarInfo.GetDEUID();
			}
		}
		return "";
	}
	//***************************************************
	//
	public  CarInfo  GetLicenseByCarInfo( String  strLicense ){
		
		CarInfo									oCarInfo = null;		
		Iterator<Map.Entry<String, CarInfo>>	it = null;
				
		it = m_mapDEUIDToObj.entrySet().iterator();		
		while( it.hasNext() ){
		
			Map.Entry<String, CarInfo> entry = it.next();
			oCarInfo = entry.getValue();
			if( strLicense == oCarInfo.GetCarLicense() ){				
				break;
			}
		}
		return oCarInfo;
	}
	//***************************************************
	//
	public  CarInfo GetVehicleData( String  strKey ){
		
		return  m_mapDEUIDToObj.get(strKey);
	}
	//***************************************************
	//
	public  void  RemoveVehicleData( String  strKey ){
		
		m_mapDEUIDToObj.remove(strKey);
	}
	//***************************************************
	//
	public  int  GetSize( ){
		
		return  m_mapDEUIDToObj.size();
	}
	//***************************************************
	//
	public  ArrayList<CarInfo> getAllVehicle(){
		
		CarInfo									oCarInfo = null;
		Iterator<Map.Entry<String, CarInfo>>	it = null;
		ArrayList<CarInfo>						oResultList = new ArrayList<CarInfo>();
		
		oResultList.clear();
		it = m_mapDEUIDToObj.entrySet().iterator();		
		while( it.hasNext() ){
			
			Map.Entry<String, CarInfo> entry = it.next();
			oCarInfo = entry.getValue();	
			oResultList.add(oCarInfo);
		}		
		return oResultList;
	}
	public  void  delAllVehicle(){
		
		m_mapDEUIDToObj.clear();
	}
	//***************************************************
	//
	public  void  setDeviceSetup( String  strKey,  DeviceSetup  inObj ){
		
		if( m_mapSetupDEUIDToObj.get(strKey) == null) {
			
			m_mapSetupDEUIDToObj.put(strKey, inObj);
		}
		else{
			m_mapSetupDEUIDToObj.remove(strKey);
			m_mapSetupDEUIDToObj.put(strKey, inObj);
		}
	}
	//***************************************************
	//
	public  DeviceSetup  getDeviceSetup( String  strKey ){
		
		return m_mapSetupDEUIDToObj.get(strKey);
	}
	
}
