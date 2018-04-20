package com.Protocol;

import com.Language.Language;

public class MacroDefinitions {
	
	 public static final byte  TYPE_ERROR_CODE = 0x00;		//������
     public static final byte  TYPE_USER_ONLY  = 0x01;
     public static final byte  TYPE_GROUPNAME_ONLY= 0x02;
     public static final byte  TYPE_DEUID_ONLY	= 0x03;

     public static final byte  TYPE_LOGIN	= 0x04;					
     public static final byte  TYPE_USERINFO= 0x05;				//��ϸ���û���Ϣ
     public static final byte  TYPE_SUBUSERINFO= 0x06;			//��ϸ�����û���Ϣ
     public static final byte  TYPE_CARINFO= 0x07;				//������Ϣ
     public static final byte  TYPE_GROUPINFO= 0x08;			//������Ϣ
     public static final byte  TYPE_SUBUSERMANAGE= 0x09;		//���û�������Ϣ
     public static final byte  TYPE_GROUPMANAGE= 0x0A;			//���û�������Ϣ
     public static final byte  TYPE_QUERY_CONDITION= 0x0B;
     public static final byte  TYPE_MILEAGEDATA= 0x0C;
     public static final byte  TYPE_GPSDATA= 0x0D;
     public static final byte  TYPE_IMGDATA= 0x0E;
     public static final byte  TYPE_TESETUP= 0x0F;
     public static final byte  TYPE_ATTEMPER= 0x10;
     public static final byte  TYPE_TECMD= 0x11;
     public static final byte  TYPE_SEND_TECMD_RESULT= 0x12;
     public static final byte  TYPE_PHONE_MAMAGE= 0x13;			//�������
				// ASICO ��λָ��; ���ú��������(wyb 2009-10-05)
     public static final byte  TYPE_MODIFY_CODESTATE= 0x14;
     public static final byte  TYPE_BLINDGPSDATA= 0x15;
     public static final byte  TYPE_IMAGEVPATH= 0x16;			//ͼ��ӳ���ַ

     public static final byte  TYPE_RUNDATA= 0x17;
     public static final byte  TYPE_ALARMDATA= 0x18;
     public static final byte  TYPE_GASDATA= 0x19;				//��������
     public static final byte  TYPE_FENCEDATA= 0x1A;			//����Χ������
     public static final byte  TYPE_POIDATA= 0x1B;				//POI��	
     public static final byte  TYPE_NAME= 0x1C;					//����
     public static final byte  TYPE_POIFENCE= 0x1D;	
     public static final byte  TYPE_QUERY_POICO= 0x1E;
     public static final byte  TYPE_QUERY_GG_ADDR_CN= 0x1F;
     public static final byte  TYPE_QUERY_GG_ADDR_EN= 0x20;
     public static final byte  TYPE_TEMPERATURE_DATA= 0x21;		//�¶�����	
     public static final byte  TYPE_QUERY_GG_ADDR_TH= 0x22;     //̩��
}

//******************************************************************
//=====�豸Ӧ������==================================================
//bit7  1/0ִ�гɹ�/ʧ��, 
//bit0~6: ��������
//		0:��
//			1~7: reserved
//		7: ������·(old version)
//		8: Զ���п���(old version)
class ACKCmdCode{
	public static final byte  ACK_OLD_OIL = 0x07;		  		//7.	�ر�/�����·��Ϣ       (MT_ DELIVERY)
	public static final byte  ACK_OLD_DOOR = 0x08;	  		//8.	��/�� ����		        (MT_ DELIVERY)	
	
	public static final byte  ACK_LOCATION = 0x09;	  		//��λ
	public static final byte  ACK_LISTEN = 0x0A;		  		//����
	public static final byte  ACK_TALK=0x0B;				  	//ͨ��
	public static final byte  ACK_SETUP_MODE = 0x0C;			//���������ϴ�ģʽ
	public static final byte  ACK_SETUP_PHONE = 0x0D;		  	//���ú���	
	public static final byte  ACK_SETUP_SERVER = 0x0E;		//���÷�����
	public static final byte  ACK_SETUP_ALARM = 0x0F;		  	//��������
	public static final byte  ACK_SETUP_RESET = 0x10;		  	//�ն˸�λ
	public static final byte  ACK_RESTORE_FACTORY = 0x11;  	//��㹳���	
	public static final byte  ACK_SETUP_FENCE = 0x12;		  	//����Χ��
	
	public static final byte  ACK_HANDSET_INFO = 20;	  		//20.   ���͵�����Ϣ
	public static final byte  ACK_AD_INFO = 21;			  	//21.	  ���͹������Ϣ
	public static final byte  ACK_TAKEPHOTO = 22;			  	//22.   ������Ϣ
	public static final byte  ACK_SETUP_CARTURE = 23;		  	//23.   ����������Ϣ
	public static final byte  ACK_VOICE_INFO = 24;			//24.   ����������Ϣ
	public static final byte  ACK_IMAGE_DATA = 25;			//25.	  ��ʼ�ϴ�ͼ������    
	public static final byte  ACK_IMAGE_RESULT = 27;	  		//27    ͼ�񲹰� 
	public static final byte  ACK_REQUIRE_SESSION_RESULT = 28;	
	public static final byte  ACK_TE_SETUP_CONSUMPTION = 29; 	//29   ���ÿ̶�ֵ��Ӧ����
	public static final byte  ACK_SETUP_GAS = 31;		  		//31   ������·����
	
	public static final byte  ACK_GENERAL_INFO = 32;	 		//32   ������ͨ��Ϣ
	public static final byte  ACK_INSERTION_INFO=33;	 		//33   ���ϲ岥��Ϣ
	public static final byte  ACK_EMERGENCY_INFO=34;	 		//34   ���ý�����Ϣ
	public static final byte  ACK_REMOVE_INFO	  = 35;  		//35   ɾ����Ϣ
	public static final byte  ACK_OFF_ON_SETUP  = 36;  		//36   ���ö�ʱ���ػ�
	public static final byte  ACK_FORCED_SHUTDOWN = 37;		//37   ǿ�ƿ��ػ�
	public static final byte  ACK_TIME_SETUP = 38;	 		//38   ����LEDʱ��
	public static final byte  ACK_SHOW_TIME_SETUP = 39;		//39   ����ʱ����ʾ
	public static final byte  ACK_LEDRESULT_MARK = 40; 		//40   ����LED���ر�־
	
	//(ASICO)2009-10-06
	public static final byte  ACK_EXTEND_FUNCTION = 0x40; 		//0x40  ������չ����
	public static final byte  ACK_IO_OUTPUT_CONTROL=0x41;		//0x41  ����IO����չ����
	
	public static final byte  ACK_DISABLE_OIL = 120;			//�������� (new version)
	public static final byte  ACK_ENABLE_OIL = 121;			//�����·(new version)	
	public static final byte  ACK_CLOSE_DOOR = 122;			//Զ�̹���(new version)
	public static final byte  ACK_OPEN_DOOR  = 123;			//Զ�̿���(new version)
}
//=============================================
//==hardware state
//BYTE1:
//BIT0:	1/0 ACC��/��
//BIT1:	1/0 ���ſ�/��
//BIT2:	1/0 ����Դ����/��ع���
//BIT3:	1/0 ���õ�Դ�͵�ѹ/���õ�Դ����
//BIT4:	1/0 ��·��ͨ/�Ͽ�
//BIT5:	1/0 �������ذ���/���������ɿ�
//BIT6:	1/0 �Ѳ���/δ����
//BIT7:	1/0 ����������/������δ����
//		
//BYTE2: 
//BIT0:	�Զ��屨���߸ߵ�ƽ/�Զ��屨���ߵ͵�ƽ
//BIT1:	�Զ��屨���߸ߵ�ƽ/�Զ��屨���ߵ͵�ƽ
//BIT2:	�Զ��屨���߸ߵ�ƽ/�Զ��屨���ߵ͵�ƽ
//BIT3:	�Զ��屨���߸ߵ�ƽ/�Զ��屨���ߵ͵�ƽ
//BIT4:	1/0 �س�/�ճ�
//BIT5:	1/0 ��Ӫ״̬/����Ӫ״̬
//BIT6:	1/0 ���������/������	
//BIT7:	1/0 �ֱ�����/������	
//BYTE3: 
//			BIT0~1:	GPS�����ź�ǿ�� 0~3����0��Ϊ���ź�
//			BIT2~3: GSM�����ź�ǿ�� 0~3����0��Ϊ���ź�
//			
//BYTE3:	Reserved
class HardwareState{
	
	public static final int  HW_ACC			= 0x01;
	public static final int  HW_DOOR			= 0x02;
	public static final int  HW_MAINPOWER		= 0x04;
	public static final int  HW_BACKBATTERY	= 0x08;
	public static final int  HW_OIL			= 0x10;
	public static final int  HW_SOS			= 0x20;
	public static final int  HW_ANTISTEAL		= 0x40;
	//public static final int  HW_MOTOR			= 0x80;	//reserved

	public static final int  HW_CUSTOME1		= 0x100;
	public static final int  HW_CUSTOME2		= 0x200;
	public static final int  HW_CUSTOME3		= 0x400;
	public static final int  HW_CUSTOME4		= 0x800;
	public static final int  HW_LOAD			= 0x1000;
	public static final int  HW_WORK			= 0x2000;
	public static final int  HW_AD_PANEL		= 0x4000;
	public static final int  HW_HANDSET		= 0x8000;	

	public static final int  HW_GPS_SIGNAL	= 0x30000;
	public static final int  HW_GSM_SIGNAL	= 0xc0000;
}
//*******************************************************
//alarm state
//BYTE1:
//BIT0: ��������
//BIT1: ���ٱ���
//BIT2: ͣ������
//BIT3: �ϳ�����
//BIT4: ��籨��				
//BIT5: ���籨��
//BIT6: �ϵ籨��
//BIT7: �͵�ѹ����
//BYTE2:
//BIT0: GPS��·����
//BIT1: GPS��·����
//BIT2: �Ƿ����ű���(�ڲ���֮���ų�Ϊ�Ƿ����� Ҳ�е���)
//BIT3: �Ƿ����(�ڲ���֮�����Ϊ�Ƿ����)
//BIT4: �Զ��屨��1
//BIT5: �Զ��屨��2
//BIT6: �Զ��屨��3
//BIT7: �Զ��屨��4
//BYTE3: 
//BIT0: ƣ�ͼ�ʻ
//BIT1: ��·����
//BYTE1
class AlarmState{
	public static final int  ALARM_SOS					= 0x01;
	public static final int  ALARM_OVERSPEED			= 0x02;
	public static final int  ALARM_PARKING				= 0x04;
	public static final int  ALARM_TOW					= 0x08;
	public static final int  ALARM_IN_AREA				= 0x10;
	public static final int  ALARM_OUT_AREA				= 0x20;
	public static final int  ALARM_POWER_OFF			= 0x40;
	public static final int  ALARM_LOW_POWER			= 0x80;

	public static final int  ALARM_GPSANTENNA_OPEN		= 0x100;
	public static final int  ALARM_GPSANTENNA_SHORT		= 0x200;
	public static final int  ALARM_ILL_DOOROPEN			= 0x400;
	public static final int  ALARM_ILL_ACCON			= 0x800;
	public static final int  ALARM_CUSTOM1				= 0x1000;
	public static final int  ALARM_CUSTOM2				= 0x2000;
	public static final int  ALARM_CUSTOM3				= 0x4000;
	public static final int  ALARM_CUSTOM4				= 0x8000;

	public static final int  ALARM_TRIED_DRIVE			= 0x10000;
	public static final int  ALARM_FUEL_LIAK			= 0x20000;
	public static final int  ALARM_ACC_ALARM			= 0x40000;
	public static final int  ALARM_NOGPS_ALARM		    = 0x80000;
	//  �¶ȱ���ֵ
	public static final int  AL_TEMPERATUREDATA			= 0x100000;
	//  ײ������
	public static final int  AL_CRASH_ALARM				= 0x200000;

};
//****************************************************
//  ������Ӧ���ַ���
class AlarmToString{
	
	public static final int[][]  g_nAlarmString = {
		{AlarmState.ALARM_SOS,				Language.TEXT_ALARM_SOS},
		{AlarmState.ALARM_OVERSPEED,		Language.TEXT_ALARM_OVERSPEED},
		{AlarmState.ALARM_PARKING,			Language.TEXT_ALARM_PARKING},
		{AlarmState.ALARM_TOW,				Language.TEXT_ALARM_TOW},
		{AlarmState.ALARM_IN_AREA,			Language.TEXT_ALARM_IN_AREA},
		{AlarmState.ALARM_OUT_AREA,			Language.TEXT_ALARM_OUT_AREA},
		{AlarmState.ALARM_POWER_OFF,		Language.TEXT_ALARM_POWER_OFF},
		{AlarmState.ALARM_LOW_POWER,		Language.TEXT_ALARM_LOW_POWER},
		
		{AlarmState.ALARM_GPSANTENNA_OPEN,	Language.TEXT_ALARM_GPS_OPEN},
		{AlarmState.ALARM_GPSANTENNA_SHORT,	Language.TEXT_ALARM_GPS_SHORT},
		{AlarmState.ALARM_ILL_DOOROPEN,		Language.TEXT_ALARM_ILL_DOOROPEN},
		{AlarmState.ALARM_ILL_ACCON,		Language.TEXT_ALARM_ILL_ACCON},
		{AlarmState.ALARM_CUSTOM1,			Language.TEXT_ALARM_CUSTOM1},
		{AlarmState.ALARM_CUSTOM2,			Language.TEXT_ALARM_CUSTOM2},
		{AlarmState.ALARM_CUSTOM3,			Language.TEXT_ALARM_CUSTOM3},
		{AlarmState.ALARM_CUSTOM4,			Language.TEXT_ALARM_CUSTOM4},
		{AlarmState.ALARM_TRIED_DRIVE,		Language.TEXT_ALARM_TIRED_DRIVE},
		{AlarmState.ALARM_FUEL_LIAK,	    Language.TEXT_ALARM_FUEL_LIAK},	
		{AlarmState.ALARM_ACC_ALARM,		Language.TEXT_ACC_ALARM},				
		{AlarmState.ALARM_NOGPS_ALARM,		Language.TEXT_NOGPS_ALARM},	
		{AlarmState.AL_TEMPERATUREDATA,		Language.TEXT_TEMPERATURE_ALARM},
		{-1,								-1},
	};
}
//****************************************************
// CL-TES��������
class CLToTE_Control{	
	public static final byte CM_CTRL_TE_NORMAL = 1;
	public static final byte CM_CTRL_TE_OILWAY = 2;				//2.	������·
	public static final byte CM_CTRL_TE_DOOR = 3;				//3.	���Ʊ���
	public static final byte CM_CTRL_TE_TAKEPHOTO = 4;			//4.	����
	public static final byte CM_CTRL_TE_HANDSET_INFO = 5;		//5.	������Ϣ����-�ֱ�ͨ��
	public static final byte CM_CTRL_TE_AD_INFO = 6;				//6.	���(����)��Ϣ����
	public static final byte CM_CTRL_TE_LISTEN = 7;				//7.	TE�Գ�������
	public static final byte CM_CTRL_TE_TALK = 8;				//8.	TE�Գ���ͨ��
	public static final byte CM_CTRL_TE_SETUP_CALLBACK = 9;		//9.	�ش���ʽ���������
	public static final byte CM_CTRL_TE_SETUP_ALARM = 10;			//10.	��������
	public static final byte CM_CTRL_TE_SETUP_SERVER = 11;		//11.	�޸��ն�TEServer IP��ַ�Ͷ˿�
	public static final byte CM_CTRL_TE_SETUP_NUMBER = 12;		//12.	���ø��ֺ���
	public static final byte CM_CTRL_TE_FENCE = 13;				//13.   ���õ���Χ��
	public static final byte CM_CTRL_TE_VOICE_INFO = 14;			//14.   ��������
	public static final byte CM_CTRL_TE_SETUP_CAPTURE = 15;		//15.	����ͼ��ʽ
	public static final byte CM_CTRL_TE_CAS_RESISTANCE = 16;		//16.	���������̶�ֵ	
	public static final byte CM_CTRL_TE_SETUP_GAS = 17;			//17.	������������


	// 2009-10-03(wyb)���� ASICO
	public static final byte CM_CTRL_EXTEND_FUNCTION = 0x40; //0x40  ������չ����
	public static final byte CM_CTRL_IO_OUTPUT_CONTROL = 0x41;		//0x41  ����IO����չ����
	public static final byte CM_CTRL_UART1_FUNCTION = 0x42;			//0x42  ����UART1�ڹ���
	public static final byte CM_CTRL_UART2_FUNCTION = 0x43;			//0x43  ����UART2�ڹ���	
	public static final byte CM_CTRL_READ_PARAMETERS = 0x44;		//0x44  ��ȡ�豸����
}
