package com.Protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.util.Log;


public class GroupManage {

	public 		byte[]			m_nUser;	
	public      byte[]			m_nGroupName;
	public 		byte[]			m_nDEUID;
	public 		List<String>	m_lstDEUID;
	public 		int				m_nLen;		

	//**************************************************
	//
	public GroupManage(){
		
		m_nLen = 0;
		
		m_lstDEUID = new ArrayList<String>();
		m_lstDEUID.clear();
		
		m_nUser = new byte[Structs.TEXT_USER_LEN];
		Arrays.fill(m_nUser, (byte) 0);
		
		m_nGroupName = new byte[Structs.TEXT_GROUPNAME_LEN];
		Arrays.fill(m_nGroupName, (byte)0 );
		
		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte) 0);
	}
	//**************************************************
	//
	public void ParseGroupManage( byte[] lpBuf, int  nLen ){
		
		int    nDataLen = 0;
		int	   nPos = 0;		
				
		System.arraycopy(lpBuf, 0, m_nUser, 0, Structs.TEXT_USER_LEN );
		nDataLen += Structs.TEXT_USER_LEN;
		
		System.arraycopy(lpBuf, 0, m_nGroupName, 0, Structs.TEXT_GROUPNAME_LEN );
		nDataLen += Structs.TEXT_GROUPNAME_LEN;
		
		nDataLen += 2;   //处理GroupManage结构对齐方式	
		
		m_nLen = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										 lpBuf[nDataLen+2], 
										 lpBuf[nDataLen+1], 
										 lpBuf[nDataLen]);
		nDataLen += 4;				
		if( m_nLen <= 0 ){
			return ;
		}		
		
		
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
			Log.e("组DUID信息",  str);
		}
	}
}
