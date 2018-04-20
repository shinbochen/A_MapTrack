package com.Protocol;


import java.util.List;
import java.util.ArrayList;
//import java.util.Iterator;

import android.util.Log;


//*************************************************
// �����
public class ComposeData {
	
	Data				m_oRecvData;
	List<Data>		   	m_oOutData;	
	
	public ComposeData(){		
		m_oRecvData = new Data();
		m_oOutData = new ArrayList<Data>();
		m_oOutData.clear();
	}
	//*******************************************************
	//
	public boolean	HasValidData( ){ 
		if( m_oOutData.size() > 0 ){
			return true;
		}
		else{
			return false;
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//�����������ݿ��Ƿ����һ������������
	//|*|*|- |*|*|- |*|*|-   |************|-  |*|
	//ͬ��   ����   �����    ��������        checksum
	//�����ɹ�����TRUE
	public boolean ParseData( ){
		
		int			nCnt = 0;
		boolean		bFlag = false;
		Data		oData = null;
		boolean		bResult = false;
		byte[]		nBuf = new byte[8];

		while( true ){		
			if( m_oRecvData.GetDataLen() < TLProtocolData.GetMinDataLen()){
				break;
			}			
			bFlag = false;
			// ��ͬ����
			for( nCnt = 0; nCnt < m_oRecvData.GetDataLen(); nCnt++ ){
				if( (m_oRecvData.GetDataLen() - nCnt) >= 2  ){
					System.arraycopy(m_oRecvData.GetDataBuf(), nCnt, nBuf, 0, 2 );
					if( TLProtocolData.IsSYNHEAD( nBuf ) ){
						bFlag = true;
						break;
					}
				}
			}
			// û���ҵ�ͬ�����˳�
			if( bFlag == false ){
				Log.e("ParseData", "4" );
				break;
			}
			else{
				m_oRecvData.TrimLeft( nCnt );				
				nCnt = TLProtocolData.IsTLProtocolData( m_oRecvData.GetDataBuf(), m_oRecvData.GetDataLen() );
	 			if( nCnt > 0 ){
	 				Log.e("ParseData", "5" );
					bResult = true;
					oData = new Data();
					oData.AddData( m_oRecvData.GetDataBuf(), nCnt );				
					m_oRecvData.TrimLeft( nCnt );
					m_oOutData.add( oData );
				}
				else{ //����̫С,������һ������
					Log.e("ParseData", "6" );
					break;
				}
			}
		}
		return bResult;
	}
	//*******************************************************
	//���buf��������FALSE
	public boolean AddRecvData( byte[] lpBuf, int nLen ){
	
		boolean  bResult = true;		
//		synchronized(this){		
			m_oRecvData.AddData( lpBuf, nLen );
			ParseData( );
//		}		
		return bResult;
	}	
	//////////////////////////////////////////////////////////////////////////
	//��������ָ��
	public byte[]  GetOutData( ){
	
		Data    oData;
		byte[]  nTmp = null;
		byte[]	nBuf = null;
		
	//	synchronized(this){				
			if( HasValidData() ){
				
				oData = m_oOutData.get(0);
				nTmp = oData.GetDataBuf();
				nBuf = new byte[nTmp.length];
				System.arraycopy(nTmp, 0, nBuf, 0, nTmp.length );
				m_oOutData.remove(0);
			}
	//	}
		return nBuf;		
	}
}

