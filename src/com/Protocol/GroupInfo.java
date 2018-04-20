package com.Protocol;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;

public class GroupInfo {

	public byte[]			m_nUser = null; 
	public int				m_nLen = 0;
	public byte[]			m_nGroupName;
	public List<String>		m_lstGroupName = null;
	
	//***************************************************
	//
	public GroupInfo(){
				
		m_nUser = new byte[Structs.TEXT_USER_LEN];
		Arrays.fill(m_nUser, (byte)0);
		m_nGroupName = new byte[Structs.TEXT_GROUPNAME_LEN];
		Arrays.fill(m_nGroupName, (byte)0);
		m_nLen = 0;		
		m_lstGroupName = new ArrayList<String>();
	}
	//***************************************************
	//
	public  void  ParseGroupInfo( byte[]  lpBuf, int  nLen) {
		
		int    nDataLen = 0;
		int	   nPos = 0;		
			
		System.arraycopy(lpBuf, 0, m_nUser, 0, Structs.TEXT_USER_LEN );
		nDataLen = Structs.TEXT_USER_LEN;
		
		nDataLen += 3;   //处理GroupInfo结构对齐方式	
		
		m_nLen = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										 lpBuf[nDataLen+2], 
										 lpBuf[nDataLen+1], 
										 lpBuf[nDataLen]);
		nDataLen += 4;				
		if( m_nLen <= 0 ){			
			return ;
		}

		//nDataLen += 3;   //处理GroupInfo结构对齐方式	
		nPos = nDataLen;
		for( int nCnt = 0; nCnt < m_nLen; nCnt++ ){
			
			System.arraycopy(lpBuf, nPos, m_nGroupName, 0, Structs.TEXT_GROUPNAME_LEN );
			
			m_lstGroupName.add( AppData.ByteToString( m_nGroupName ) );			
			nPos += Structs.TEXT_GROUPNAME_LEN;
		}
		
		String  str;
		Iterator<String>  it = m_lstGroupName.iterator();
		while( it.hasNext() ){
			str = it.next();
			Log.e("组信息",  str);
		}
		return ;
	}
	
}
