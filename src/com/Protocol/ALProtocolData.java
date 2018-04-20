package com.Protocol;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;


public	class ALProtocolData extends PLProtocolData{
	
	// ��Ӧ������( Ӧ����������+Ӧ�����ݳ���+Ӧ������)
	protected	List<Data>		m_lstAppSendData;
	protected	List<Data>		m_lstAppRecvData;	
	
	public static final int		PACKAGESIZE	= 2048;

	public static final byte	WHERE_SUM_APPDATA =	0x00;
	public static final byte	WHERE_TYPE_APPDATA=	0x01;
	public static final byte	WHERE_LEN_APPDATA=	0x02;
	public static final byte	WHERE_DATA_APPDATA= 0x04;
		
	public ALProtocolData(){
		
		m_lstAppSendData = new ArrayList<Data>();
		m_lstAppRecvData = new ArrayList<Data>();
		m_lstAppSendData.clear();
		m_lstAppRecvData.clear();
	}	
	//////////////////////////////////////////////////////////////////////////
	//�յ����һ������TRUE
	//����:		�������������
	//�������:	N��Ӧ�ò�����
	public boolean	ParseALRecvData( byte[] lpBuf, int nLen ){
	
		Data		oData;
		boolean		bResult = false;
		int			nAppSum;		
		Log.e("ParseALRecvData", "1" );
		if( super.ParsePLRecvData(  lpBuf, nLen ) ){	
			while( true ){
				oData = super.GetPLRecvData();
				if( oData == null ){
					break;
				}
				bResult = true;
				nLen = WHERE_SUM_APPDATA;				
				nAppSum = AppData.ByteValueToInt((byte)0x00, oData.GetAt( WHERE_SUM_APPDATA ));
				nLen++;
				
				for( int nCnt = 0; nCnt < nAppSum; nCnt++ ){
				
					byte		nAppType = 0;
					int 		nAppLen = 0;
					byte[]		buf = null;
					Data		oData2 = null;
										
					nAppType = oData.GetAt( nLen );
					nLen++;
					
					buf = oData.GetDataBuf();
					nAppLen = super.ByteValueToInt(buf[nLen+1], buf[nLen] );
					nLen += 2;
					
					oData2 = new Data();
					oData2.MallocBuf( nAppLen+1+2 );
					oData2.AddData( nAppType );
					
					oData2.AddData( (byte)(nAppLen&0xFF) );
					oData2.AddData( (byte)((nAppLen>>8)&0xFF) );
					
					buf = new byte[nAppLen];
					
					System.arraycopy(oData.GetDataBuf(), nLen, buf, 0, nAppLen );
					oData2.AddData(buf, nAppLen );

					nLen += nAppLen;
					
					m_lstAppRecvData.add( oData2 );
				}
			}
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	AddAppSendData( byte[] lpBuf, int nLen ){
	
		Data			oData = null;
		
		oData = new Data();		
		oData.AddData( lpBuf, nLen );
		m_lstAppSendData.add( oData );
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	AddAppSendData( Data  oData ){
	
		AddAppSendData( oData.GetDataBuf(), oData.GetDataLen() );
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void ComposeALData( byte nCmdCode  ){	
			
		Data							oData = null;
		Data							oData2 = null;
		byte							nSumApp = 0;
		
		////////////////////��Ӧ�����ݴ��///////////////////
		//
		// Ӧ�����ݸ���(1)Ԥ���ռ�
		oData = new Data();
		oData.MallocBuf(PACKAGESIZE+1);
		oData.SetDataLen( 1 );
		nSumApp = 0;
		
		Iterator<Data>	it = m_lstAppSendData.iterator();
		while ( it.hasNext() ){
			
			oData2 = it.next();
			if( oData.GetDataLen()+oData2.GetDataLen() > PACKAGESIZE+1 ){
				oData.SetData(WHERE_SUM_APPDATA, nSumApp );
				super.AddPLSendData( oData.GetDataBuf(), oData.GetDataLen() );
				// bufferָ�����¿�ʼ
				oData.SetDataLen( 1 );
				nSumApp = 0;
			}				
			nSumApp++;
			oData.AddData( oData2.GetDataBuf(), oData2.GetDataLen() );
		}
		m_lstAppSendData.clear();
		
		if( oData.GetDataLen() > 1 ){
			oData.SetData( WHERE_SUM_APPDATA, nSumApp );
			super.AddPLSendData( oData.GetDataBuf(), oData.GetDataLen() );
		}			
		super.ComposePLData( nCmdCode );
		
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//Ӧ�����ݸ���Ϊ0
	public void ComposeALACKOKData( byte nCmd ){
	
		byte		nData = 0;
		byte[]		buf = new byte[2];
		
		buf[0] = nData;
		super.AddPLSendData( buf, 1 );			
		super.ComposePLData( (byte)(nCmd | CM_ACK_MARK | CM_ACK_OK) );
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//Ӧ�����ݸ���Ϊ0
	public void ComposeALACKNGData( byte nCmd, byte nErrorCode ){
	
		Data		oData = null;
		byte[]		buf = new byte[2];
		
		oData = new Data();	
		buf[0] = nErrorCode; 
		AppData.ComposeData( oData, MacroDefinitions.TYPE_ERROR_CODE, buf, 1 );
		AddAppSendData( oData );
		ComposeALData( (byte)(nCmd | CM_ACK_MARK | CM_ACK_NG) );
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public Data GetALRecvData( ){
	
		Data		oData = null;
		Data		oData2 = null;
		
		Iterator<Data>  it = m_lstAppRecvData.iterator();
		if( it.hasNext() ){
			oData2 = it.next();
			oData = new Data();
			oData.AddData( oData2.GetDataBuf(), oData2.GetDataLen() );
			m_lstAppRecvData.remove( oData2 );
		}
		return oData;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public Data GetALSendData( ){
		
		Data			oData = null;
		Data			oData2 = null;
		
		Iterator<Data>	it = m_lstAppSendData.iterator();
		if( it.hasNext() ){
			oData2 = it.next();
			oData = new Data();
			oData.AddData(oData2.GetDataBuf(), oData2.GetDataLen() );
			m_lstAppSendData.remove(oData2);
		}
		return oData;
	}
}