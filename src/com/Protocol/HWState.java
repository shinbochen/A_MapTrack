package com.Protocol;

import com.Language.Language;

//******************************************************************
//Ӳ��״̬
//
public class  HWState{
	
	//***********************************************
	//  ��������
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
		case  ACKCmdCode.ACK_OLD_OIL:			//7.	�ر�/�����·��Ϣ       (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_CONTROL)+" "+Language.getLangStr(Language.TEXT_OIL_WAY);				
			break;
		case  ACKCmdCode.ACK_OLD_DOOR:			//8.	��/�� ����		        (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_CONTROL)+" "+Language.getLangStr(Language.TEXT_DOOR);
			break;
		case  ACKCmdCode.ACK_LOCATION:			//9.	��������: (�����ش�������Ϣ) 				   (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_TRACK_ONCE);
			break;
		case  ACKCmdCode.ACK_LISTEN:				//10.	�绰����: (�򿪳����ն˻�Ͳ��Զ�̼������ڶ���) (MT_ DELIVERY)
		case  ACKCmdCode.ACK_TALK:				//11.	�绰ͨ��: (�򿪳����ն˻�Ͳ��Զ�̺���) 		   (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_LISTEN_IN);
			break;
		case  ACKCmdCode.ACK_SETUP_MODE: 	//12.	���������ϴ�ģʽ 	    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_CB_MODE);
			break;
		case  ACKCmdCode.ACK_SETUP_PHONE:		//13.	���ø��ֺ���		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_TELNO);
			break;
		case  ACKCmdCode.ACK_SETUP_SERVER:		//14.	���÷�����   		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_SERVER_IP);
			break;
		case  ACKCmdCode.ACK_SETUP_ALARM:		//15.	��������			    (MT_ DELIVERY) 
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_ALARM);
			break;
		case  ACKCmdCode.ACK_SETUP_RESET:				//16.	�ն˸�λ		        (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_DEVICE_RESET);
			break;
		case  ACKCmdCode.ACK_RESTORE_FACTORY:	//17.	��㹳�������		    (MT_ DELIVERY)
			strResult =Language.getLangStr(Language.TEXT_RESTORE_FACTORY);
			break;
		case  ACKCmdCode.ACK_SETUP_FENCE:				//18.   ���õ���Χ��			(MT_ DELIVERY)	
			strResult =Language.getLangStr(Language.TEXT_FENCE_SETUP);
			break;
		case  ACKCmdCode.ACK_DISABLE_OIL:				//�������� (new version)	
			strResult =Language.getLangStr(Language.TEXT_FUEL_OFF);
			break;
		case  ACKCmdCode.ACK_ENABLE_OIL:				//�����·(new version)	
			strResult =Language.getLangStr(Language.TEXT_FUEL_ON);	
			break;
		case  ACKCmdCode.ACK_CLOSE_DOOR:				//Զ�̹���(new version)	
			strResult =Language.getLangStr(Language.TEXT_LOCK);	
			break;
		case  ACKCmdCode.ACK_OPEN_DOOR: 				//Զ�̿���(new version)	
			strResult =Language.getLangStr(Language.TEXT_UNLOCK);	
			break;
		case  ACKCmdCode.ACK_HANDSET_INFO:				//20.   ���͵�����Ϣ
			strResult =Language.getLangStr(Language.TEXT_SMSBOX_INFO);	
			break;
		case  ACKCmdCode.ACK_AD_INFO:					//21.	���͹������Ϣ
			bState = false;
			bSucceed = false;
			break;
		case  ACKCmdCode.ACK_TAKEPHOTO:				//22.   ������Ϣ		
			if( bSucceed == false ){		
				strResult =Language.getLangStr(Language.TEXT_CAPTUREING);	
			}
			else{
				strResult =Language.getLangStr(Language.TEXT_IMAGE_CAPTUREING_PLESASE_WAIT);
				bState = false;
			}
			break;
		case  ACKCmdCode.ACK_SETUP_CARTURE:			//23.   ����������Ϣ
			strResult =Language.getLangStr(Language.TEXT_SETUP);
			break;
		case  ACKCmdCode.ACK_VOICE_INFO:				//24.   ����������Ϣ
			strResult =Language.getLangStr(Language.TEXT_VOICE_BROADCAST);
			break;
		case  ACKCmdCode.ACK_IMAGE_DATA:				//25    �ϴ�ͼ������	    (MT_SUBMIT)	
			strResult =Language.getLangStr(Language.TEXT_UPLOADING_PLEASE_WAIT);	
			bState = false;
			break;
		case  ACKCmdCode.ACK_IMAGE_RESULT:				//27	����ͼ�����		(MT_ DELIVERY)
			bState = false;
			bSucceed = false;
			break;	
		case  ACKCmdCode.ACK_SETUP_GAS:				//31    ������·����
			strResult =Language.getLangStr(Language.TEXT_SETUP)+" "+Language.getLangStr(Language.TEXT_ALARM_FUEL_LIAK);
			break;
		case ACKCmdCode.ACK_TE_SETUP_CONSUMPTION:
			strResult =Language.getLangStr(Language.TEXT_GAS_RESISTANCE);		
			break;
		case ACKCmdCode.ACK_GENERAL_INFO:		 //32   ������ͨ��Ϣ		
			strResult =Language.getLangStr(Language.TEXT_INFO);	
			break;
		case ACKCmdCode.ACK_INSERTION_INFO:	 //33   ���ϲ岥��Ϣ		
			break;
		case ACKCmdCode.ACK_EMERGENCY_INFO:	 //34   ���ý�����Ϣ		
			break;
		case ACKCmdCode.ACK_REMOVE_INFO:		 //35   ɾ����Ϣ		
			break;
		case ACKCmdCode.ACK_OFF_ON_SETUP:		 //36   ���ö�ʱ���ػ�		
			break;
		case ACKCmdCode.ACK_FORCED_SHUTDOWN:	 //37   ǿ�ƿ��ػ�		
			break;
		case ACKCmdCode.ACK_TIME_SETUP:		 //38   ����LEDʱ��
			strResult =Language.getLangStr(Language.TEXT_COREECTION_TIME);	
			break;
		case ACKCmdCode.ACK_SHOW_TIME_SETUP:	 //39   ����ʱ����ʾ
			strResult =Language.getLangStr(Language.TEXT_CLOCK_SHOW);	
			break;
		case ACKCmdCode.ACK_LEDRESULT_MARK:	  //40  ����LED���ر�־		
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
	// ��ȡ����
	public static String	getDirection( int	nDirection ){
	
		String  strResult = new String();		//���������I�J�K�L
		
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
	//ACC״̬
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
	//�ż�״̬
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
	//��ȡ��ѹֵ
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