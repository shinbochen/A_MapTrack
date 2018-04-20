package com.Protocol;


import java.io.UnsupportedEncodingException;
import  java.util.Arrays;

import android.util.Log;


//**************************************************************
//车辆信息
public class CarInfo{	
	
	public 	byte[]	 m_nDEUID = null;
	public 	byte[]	 m_nCarLicense = null;
	public 	byte[]	 m_nFName = null;
	public 	byte[]	 m_nLName = null;
	public 	byte[]	 m_nOwnerTel = null;
	public 	byte[]	 m_nOwnerAddr = null;
	public 	byte[]	 m_nDESIM = null;
	public 	byte[]	 m_nRemark = null;
	
	public  int		m_nDEType = 0;
	
	
	//*************************************************
	//
	public CarInfo(){

		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID,  (byte)0);
		m_nCarLicense = new byte[Structs.TEXT_TEL_LEN];
		Arrays.fill(m_nCarLicense,  (byte)0);
		m_nFName = new byte[Structs.TEXT_NAME_LEN];
		Arrays.fill(m_nFName,  (byte)0);
		m_nLName = new byte[Structs.TEXT_NAME_LEN];
		Arrays.fill(m_nLName,  (byte)0);
		m_nOwnerTel = new byte[Structs.TEXT_TEL_LEN];
		Arrays.fill(m_nOwnerTel,  (byte)0);
		m_nOwnerAddr = new byte[Structs.TEXT_ADDR_LEN];
		Arrays.fill(m_nOwnerAddr,  (byte)0);
		m_nDESIM = new byte[Structs.TEXT_TEL_LEN];
		Arrays.fill(m_nDESIM,  (byte)0);
		m_nRemark = new byte[Structs.TEXT_REMARK_LEN];
		Arrays.fill(m_nRemark,  (byte)0);		
		m_nDEType = 0;	
	}
	//*************************************************
	//
	public String  GetOwnerTel(){		
		
		return AppData.ByteToStringGBK(m_nOwnerTel);
	}
	//*************************************************
	//
	public void  SetOwnerTel( String  strOwnerTel){		
		
		try {
			m_nOwnerTel = strOwnerTel.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public String  GetDEUID(){		
		
		return AppData.ByteToStringGBK(m_nDEUID);
	}
	//*************************************************
	//
	public void  SetDEUID( String  strDEUID){		
		
		try {
			m_nDEUID = strDEUID.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public String GetDESIM(){
		
		return  AppData.ByteToStringGBK(m_nDESIM);
	}
	//*************************************************
	//
	public void  SetDESIM( String  strDESIM){		
		
		try {
			m_nDESIM = strDESIM.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public String  GetCarLicense(){
				
		return AppData.ByteToStringGBK(m_nCarLicense);
	}
	//*************************************************
	//
	public void  SetCarLicense( String  strCarLicense){		
		
		try {
			m_nCarLicense = strCarLicense.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public  String	GetFName(){
		return AppData.ByteToStringGBK( m_nFName );
	}
	//*************************************************
	//
	public void  SetFName( String  strFName){		
		
		try {
			m_nFName = strFName.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public  String	GetLName(){
		return AppData.ByteToStringGBK( m_nLName );
	}
	//*************************************************
	//
	public void  SetLName( String  strLName){		
		
		try {
			m_nLName = strLName.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public  String	GetOwnerAddr(){
		return AppData.ByteToStringGBK( m_nOwnerAddr );
	}
	//*************************************************
	//
	public void  SetOwnerAddr( String  strOwnerAddr){		
		
		try {
			m_nOwnerAddr = strOwnerAddr.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public  String	GetRemark(){
		return AppData.ByteToStringGBK( m_nRemark );
	}
	//*************************************************
	//
	public void  SetRemark( String  strRemark){		
		
		try {
			m_nRemark = strRemark.getBytes("GBK");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//*************************************************
	//
	public  byte  GetTEType(){
		
		return (byte)(m_nDEType&0xFF);
	} 
	//*************************************************
	//
	public  void  SetTEType( int  nDEType ){
		
		m_nDEType = nDEType;
	}  	 	
	//*************************************************
	//
	public  void  ParseCarInfo( byte[]  lpBuf,  int   nLen ){
		
		int			nDataLen = 0;		
		
		System.arraycopy(lpBuf, nDataLen, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
		nDataLen = Structs.TEXT_DEUID_LEN;
		
		nDataLen += 3;   //处理CarInfo结构对齐方式
		
		m_nDEType = AppData.ByteValueToInt( lpBuf[nDataLen+3],
											lpBuf[nDataLen+2],
											lpBuf[nDataLen+1],
											lpBuf[nDataLen]);
		nDataLen += 4;
		
		//nDataLen += 3;   //处理CarInfo结构对齐方式
		
		System.arraycopy(lpBuf, nDataLen, m_nCarLicense, 0, Structs.TEXT_TEL_LEN );
		nDataLen += Structs.TEXT_TEL_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nFName, 0, Structs.TEXT_NAME_LEN );
		nDataLen += Structs.TEXT_NAME_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nLName, 0, Structs.TEXT_NAME_LEN );
		nDataLen += Structs.TEXT_NAME_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nOwnerTel, 0, Structs.TEXT_TEL_LEN );
		nDataLen += Structs.TEXT_TEL_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nOwnerAddr, 0, Structs.TEXT_ADDR_LEN );
		nDataLen += Structs.TEXT_ADDR_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nDESIM, 0, Structs.TEXT_TEL_LEN );
		nDataLen += Structs.TEXT_TEL_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nRemark, 0, Structs.TEXT_REMARK_LEN );
		nDataLen += Structs.TEXT_REMARK_LEN;
		
		Log.e("车辆信息","DEUID:"+AppData.ByteToString(m_nDEUID)+" "+ 
				"车牌号:"+AppData.ByteToString(m_nCarLicense)+ " "+ 
				AppData.ByteToString(m_nFName)+" "+
				AppData.ByteToString(m_nLName)+ " "+
				AppData.ByteToString( m_nOwnerTel)+ " "+
				AppData.ByteToString(m_nOwnerAddr)+" "+
				AppData.ByteToString(m_nDESIM)+" "+
				AppData.ByteToString(m_nRemark) );
		return ;
	}
	//*************************************************
	//
	public void	setCarInfo( CarInfo  inData ){
		
		SetCarLicense( inData.GetCarLicense() );
		SetTEType( inData.GetTEType() );
		SetDEUID( inData.GetDEUID() );
		SetDESIM( inData.GetDESIM() );
		SetFName( inData.GetFName() );
		SetLName( inData.GetLName() );
		SetOwnerAddr( inData.GetOwnerAddr() );
		SetRemark( inData.GetRemark() );
		SetOwnerTel( inData.GetOwnerTel() );
	}
}