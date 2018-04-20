package com.Protocol;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.Language.Language;


//**************************************************************
//GPS数据信息
public class GPSData{

	public 	byte[]	 m_nDEUID;
	public  byte	 m_bGPSValid;
	public  int		 m_nTime;
	public  double   m_dbLon;
	public  double   m_dbLat;  
	public  byte 	 m_nSpeed;			// KM/H
	public  short	 m_nDirection;		// 0~360
	public  int		 m_nMileage;		// KM
	public  int 	 m_nAlarmState;		// Alarm
	public  int		 m_nHWState;		// HW
	public  int		 m_nCodeState;		// 指令执行状态
	public  short	 m_nDriverTime;		// 0~255 minute
	public  int		 m_nReadFlag;		// 标志
	public  String	 m_strAddr;			// 地址
	public  int		 m_ReadAlarmFlag;	// 报警读标志
	public  int		 m_ReadCodeStateFlag;  //控制状态码标志
	
	// ditu.google.com: FALSE
	// google.com     : TRUE
	public  boolean	 m_bConvert = false; 
	
	public double pi = 3.14159265358979324;
	public double a = 6378245.0;
	public double ee = 0.00669342162296594323;
	
	
	//**************************************************
	//
	public GPSData(){
		m_nDEUID 	 = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte)0);
		m_bGPSValid  = 0;
		m_nTime 	 = 0;
		m_dbLon 	 = 0.0;
		m_dbLat 	 = 0.0;  
		m_nSpeed 	 = 0;		// KM/H
		m_nDirection = 0;		// 0~360
		m_nMileage   = 0;		// KM
		m_nAlarmState= 0;		// Alarm
		m_nHWState   = 0;		// HW
		m_nCodeState = 0;		// 指令执行状态
		m_nDriverTime= 0;		// 0~255 minute
		
		m_nReadFlag = 0;
		m_strAddr = "";
		
		m_ReadCodeStateFlag = 0;
	}
	//////////////////////////////////////////////////////////////////////////
	//卫星坐标转火星坐标的算法
	//可以消除ditu.google.cn地图的偏移
	boolean outOfChina(double lat, double lon)
	{
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}	
	public double transformLat(double x, double y)
	{
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}
	
	public double transformLon(double x, double y)
	{
		
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}
	//World Geodetic System ==> Mars Geodetic System
	public void ConvertLatLog(double srcLat, double srcLon )
	{
		if( m_bConvert ){
			
			m_dbLat = srcLat;
			m_dbLon = srcLon;
			return ;
		}
		if (outOfChina(srcLat, srcLon))
		{
			m_dbLat = srcLat;
			m_dbLon = srcLon;
			return;
		}
		double dLat = transformLat(srcLon - 105.0, srcLat - 35.0);
		double dLon = transformLon(srcLon - 105.0, srcLat - 35.0);
		double radLat = srcLat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		m_dbLat = srcLat + dLat;
		m_dbLon = srcLon + dLon;
	}
	public void  setGPSValid( byte  nGPSValid ){
		
		m_bGPSValid = nGPSValid;
	}
	public byte getGPSValid( ){
		return  m_bGPSValid;
	}
	public void  setLon( double  dbLon ){
		m_dbLon = dbLon;
	}
	public double getLon(){
		return m_dbLon;
	}
	public void setLat( double dbLat ){
		m_dbLat = dbLat;
	}
	public double getLat(){
		return m_dbLat;
	}
	public void  setDirection( short nDirection ){
		m_nDirection = nDirection;
	}
	public short getDirection(){
		return m_nDirection;
	}
	public void setSpeed( byte  nSpeed ){
		m_nSpeed = nSpeed;
	}
	public byte getSpeed(){
		return  m_nSpeed;
	}
	public void setHWState( int  nHWState ){
		m_nHWState = nHWState;
	}
	public int  getHWState(){
		return m_nHWState;
	}
	public void setAlarmState( int nAlarmState ){
		
		m_nAlarmState = nAlarmState;
	}
	public void  setDriverTime( short  nDriverTime){
		m_nDriverTime = nDriverTime;
	}
	public short getDriverTime(){
		return m_nDriverTime;
	}
	public void setCodeState( int nCodeState ){
		
		m_nCodeState = nCodeState;
	}
	public int getCodeState(){
		return m_nCodeState;
	}
	public void  setGPSTime( int  nTime ){
		
		m_nTime = nTime;
	}
	public int  getGPSTime(){
		return m_nTime;
	}
	public  void  setMileage( int nMileage ){
		
		m_nMileage = nMileage;
	}
	public int getMileage(){
		return m_nMileage;
	}
	//**************************************************
	//
	public 	void  setReadFlag(){		
		m_nReadFlag = 1;
	}
	//**************************************************
	//
	public int  getReadFlag(){
		return m_nReadFlag;
	}
	public void setAlarmReadFlag(){
		
		m_ReadAlarmFlag = 1;
	}
	public int getAlarmReadFlag(){
		
		return m_ReadAlarmFlag;
	}
	public int getAlarmState(){
		
		return m_nAlarmState;
	}
	public  void  setReadCodeStateFlag(){
		
		m_ReadCodeStateFlag = 1;
	}
	public  int  getReadCodeStateFlag(){
		
		return  m_ReadCodeStateFlag;
	}
	//**************************************************
	//
	public  void  ParseGPSData( byte[] lpBuf,  int  nLen ){
		
		int			nDataLen = 0;
		double		dbLon = 0.0;
		double 		dbLat = 0.0;
		
		System.arraycopy(lpBuf, 0, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
		nDataLen = Structs.TEXT_DEUID_LEN;
		
		m_bGPSValid = lpBuf[nDataLen++];
		
		nDataLen += 2;		//STGPSDATA结构体对齐方式
		
		m_nTime = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										  lpBuf[nDataLen+2], 
										  lpBuf[nDataLen+1], 
										  lpBuf[nDataLen]);
		nDataLen += 4;
		
		dbLon = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
										  	 lpBuf[nDataLen+6], 
										  	 lpBuf[nDataLen+5], 
										  	 lpBuf[nDataLen+4],
										  	 lpBuf[nDataLen+3], 
										  	 lpBuf[nDataLen+2], 
										  	 lpBuf[nDataLen+1], 
										  	 lpBuf[nDataLen] );
		nDataLen += 8;
		
		
		dbLat = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
										  	 lpBuf[nDataLen+6], 
										  	 lpBuf[nDataLen+5], 
										  	 lpBuf[nDataLen+4],
										  	 lpBuf[nDataLen+3], 
										  	 lpBuf[nDataLen+2], 
										  	 lpBuf[nDataLen+1], 
										  	 lpBuf[nDataLen] );
		nDataLen += 8;
		ConvertLatLog( dbLat, dbLon );
		
		m_nSpeed = lpBuf[nDataLen++];
		
		nDataLen += 1; 	//STGPSDATA结构体对齐方式
		
		m_nDirection = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], 
											  		 lpBuf[nDataLen]);
		nDataLen += 2;
		
		m_nMileage = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], 
		  		 								   lpBuf[nDataLen]);
		nDataLen += 2;
		
		nDataLen += 2; 	//STGPSDATA结构体对齐方式
		
		m_nAlarmState = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
												lpBuf[nDataLen+2], 
												lpBuf[nDataLen+1], 
												lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nHWState = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
											 lpBuf[nDataLen+2], 
											 lpBuf[nDataLen+1], 
											 lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nCodeState = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
											 lpBuf[nDataLen+2], 
											 lpBuf[nDataLen+1], 
											 lpBuf[nDataLen]);
		nDataLen += 4;
		m_nDriverTime = lpBuf[nDataLen];
	}	
	//*************************************************
	//
	public String  getDEUID(){
		
		return AppData.ByteToString(m_nDEUID);
	}
	//************************************************
	//
	public void   setDEUID( String strDEUID ){
		
		int		nLen = strDEUID.getBytes().length;
		
		if( nLen > Structs.TEXT_DEUID_LEN ){
			nLen = Structs.TEXT_DEUID_LEN-1;
		}
		System.arraycopy(strDEUID.getBytes(), 0, m_nDEUID, 0, nLen );
	}
	//*************************************************
	//
	public String  getTime(){
		
		long  				lTime = 0;
		Date				de = null;
		SimpleDateFormat 	oFormat= null;
		
		de = new Date();		
		lTime = (long)m_nTime*1000;		
		de.setTime(lTime);
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return oFormat.format(de);
	}
	public int   getTimeData(){
		
		return m_nTime;
	}
	public void  setAddr( String  strAddr ){
		
		m_strAddr = strAddr;
	}
	//*************************************************
	//
	public  String  getAddr(){
		
		return  m_strAddr;
	}
	//*************************************************
	//
	public String  getGeneralInfo(){
		
		String 		strResult = new String();
		
		strResult = HWState.getControlStr(m_nCodeState);
		strResult +=" "+ HWState.GetAlarmString(m_nAlarmState);
		if( m_bGPSValid == 1 ){
			strResult +=" GPS:" + Language.getLangStr(Language.TEXT_GPS_LOCATE);
		}
		else{
			strResult +=" GPS:" + Language.getLangStr(Language.TEXT_GPS_UNLOCATE);
		}
		strResult +=" "+Integer.toString(m_nSpeed)+"(km/h)";
		strResult +=" "+HWState.getDirection(m_nDirection);
		strResult += " ACC:"+HWState.getACCState(m_nHWState);
		strResult += " "+Language.getLangStr(Language.TEXT_DOOR)+":"+HWState.GetDoorState(m_nHWState);
		strResult +=" "+Language.getLangStr(Language.TEXT_POWER)+":"+HWState.GetPowerVoltage(m_nHWState)+"V";
		
		return strResult;
	}
	//*************************************************
	//
	public void setNewValues( GPSData  inData ){
		
		if( inData.m_nTime > m_nTime ){ 
			
			m_bGPSValid = inData.m_bGPSValid ;
			m_nTime = inData.m_nTime ;
			m_dbLon = inData.m_dbLon ;
			m_dbLat = inData.m_dbLat ; 
			m_nSpeed = inData.m_nSpeed ;			// KM/H
			m_nDirection = inData.m_nDirection ;	// 0~360
			m_nMileage = inData.m_nMileage ;		// KM
			m_nAlarmState = inData.m_nAlarmState ;	// Alarm
			m_nHWState = inData.m_nHWState ;		// HW
			m_nCodeState = inData.m_nCodeState ;	// 指令执行状态
			m_nDriverTime = inData.m_nDriverTime ;	// 0~255 minute
			m_nReadFlag = inData.m_nReadFlag ;		// 标志
			//m_strAddr = "";			// 地址
		}
	}
}



