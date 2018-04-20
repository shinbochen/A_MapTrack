package com.Protocol;

import com.Language.Language;


public class QueryServerAlarmType {
	
	public static final int[][]  m_nAlarmTitle = {
		{SAlarmType.TYPE_ALLALARM,			Language.TEXT_ALARM_ALL },
		{SAlarmType.TYPE_SOS,				Language.TEXT_ALARM_SOS},			   // 紧急报警
		{SAlarmType.TYPE_OVERSPEED,			Language.TEXT_ALARM_OVERSPEED},	   // 超速报警	    
		{SAlarmType.TYPE_POWER_OFF,			Language.TEXT_ALARM_POWER_OFF},	   // 断电报警
		{SAlarmType.TYPE_LOW_POWER,			Language.TEXT_ALARM_LOW_POWER},	   // 弱电报警
		{SAlarmType.TYPE_PARKING,				Language.TEXT_ALARM_PARKING},		   // 泊车报警
		{SAlarmType.TYPE_TOW,				Language.TEXT_ALARM_TOW},			   // 拖车
		{SAlarmType.TYPE_IN_AREA,				Language.TEXT_ALARM_IN_AREA},		   // 入界
		{SAlarmType.TYPE_OUT_AREA,			Language.TEXT_ALARM_OUT_AREA},       // 越界
		{SAlarmType.TYPE_ILL_ACCON,			Language.TEXT_ALARM_ILL_ACCON},      // 非法启动
		{SAlarmType.TYPE_ILL_DOOROPEN,		Language.TEXT_ALARM_ILL_DOOROPEN},   // 非法开门
		{SAlarmType.TYPE_CUSTOM1,				Language.TEXT_ALARM_CUSTOM1},        // 自定义1
		{SAlarmType.TYPE_CUSTOM2,				Language.TEXT_ALARM_CUSTOM2},        // 自定义2
		{SAlarmType.TYPE_CUSTOM3,				Language.TEXT_ALARM_CUSTOM3},        // 自定义3
		{SAlarmType.TYPE_CUSTOM4,				Language.TEXT_ALARM_CUSTOM4},        // 自定义4
		{SAlarmType.TYPE_TIRED_DRIVE,			Language.TEXT_ALARM_TIRED_DRIVE},	   // 疲劳驾驶	
		{SAlarmType.TYPE_ALARM_FUEL_LIAK,		Language.TEXT_ALARM_FUEL_LIAK},	   // 油路报警
		
		{SAlarmType.TYPE_ACC_ALARM,			Language.TEXT_ACC_ALARM},				//  ACC关报警			
		{SAlarmType.TYPE_NOGPS_ALARM,			Language.TEXT_NOGPS_ALARM},			//  GPS未定位报警
		{SAlarmType.TYPE_TEMPERATURE_ALARM,	Language.TEXT_TEMPERATURE_ALARM},		//  温度报警
		{-1,								-1},
	}; 	
	class SAlarmType{
		public static final  int TYPE_ALLALARM = 2;
		public static final  int TYPE_SOS = 3;
		public static final  int TYPE_OVERSPEED=4;
		public static final  int TYPE_POWER_OFF=5;
		public static final  int TYPE_LOW_POWER=6;
		public static final  int TYPE_PARKING=7;
		public static final  int TYPE_TOW=8;
		public static final  int TYPE_IN_AREA = 9;
		public static final  int TYPE_OUT_AREA = 9;
		public static final  int TYPE_ILL_ACCON = 11;
		public static final  int TYPE_ILL_DOOROPEN=12;
		public static final  int TYPE_CUSTOM1=13;
		public static final  int TYPE_CUSTOM2=14;
		public static final  int TYPE_CUSTOM3=15;
		public static final  int TYPE_CUSTOM4=16;
		public static final  int TYPE_TIRED_DRIVE=17;
		public static final  int TYPE_ALARM_FUEL_LIAK=18;
		public static final  int TYPE_ACC_ALARM=19;
		public static final  int TYPE_NOGPS_ALARM=20;
		public static final  int TYPE_TEMPERATURE_ALARM =21;
	}
}
