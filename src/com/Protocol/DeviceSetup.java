package com.Protocol;

import  java.util.Arrays;


//**************************************************************
//终端配置信息
public	class  DeviceSetup{	
	
	public 	byte[]	 m_nDEUID;
	public  byte[]   m_nServerIP;
	public  byte[]   m_nArea1;
	public  byte[]   m_nArea2;
	
	public  byte	 m_nWorkMode;			//工作模式
	public  byte     m_nTrackMode;			//跟踪模式
	public  byte	 m_nOverspeed;			//超速里程
	public  byte	 m_nTiredDriveTime;		//设备运行时间
	public  byte	 m_nIllDoorOpenTime;	//车门打开时间
	public  short	 m_nParkingTime;		//停车时间	 
	public  short	 m_nTrackSpace;			//跟踪间隔
	public  short	 m_nServerPort;			//服务器端口号
	public  int		 m_nHWValid;			//硬件状态
	public  int		 m_nALValid;			//报警状态	
	public  int		 m_nALEnable;			//报警开启状态
	public  int		 m_nGas;				//油量
	
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
		
		m_nWorkMode = 0;			//工作模式
		m_nTrackMode = 0;			//跟踪模式
		m_nOverspeed = 0;			//超速里程
		m_nTiredDriveTime = 0;		//设备运行时间
		m_nIllDoorOpenTime = 0;		//车门打开时间
		m_nParkingTime = 0;			//停车时间	 
		m_nTrackSpace = 0;			//跟踪间隔
		m_nServerPort = 0;			//服务器端口号
		m_nHWValid = 0;				//硬件状态
		m_nALValid = 0;				//报警状态	
		m_nALEnable = 0;			//报警开启状态
		m_nGas = 0;					//油量
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
		
		nDataLen++;  //DeviceSetup结构体对齐
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
Log.e("配置信息",   AppData.ByteToString(m_nDEUID)+" "+ 
				    AppData.ByteToString(m_nServerIP)+ " "+ 
					AppData.ByteToString(m_nArea1)+" "+
					AppData.ByteToString(m_nArea2) );

String  str = new String();
for( int nCnt = nDataLen; nCnt < nLen; nCnt++ ){
	
	str += Integer.toHexString( lpBuf[nCnt] );
	str += " ";
}
Log.e( "配置HEX", str );
*/
