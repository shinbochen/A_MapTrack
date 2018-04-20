//******************************************************
//  ´æ´¢¶ÌÐÅÊý¾Ý
//
package com.smsmanager;

import java.util.Arrays;


public class SMSData {

	public 	 byte			m_nDCSType = 0;
	public   byte			m_nTPUDLen = 0;
	public   String			m_strPhone = "";
	public   String			m_strCenterPhone = "";	
	public   byte[]			m_nSMSBuf = null;
	public   int			m_nSMSLen = 0;
	public   long			m_lSMSTime = 0;
	
	//*****************************************
	//
	public SMSData(){
		m_strPhone = "";
		m_nSMSBuf = null;
		m_nSMSLen = 0;
		m_lSMSTime = 0;
		m_strCenterPhone="";
	}
	//*****************************************
	//
	public SMSData( String  strPhone,  byte[] nSMSBuf, int  nSMSLen ){
		
		m_strPhone = strPhone;
		m_nSMSLen = nSMSLen;
		m_nSMSBuf = new byte[nSMSLen];
		Arrays.fill(m_nSMSBuf, (byte)0);
		System.arraycopy(nSMSBuf, 0, m_nSMSBuf, 0, m_nSMSLen);
	}
	public void  setTPUDLen( byte nTPUDLen ){
		m_nTPUDLen = nTPUDLen;
	}
	public byte  getTPDULen(){
		return m_nTPUDLen;
	}
	public void  setDCSType( byte nDCSType ){
		
		m_nDCSType = nDCSType;
	}
	public byte getDCSType(){
		return m_nDCSType;
	}
	//******************************************
	//
	public  String   getPhone(){
		
		return  m_strPhone;
	}
	//******************************************
	//
	public  byte[]  getSMSData(){
		return m_nSMSBuf;
	}
	//*******************************************
	//
	public  int   getSMSLen(){
		return m_nSMSLen;
	}
	//*******************************************
	//
	public  void  setPhone( String  strPhone ){
		m_strPhone  = strPhone;
	}
	//*******************************************
	//
	public void   setCenterPhone( String  strCenterPhone ){
		
		m_strCenterPhone = strCenterPhone;
	}
	//*******************************************
	//
	public String   getCenterPhone(){
		
		if( m_strCenterPhone.length() > 0 ){
			return m_strCenterPhone;
		}
		else{
			return " ";
		}		
	}
	//********************************************
	//
	public void  setRecvTimeLog( long  lTime ){
		
		m_lSMSTime = lTime;
	}
	//******************************************
	//
	public long  getRecvTime(){
		
		return m_lSMSTime;
	}
	//*******************************************
	//
	public  void  setSMSData( byte[]  nSMSBuf, 
							  int	  nSMSPos,
							  int     nSMSDataLen ){
		
		m_nSMSLen = nSMSDataLen;
		m_nSMSBuf = new byte[nSMSDataLen];
		Arrays.fill(m_nSMSBuf, (byte)0);
		System.arraycopy(nSMSBuf, nSMSPos, m_nSMSBuf, 0, nSMSDataLen);
	}
}
