package com.Protocol;

import com.Language.Language;

//******************************************************************
//硬件状态
//
public class  HWState{
	
	//***********************************************
	//  控制命令
	public static  String getControlStr( int nCodeState ){
		
		byte		nCmdCode = 0;
		boolean		bState = true;
		boolean		bSucceed = false;
		String		strResult = new String();
		
		
		nCmdCode = (byte) (nCodeState & 0x7F);
		if( (nCodeState & 0x80) == 0x80 ){
			bSucceed = true;
		}
		switch( nCmdCode ) {
		case  ACKCmdCode.ACK_OLD_OIL:			//7.	关闭/恢愎油路信息       (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_CONTROL)+" "+Language.getLangStr(Language.TEXT_OIL_WAY);				
			break;
		case  ACKCmdCode.ACK_OLD_DOOR:			//8.	开/关 车门		        (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_CONTROL)+" "+Language.getLangStr(Language.TEXT_DOOR);
			break;
		case  ACKCmdCode.ACK_LOCATION:			//9.	点名呼叫: (立即回传最新信息) 				   (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_TRACK_ONCE);
			break;
		case  ACKCmdCode.ACK_LISTEN:				//10.	电话监听: (打开车载终端话筒，远程监听车内动静) (MT_ DELIVERY)
		case  ACKCmdCode.ACK_TALK:				//11.	电话通话: (打开车载终端话筒，远程喊话) 		   (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_LISTEN_IN);
			break;
		case  ACKCmdCode.ACK_SETUP_MODE: 	//12.	设置数据上传模式 	    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_CB_MODE);
			break;
		case  ACKCmdCode.ACK_SETUP_PHONE:		//13.	设置各种号码		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_TELNO);
			break;
		case  ACKCmdCode.ACK_SETUP_SERVER:		//14.	设置服务器   		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_SERVER_IP);
			break;
		case  ACKCmdCode.ACK_SETUP_ALARM:		//15.	报警设置			    (MT_ DELIVERY) 
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_ALARM);
			break;
		case  ACKCmdCode.ACK_SETUP_RESET:				//16.	终端复位		        (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_DEVICE_RESET);
			break;
		case  ACKCmdCode.ACK_RESTORE_FACTORY:	//17.	恢愎出厂设置		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_RESTORE_FACTORY);
			break;
		case  ACKCmdCode.ACK_SETUP_FENCE:				//18.   设置电子围栏			(MT_ DELIVERY)	
			strResult =Language.getLangStr(Language.TEXT_FENCE_SETUP);
			break;
		case  ACKCmdCode.ACK_DISABLE_OIL:				//断油命令 (new version)	
			strResult =Language.getLangStr(Language.TEXT_FUEL_OFF);
			break;
		case  ACKCmdCode.ACK_ENABLE_OIL:				//恢愎油路(new version)	
			strResult =Language.getLangStr(Language.TEXT_FUEL_ON);	
			break;
		case  ACKCmdCode.ACK_CLOSE_DOOR:				//远程关门(new version)	
			strResult =Language.getLangStr(Language.TEXT_LOCK);	
			break;
		case  ACKCmdCode.ACK_OPEN_DOOR: 				//远程开门(new version)	
			strResult =Language.getLangStr(Language.TEXT_UNLOCK);	
			break;
		case  ACKCmdCode.ACK_HANDSET_INFO:				//20.   发送调度信息
			strResult =Language.getLangStr(Language.TEXT_SMSBOX_INFO);	
			break;
		case  ACKCmdCode.ACK_AD_INFO:					//21.	发送广告屏信息
			bState = false;
			bSucceed = false;
			break;
		case  ACKCmdCode.ACK_TAKEPHOTO:				//22.   拍照信息		
			if( bSucceed == false ){		
				strResult =Language.getLangStr(Language.TEXT_CAPTUREING);	
			}
			else{
				strResult =Language.getLangStr(Language.TEXT_IMAGE_CAPTUREING_PLESASE_WAIT);
				bState = false;
			}
			break;
		case  ACKCmdCode.ACK_SETUP_CARTURE:			//23.   设置拍照信息
			strResult =Language.getLangStr(Language.TEXT_SETUP);
			break;
		case  ACKCmdCode.ACK_VOICE_INFO:				//24.   语音播报信息
			strResult =Language.getLangStr(Language.TEXT_VOICE_BROADCAST);
			break;
		case  ACKCmdCode.ACK_IMAGE_DATA:				//25    上传图像数据	    (MT_SUBMIT)	
			strResult =Language.getLangStr(Language.TEXT_UPLOADING_PLEASE_WAIT);	
			bState = false;
			break;
		case  ACKCmdCode.ACK_IMAGE_RESULT:				//27	传输图像完成		(MT_ DELIVERY)
			bState = false;
			bSucceed = false;
			break;	
		case  ACKCmdCode.ACK_SETUP_GAS:				//31    设置油路报警
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_ALARM_FUEL_LIAK);
			break;
		case ACKCmdCode.ACK_TE_SETUP_CONSUMPTION:
			strResult =Language.getLangStr(Language.TEXT_GAS_RESISTANCE);		
			break;
		case ACKCmdCode.ACK_GENERAL_INFO:		 //32   设置普通信息		
			strResult =Language.getLangStr(Language.TEXT_INFO);	
			break;
		case ACKCmdCode.ACK_INSERTION_INFO:	 //33   马上插播信息		
			break;
		case ACKCmdCode.ACK_EMERGENCY_INFO:	 //34   设置紧急信息		
			break;
		case ACKCmdCode.ACK_REMOVE_INFO:		 //35   删除信息		
			break;
		case ACKCmdCode.ACK_OFF_ON_SETUP:		 //36   设置定时开关机		
			break;
		case ACKCmdCode.ACK_FORCED_SHUTDOWN:	 //37   强制开关机		
			break;
		case ACKCmdCode.ACK_TIME_SETUP:		 //38   设置LED时间
			strResult =Language.getLangStr(Language.TEXT_COREECTION_TIME);	
			break;
		case ACKCmdCode.ACK_SHOW_TIME_SETUP:	 //39   设置时间显示
			strResult =Language.getLangStr(Language.TEXT_CLOCK_SHOW);	
			break;
		case ACKCmdCode.ACK_LEDRESULT_MARK:	  //40  设置LED返回标志		
			break;
		default:	
			bState = false;
			bSucceed = false;
			break;
		}
		if( bState ){
			if( (bSucceed == true) && ( nCmdCode > 0) ){
				strResult +=" ";
				strResult +=Language.getLangStr(Language.TEXT_SUCCEED);
			}
			else if( (nCmdCode > 0) && (bSucceed == false)){
				strResult +=" ";
				strResult += Language.getLangStr(Language.TEXT_FAIL);
			}
		}
		else{
			if ( bSucceed == false ){
				strResult +=  Language.getLangStr(Language.TEXT_CURRENT_POSITION); 
			}
		}		
		return strResult;
	}
	//***********************************************
	// 获取方向
	public static String	getDirection( int	nDirection ){
	
		String  strResult = new String();		//←↑→↓↖↗↘↙
		
		if ( nDirection <= 20 || nDirection >340 ){
			
			strResult = Language.getLangStr(Language.TEXT_NORTH); 
		}
		else if ( nDirection <= 70 ){
			
			strResult = Language.getLangStr(Language.TEXT_NORTH_EAST);
		}
		else if ( nDirection <= 110 ){
			strResult = Language.getLangStr(Language.TEXT_EAST);
		}
		else if ( nDirection <= 160 ){
			strResult = Language.getLangStr(Language.TEXT_SOUTH_EAST);
		}
		else if ( nDirection <= 200 ){
			strResult = Language.getLangStr(Language.TEXT_SOUTH);
		}
		else if ( nDirection <= 250 ){
			strResult = Language.getLangStr(Language.TEXT_SOUTHE_WEST);
		}
		else if ( nDirection <= 290 ){
			strResult = Language.getLangStr(Language.TEXT_WEST);
		}
		else if ( nDirection <= 340 ){
			strResult = Language.getLangStr(Language.TEXT_NORTH_WEST);
		}
		else{
			strResult = Language.getLangStr(Language.TEXT_NORTH);
		}
		return strResult;
	}
	//**************************************************************
	//ACC状态
	public static String		getACCState( int	nHWState ){
	
		String		strResult;
		
		if ( (nHWState & HardwareState.HW_ACC) ==  HardwareState.HW_ACC ){
			strResult =  Language.getLangStr(Language.TEXT_ACC_ON);
		}
		else{
			strResult =  Language.getLangStr(Language.TEXT_ACC_OFF);
		}
		return strResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//门检状态
	public static String		GetDoorState( int	nHWState ){
	
		String		strResult = new String();
		
		if ( (nHWState & HardwareState.HW_DOOR) == HardwareState.HW_DOOR){
			strResult = Language.getLangStr( Language.TEXT_DOOR_ON );
		}
		else{
			strResult = Language.getLangStr( Language.TEXT_DOOR_OFF );
		}
		return strResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//获取电压值
	public static int	GetPowerVoltage( int nHWState ){
	
	
		return ( nHWState >> 24 ) & 0x1F;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public static String GetAlarmString( int nState ){
	
		String		strResult = new String();
		int			nCnt;
		int			nAlarmBit;
		int			nTextAlarm;
		
		nCnt = 0;
		
		while ( true ){
			nAlarmBit = AlarmToString.g_nAlarmString[nCnt][0];
			nTextAlarm = AlarmToString.g_nAlarmString[nCnt][1];
			if( nAlarmBit == -1 ){
				break;
			}
			if( (nState& nAlarmBit) == nAlarmBit ){
				
				//if( strResult.isEmpty() == false ){
				if( strResult.length() > 0 ){
					strResult += "||";
				}
				strResult += Language.getLangStr( nTextAlarm );
			}	
			nCnt++;
		}	
		return strResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	int		GetShowColor( GPSData   oGPSData, int	nOnLine ){
	/*
		int			nColorType = COLOR_ERROR;
		CTime		oTime;
		CTime		oTime2 = CTime::GetCurrentTime();
		
		
		if( pStGPSData->m_nAlarmState > 0 ){
			nColorType = COLOR_ALARM;
		}
		else{
			if( pStGPSData->m_bGPSValid == 0 ){			
				nColorType = COLOR_ONLINE;
			}
			else{
				oTime = CTime( oTime2.GetYear(), 
				oTime2.GetMonth(),
				oTime2.GetDay(),
				oTime2.GetHour(),
				oTime2.GetMinute(),
				oTime2.GetSecond() );
				if( ( oTime - pStGPSData->m_oTime) <= nOnLine ){
					nColorType = COLOR_ONLINE;
				}
				else{
					nColorType = COLOR_OFFLINE;
				}
			}
		}
		return nColorType;*/
		return  0;
	}
}