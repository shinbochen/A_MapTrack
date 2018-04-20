package com.Protocol;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import android.util.Log;

//**************************************************************
//用户信息
public class UserInfo{			
	
	public  byte[] 	m_nUser;
	public  byte[] 	m_nPsd ;
	public  byte[] 	m_nFName ;
	public  byte[] 	m_nLName;
	public  byte[] 	m_nCoName ;
	public  byte[] 	m_nTelNum;
	public  byte[] 	m_nEmail;	
	public  byte[] 	m_nAddr;
	public  byte[] 	m_nRemark;
	public  byte[] 	m_nKey;
	public  short	m_nUserPrivilege = 0;
	public  int		m_nValidDate = 0;
	
	
	
	public  UserInfo(){
		
		m_nUser = new byte[Structs.TEXT_USER_LEN];
		Arrays.fill(m_nUser, (byte)0x00 );
		m_nPsd = new byte[Structs.TEXT_PSD_LEN];
		Arrays.fill(m_nPsd, (byte)0x00 );
		m_nFName = new byte[Structs.TEXT_NAME_LEN];
		Arrays.fill(m_nFName, (byte)0x00 );
		m_nLName = new byte[Structs.TEXT_NAME_LEN];
		Arrays.fill(m_nLName, (byte)0x00 );
		m_nCoName = new byte[Structs.TEXT_CONAME_LEN];
		Arrays.fill(m_nCoName, (byte)0x00 );
		m_nTelNum = new byte[Structs.TEXT_TEL_LEN];
		Arrays.fill(m_nTelNum, (byte)0x00 );
		m_nEmail = new byte[Structs.TEXT_EMAIL_LEN];
		Arrays.fill(m_nEmail, (byte)0x00 );
		m_nAddr = new byte[Structs.TEXT_ADDR_LEN];
		Arrays.fill(m_nAddr, (byte)0x00 );
		m_nRemark = new byte[Structs.TEXT_REMARK_LEN];
		Arrays.fill(m_nRemark, (byte)0x00 );
		m_nKey = new byte[Structs.TEXT_TEL_LEN];
		Arrays.fill(m_nKey, (byte)0x00 );
		m_nUserPrivilege = 0;
		m_nValidDate = 0;
	}
	//******************************************************
	//
	public void ParseUserInfo( byte[]  lpBuf, int  nLen ){
		
		int			nDataLen = 0;
				
		System.arraycopy(lpBuf, 0, m_nUser, 0, Structs.TEXT_USER_LEN );
		nDataLen = Structs.TEXT_USER_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nPsd, 0, Structs.TEXT_PSD_LEN );
		nDataLen += Structs.TEXT_PSD_LEN;
		
		m_nUserPrivilege = (short)AppData.ByteValueToInt( lpBuf[nDataLen+1], lpBuf[nDataLen]);
		nDataLen += 2;

		System.arraycopy(lpBuf, nDataLen, m_nFName, 0, Structs.TEXT_NAME_LEN );
		nDataLen += Structs.TEXT_NAME_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nLName, 0, Structs.TEXT_NAME_LEN );
		nDataLen += Structs.TEXT_NAME_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nCoName, 0, Structs.TEXT_CONAME_LEN );
		nDataLen += Structs.TEXT_CONAME_LEN;

		System.arraycopy(lpBuf, nDataLen, m_nTelNum, 0, Structs.TEXT_TEL_LEN );
		nDataLen += Structs.TEXT_TEL_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nAddr, 0, Structs.TEXT_ADDR_LEN );
		nDataLen += Structs.TEXT_ADDR_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nEmail, 0, Structs.TEXT_EMAIL_LEN );
		nDataLen += Structs.TEXT_EMAIL_LEN;

		System.arraycopy(lpBuf, nDataLen, m_nRemark, 0, Structs.TEXT_REMARK_LEN );
		nDataLen += Structs.TEXT_REMARK_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nKey, 0, Structs.TEXT_TEL_LEN );
		nDataLen += Structs.TEXT_TEL_LEN;

		m_nValidDate = AppData.ByteValueToInt(lpBuf[nDataLen+3], 
											  lpBuf[nDataLen+2], 
											  lpBuf[nDataLen+1], 
											  lpBuf[nDataLen]);
		
		Log.e("用户信息", AppData.ByteToString(m_nUser)+" "+ 
				AppData.ByteToString(m_nPsd)+ " "+ 
				AppData.ByteToString(m_nFName)+" "+
				AppData.ByteToString(m_nLName)+ " "+
				AppData.ByteToString( m_nCoName)+ " "+
				AppData.ByteToString(m_nTelNum)+" "+
				AppData.ByteToString(m_nEmail)+" "+
				AppData.ByteToString(m_nAddr)+" "+
				AppData.ByteToString(m_nRemark) );
		
	}	
	//***************************************************
	//
	public  void 	setUserName( String   strUser ){
		
		try {
			m_nUser = strUser.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getUserName(){
				
		return AppData.ByteToStringGBK(m_nUser);
	}
	//***************************************************
	//
	public  void    setUserPsd( String  strPsd ){
		try {
			m_nPsd = strPsd.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getUserPsd(){
		return AppData.ByteToStringGBK(m_nPsd );
	}
	//***************************************************
	//
	public  void    setFName( String  strFName ){	
		try {
			m_nFName = strFName.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getFName(){
		
		return AppData.ByteToStringGBK( m_nFName );
	}
	//***************************************************
	//
	public  void    setLName( String  strLName ){
		
		try {
			m_nLName = strLName.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getLName(){
		
		return AppData.ByteToStringGBK( m_nLName );
	}
	//***************************************************
	//
	public  void    setCoName( String  strCoName ){		
		try {
			m_nCoName = strCoName.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getCoName(){
		
		return AppData.ByteToStringGBK( m_nCoName );
	}
	//***************************************************
	//
	public  void    setTelNum( String  strTel ){
		
		try {
			m_nTelNum = strTel.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getTelNum(){
		return AppData.ByteToStringGBK(m_nTelNum );
	}
	//***************************************************
	//
	public  void    setEmail( String  strEmail ){
		
		try {
			m_nEmail = strEmail.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getEmail(){
		
		return AppData.ByteToStringGBK(m_nEmail );
	}
	//***************************************************
	//
	public  void    setAddr( String  strAddr ){		
		try {
			m_nAddr = strAddr.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getAddr(){
		
		return AppData.ByteToStringGBK( m_nAddr );
	}
	//***************************************************
	//
	public  void    setRemark( String  strRemark ){
		
		try {
			m_nRemark = strRemark.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getRemark(){
		
		return AppData.ByteToStringGBK( m_nRemark );
	}
	//***************************************************
	//
	public  void    setkey( String  strKey ){
		
		try {
			m_nKey = strKey.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public  String  getkey(){
		return  AppData.ByteToStringGBK(m_nKey);
	}
}