package com.Protocol;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.Language.Language;

import android.util.Log;

//**********************************************
//
public class AlarmData  {

	public  byte[]			m_nDEUID = null;
	public  int				m_nBegUTC = 0;		//开始时间
	public  int				m_nStopUTC = 0;		//结束时间
	public  int				m_nALState = 0;		//报警状态
	public  int				m_nSumUTC = 0;		//报警时间和
	public  double   		m_dbBegLon = 0;
	public  double   		m_dbBegLat = 0;
	public  double   		m_dbEndLon = 0;
	public  double   		m_dbEndLat = 0;
	
	public  String	 		m_strBegAddr="";	// 地址
	public  String	 		m_strEndAddr="";	// 地址
	
	public 	int				m_nCopySumUTC = 0;
	
	
	
	//**********************************************
	//
	public  AlarmData(){
		
		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte)0);
	}
	//**********************************************
	//	
	public  String  getPositionTitle( int  nPos ){
		
		String 		strResult = "";
		
		switch( nPos ){
		case 1:  	//报警类型
			strResult = Language.getLangStr(Language.TEXT_ALARM_TYPE);
			break;
		case 2:		//开始时间
			strResult = Language.getLangStr(Language.TEXT_STARTTIME);
			break;	
		case 3:		//开始地址
			strResult = Language.getLangStr(Language.TEXT_STARTADDR);
			break;  
		case 4:		//结束时间
			strResult = Language.getLangStr(Language.TEXT_ENDTIME);
			break;
		case 5:		//结束地址
			strResult = Language.getLangStr(Language.TEXT_ENDADDR);
			break;
		case 6:		//特续时间
			strResult = Language.getLangStr(Language.TEXT_DURATION);
			break;
		}
		return strResult;
	}
	//**********************************************
	//
	public String  getPositionValues( int  nPos ){
		
		String 		strResult = "";
		
		switch( nPos ){
		case 1:  	//报警类型
			strResult = HWState.GetAlarmString(m_nALState);
			break;
		case 2:		//开始时间
			strResult = getStartTime();
			break;	
		case 3:		//开始地址
			strResult = m_strBegAddr;
			break;  
		case 4:		//结束时间
			strResult = getEndTime();
			break;
		case 5:		//结束地址
			strResult = m_strEndAddr;
			break;
		case 6:		//特续时间
			strResult = getSumUTC();
			break;
		}
		return strResult;
	}
	//**********************************************
	//
	public void   setDEUID( String  strDEUID){
		
		int		nLen = 0;

		try {
			nLen = strDEUID.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if( nLen > Structs.TEXT_DEUID_LEN ){
			nLen = Structs.TEXT_DEUID_LEN-1 ;
		}
		System.arraycopy(strDEUID.getBytes(), 0, m_nDEUID, 0, nLen);
	}
	//**********************************************
	//
	public String  getDEUID(){
		
		return AppData.ByteToString( m_nDEUID );
	}
	public void   setBegAddr( String  strAddr ){
		
		m_strBegAddr = strAddr;
	}
	public String  getBegAddr(){
		return  m_strBegAddr;
	}
	public void   setEndAddr( String  strAddr ){
		
		m_strEndAddr = strAddr;
	}
	public String  getEndAddr(){
		
		return m_strEndAddr;
	}
	public void setDuration( int nSumUTC ){
		m_nSumUTC = nSumUTC;
	}
	public int  getDuration(){
		
		return m_nSumUTC;
	}
	public  void setAlarmType( int  nAlState ){
		m_nALState = nAlState;
	}
	public int getAlarmType(){
		
		return  m_nALState;
	}
	public  void setStartUTC( int  nBegUTC ){
		m_nBegUTC = nBegUTC;
	}
	public int getStartUTC(){
		
		return  m_nBegUTC;
	}
	public  void setEndUTC( int  nEndUTC ){
		m_nStopUTC = nEndUTC;
	}
	public int getEndUTC(){
		
		return  m_nStopUTC;
	}
	public  void  setBeginLonAndLat( double dbLon, double dbLat ){
		m_dbBegLon = dbLon;
		m_dbBegLat = dbLat;
	}
	public  void  setEndLonAndLat( double dbLon, double dbLat ){
		m_dbEndLon = dbLon;
		m_dbEndLat = dbLat;
	}
	//**********************************************
	//
	String	 GetDay( ){
	
		String			strData="";
		byte			nDay = 0;
		
		nDay = (byte)(m_nCopySumUTC / 86400);
		m_nCopySumUTC = m_nCopySumUTC % 86400;
				
		if( nDay != 0 ){			
			strData = Integer.toString(nDay)+Language.getLangStr( Language.TEXT_DAY);
		}
		return strData;
	}
	//**********************************************
	//
	String	 GetHour( ){
	
		String			strData="";
		byte			nHour;
		
		nHour = (byte)(m_nCopySumUTC / 3600);
		m_nCopySumUTC = m_nCopySumUTC % 3600;
		if( nHour != 0 ){			
			strData = Integer.toString(nHour)+Language.getLangStr( Language.TEXT_HOUR);
		}
		return strData;
	}
	//**********************************************
	//
	String	 GetMin(){
	
		String			strData="";
		byte			nMin;
		
		nMin = (byte)(m_nCopySumUTC / 60);
		m_nCopySumUTC = m_nCopySumUTC % 60;
		if( nMin != 0 ){
			strData = Integer.toString(nMin)+Language.getLangStr( Language.TEXT_MIN);
		}
		return strData;
	}
	//**********************************************
	//
	String	 GetSec( ){
	
		String			strData="";
		
		if( m_nCopySumUTC != 0 ){
			strData = Integer.toString(m_nCopySumUTC)+Language.getLangStr( Language.TEXT_SEC);
		}
		return strData;
	}
	//**********************************************
	//
	public String	 getSumUTC(  ){
	
		String				strResult;
		
		m_nCopySumUTC = m_nSumUTC;
		strResult = GetDay();
		strResult += GetHour();
		strResult += GetMin();
		strResult += GetSec();
		//if( strResult.isEmpty() ){
		if( strResult.length() <= 0){
			strResult += Language.getLangStr( Language.TEXT_SEC) ;
		}
		return strResult;
	}
	//**********************************************
	//
	public  String  getStartTime(){
		
		long				lTime = 0;
		Date				oDate = null;
		SimpleDateFormat 	oFormat= null;
		
		lTime = (long)m_nBegUTC*1000;
		oDate =  new Date();				
		oDate.setTime(lTime);
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return oFormat.format(oDate );
	}
	//**********************************************
	//
	public  String  getEndTime(){
		
		long				lTime = 0;
		Date				oDate = null;
		SimpleDateFormat 	oFormat= null;
		
		lTime = (long)m_nStopUTC*1000;
		oDate =  new Date();				
		oDate.setTime(lTime);
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return oFormat.format(oDate );
	}
	//**********************************************
	//
	public void ParseAlarmData( byte[] lpBuf, int  nLen ){
		
		int			nDataLen =0;
		
		System.arraycopy(lpBuf, 0, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
		nDataLen = Structs.TEXT_DEUID_LEN;
		
		nDataLen += 3;			//结构体对齐方式
		
		m_nBegUTC = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										    lpBuf[nDataLen+2], 
										    lpBuf[nDataLen+1], 
										    lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nStopUTC = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										    lpBuf[nDataLen+2], 
										    lpBuf[nDataLen+1], 
										    lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nALState = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
											    lpBuf[nDataLen+2], 
											    lpBuf[nDataLen+1], 
											    lpBuf[nDataLen]);
		nDataLen += 4;
		

		m_nSumUTC = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
											    lpBuf[nDataLen+2], 
											    lpBuf[nDataLen+1], 
											    lpBuf[nDataLen]);
		nDataLen += 4;
		
		nDataLen += 4;		//结构体对齐方式
		
		m_dbBegLon = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
										  	 lpBuf[nDataLen+6], 
										  	 lpBuf[nDataLen+5], 
										  	 lpBuf[nDataLen+4],
										  	 lpBuf[nDataLen+3], 
										  	 lpBuf[nDataLen+2], 
										  	 lpBuf[nDataLen+1], 
										  	 lpBuf[nDataLen] );
		nDataLen += 8;
		
		m_dbBegLat = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
											  	 lpBuf[nDataLen+6], 
											  	 lpBuf[nDataLen+5], 
											  	 lpBuf[nDataLen+4],
											  	 lpBuf[nDataLen+3], 
											  	 lpBuf[nDataLen+2], 
											  	 lpBuf[nDataLen+1], 
											  	 lpBuf[nDataLen] );
		nDataLen += 8;
		

		m_dbEndLon = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
											  	 lpBuf[nDataLen+6], 
											  	 lpBuf[nDataLen+5], 
											  	 lpBuf[nDataLen+4],
											  	 lpBuf[nDataLen+3], 
											  	 lpBuf[nDataLen+2], 
											  	 lpBuf[nDataLen+1], 
											  	 lpBuf[nDataLen] );
		nDataLen += 8;

		m_dbEndLat = AppData.ByteValueToDouble( lpBuf[nDataLen+7], 
											  	 lpBuf[nDataLen+6], 
											  	 lpBuf[nDataLen+5], 
											  	 lpBuf[nDataLen+4],
											  	 lpBuf[nDataLen+3], 
											  	 lpBuf[nDataLen+2], 
											  	 lpBuf[nDataLen+1], 
											  	 lpBuf[nDataLen] );
		String		str = new String();
		
		str = "DEUD:"+getDEUID()+" begUTC:"+ Integer.toString( m_nBegUTC )+
				" StopUTC:"+Integer.toString(m_nStopUTC)+
				" SumUTC:"+Integer.toString( m_nSumUTC )+
				" begLn:"+Double.toString( m_dbBegLon)+
				" begLa:"+Double.toString( m_dbBegLat)+
				" endLn:"+Double.toString( m_dbEndLon)+
				" endLa:"+Double.toString( m_dbEndLat) ;
		Log.e("Alarm Data", str);
	}
}
