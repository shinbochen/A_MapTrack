package com.Protocol;


import  java.util.Arrays;

public class Data{

	protected  byte  []m_lpBuf;
	protected  int	 m_nBufLen;
	protected  int	 m_nDataLen;
	
	
	//*******************************************
	//
	public Data(){
		m_lpBuf=null;
		m_nBufLen = 0;
		m_nDataLen = 0;
	}
	//*******************************************
	//
	public int		GetDataLen( ) { return m_nDataLen; }
	public void	SetDataLen( int nDataLen ){m_nDataLen = nDataLen;}
	public int		GetBufLen( ){return m_nBufLen;}
	public byte[]	GetDataBuf() { return m_lpBuf;}			
	//*******************************************
	//
	public byte	GetAt( int nCnt){ 
		if( m_nDataLen > nCnt ){ 
			return m_lpBuf[nCnt];
		}
		else{
			return 0;
		} 
	}		
	//*******************************************
	//
	public void MallocBuf( int nLen ){	
		
		m_lpBuf = new  byte[nLen];
		Arrays.fill(m_lpBuf, (byte)0x00 );
		m_nDataLen = 0;
		m_nBufLen = nLen;		
	}
	//*******************************************
	// 增加数据到数组
	public void  AddData( byte[] lpBuf, int  nLen ){
		
		if( nLen + m_nDataLen < m_nBufLen ){ 
			System.arraycopy(lpBuf, 0, m_lpBuf, m_nDataLen, nLen );
		}
		else{
			if( m_nDataLen > 0 ){
				byte[] tmpBuf = new byte[m_nDataLen];			
				//tmpBuf = Arrays.copyOf(m_lpBuf, m_nDataLen);
				System.arraycopy(m_lpBuf, 0, tmpBuf, 0, m_nDataLen );
				
				m_lpBuf = new byte[m_nDataLen+nLen];
				System.arraycopy(tmpBuf, 0, m_lpBuf, 0, m_nDataLen );
				System.arraycopy(lpBuf, 0, m_lpBuf, m_nDataLen, nLen );
			}
			else{
				m_lpBuf = new byte[m_nDataLen+nLen];
				System.arraycopy(lpBuf, 0, m_lpBuf, m_nDataLen, nLen );
			}
		}
		m_nDataLen += nLen;
	}
	//*******************************************
	//不自动扩展内存
	public boolean AddData( byte nData ){	
		
		if( m_nDataLen < m_nBufLen ){
			m_lpBuf[m_nDataLen] = nData;
			m_nDataLen += 1;
			return true;
		}
		else{
			return false;
		}
	}
	//*******************************************
	//
	public void	SetData( int nCnt, byte nData){
		
		if( m_nDataLen > nCnt ){
			m_lpBuf[nCnt] = nData;
		}
		return;
	}
	//*******************************************
	//
	public void	SetData( int nCnt, int nData){
		
		m_lpBuf[nCnt] = (byte)(nData & 0xFF);
		m_lpBuf[nCnt+1]  = (byte)((nData >> 8) & 0xFF); 
		//	result[1] = (byte)((nData >> 16) & 0xFF);
		//	result[0] = (byte)((nData >> 24) & 0xFF);		  
		return;
	}
	//*******************************************
	//从前面移出nLen个数据
	public void TrimLeft( int nLen ){		
		//m_lpBuf = Arrays.copyOfRange(m_lpBuf, nLen, m_nDataLen);			
		//m_nDataLen -= nLen;
		
		System.arraycopy(m_lpBuf, nLen, m_lpBuf, 0, m_nDataLen-nLen );
		m_nDataLen -= nLen;
	}
}

