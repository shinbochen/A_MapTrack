package com.Protocol;

import java.util.Arrays;
import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Iterator;

import android.util.Log;


//////////////////////////////////////////////////////////////////////////
//传输层协议
//2      2           n          1
//|*|*|- |*|*|－ |************|- |*|
//同步   长度    命令数据        chksum
public class TLProtocolData {

	public static final byte  SYN_HEADERCODE1 = (byte)0xE6;
	public static final byte  SYN_HEADERCODE2 = (byte)0xE6;
	
	public static final byte   WHERE_SYN_START = 0x00;
	public static final byte   WHERE_TLLEN_START = 0x02;
	public static final byte   WHERE_TLDATA_START = 0x04;
	
	// 存传输层数据
	protected	ArrayList<Data>	m_lstTLSendData;
	protected	ArrayList<Data>	m_lstTLRecvData;
	
	//***********************************************
	//
	public TLProtocolData(){
				
		m_lstTLSendData = new ArrayList<Data>();
		m_lstTLSendData.clear();
		m_lstTLRecvData = new ArrayList<Data>();	
		m_lstTLRecvData.clear();
	}	
	public static	int	GetSYNHEADLEN( ){ return 2;}
	public static	int	GetMinDataLen( ){ return 5;}
	//////////////////////////////////////////////////////////////////////////
	//
	public static boolean	IsSYNHEAD( byte[] lpBuf){
	
		if( lpBuf[0] == SYN_HEADERCODE1 &&
			lpBuf[1] == SYN_HEADERCODE2 ){
			return true;
		}
		else{
			return false;
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//判断给定的数据是否符合TL协议的规则，并返回长度
	//不是返回0
	public static int	IsTLProtocolData( byte[] lpBuf, int nLen ){
	
		int				nResult = 0;
		int				nTLLen = 0;
		
		if( nLen >= GetMinDataLen() ){
			nTLLen = AppData.ByteValueToInt( lpBuf[WHERE_TLLEN_START+1],
						lpBuf[WHERE_TLLEN_START] );
			
			Log.e("IsTLProtocolData", "len:"+String.valueOf(nTLLen) );
			// SYNHEAD(2) + nLen(2) +...+chksum(1)
			if( 2 + 2 + nTLLen + 1 <= nLen ){
				nResult = 2+2+nTLLen+1;
			}
		}
		return nResult;	
	
	}
	//***********************************************
	//
	public void  FreeMemory( ){
		
		m_lstTLSendData.clear();
		m_lstTLRecvData.clear();
	}
	//***********************************************
	//组织数据
	//并加入发送列表
	public void ComposeAndAddTLData( byte[] lpData, int nLen ){
		
		byte 	nChkSum = 0;
		
		Data  oData = new Data();
		// 同步码(2)+长度(2)+...+chksum(1)    
		oData.MallocBuf( nLen+2+2+1);
		oData.AddData( (byte)(SYN_HEADERCODE1&0xFF) );
		oData.AddData( (byte)(SYN_HEADERCODE2&0xFF) );
		//长度	
		oData.AddData( (byte)(nLen&0xFF) );
		oData.AddData( (byte)((nLen >> 8)&0xFF));
		//数据
		oData.AddData( lpData, nLen );	
		// chksum
		for ( int nCnt = 0; nCnt < nLen + 4; nCnt++){		
			nChkSum -= oData.GetAt(nCnt);
		}
		oData.AddData( nChkSum );
		m_lstTLSendData.add(oData);
		return;
	}
	//***********************************************
	//  nBuf[0]  byte3
	//	nBuf[1]  byte2
	//	nBuf[2]  byte1
	//	nBuf[3]  byte0
	public int     ByteValueToInt(  byte nHig, byte nLow ){
		
		int			nResult = 0;
		BigInteger  bInt = null;
		byte[]  	nBuf = new byte[4];
		
		nBuf[0] = 0x00;
		nBuf[1] = 0x00;
		nBuf[2] = nHig;
		nBuf[3] = nLow;  
		bInt = new BigInteger(nBuf);
		nResult = bInt.intValue();
		
		return nResult;
	}
	//***********************************************
	//解释数据
	//输出协议层数据
	public boolean  ParseTLRecvData( byte[] lpBuf, int nLen ){
	
		byte[]	   tmpBuf = null;
		boolean    bResult = false;
		
		if ( nLen >= GetMinDataLen() ){ 
		
			if( VerifyCheckSum(lpBuf, nLen) ){
				
				int		nTLLen = 0;
							
				bResult = true;				
				nTLLen = ByteValueToInt( lpBuf[WHERE_TLLEN_START+1], 
										 lpBuf[WHERE_TLLEN_START] );
				tmpBuf = new byte[nTLLen];
				Arrays.fill(tmpBuf, (byte)0x00);
				Log.e("ParseTLRecvData_Len", String.valueOf(nTLLen ));
				System.arraycopy(lpBuf, WHERE_TLDATA_START, tmpBuf, 0, nTLLen );
												
				Data	oData = new Data();	
				oData.AddData( tmpBuf, nTLLen );
				
				m_lstTLRecvData.add(oData);
			}				
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public Data	GetTLRecvData( ){
	
		Data	    	oData = null;
		Data			nResultData = null;		
		Iterator<Data>	it = m_lstTLRecvData.iterator();

		if( it.hasNext() ){
			oData =  it.next();
			nResultData = new Data();
			nResultData.AddData(oData.GetDataBuf(),oData.GetDataLen() );
			m_lstTLRecvData.remove(oData);	
		}
		return nResultData;
	}
	//***********************************************
	//
	public Data	GetTLSendData(){
	
		Data	    			oData = null;
		Data  					nResultData = null;		
		Iterator<Data>			it = m_lstTLSendData.iterator();
		
		if( it.hasNext() ){
			oData =  it.next();
			nResultData = new Data();
			nResultData.AddData(oData.GetDataBuf(),oData.GetDataLen() );
			m_lstTLSendData.remove(oData);
		}
		return  nResultData;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	AddTLSendData( byte[] lpBuf, int nLen ){
	
		Data			oData;
		
		oData = new Data();
		oData.AddData( lpBuf, nLen );
		m_lstTLSendData.add(oData);
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	AddTLSendData( Data oData ){
	
		AddTLSendData( oData.GetDataBuf(), oData.GetDataLen() );
	}
	//***********************************************
	//
	public boolean	VerifyCheckSum( byte[] lpBuf, int nLen ){
	
		boolean	    bResult = false;
		byte		nChkSum = 0;
	
		for (int nCnt = 0; nCnt < nLen; nCnt++){
			nChkSum += lpBuf[nCnt];
		}
		if (nChkSum == 0x00){
			bResult = true;
		}
		return bResult;
	}
}








