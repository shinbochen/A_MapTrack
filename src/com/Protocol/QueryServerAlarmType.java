package com.Protocol;

import com.Language.Language;


public class QueryServerAlarmType {
	
	public static final int[][]  m_nAlarmTitle = {
		{SAlarmType.TYPE_ALLALARM,			Language.TEXT_ALARM_ALL },
		{SAlarmType.TYPE_SOS,				Language.TEXT_ALARM_SOS},			   // ��������
		{SAlarmType.TYPE_OVERSPEED,			Language.TEXT_ALARM_OVERSPEED},	   // ���ٱ���	    
		{SAlarmType.TYPE_POWER_OFF,			Language.TEXT_ALARM_POWER_OFF},	   // �ϵ籨��
		{SAlarmType.TYPE_LOW_POWER,			Language.TEXT_ALARM_LOW_POWER},	   // ���籨��
		{SAlarmType.TYPE_PARKING,				Language.TEXT_ALARM_PARKING},		   // ��������
		{SAlarmType.TYPE_TOW,				Language.TEXT_ALARM_TOW},			   // �ϳ�
		{SAlarmType.TYPE_IN_AREA,				Language.TEXT_ALARM_IN_AREA},		   // ���
		{SAlarmType.TYPE_OUT_AREA,			Language.TEXT_ALARM_OUT_AREA},       // Խ��
		{SAlarmType.TYPE_ILL_ACCON,			Language.TEXT_ALARM_ILL_ACCON},      // �Ƿ�����
		{SAlarmType.TYPE_ILL_DOOROPEN,		Language.TEXT_ALARM_ILL_DOOROPEN},   // �Ƿ�����
		{SAlarmType.TYPE_CUSTOM1,				Language.TEXT_ALARM_CUSTOM1},        // �Զ���1
		{SAlarmType.TYPE_CUSTOM2,				Language.TEXT_ALARM_CUSTOM2},        // �Զ���2
		{SAlarmType.TYPE_CUSTOM3,				Language.TEXT_ALARM_CUSTOM3},        // �Զ���3
		{SAlarmType.TYPE_CUSTOM4,				Language.TEXT_ALARM_CUSTOM4},        // �Զ���4
		{SAlarmType.TYPE_TIRED_DRIVE,			Language.TEXT_ALARM_TIRED_DRIVE},	   // ƣ�ͼ�ʻ	
		{SAlarmType.TYPE_ALARM_FUEL_LIAK,		Language.TEXT_ALARM_FUEL_LIAK},	   // ��·����
		
		{SAlarmType.TYPE_ACC_ALARM,			Language.TEXT_ACC_ALARM},				//  ACC�ر���			
		{SAlarmType.TYPE_NOGPS_ALARM,			Language.TEXT_NOGPS_ALARM},			//  GPSδ��λ����
		{SAlarmType.TYPE_TEMPERATURE_ALARM,	Language.TEXT_TEMPERATURE_ALARM},		//  �¶ȱ���
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
