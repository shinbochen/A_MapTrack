package com.Protocol;

import java.util.Arrays;


public class GASData {
	
	public 	byte[]		m_nDEUID;
	public  int			m_nTime;
	public  int 		m_nGas;
	
	
	//*******************************************************
	//
	public  GASData(){
		
		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte)0);
		m_nTime = 0;
		m_nGas = 0;
	}
	//*******************************************************
	//
	public  void  ParseUserInfo( byte[] lpBuf,  int  nLen ){
		
		int			nDataLen = 0;
		
		System.arraycopy(lpBuf, 0, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
		nDataLen += Structs.TEXT_DEUID_LEN;
		
		nDataLen += 3;   //STGASDATA结构体对齐方式
		
		m_nGas = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										 lpBuf[nDataLen+2], 
										 lpBuf[nDataLen+1], 
										 lpBuf[nDataLen] );
		nDataLen += 4;
		
		m_nTime = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										 lpBuf[nDataLen+2], 
										 lpBuf[nDataLen+1], 
										 lpBuf[nDataLen] );
	}
}
