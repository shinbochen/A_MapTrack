package com.Protocol;

import  java.util.Arrays;


//**************************************************************
//�ն�������Ϣ
public	class  DeviceSetup{	
	
	public 	byte[]	 m_nDEUID;
	public  byte[]   m_nServerIP;
	public  byte[]   m_nArea1;
	public  byte[]   m_nArea2;
	
	public  byte	 m_nWorkMode;			//����ģʽ
	public  byte     m_nTrackMode;			//����ģʽ
	public  byte	 m_nOverspeed;			//�������
	public  byte	 m_nTiredDriveTime;		//�豸����ʱ��
	public  byte	 m_nIllDoorOpenTime;	//���Ŵ�ʱ��
	public  short	 m_nParkingTime;		//ͣ��ʱ��	 
	public  short	 m_nTrackSpace;			//���ټ��
	public  short	 m_nServerPort;			//�������˿ں�
	public  int		 m_nHWValid;			//Ӳ��״̬
	public  int		 m_nALValid;			//����״̬	
	public  int		 m_nALEnable;			//��������״̬
	public  int		 m_nGas;				//����
	
	//*******************************************************
	//
	public DeviceSetup(){
		
		m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
		Arrays.fill(m_nDEUID, (byte) 0);
		m_nServerIP = new byte[Structs.TEXT_SERVERIP_LEN];
		Arrays.fill(m_nServerIP, (byte) 0);
		m_nArea1 =  new byte[Structs.TEXT_AREA_LEN];
		Arrays.fill(m_nArea1, (byte) 0);
		m_nArea2 =  new byte[Structs.TEXT_AREA_LEN];
		Arrays.fill(m_nArea2, (byte) 0);
		
		m_nWorkMode = 0;			//����ģʽ
		m_nTrackMode = 0;			//����ģʽ
		m_nOverspeed = 0;			//�������
		m_nTiredDriveTime = 0;		//�豸����ʱ��
		m_nIllDoorOpenTime = 0;		//���Ŵ�ʱ��
		m_nParkingTime = 0;			//ͣ��ʱ��	 
		m_nTrackSpace = 0;			//���ټ��
		m_nServerPort = 0;			//�������˿ں�
		m_nHWValid = 0;				//Ӳ��״̬
		m_nALValid = 0;				//����״̬	
		m_nALEnable = 0;			//��������״̬
		m_nGas = 0;					//����
	}
	//*******************************************************
	//
	public  void  ParseDeviceSetup( byte[] lpBuf, int nLen ){
		
		int			nDataLen = 0;
		
		System.arraycopy(lpBuf, 0, m_nDEUID, 0, Structs.TEXT_DEUID_LEN );
		nDataLen += Structs.TEXT_DEUID_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nServerIP, 0, Structs.TEXT_SERVERIP_LEN );
		nDataLen += Structs.TEXT_SERVERIP_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nArea1, 0, Structs.TEXT_AREA_LEN );
		nDataLen += Structs.TEXT_AREA_LEN;
		
		System.arraycopy(lpBuf, nDataLen, m_nArea2, 0, Structs.TEXT_AREA_LEN );
		nDataLen += Structs.TEXT_AREA_LEN;
	
		
		m_nWorkMode = lpBuf[nDataLen++];
		m_nTrackMode = lpBuf[nDataLen++];
		m_nOverspeed = lpBuf[nDataLen++];
		m_nTiredDriveTime = lpBuf[nDataLen++];
		m_nIllDoorOpenTime = lpBuf[nDataLen++];
		
		nDataLen++;  //DeviceSetup�ṹ�����
		m_nParkingTime = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		nDataLen += 2;
		
		m_nTrackSpace = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		nDataLen += 2;
		
		m_nServerPort = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		nDataLen += 2;	
		
		m_nServerPort = (short)AppData.ByteValueToInt(lpBuf[nDataLen+1], lpBuf[nDataLen]);
		nDataLen += 2;
		
		m_nHWValid = AppData.ByteValueToInt(lpBuf[nDataLen+3], 
												    lpBuf[nDataLen+2],
												    lpBuf[nDataLen+1],
												    lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nALValid = AppData.ByteValueToInt(lpBuf[nDataLen+3], 
												    lpBuf[nDataLen+2],
												    lpBuf[nDataLen+1],
												    lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nALEnable = AppData.ByteValueToInt(lpBuf[nDataLen+3], 
												    lpBuf[nDataLen+2],
												    lpBuf[nDataLen+1],
												    lpBuf[nDataLen]);
		nDataLen += 4;
		
		m_nGas = AppData.ByteValueToInt(lpBuf[nDataLen+3], 
											    lpBuf[nDataLen+2],
											    lpBuf[nDataLen+1],
											    lpBuf[nDataLen]);
		nDataLen += 4;
	}	
	public  String 	GetDEUID(){		
		return AppData.ByteToString(m_nDEUID);
	}
	public  String  getServerIP(){		
		return AppData.ByteToString(m_nServerIP);
	}
}

/*
Log.e("������Ϣ",   AppData.ByteToString(m_nDEUID)+" "+ 
				    AppData.ByteToString(m_nServerIP)+ " "+ 
					AppData.ByteToString(m_nArea1)+" "+
					AppData.ByteToString(m_nArea2) );

String  str = new String();
for( int nCnt = nDataLen; nCnt < nLen; nCnt++ ){
	
	str += Integer.toHexString( lpBuf[nCnt] );
	str += " ";
}
Log.e( "����HEX", str );
*/
