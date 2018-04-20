package com.Protocol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//////////////////////////////////////////////////////////////////////////
//Э�����Ӧ�ò�ʵ��
//Э���
//---------------------------------
//|   1  |	 2	 |	 2	|	2	 | n      |
//---------------------------------
//|������|�����|�ܰ���|��ǰ����|Ӧ������|
//---------------------------------
//Ӧ�ò�
//|   1        |	 1	     |	 2	      |	 n	   |
//---------------------------------
//|Ӧ�����ݸ���|Ӧ����������|Ӧ�����ݳ���|Ӧ������|
//WHERE_CMD_CODE		0x00
//WHERE_SEQ_PACKAGE	0x01
//WHERE_SUM_PACKAGE	0x03
//WHERE_CUR_PACKAGE	0x05
//WHERE_APPDATA_START	0x07

public class PLProtocolData  extends TLProtocolData{		
	// ��Ӧ������( Ӧ����������+Ӧ�����ݳ���+Ӧ������)
	protected   	List<Data>	m_lstPLSendData;
	protected   	List<Data>	m_lstPLRecvData;
	protected   	byte		m_nCmdCode;
	protected   	short		m_nSequence;
	protected   	int			m_nRecvSumPackages;
	protected   	int			m_nRecvCurPackages;
	
	public static final int     WHERE_CMD_CODE	    =	0x00;
	public static final int     WHERE_SEQ_PACKAGE   =	0x01;
	public static final int     WHERE_SUM_PACKAGE   =	0x03;
	public static final int     WHERE_CUR_PACKAGE   =	0x05;
	public static final int     WHERE_APPDATA_START =	0x07;
	
	public static final byte     CM_ACK_MARK	=	(byte)0x80;
	public static final byte     CM_ACK_OK		=	0x40;
	public static final byte     CM_ACK_NG		=	0x00;
	
	public PLProtocolData(){
		m_lstPLSendData = new ArrayList<Data>();
		m_lstPLRecvData = new ArrayList<Data>();
		m_lstPLSendData.clear();
		m_lstPLRecvData.clear();
		m_nSequence = 0;
		m_nRecvSumPackages = 0;
		m_nRecvCurPackages = 0;
	}	
	public void	SetSequence(short nSequence){ m_nSequence=nSequence; }
	public short	GetSequence( )				{ return m_nSequence; }
	public void	SetCMDType( byte nCmd)		{ m_nCmdCode = nCmd; }
	public byte	GetCMDType( )				{ return (byte)(m_nCmdCode & ~(CM_ACK_MARK|CM_ACK_OK));}
	public boolean	IsAckType( ){	
		
		if( (m_nCmdCode & CM_ACK_MARK) != 0x00 ){
			return true;
		}
		else{
			return false;
		}
	}	
	public boolean	IsAckOK( ){		
		if( IsAckType() == false ){
			return false;
		}
		if( (m_nCmdCode & CM_ACK_OK) == 0x00 ){
			return false;
		}
		else{
			return true;
		}
	}
	public int		GetRecvSumPackages( )		{ return m_nRecvSumPackages; }
	public int		GetRecvCurPackages( )		{ return m_nRecvCurPackages; }
	
	//////////////////////////////////////////////////////////////////////////
	//�յ����һ������TRUE
	//����:		�������������
	//�������:	N��Ӧ�ò�����
	//����:		TRUE��ʾ�����ݿɽ���
	public boolean	ParsePLRecvData( byte[] lpBuf, int nLen ){
	
		Data		oData = null;
		Data		oData2 = null;
		boolean		bResult = false;
		
		if( super.ParseTLRecvData( lpBuf, nLen ) ){
		
			while( true ){
				oData = super.GetTLRecvData();	
				if( oData == null ){
					break;
				}
				bResult = true;
				m_nCmdCode = oData.GetAt( WHERE_CMD_CODE );
				
				m_nSequence = (short)super.ByteValueToInt( oData.GetAt(WHERE_SEQ_PACKAGE+1),
				oData.GetAt(WHERE_SEQ_PACKAGE));
				
				m_nRecvSumPackages = super.ByteValueToInt( oData.GetAt(WHERE_SUM_PACKAGE+1), 
				oData.GetAt(WHERE_SUM_PACKAGE));
				
				m_nRecvCurPackages = super.ByteValueToInt( oData.GetAt(WHERE_CUR_PACKAGE+1), 
				oData.GetAt(WHERE_CUR_PACKAGE));
				
				oData2 = new Data();
				byte[] nBuf = new byte[oData.GetDataLen()-1-2-2-2];	
				System.arraycopy(oData.GetDataBuf(), WHERE_APPDATA_START, nBuf, 0, oData.GetDataLen()-1-2-2-2 );				
				oData2.AddData( nBuf, oData.GetDataLen()-1-2-2-2 );
				m_lstPLRecvData.add(oData2);
			}
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	AddPLSendData( byte[] lpBuf, int nLen ){
	
		Data			oData=null;
		
		oData = new Data();
		// Ϊ������(1)+�ܰ���(2)+���к�(2)+���ڰ���(2)Ԥ���ռ�
		oData.MallocBuf( nLen+1+2+2+2 );
		// �൱��ADD��7������
		oData.SetDataLen( 7 );		
		oData.AddData( lpBuf, nLen );
		m_lstPLSendData.add(oData);
		return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void ComposePLData( byte nCmdCode ){			
	
		////////////////////��֯Э�������///////////////////
		////////////////////��֯���������///////////////////
		//
		Data oData = null;
		int	 nSumPackage, nCurPackage;
		
		nSumPackage = m_lstPLSendData.size();
		nCurPackage = 1;
		Iterator<Data>	it = m_lstPLSendData.iterator();	
		while( it.hasNext() ){
		
			oData = it.next();
			
			oData.SetData(WHERE_CMD_CODE, nCmdCode );
			oData.SetData(WHERE_SEQ_PACKAGE, GetSequence() );
			oData.SetData(WHERE_SUM_PACKAGE, nSumPackage );
			oData.SetData(WHERE_CUR_PACKAGE, nCurPackage++ );			
			super.ComposeAndAddTLData( oData.GetDataBuf(), oData.GetDataLen() );
		}
		m_lstPLSendData.clear();	
	return;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public Data	GetPLRecvData(){
	
		Data  oData = null;
		Data  oData2 = null;
		
		Iterator<Data> it = m_lstPLRecvData.iterator();
		if( it.hasNext() ){
			oData2 = it.next();
			oData = new Data();
			oData.AddData(oData2.GetDataBuf(), oData2.GetDataLen() );			
			m_lstPLRecvData.remove( oData2 );
		}
		return oData;
	}
	////////////////////////////////////////////////////////////
	//
	public Data   GetPLSendData(  ) {
	
		Data		oData = null;
		Data		oData2 = null;
		Iterator<Data>  it = m_lstPLSendData.iterator();
		if( it.hasNext() ){
		
			oData2 = it.next();
			oData = new Data();
			oData.AddData( oData2.GetDataBuf(), oData.GetDataLen() );			
			m_lstPLSendData.remove(oData2);
		}
		return oData;
	}
}
