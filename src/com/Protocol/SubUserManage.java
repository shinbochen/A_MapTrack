package com.Protocol;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
//import java.util.Arrays;

import android.util.Log;

public class SubUserManage {

	public   byte[]			m_nUser;
	public   byte[]			m_nDEUID;
	int						m_nLen;
	public   List<String>	m_lstDEUID;
	
	
	public  SubUserManage(){
		
		m_nLen = 0;
		m_nUser = new byte[Structs.TEXT_USER_LEN];
		Arrays.fill(m_nUser, (byte)0);
		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte)0);
		m_lstDEUID = new ArrayList<String>();
	}
	//*********************************************************
	//
	public void  ParseSubUserManager( byte[]  lpBuf, int  nLen ){
		
		int    nDataLen = 0;
		int	   nPos = 0;		
			
		System.arraycopy(lpBuf, 0, m_nUser, 0, Structs.TEXT_USER_LEN );
		nDataLen = Structs.TEXT_USER_LEN;
		
		nDataLen += 3;   //处理SubUserManage结构对齐方式	
		
		m_nLen = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										 lpBuf[nDataLen+2], 
										 lpBuf[nDataLen+1], 
										 lpBuf[nDataLen]);
		nDataLen += 4;				
		if( m_nLen <= 0 ){
			return ;
		}
		//nDataLen += 3;   //处理SubUserManage结构对齐方式	
		nPos += nDataLen;
		for( int nCnt = 0; nCnt < m_nLen; nCnt++ ){
			
			System.arraycopy(lpBuf, nPos, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
			
			m_lstDEUID.add( AppData.ByteToString( m_nDEUID ) );			
			nPos += Structs.TEXT_DEUID_LEN;
		}
		String  str;
		Iterator<String>  it = m_lstDEUID.iterator();
		while( it.hasNext() ){
			str = it.next();
			Log.e("子用户DUID信息",  str);
		}
	}
}
