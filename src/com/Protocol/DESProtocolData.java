package com.Protocol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

//******************************************************
//
//
public class DESProtocolData extends ALProtocolData {

	public 	 String				m_strDEUID = "";
	public   List<Integer>		m_lstSendMode = null;
	public   List<GPSData>		m_lstGPSData = null;
	public   List<Data1>		m_lstDBData = null;
	
	public static final int  	DETYPE_STANDARD_A = 0;
	public static final int  	DETYPE_STANDARD_B = 1;
	public static final int  	DETYPE_ENHANCE = 2;
	public static final int  	DETYPE_ADVANCE = 3;	
	public static final int  	DETYPE_UNKNOW = 8;
	
	//***********************************************
	//
	public  DESProtocolData(){
	
		m_strDEUID = "";
		m_lstSendMode = new ArrayList<Integer>();
		m_lstSendMode.clear();
		
		m_lstGPSData = new ArrayList<GPSData>();
		m_lstGPSData.clear();
		
		m_lstDBData = new ArrayList<Data1>();
		m_lstDBData.clear();
	}	
	//***********************************************
	//
	public  void freeMemory(){
		
		super.FreeMemory();
		
		m_lstSendMode.clear();
		m_lstDBData.clear();
	}
	public void setDEUID( String  strDEUID ){
		
		m_strDEUID = strDEUID;
	}
	public  String  getDEUID(){
		return m_strDEUID;
	}

	public  int  getGPSDataSize(){
		return  m_lstGPSData.size();
	}
	public  GPSData getGPSData(){
		
		GPSData		oGPSData = null;
		
		if( m_lstGPSData.size() > 0 ){			
			oGPSData = m_lstGPSData.get(0);
			m_lstGPSData.remove(0);
		}
		return oGPSData;
	}
	//////////////////////////////////////////////////////////////////////////
	//同步码
	public int	  getDEType( byte[]	pBuf ){
	
		int		nResult = DETYPE_STANDARD_A;
		
		if ( (pBuf[DEProtocolB.WHERE_MESSAGE_HEAD1] == SYN_HEADERCODE1) &&
				(pBuf[DEProtocolB.WHERE_MESSAGE_HEAD2] == SYN_HEADERCODE1) ){
		
			nResult = DETYPE_STANDARD_B;
		}		
		return nResult;
	}
	//***********************************************
	//
	public  boolean  GetTypeStandardB( DEProtocolB  oProtocolb ){
		
		boolean 	bResult = false;
		
		switch( oProtocolb.getDataType() ){
		case MacroDefinitions.TYPE_GPSDATA:
			while( oProtocolb.getGPSDataSize()> 0 ){				
				m_lstGPSData.add( oProtocolb.getGPSData() );
				bResult = true;
			}
			break;
		}
		return  bResult;
	}
	//***********************************************
	//
	public  boolean ParseDESData( byte[] pBuf, int nLen ){
		
		boolean   		bResult = false;
		DEProtocolB		oProtocolB = null;
		
		if ( (pBuf == null) || (nLen <= 0 )){
			return bResult;
		}
		if ( VerifyCheckSum(pBuf, nLen) == false ) {
			return bResult;
		}
		switch( getDEType(pBuf) ){
		case DETYPE_STANDARD_A:
			break;
		case DETYPE_STANDARD_B:
			Log.e("2", "1");
			oProtocolB = new DEProtocolB();
			if ( oProtocolB.ParseData( pBuf, nLen) ){
				Log.e("2", "2");
				bResult = GetTypeStandardB( oProtocolB );
				setDEUID( oProtocolB.getDEUID() );
			}			
			break;
		}		
		return  bResult;
	}
	//**************************************************
	//  明码解释
	public  boolean  ParseMingData( String  strMing,
									byte	nType,
									String	strDEUID){
		
		boolean 	 	bResult = false;
		DEProtocolB		oProtocolB = null;
		
		switch( nType ){
		case DETYPE_STANDARD_A:
			break;
		case DETYPE_STANDARD_B:
		case DETYPE_ENHANCE:
		case DETYPE_ADVANCE:
			oProtocolB = new DEProtocolB();
			if( oProtocolB.ParseMingData( strMing, strDEUID) ){
				
				bResult = GetTypeStandardB( oProtocolB );
				setDEUID( strDEUID );
				bResult = true;
			}
			break;
		}		
		return  bResult;
	}
	//********************************************************
	//常用指令控制
	public void  ComposeCtrlTENormal(  byte	 	 nSequnce, 
										String		 strDEUID,
										byte		 nDEType,
										byte		 nCmdType ){
	
		DEProtocolB				oDEProtocolB;	
		
		if ( nDEType ==  DETYPE_STANDARD_B ){
		
			oDEProtocolB = new DEProtocolB();
			oDEProtocolB.ComposeCtrlNormal( nSequnce, strDEUID, nCmdType );
			ComposeDEProtocolB( oDEProtocolB );
			m_strDEUID = strDEUID;	
		}	
	}
	//********************************************************
	//
	public  void  ComposeCtrlDoor( byte  nSequnce,
										String		 strDEUID,
										byte		 nDEType,
										byte		 nCmdType ){
		
		DEProtocolB				oDEProtocolB;	
		
		if ( nDEType ==  DETYPE_STANDARD_B ){
		
			oDEProtocolB = new DEProtocolB();
			oDEProtocolB.ComposeCtrlDoor( nSequnce, strDEUID, nCmdType );
			ComposeDEProtocolB( oDEProtocolB );
			m_strDEUID = strDEUID;	
		}	
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void   ComposeCtrlTEOilWay( byte 		nSequnce,
								String		strDEUID,
								byte 		nDEType,
								byte 		nOilType ){
	
		DEProtocolB				oDEProtocolB;	
		
		if ( nDEType == DETYPE_STANDARD_B){
			
			oDEProtocolB = new DEProtocolB();
			oDEProtocolB.ComposeCtrlTEOilWay( nSequnce, strDEUID, nOilType );
			ComposeDEProtocolB( oDEProtocolB );
			m_strDEUID = strDEUID;	
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void ComposeCtrlTEListen( byte		 	nSequnce,
							  String		strDEUID,
							  byte 			nDEType,
							  String		strPhone ){

		DEProtocolB				oDEProtocolB;	
		

		if ( nDEType == DETYPE_STANDARD_B){
			
			oDEProtocolB = new DEProtocolB();
			oDEProtocolB.ComposeCtrlListen( nSequnce, strDEUID, strPhone );
			ComposeDEProtocolB( oDEProtocolB );
			m_strDEUID = strDEUID;	
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void  ComposeCtrlTETalk( byte 		nSequnce,
							 String		strDEUID,
							 byte 		nDEType,
							 String		strPhone ){
	
		DEProtocolB				oDEProtocolB;	
		
		if ( nDEType == DETYPE_STANDARD_B){
			
			oDEProtocolB = new DEProtocolB();
			oDEProtocolB.ComposeCtrlTalk( nSequnce, strDEUID, strPhone );
			ComposeDEProtocolB( oDEProtocolB );
			m_strDEUID = strDEUID;	
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	ComposeDEProtocolB(DEProtocolB  oProlB ){
	
		Data						oData = null;
		
		if ( oProlB == null ){
			return;
		}
		// 控制终端指令
		if( oProlB.getDataType() == MacroDefinitions.TYPE_TECMD ){
			
			while( oProlB.getSendDataSize() > 0 ){
				
				oData = oProlB.getSendData();
				if( oData != null ){
					super.AddTLSendData(oData);
				}
			}
		}
	}
}
