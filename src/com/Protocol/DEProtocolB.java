package com.Protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.util.Log;

//***********************************************
//
public class DEProtocolB {
		
	public  static final  byte 	WHERE_MESSAGE_HEAD1	= 0;
	public  static final  byte 	WHERE_MESSAGE_HEAD2	= 1;
	public  static final  byte 	WHERE_MESSAGE_TYPE	= 2;
	public  static final  byte 	WHERE_MESSAGE_TOTAL_LENGHT = 3;
	public  static final  byte 	WHERE_MESSAGE_SEQNUM = 4;

	public  static final  byte 	WHERE_MESSAGE_DEUIDLEN = 5;
	public  static final  byte 	WHERE_MESSAGE_DEUID	= 6;

	public  static final  int	MULTI_PACKAGE = 0x80;
	
	public  String   			m_strDEUID;
	public  int					m_nDataType;
	public  List<GPSData>		m_lstGPSData = null;
	public  List<Data>			m_lstSendData = null;
		
	//********************************************
	//
	public DEProtocolB(){	
		
		m_strDEUID = "";
		m_lstGPSData = new ArrayList<GPSData>();
		m_lstGPSData.clear();
		
		m_lstSendData = new ArrayList<Data>();
		m_lstSendData.clear();
	}
	public int	getDataType(){
		return m_nDataType;
	}
	public void  setDEUID( String  strDEUID ){		
		m_strDEUID = strDEUID;
	}
	public String getDEUID(){		
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
	public  int getSendDataSize(){
		
		return m_lstSendData.size();
	}
	public  Data  getSendData(){
		
		Data		oData = null;
		
		if( m_lstSendData.size() > 0 ){
			oData = m_lstSendData.get(0);
			m_lstSendData.remove(0);
		}
		
		return oData;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	byte	CountVerifySum(byte[] lpBuf, int nLen){
	
		byte nSumCheck = 0;
		
		for (int nCnt = 0; nCnt < nLen; nCnt++) {
		
			nSumCheck -= lpBuf[nCnt];
		}
		return nSumCheck;
	}
	//��ͷ(2)+��Ϣ����(1)+�ܳ���(1)+�ܰ���(1)+DEUID����(1)+DEUID����(n)+��������(1)+���ݳ���(1)+��������(n)+У���(1)
	//////////////////////////////////////////////////////////////////////////
	//���ӱ�ͷ(2)+��Ϣ����(1)
	void	AddSycHeadCode( Data	  oData, 
							byte	  nMessageType ){
		
		if ( oData == null ){
			return ;
		}
		oData.AddData( (byte)DESProtocolData.SYN_HEADERCODE1 );
		oData.AddData( (byte)DESProtocolData.SYN_HEADERCODE2 );
		oData.AddData( (byte)nMessageType );
	} 
	//////////////////////////////////////////////////////////////////////////
	//�ܳ���(1) + �ܰ���(1)
	void    AddTialPackageNum( Data			oData, 
							   byte  		nTotalLen,  
							   byte  		nTotalPackageNum  ){
		if ( oData == null ){
			return ;
		}
		oData.AddData( nTotalLen );
		oData.AddData( nTotalPackageNum );
	}
	//////////////////////////////////////////////////////////////////////////
	//DEUID����(1)+DEUID����(n)
	void   AddDEUID( Data			oData, 
					 byte   		nDEUIDLen, 
				     byte[]			lpDEUID ){
		
		if ( oData == null ){
			return ;
		}
		oData.AddData( nDEUIDLen );
		oData.AddData( lpDEUID, nDEUIDLen );
	}
	//////////////////////////////////////////////////////////////////////////
	//��������(1)+���ݳ���(1)+��������(n)
	void   AddDataType( Data	oData, byte   	  nCmdType, 
						byte   	nDataLen,byte[]   pData ){
		if ( oData == null ){
			return ;
		}
		oData.AddData( nCmdType );
		if ( nDataLen > 0 ){
			oData.AddData( nDataLen );
			oData.AddData( pData, nDataLen );
		}	
	}
	//////////////////////////////////////////////////////////////////////////
	//
	void    ComposeCtrlNormal( byte		nSequnce, 
							   String	strDEUID, 
							   byte		nCmdType  ){

		boolean				bFlag = true;
		byte				nTmpCmdType = 0;
		byte				nCheckSum = 0;
		int					nDEUIDLen = 0;
		Data				oData = null;

		switch( nCmdType ){
		case SMSControlCode.CM_CTRL_NORMAL_CALLONCE:			  //��λ����
			nTmpCmdType = DECMDData.CC_TE_CALLONCE;
			break;
		case SMSControlCode.CM_CTRL_NORMAL_RESET:				  //��λDES_TE_RESET
			nTmpCmdType = DECMDData.CC_TE_RESET;
			break;
		case SMSControlCode.CM_CTRL_NORMAL_RESTORE_FACTORY:	  	 //�ָ���������
			nTmpCmdType = DECMDData.CC_TE_RESTORE_FACTORY;
			break;
		case SMSControlCode.CM_CTRL_NORMAL_TESETUP:              //(��ȡ�ն�����)
			nTmpCmdType = DECMDData.CC_TE_QUERY_SW_SETUP;
			break;
		case SMSControlCode.CM_CTRL_NORMAL_FORTIFY:		    	 //(���)
			break;
		case SMSControlCode.CM_CTRL_NORMAL_RESHUFFLE:		     //(����)
			break;
		default :
			bFlag = false;
			break;
		}		
		if ( bFlag  ){
			byte[]		lpBuf = new byte[1];
			
			Arrays.fill(lpBuf, (byte)0 );
			oData = new Data();
			nDEUIDLen = strDEUID.getBytes().length;
			oData.MallocBuf(50);
			AddSycHeadCode( oData, (byte)(DECMDData.MT_DELIVERY&0xFF) );
			AddTialPackageNum( oData, (byte)((nDEUIDLen+2)&0xFF) , nSequnce );
			AddDEUID( oData, (byte)(nDEUIDLen&0xFF), strDEUID.getBytes());
			AddDataType(oData,  nTmpCmdType, (byte)0, lpBuf );
			nCheckSum = CountVerifySum( oData.GetDataBuf(), oData.GetDataLen() );
			oData.AddData( nCheckSum );
			m_nDataType = MacroDefinitions.TYPE_TECMD;
			m_lstSendData.add(oData);	
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//����ָ��	
	void	ComposeCtrlTEOilWay( byte 	    nSequnce, 
								 String		strDEUID, 
							     byte		nCmdType ){
		Data		oData = null;
		byte		nCheckSum = 0;
		byte		nDEUIDLen = 0;
		byte[]		lpBuf = new byte[2];
		
		Arrays.fill(lpBuf, (byte)0);
		oData = new Data();
		nDEUIDLen = (byte)(strDEUID.getBytes().length & 0xFF);	
		//////////////////////////////////////////////////////////////////////////
		oData.MallocBuf(50);
		AddSycHeadCode(oData, (byte)(DECMDData.MT_DELIVERY&0xFF) );
		AddTialPackageNum( oData, (byte)(nDEUIDLen+3), nSequnce );
		AddDEUID( oData,nDEUIDLen, strDEUID.getBytes() );
		
		lpBuf[0] = nCmdType;
		AddDataType(oData, (byte)(DECMDData.CC_TE_CTRLOIL&0xFF), (byte)1, lpBuf );
		nCheckSum = CountVerifySum( oData.GetDataBuf(), oData.GetDataLen() );
		oData.AddData( nCheckSum );
		m_nDataType = MacroDefinitions.TYPE_TECMD;
		m_lstSendData.add( oData );
	}
	//////////////////////////////////////////////////////////////////////////
	//�绰����
	void	ComposeCtrlListen(  byte 		  nSequnce, 
								String		  strDEUID, 
								String		  strPhone ){
	
		Data			oData = null;
		byte			nCheckSum = 0;
		byte			nDEUIDLen = 0;
		byte			nLen = 0;
		
		nLen = (byte)(strPhone.getBytes().length & 0xFF);
		oData = new Data();
		nDEUIDLen =(byte)(strDEUID.getBytes().length & 0xFF);
		//////////////////////////////////////////////////////////////////////////
		oData.MallocBuf(50);
		AddSycHeadCode( oData, (byte)(DECMDData.MT_DELIVERY&0xFF) );
		AddTialPackageNum( oData, (byte)(nDEUIDLen+3+nLen), nSequnce );
		AddDEUID( oData, nDEUIDLen, strDEUID.getBytes() );
		AddDataType( oData,  (byte)(DECMDData.CC_TE_LISTEN&0xFF), nLen, strPhone.getBytes() );
		nCheckSum = CountVerifySum( oData.GetDataBuf(), oData.GetDataLen() );
		oData.AddData( nCheckSum );
		m_nDataType = MacroDefinitions.TYPE_TECMD;
		m_lstSendData.add( oData );
	}
	//////////////////////////////////////////////////////////////////////////
	//�绰ͨ��
	void	ComposeCtrlTalk( byte 	   nSequnce, 
							 String	   strDEUID, 
							 String	   strPhone ){
	
		Data			oData = null;
		byte			nCheckSum = 0;
		byte			nDEUIDLen = 0;
		byte			nLen = 0;
		
		nLen = (byte)(strPhone.getBytes().length&0xFF);
		oData = new Data();
		nDEUIDLen = (byte)(strDEUID.getBytes().length&0xff);
		//////////////////////////////////////////////////////////////////////////
		oData.MallocBuf(50);
		AddSycHeadCode( oData, (byte)(DECMDData.MT_DELIVERY&0xFF) );
		AddTialPackageNum( oData, (byte)(nDEUIDLen+3+nLen), nSequnce );
		AddDEUID( oData, nDEUIDLen, strDEUID.getBytes() );
		AddDataType( oData, (byte)(DECMDData.CC_TE_TALK&0xFF), nLen, strPhone.getBytes() );
		nCheckSum = CountVerifySum( oData.GetDataBuf(), oData.GetDataLen() );
		oData.AddData( nCheckSum );
		m_nDataType = MacroDefinitions.TYPE_TECMD;
		m_lstSendData.add( oData );
	}
	//////////////////////////////////////////////////////////////////////////
	//��/�س���
	void	ComposeCtrlDoor( byte		 nSequnce, 
							 String		 strDEUID, 
							 byte 		 nCmdType) {
	
		Data				oData = null;
		byte				nCheckSum = 0;
		byte				nDEUIDLen = 0;
		byte[]				lpBuf = new byte[2];
		
		Arrays.fill(lpBuf, (byte)0 );
		oData = new Data();
		nDEUIDLen = (byte)(strDEUID.getBytes().length & 0xFF);
		//////////////////////////////////////////////////////////////////////////
		oData.MallocBuf(100);
		AddSycHeadCode( oData, (byte)(DECMDData.MT_DELIVERY&0xFF) );
		AddTialPackageNum( oData, (byte)(nDEUIDLen+3), nSequnce );
		AddDEUID( oData, nDEUIDLen, strDEUID.getBytes() );
		lpBuf[0] = nCmdType;
		AddDataType( oData, (byte)(DECMDData.CC_TE_CTRLDOOR&0xFF), (byte)1, lpBuf );
		nCheckSum = CountVerifySum( oData.GetDataBuf(), oData.GetDataLen() );
		oData.AddData( nCheckSum );
		m_nDataType = MacroDefinitions.TYPE_TECMD;
		m_lstSendData.add( oData );
	}
	//********************************************
	//
	public  boolean ParseData( byte[] lpBuf,  int  nLen ){
		
		boolean  		bResult = true;
		byte			nMessageType;

		nMessageType = 	lpBuf[WHERE_MESSAGE_TYPE];
		// ��������		
		switch( nMessageType&0x7F ){	
		case DECMDData.MT_LOGIN:  	  // �յ���½Ӧ������
			ParseLogin( lpBuf );
			break;	
		case DECMDData.MT_HANDSHAKE:  // ֯֯������Ϣ	
			ParseHandShake( lpBuf );
			break;
		case DECMDData.MT_SUBMIT:     //��֯GPS����	
			ParseSubmit( lpBuf );
			break;	
		default:
			bResult = false;
		}
		return bResult;
	}
	//***************************************************************
	//  �յ���½Ӧ������
	public  void  ParseLogin( byte[] lpBuf ){
		
	}
	//***************************************************************
	//	֯֯������Ϣ
	public  void  ParseHandShake( byte[] lpBuf ){
		
	}
	//***************************************************************
	//	��֯GPS����	
	public  void  ParseSubmit( byte[] lpBuf ){
				
		byte	nMesageCmd;
		byte	nMesageCmdLen;
		byte	nMessageType;
		
		byte	nTotalLenght = 0;
		byte	nSeqNum = 0;	
		byte	nDEUIDLen = 0;
		byte	nTotalPacks;
		byte	nStartPackLen = 0;
		byte	nPos = 0;
		int		nData = 0;
		
		byte[]	nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(nDEUID, (byte)0);
		nMessageType = 	lpBuf[WHERE_MESSAGE_TYPE];	
		nTotalLenght = lpBuf[WHERE_MESSAGE_TOTAL_LENGHT];
		nSeqNum = lpBuf[WHERE_MESSAGE_SEQNUM];	
		nDEUIDLen = lpBuf[WHERE_MESSAGE_DEUIDLEN];
		if( nDEUIDLen > Structs.TEXT_DEUID_LEN ){
			nDEUIDLen = Structs.TEXT_DEUID_LEN-1;
		}
		System.arraycopy(lpBuf, WHERE_MESSAGE_DEUID, nDEUID, 0, nDEUIDLen );
		nDEUID[nDEUIDLen] = 0;
		setDEUID( AppData.ByteToString(nDEUID) );
		
		// ���һ�������ɶ�����
		if( (nMessageType & MULTI_PACKAGE) > 0 ){	
			 // �ܰ���
			 nPos = 0;
			 nTotalPacks = lpBuf[WHERE_MESSAGE_DEUIDLEN+nDEUIDLen+1];
			 nStartPackLen = (byte)(nDEUIDLen+2+WHERE_MESSAGE_DEUIDLEN);
			 for ( int nCnt = 0; nCnt < nTotalPacks; nCnt++ ){

				 nMesageCmd = lpBuf[nStartPackLen+nPos];
				 nMesageCmdLen = lpBuf[nStartPackLen+nPos+1];
				 switch ( nMesageCmd ){
				 case DECMDData.CC_GPS_STATE:			// GPSλ������
					 m_nDataType = MacroDefinitions.TYPE_GPSDATA;
					 ParseGPSData( lpBuf, nStartPackLen+nPos+1 );
				 	break;
				 case DECMDData.CC_BLIND_GPS_STATE:  //  ä�����ݻش�
					 m_nDataType = MacroDefinitions.TYPE_BLINDGPSDATA;
					 ParseGPSData( lpBuf, nStartPackLen+nPos+1 );
					 break;
				 case DECMDData.CC_IMAGE_DATA:		// �ϴ�ͼ������
				//	 ParseImageData( &lpBuf[nStartPackLen+nPos+1], nData );
					 break;
				 case DECMDData.CC_UTC_TIME:			// ����ʱ��
				//	 nData = ParseImageTime( &lpBuf[nStartPackLen+nPos+1] );
					 break;
				 case DECMDData.CC_OIL_RISE:			// ��������
				//	 ParseRasData( &lpBuf[nStartPackLen+nPos+1] );
					 break;
				 case DECMDData.CC_TEMPERATURE_DATA:
				//	 ParseTemperatureData( &lpBuf[nStartPackLen+nPos+1] );
					break;
				 }
				 // ������(1)+����(1)+��������(n)
				 nPos += nMesageCmdLen+2;
			 }
		}
		else{
			// ֮ǰ��Ԥ������ݰ�	
			nMesageCmd = lpBuf[WHERE_MESSAGE_DEUIDLEN+nDEUIDLen+1];
			switch ( nMesageCmd ){
			case DECMDData.CC_GPS_STATE:			//GPSλ������
				m_nDataType = MacroDefinitions.TYPE_GPSDATA;
				ParseGPSData( lpBuf , WHERE_MESSAGE_DEUIDLEN+nDEUIDLen+2 );	
				break;
			case DECMDData.CC_TE_QUERY_GG_ADDR_CN: //��ѯ���ĵ�ַ	
				//m_nGPSData = MacroDefinitions.TYPE_QUERY_GG_ADDR_CN;
				m_nDataType = MacroDefinitions.TYPE_GPSDATA;
				ParseGPSData( lpBuf, WHERE_MESSAGE_DEUIDLEN+nDEUIDLen+2 );
				break;
			case DECMDData.CC_TE_QUERY_GG_ADDR_EN: //��ѯӢ�ĵ�ַ
				//m_nGPSData = MacroDefinitions.TYPE_QUERY_GG_ADDR_EN;
				m_nDataType = MacroDefinitions.TYPE_GPSDATA;
				ParseGPSData( lpBuf,  WHERE_MESSAGE_DEUIDLEN+nDEUIDLen+2 );
				break;
			}
		}
	}		
	//***************************************************************
	//
	public  void  ParseGPSData( byte[]  lpBuf, int  nPos ){
			
		byte		nYe = 0;
		byte		nMo = 0;
		byte		nDa = 0;
		byte		nHu = 0;
		byte		nMi = 0;
		byte		nSe = 0;
		byte		GPSLen = 0;
		int			nDataLen = 0;
		int			nData = 0;
		GPSData		oGPSData = new GPSData();
		
		nDataLen = nPos;	//��¼Buf��ʼ����λ��
		
		GPSLen = lpBuf[nDataLen++];
		
		nYe = 	 lpBuf[nDataLen++];
		nMo = 	 lpBuf[nDataLen++];
		nDa = 	 lpBuf[nDataLen++];
		nHu = 	 lpBuf[nDataLen++];
		nMi = 	 lpBuf[nDataLen++];
		nSe = 	 lpBuf[nDataLen++];
		if ((nMo <= 0) || (nMo >= 13)){ //(0-11)
			nMo = 0;
		}
		else{
			nMo -= 1;
		}
		if ((nDa <= 0) || (nDa >= 32)){ //(1-31)
			nDa =1;
		}		
		if ((nHu < 0) || (nHu >= 24)){ //(0-23)
			nHu = 0;
		}
		if ((nMi <= 0) || (nMi >= 60)){ 
			nMi = 1;
		}
		if ((nSe <= 0) || (nSe >= 60)){
			nSe = 1;
		}	
		if ( nYe > 20 ){
			nYe = 1;
		}
		nYe += 100;		
				
		Date  oDate = new Date(nYe,nMo,nDa,nHu,nMi,nSe );
		long  lTimeZone = TimeZone.getDefault().getRawOffset();
		long  lMilli = oDate.getTime()+lTimeZone;
		oDate.setTime( lMilli );
		oGPSData.setGPSTime( (int)(oDate.getTime()/1000) );		
		// ��λ��־
		oGPSData.setGPSValid( lpBuf[nDataLen++] );
		
		// ����
		nData = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										lpBuf[nDataLen+2], 
										lpBuf[nDataLen+1], 
										lpBuf[nDataLen] );
		oGPSData.setLon( (double)nData/3600000 );
		nDataLen += 4;
		
		// γ��
		nData = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										lpBuf[nDataLen+2], 
										lpBuf[nDataLen+1], 
										lpBuf[nDataLen] );
		oGPSData.setLat( (double)nData/3600000) ;
		nDataLen += 4;
		
		// ����
		nData = AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		oGPSData.setDirection( (short)nData );
		nDataLen += 2;
		
		// �ٶ�
		oGPSData.setSpeed( lpBuf[nDataLen++] );
		
		// Ӳ��״̬
		nData = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										lpBuf[nDataLen+2], 
										lpBuf[nDataLen+1], 
										lpBuf[nDataLen] );
		oGPSData.setHWState( nData );
		nDataLen += 4;
		
		// ����״̬
		nData = AppData.ByteValueToInt( lpBuf[nDataLen+3], 
										lpBuf[nDataLen+2], 
										lpBuf[nDataLen+1], 
										lpBuf[nDataLen] );
		oGPSData.setAlarmState( nData );
		nDataLen += 4;
		
		// ����ʱ��
		nData = AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		oGPSData.setDriverTime( (short)nData );
		nDataLen += 2;
		
		oGPSData.setCodeState( lpBuf[nDataLen] );
		oGPSData.setDEUID( getDEUID() );
		
		m_lstGPSData.add(oGPSData);
		Log.e("GPS", "�ɹ�");
	}
	//**************************************************************
	//	�������
	public  boolean  ParseMingData( String   strMing, String  strDEUID ){
		
		boolean 	bResult = false;
		int			nLen = 0;
		String		strData;
		String		strTmp;
		GPSData		oGPSData = null;
		
		if( (strMing.charAt(0) >= 0x30 && 
			 strMing.charAt(0) <= 0x39) ||
			 strMing.charAt(0) == '+' ||
			 strMing.charAt(0) == '-'){
			
			Log.e("1", "0");
			oGPSData = new GPSData();
			oGPSData.setDEUID( strDEUID );
			Log.e("1", "1");
			nLen = strMing.indexOf(',');
			if( nLen == -1 ){
				return bResult;
			}
			Log.e("1", "2");
			strTmp = strMing.substring(0, nLen);
			oGPSData.setLat( Double.parseDouble(strTmp) );
			Log.e("1", "3");
			strData = strMing.substring(nLen+1);
			nLen = strData.indexOf(',');
			if( nLen == -1 ){
				return bResult;
			}
			Log.e("1", "4");
			strTmp = strData.substring(0, nLen );
			oGPSData.setLon( Double.parseDouble(strTmp) );
			if( oGPSData.getLon() > 0.0 &&
				oGPSData.getLat() > 0.0){				
				oGPSData.setGPSValid((byte)1);
			}
			Log.e("1", "5");
			
			strData = strData.substring( nLen+1 );
			nLen = strData.indexOf("km/h");
			if( nLen != -1 ){
				strTmp = strData.substring(0, nLen);				
				Log.e("1", strTmp );				
				oGPSData.setSpeed( (byte)(Integer.parseInt(strTmp)&0xFF) );
			
				strData = strData.substring( nLen + 4 );
				nLen = strData.indexOf(',');
				if( nLen >= 0  ){
					
					strData = strData.substring( nLen+1 );
					oGPSData.setAlarmState( ParseMingAlarmData( strData ) );
				}
			}
			oGPSData.setGPSTime( (int)(System.currentTimeMillis()/1000) );
			m_nDataType = MacroDefinitions.TYPE_GPSDATA;
			m_lstGPSData.add(oGPSData);
			bResult = true;
		}
		return  bResult;
	}
	//**************************************************************
	//	�������뱨��״̬
	public int  ParseMingAlarmData( String  strData ){
		
		String  strTmp;
		int		nAlarmState = 0;
		int		nMid = 0;
		
		strTmp = strData.toUpperCase();
		nMid = strTmp.indexOf("ACTIVATION1");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM1;
		}
		nMid = strTmp.indexOf("�Զ���1");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM1;
		}
		nMid = strTmp.indexOf("POWER SUPPLY DISCONNECTED");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_POWER_OFF;
		}
		nMid = strTmp.indexOf("�ϵ籨��");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_POWER_OFF;
		}
		nMid = strTmp.indexOf("VEHICLE PARKING ALARM");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_PARKING;
		}
		nMid = strTmp.indexOf("ͣ������");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_PARKING;
		}
		nMid = strTmp.indexOf("CAR DOOR OPENED");
		if ( nMid  != -1 ){
			nAlarmState |= AlarmState.ALARM_ILL_DOOROPEN;
		}	
		nMid = strTmp.indexOf("��������");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_ILL_DOOROPEN;
		}
		nMid = strTmp.indexOf("OVERSPEED ALARM");
		if ( nMid  != -1 ){
			nAlarmState |= AlarmState.ALARM_OVERSPEED;
		}
		nMid = strTmp.indexOf("���ٱ���");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_OVERSPEED;
		}
		nMid = strTmp.indexOf("TOWING ALARM");
		if ( nMid  != -1 ){
			nAlarmState |= AlarmState.ALARM_TOW;
		}
		nMid = strTmp.indexOf("�ϳ�����");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_TOW;
		}
		nMid = strTmp.indexOf("EMERGENCY");
		if ( nMid  != -1 ){
			nAlarmState |= AlarmState.ALARM_SOS;
		}
		nMid = strTmp.indexOf("SOS");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_SOS;
		}
		nMid = strTmp.indexOf("��������");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_SOS;
		}
		nMid = strTmp.indexOf("OVERSPEED");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_OVERSPEED;
		}
		nMid = strTmp.indexOf("PARKING");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_PARKING;
		}
		nMid = strTmp.indexOf("TOWING");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_TOW;
		}
		nMid = strTmp.indexOf("IN GEOFENCE");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_IN_AREA;
		}
		nMid = strTmp.indexOf("��籨��");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_IN_AREA;
		}
		nMid = strTmp.indexOf("OUT GEOFENCE");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_OUT_AREA;
		}
		nMid = strTmp.indexOf("���籨��");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_OUT_AREA;
		}
		nMid = strTmp.indexOf("MAIN POWER OFF");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_POWER_OFF;
		}
		nMid = strTmp.indexOf("LOW VOLTATE");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_LOW_POWER;
		}
		nMid = strTmp.indexOf("�͵�ѹ����");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_LOW_POWER;
		}
		nMid = strTmp.indexOf("ANTISTEAL");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_ILL_DOOROPEN;
		}
		nMid = strTmp.indexOf("TIRED DRIVE");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_TRIED_DRIVE;
		}
		nMid = strTmp.indexOf("ƣ�ͼ�ʻ����");
		if ( nMid != -1 ){
			return nAlarmState |= AlarmState.ALARM_TRIED_DRIVE;
		}
		nMid = strTmp.indexOf("CUSTOM2");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM2;
		}
		nMid = strTmp.indexOf("CUSTOM3");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM3;
		}
		nMid = strTmp.indexOf("CUSTOM4");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM4;
		}
		nMid = strTmp.indexOf("�Զ���2");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM2;
		}
		nMid = strTmp.indexOf("�Զ���3");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM3;
		}
		nMid = strTmp.indexOf("�Զ���4");
		if ( nMid != -1 ){
			nAlarmState |= AlarmState.ALARM_CUSTOM4;
		}
		return  nAlarmState;
	}
	
	//***************************************************************
	//
	public class DECMDData{
		//=====Ӳ����ͷ�����=============================================================================
		public static final int	MT_LOGIN = 0x02;
		public static final int	MT_LOGIN_ACK = 0x03;
		public static final int	MT_DELIVERY = 0x04;
		public static final int	MT_SUBMIT = 0x05;
		public static final int	MT_HANDSHAKE = 0x06;
		public static final int	MT_ACK = 0x07;
		
		//=====Ӳ����������붨��============================================================================
		public  static final  int CC_HW_ERROR = 0x01;					//0.	������	
		public  static final  int CC_HW_SETUP = 0x02;					//2.	Ӳ��֧����� 	        (MT_LOGIN)
		public  static final  int CC_SW_SETUP = 0x03 ;						//3.	���������� 	        (MT_LOGIN = 0x ;MT_SUBMIT)
		public  static final  int CC_DE_STATE = 0x04 ;						//4.	�ն�״̬ 		        (MT_SUBMIT)
		public  static final  int CC_GPS_STATE = 0x05 ;						//5.	GPS��λ��Ϣ		        (MT_SUBMIT)
		public  static final  int CC_TE_QUERY_SW_SETUP = 0x06 ;				//6.	��ȡ���������Ϣ        (MT_ DELIVERY)
		public  static final  int CC_TE_CTRLOIL = 0x07 ;						//7.	�ر�/�����·��Ϣ       (MT_ DELIVERY)
		public  static final  int CC_TE_CTRLDOOR = 0x08 ;						//8.	��/�� ����		        (MT_ DELIVERY)	
		public  static final  int CC_TE_CALLONCE = 0x09 ;						//9.	��������: (�����ش�������Ϣ) 				   (MT_ DELIVERY)
		public  static final  int CC_TE_LISTEN = 0x0A ;						//10.	�绰����: (�򿪳����ն˻�Ͳ��Զ�̼������ڶ���) (MT_ DELIVERY)
		public  static final  int CC_TE_TALK = 0x0B ;							//11.	�绰ͨ��: (�򿪳����ն˻�Ͳ��Զ�̺���) 		   (MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_UPLOADMODE = 0x0C ; 			//12.	���������ϴ�ģʽ 	    (MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_NUMBER = 0x0D ;					//13.	���ø��ֺ���		    (MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_SERVER = 0x0E ;					//14.	���÷�����   		    (MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_ALARM = 0x0F ;					//15.	��������			    (MT_ DELIVERY)
		public  static final  int CC_TE_RESET = 0x10 ;						//16.	�ն˸�λ		        (MT_ DELIVERY)
		public  static final  int CC_TE_RESTORE_FACTORY = 0x11 ;				//17.	��㹳�������		    (MT_ DELIVERY)	
		public  static final  int CC_TE_FENCE = 0x12 ;						//18.   ���õ���Χ��			(MT_ DELIVERY)
		public  static final  int CC_GSM_STATE = 0x13 ;						//19.	GSM��λ��Ϣ	    		(MT_SUBMIT)
		public  static final  int CC_TE_HANDSET_INFO = 0x14 ;					//20.   ���͵�����Ϣ			(MT_ DELIVERY)	
		public  static final  int CC_TE_AD_INFO = 0x15 ;						//21.	���͹������Ϣ			(MT_ DELIVERY)	
		public  static final  int CC_TE_TAKEPHOTO = 0x16 ;					//22.   ������Ϣ				(MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_CARTURE = 0x17 ;				//23.   ����������Ϣ			(MT_ DELIVERY)
		public  static final  int CC_TE_VOICE_INFO = 0x18 ;					//24    ��������				(MT_ DELIVERY)
		public  static final  int CC_IMAGE_DATA = 0x19 ;						//25    �ϴ�ͼ������		    (MT_SUBMIT)
		public  static final  int CC_UTC_TIME = 0x1A ;						//26    �ϴ�ͼ��ʱ��			(MT_SUBMIT)
		public  static final  int CC_TE_IMAGE_RESULT = 0x1B ;					//27    ͼ�񲹰�				
		public  static final  int CC_TE_REQUIRE_SESSION_RESULT = 0x1C ;		//28    ����ͼ���������		(MT_SUBMIT)	
		public  static final  int CC_TE_SETUP_CONSUMPTION = 0x1D;		    //29	���ÿ̶�ֵ��Ӧ����	    (MT_ DELIVERY)
		public  static final  int CC_OIL_RISE = 0x1E ;						//30    �ϴ�������������		(MT_SUBMIT)
		public  static final  int CC_TE_SETUP_GAS = 0x1F ;					//31    ������������			(MT_ DELIVERY)
		public  static final  int CC_BLIND_GPS_STATE = 0x20 ;					//32	����ä������			(MT_SUBMIT)
		public  static final  int CC_TE_QUERY_GG_ADDR_CN = 0x21 ;				//33    ��ѯ���ĵ�ַ����		(MT_SUBMIT)
		public  static final  int CC_TE_QUERY_GG_ADDR_EN = 0x22 ;				//34    ��ѯӢ�ĵ�ַ����		(MT_SUBMIT)
		public  static final  int CC_TE_RESULT_GG_ADDR = 0x23 ;				//35	���ص�ַ����			(MT_DELIVERY)
		public  static final  int CC_TEMPERATURE_DATA = 0x24 ;				//36    �¶�����				(MT_SUBMIT)
		public  static final  int CC_TE_SETUP_CONSUMPTION_NEW = 0x25 ;		//37    (�¼� = 0x ;����Ϊ���ֽ�)���ÿ̶�ֵ��Ӧ����	    (MT_ DELIVERY)
		public  static final  int CC_TE_SETUP_GAS_NEW = 0x26 ;				//38    (�¼������������ֽ�)������������ֵ
	}
}
