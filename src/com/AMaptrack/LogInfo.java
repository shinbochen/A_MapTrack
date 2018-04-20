package com.AMaptrack;

import com.Protocol.GPSData;

//*********************************************
//
public class LogInfo{
	
	public  final int	TYPE_GPSDATA = 1;
	public  final int	TYPE_OPERATING = 2;
	public  int			m_nType = TYPE_GPSDATA;
	public  int			m_nOperatCmd = 0;
	public  long		m_nOperatTime = 0;
	public  String		m_strDEUID = "";
	public  GPSData		m_oGPSData = null;
	    	
	
	public LogInfo( String  strDEUID, GPSData oGPSData ){
		
		m_strDEUID = strDEUID;
		
	//	m_oGPSData = oGPSData;		
		copyGPSData( oGPSData );
		m_nType = TYPE_GPSDATA;
	}
	public void copyGPSData( GPSData  inGPSData ){
		
		if( m_oGPSData == null){
			m_oGPSData = new GPSData();
		}
		m_oGPSData.setDEUID( inGPSData.getDEUID() );
		m_oGPSData.m_bGPSValid = inGPSData.m_bGPSValid;
		m_oGPSData.m_nTime = inGPSData.m_nTime;
		m_oGPSData.m_dbLon = inGPSData.m_dbLon;
		m_oGPSData.m_dbLat = inGPSData.m_dbLat;  
		m_oGPSData.m_nSpeed = inGPSData.m_nSpeed;				// KM/H
		m_oGPSData.m_nDirection = inGPSData.m_nDirection;		// 0~360
		m_oGPSData.m_nMileage = inGPSData.m_nMileage;			// KM
		m_oGPSData.m_nDriverTime = inGPSData.m_nDriverTime;		// 0~255 minute
		m_oGPSData.m_nAlarmState = inGPSData.m_nAlarmState;
		m_oGPSData.m_nCodeState = inGPSData.m_nCodeState;
		m_oGPSData.m_nHWState = inGPSData.m_nHWState;
		m_oGPSData.m_strAddr = inGPSData.getAddr();				// µÿ÷∑
	}
	public LogInfo( String  strDEUID,int	nOperatCmd ){

		m_strDEUID = strDEUID;
		m_nOperatCmd = nOperatCmd;
		m_nOperatTime = System.currentTimeMillis();
		m_nType = TYPE_OPERATING;
	}
	public  long  getOperatTime(){
		return  m_nOperatTime;
	}
	public  void  setDEUID( String  strDEUID ){
		
		m_strDEUID = strDEUID;
	}
	public String  getDEUID(){
		
		return  m_strDEUID;
	}
	public  void  setType( int  nType){
		
		m_nType = nType;
	}
	public  int   getType(){
		return m_nType;
	}
	public  void  setGPSData( GPSData  inData ){
		
		if( m_oGPSData == null ){
			m_oGPSData = new GPSData();
		}
		copyGPSData( inData );
	//	m_oGPSData = inData;
		setType( TYPE_GPSDATA );
	}
	public  GPSData getGPSData(){
		return m_oGPSData;
	}
	public  void  setOperatCmd( int  nCmdType ){
		m_nOperatCmd = nCmdType;
	}
	public int  getOperating( ){
		return m_nOperatCmd;
	}
	public boolean  isGPSData(){
		if( getType() == TYPE_GPSDATA){
			return true;
		}
		return false;
	}
} 
