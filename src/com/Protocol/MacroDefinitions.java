package com.Protocol;

import com.Language.Language;

public class MacroDefinitions {
	
	 public static final byte  TYPE_ERROR_CODE = 0x00;		//错误码
     public static final byte  TYPE_USER_ONLY  = 0x01;
     public static final byte  TYPE_GROUPNAME_ONLY= 0x02;
     public static final byte  TYPE_DEUID_ONLY	= 0x03;

     public static final byte  TYPE_LOGIN	= 0x04;					
     public static final byte  TYPE_USERINFO= 0x05;				//详细型用户信息
     public static final byte  TYPE_SUBUSERINFO= 0x06;			//详细型子用户信息
     public static final byte  TYPE_CARINFO= 0x07;				//车辆信息
     public static final byte  TYPE_GROUPINFO= 0x08;			//分组信息
     public static final byte  TYPE_SUBUSERMANAGE= 0x09;		//子用户管理信息
     public static final byte  TYPE_GROUPMANAGE= 0x0A;			//子用户管理信息
     public static final byte  TYPE_QUERY_CONDITION= 0x0B;
     public static final byte  TYPE_MILEAGEDATA= 0x0C;
     public static final byte  TYPE_GPSDATA= 0x0D;
     public static final byte  TYPE_IMGDATA= 0x0E;
     public static final byte  TYPE_TESETUP= 0x0F;
     public static final byte  TYPE_ATTEMPER= 0x10;
     public static final byte  TYPE_TECMD= 0x11;
     public static final byte  TYPE_SEND_TECMD_RESULT= 0x12;
     public static final byte  TYPE_PHONE_MAMAGE= 0x13;			//号码管理
				// ASICO 定位指令; 设置后更改命令(wyb 2009-10-05)
     public static final byte  TYPE_MODIFY_CODESTATE= 0x14;
     public static final byte  TYPE_BLINDGPSDATA= 0x15;
     public static final byte  TYPE_IMAGEVPATH= 0x16;			//图像映射地址

     public static final byte  TYPE_RUNDATA= 0x17;
     public static final byte  TYPE_ALARMDATA= 0x18;
     public static final byte  TYPE_GASDATA= 0x19;				//油量数据
     public static final byte  TYPE_FENCEDATA= 0x1A;			//电子围栏数据
     public static final byte  TYPE_POIDATA= 0x1B;				//POI点	
     public static final byte  TYPE_NAME= 0x1C;					//名字
     public static final byte  TYPE_POIFENCE= 0x1D;	
     public static final byte  TYPE_QUERY_POICO= 0x1E;
     public static final byte  TYPE_QUERY_GG_ADDR_CN= 0x1F;
     public static final byte  TYPE_QUERY_GG_ADDR_EN= 0x20;
     public static final byte  TYPE_TEMPERATURE_DATA= 0x21;		//温度数据	
     public static final byte  TYPE_QUERY_GG_ADDR_TH= 0x22;     //泰文
}

//******************************************************************
//=====设备应答数据==================================================
//bit7  1/0执行成功/失败, 
//bit0~6: 命令类型
//		0:无
//			1~7: reserved
//		7: 控制油路(old version)
//		8: 远程中控锁(old version)
class ACKCmdCode{
	public static final byte  ACK_OLD_OIL = 0x07;		  		//7.	关闭/恢愎油路信息       (MT_ DELIVERY)
	public static final byte  ACK_OLD_DOOR = 0x08;	  		//8.	开/关 车门		        (MT_ DELIVERY)	
	
	public static final byte  ACK_LOCATION = 0x09;	  		//定位
	public static final byte  ACK_LISTEN = 0x0A;		  		//监听
	public static final byte  ACK_TALK=0x0B;				  	//通话
	public static final byte  ACK_SETUP_MODE = 0x0C;			//设置数据上传模式
	public static final byte  ACK_SETUP_PHONE = 0x0D;		  	//设置号码	
	public static final byte  ACK_SETUP_SERVER = 0x0E;		//设置服务器
	public static final byte  ACK_SETUP_ALARM = 0x0F;		  	//报警设置
	public static final byte  ACK_SETUP_RESET = 0x10;		  	//终端复位
	public static final byte  ACK_RESTORE_FACTORY = 0x11;  	//恢愎出厂	
	public static final byte  ACK_SETUP_FENCE = 0x12;		  	//设置围栏
	
	public static final byte  ACK_HANDSET_INFO = 20;	  		//20.   发送调度信息
	public static final byte  ACK_AD_INFO = 21;			  	//21.	  发送广告屏信息
	public static final byte  ACK_TAKEPHOTO = 22;			  	//22.   拍照信息
	public static final byte  ACK_SETUP_CARTURE = 23;		  	//23.   设置拍照信息
	public static final byte  ACK_VOICE_INFO = 24;			//24.   语音播报信息
	public static final byte  ACK_IMAGE_DATA = 25;			//25.	  开始上传图像数据    
	public static final byte  ACK_IMAGE_RESULT = 27;	  		//27    图像补包 
	public static final byte  ACK_REQUIRE_SESSION_RESULT = 28;	
	public static final byte  ACK_TE_SETUP_CONSUMPTION = 29; 	//29   设置刻度值对应油量
	public static final byte  ACK_SETUP_GAS = 31;		  		//31   设置油路报警
	
	public static final byte  ACK_GENERAL_INFO = 32;	 		//32   设置普通信息
	public static final byte  ACK_INSERTION_INFO=33;	 		//33   马上插播信息
	public static final byte  ACK_EMERGENCY_INFO=34;	 		//34   设置紧急信息
	public static final byte  ACK_REMOVE_INFO	  = 35;  		//35   删除信息
	public static final byte  ACK_OFF_ON_SETUP  = 36;  		//36   设置定时开关机
	public static final byte  ACK_FORCED_SHUTDOWN = 37;		//37   强制开关机
	public static final byte  ACK_TIME_SETUP = 38;	 		//38   设置LED时间
	public static final byte  ACK_SHOW_TIME_SETUP = 39;		//39   设置时间显示
	public static final byte  ACK_LEDRESULT_MARK = 40; 		//40   设置LED返回标志
	
	//(ASICO)2009-10-06
	public static final byte  ACK_EXTEND_FUNCTION = 0x40; 		//0x40  设置扩展功能
	public static final byte  ACK_IO_OUTPUT_CONTROL=0x41;		//0x41  设置IO口扩展功能
	
	public static final byte  ACK_DISABLE_OIL = 120;			//断油命令 (new version)
	public static final byte  ACK_ENABLE_OIL = 121;			//恢愎油路(new version)	
	public static final byte  ACK_CLOSE_DOOR = 122;			//远程关门(new version)
	public static final byte  ACK_OPEN_DOOR  = 123;			//远程开门(new version)
}
//=============================================
//==hardware state
//BYTE1:
//BIT0:	1/0 ACC开/关
//BIT1:	1/0 车门开/关
//BIT2:	1/0 主电源供电/电池供电
//BIT3:	1/0 备用电源低电压/备用电源正常
//BIT4:	1/0 油路连通/断开
//BIT5:	1/0 紧急开关按下/紧急开关松开
//BIT6:	1/0 已布防/未布防
//BIT7:	1/0 发动机启动/发动机未启动
//		
//BYTE2: 
//BIT0:	自定义报警线高电平/自定义报警线低电平
//BIT1:	自定义报警线高电平/自定义报警线低电平
//BIT2:	自定义报警线高电平/自定义报警线低电平
//BIT3:	自定义报警线高电平/自定义报警线低电平
//BIT4:	1/0 重车/空车
//BIT5:	1/0 运营状态/非运营状态
//BIT6:	1/0 广告屏正常/不正常	
//BIT7:	1/0 手柄正常/不正常	
//BYTE3: 
//			BIT0~1:	GPS天线信号强度 0~3级，0级为无信号
//			BIT2~3: GSM天线信号强度 0~3级，0级为无信号
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
//BIT0: 紧急报警
//BIT1: 超速报警
//BIT2: 停车报警
//BIT3: 拖车报警
//BIT4: 入界报警				
//BIT5: 出界报警
//BIT6: 断电报警
//BIT7: 低电压报警
//BYTE2:
//BIT0: GPS开路报警
//BIT1: GPS短路报警
//BIT2: 非法开门报警(在布防之后开门称为非法开门 也叫盗警)
//BIT3: 非法点火(在布防之后点火称为非法点火)
//BIT4: 自定义报警1
//BIT5: 自定义报警2
//BIT6: 自定义报警3
//BIT7: 自定义报警4
//BYTE3: 
//BIT0: 疲劳驾驶
//BIT1: 油路报警
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
	//  温度报警值
	public static final int  AL_TEMPERATUREDATA			= 0x100000;
	//  撞车报警
	public static final int  AL_CRASH_ALARM				= 0x200000;

};
//****************************************************
//  报警对应的字符串
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
// CL-TES控制命令
class CLToTE_Control{	
	public static final byte CM_CTRL_TE_NORMAL = 1;
	public static final byte CM_CTRL_TE_OILWAY = 2;				//2.	控制油路
	public static final byte CM_CTRL_TE_DOOR = 3;				//3.	控制边门
	public static final byte CM_CTRL_TE_TAKEPHOTO = 4;			//4.	拍照
	public static final byte CM_CTRL_TE_HANDSET_INFO = 5;		//5.	调度信息传输-手柄通信
	public static final byte CM_CTRL_TE_AD_INFO = 6;				//6.	广告(电召)信息传输
	public static final byte CM_CTRL_TE_LISTEN = 7;				//7.	TE对车辆监听
	public static final byte CM_CTRL_TE_TALK = 8;				//8.	TE对车辆通话
	public static final byte CM_CTRL_TE_SETUP_CALLBACK = 9;		//9.	回传方式及间隔设置
	public static final byte CM_CTRL_TE_SETUP_ALARM = 10;			//10.	报警设制
	public static final byte CM_CTRL_TE_SETUP_SERVER = 11;		//11.	修改终端TEServer IP地址和端口
	public static final byte CM_CTRL_TE_SETUP_NUMBER = 12;		//12.	设置各种号码
	public static final byte CM_CTRL_TE_FENCE = 13;				//13.   设置电子围栏
	public static final byte CM_CTRL_TE_VOICE_INFO = 14;			//14.   语音播报
	public static final byte CM_CTRL_TE_SETUP_CAPTURE = 15;		//15.	设置图像方式
	public static final byte CM_CTRL_TE_CAS_RESISTANCE = 16;		//16.	设置油量刻度值	
	public static final byte CM_CTRL_TE_SETUP_GAS = 17;			//17.	设置油量报警


	// 2009-10-03(wyb)增加 ASICO
	public static final byte CM_CTRL_EXTEND_FUNCTION = 0x40; //0x40  设置扩展功能
	public static final byte CM_CTRL_IO_OUTPUT_CONTROL = 0x41;		//0x41  设置IO口扩展功能
	public static final byte CM_CTRL_UART1_FUNCTION = 0x42;			//0x42  设置UART1口功能
	public static final byte CM_CTRL_UART2_FUNCTION = 0x43;			//0x43  设置UART2口功能	
	public static final byte CM_CTRL_READ_PARAMETERS = 0x44;		//0x44  读取设备参数
}
