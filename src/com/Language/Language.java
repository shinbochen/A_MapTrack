package com.Language;

//*************************************************
//
public class Language {
	
	public static final int	LANG_CN = 0;
	public static final int	LANG_EN = 1;
	public static 		int	m_sLang = LANG_CN;
	
	//*****数组对应的列*************************************
	public static final int TEXT_CONTROL = 0;
	public static final int TEXT_OIL_WAY=1;
	public static final int TEXT_DOOR=2;
	public static final int TEXT_TRACK_ONCE=3;
	public static final int TEXT_SETUP=4;
	public static final int TEXT_LISTEN_IN=5;
	public static final int TEXT_CB_MODE=6;
	public static final int TEXT_TELNO=7;
	public static final int TEXT_SERVER_IP=8;
	public static final int TEXT_ALARM=9;
	public static final int TEXT_DEVICE_RESET=10;
	public static final int TEXT_RESTORE_FACTORY=11;
	public static final int TEXT_FENCE_SETUP=12;
	public static final int TEXT_FUEL_OFF=13;
	public static final int TEXT_FUEL_ON=14;
	public static final int TEXT_LOCK=15;
	public static final int TEXT_UNLOCK=16;
	public static final int TEXT_SMSBOX_INFO=17;
	public static final int TEXT_CAPTUREING=18;
	public static final int TEXT_IMAGE_CAPTUREING_PLESASE_WAIT=19;
	public static final int TEXT_VOICE_BROADCAST=20;
	public static final int TEXT_UPLOADING_PLEASE_WAIT=21;
	public static final int TEXT_ALARM_FUEL_LIAK=22;
	public static final int TEXT_GAS_RESISTANCE=23;
	public static final int TEXT_INFO=24;
	public static final int TEXT_COREECTION_TIME=25;
	public static final int TEXT_CLOCK_SHOW=26;
	public static final int TEXT_SUCCEED=27;
	public static final int TEXT_FAIL=28;
	public static final int TEXT_CURRENT_POSITION=29;
	public static final int TEXT_NORTH=30;
	public static final int TEXT_NORTH_EAST=31;
	public static final int TEXT_EAST=32;
	public static final int TEXT_SOUTH_EAST=33;
	public static final int TEXT_SOUTH=34;
	public static final int TEXT_SOUTHE_WEST=35;
	public static final int TEXT_WEST=36;
	public static final int TEXT_NORTH_WEST=37;
	public static final int TEXT_ACC_ON = 38;
	public static final int TEXT_ACC_OFF = 39;
	public static final int TEXT_DOOR_ON = 40;
	public static final int TEXT_DOOR_OFF =41;
	public static final int TEXT_ALARM_SOS=42;
	public static final int TEXT_ALARM_OVERSPEED=43;
	public static final int TEXT_ALARM_PARKING=44;
	public static final int TEXT_ALARM_TOW=45;
	public static final int TEXT_ALARM_IN_AREA=46;
	public static final int TEXT_ALARM_OUT_AREA=47;
	public static final int TEXT_ALARM_POWER_OFF=48;
	public static final int TEXT_ALARM_LOW_POWER=49;
		
	public static final int TEXT_ALARM_GPS_OPEN=50;
	public static final int TEXT_ALARM_GPS_SHORT=51;
	public static final int TEXT_ALARM_ILL_DOOROPEN=52;
	public static final int TEXT_ALARM_ILL_ACCON=53;
	public static final int TEXT_ALARM_CUSTOM1=54;
	public static final int TEXT_ALARM_CUSTOM2=55;
	public static final int TEXT_ALARM_CUSTOM3=56;
	public static final int TEXT_ALARM_CUSTOM4=57;
	public static final int TEXT_ALARM_TIRED_DRIVE=58;
	public static final int TEXT_ACC_ALARM=59;			   //  ACC关报警			
	public static final int TEXT_NOGPS_ALARM=60;		   //  GPS未定位报警
	public static final int TEXT_TEMPERATURE_ALARM=61;
	public static final int TEXT_STARTTIME = 62;
	public static final int TEXT_ENDTIME = 63;
	public static final int TEXT_MAP_WINDOW = 64;
	public static final int TEXT_MONITOR_OBJECT = 65;
	public static final int TEXT_ALARM_REPORT = 66;
	public static final int TEXT_ALARM_INFO = 67;
	
	public static final  int  TEXT_MAP_LAYER_MANAGE  = 68;
	public static final  int  TEXT_USER_MANAGE  = 69;
	public static final  int  TEXT_SWITCH_ACCOUNT_NUMBER  = 70;
	public static final  int  TEXT_MODIFY_PASD  =  71;
	public static final  int  TEXT_HELP  = 72;
	public static final  int  TEXT_EXIT  =  73;

	public static final  int  TEXT_ADD  =  74;
	public static final  int  TEXT_MODIFY  =  75;
	public static final  int  TEXT_DEL  =  76;
	public static final  int  TEXT_VIEW  =  77;
	public static final  int  TEXT_DEL_ALL  =  78;

	public static final  int  TEXT_OK  =  79;
	public static final  int  TEXT_CANCEL  =  80;
	public static final  int  TEXT_LAYERCTRL  =  81;
	
	public static final  int  TEXT_SATELLITE = 82;
	public static final  int  TEXT_STREETVIEW = 83;
	public static final  int  TEXT_TRAFFIC = 84;	

	public static final int    TEXT_YOUR_CAUSE_FOLLOWING = 85;
	public static final int    TEXT_MSG_HEARD = 86;
	public static final int    TEXT_MSG_ONE = 87;
	public static final int    TEXT_MSG_TWO = 88;
	public static final int    TEXT_MSG_THREE = 89;
	public static final int    TEXT_MSG_FOUR = 90;
	public static final int    TEXT_MSG_DESCRIPTION =91 ;
	public static final int    TEXT_AGREE = 92;
	public static final int    TEXT_DENY = 93;
	public static final int    TEXT_POWER = 94;
	public static final int    TEXT_WARING_NO_DATA = 95;
	public static final int    TEXT_WARNING = 96;
	
	public  static  final int  TEXT_REGISTER_OK= 97;
	public  static  final int  TEXT_REGISTER_FAILED= 98;
	
	public static final int    TEXT_SIMPLEA  = 99;
	public static final int    TEXT_SIMPLEB  = 100;
	public static final int    TEXT_SIMPLEC  = 101;
	public static final int    TEXT_SIMPLED  = 102;
	public static final int    TEXT_STANDARDA  =103;
	public static final int    TEXT_STANDARDB  =104;
	public static final int    TEXT_ENHANCE  = 	105;
	public static final int    TEXT_ADVANCE  = 106;
	public static final int    TEXT_UNKNOW  = 	107;
	public static final int	   TEXT_ALARM_ALL = 108;
	public static final int    TEXT_DURATION = 109;
	public static final int  TEXT_DAY  = 110;
	public static final int  TEXT_HOUR  =  111; 
	public static final int  TEXT_MIN  =   112;
	public static final int	 TEXT_SEC = 113;
	public static final int	 TEXT_M = 114;
	public static final int   TEXT_VEHICLE_SHOW = 115;
	public static final int  TEXT_ALARM_TYPE = 116;
	public static final int  TEXT_STARTADDR = 117;
	public static final int  TEXT_ENDADDR =  118;
	public static final int  TEXT_LAST_POSITION =  119;
	public static final int  TEXT_TALK =  120;
	public static final  int  TEXT_GPS_LOCATE =	 121; 
	public static final  int  TEXT_GPS_UNLOCATE = 122; 
	public static final  int  TEXT_QUERY = 123;
	public static final  int  TEXT_TIME = 124;
	public static final  int  TEXT_REMARK = 125;
	public static final int TEXT_DEUID = 126;
	public static final int TEXT_TYPE =	127;
	public static final int TEXT_DEVICE_SIM =128;
	public static final int TEXT_FNAME = 129;
	public static final int TEXT_LNAME = 130;
	public static final int TEXT_ADDRESS =131;
	public static final int TEXT_PORT = 132;
	public static final int  TEXT_ACCOUNT = 133;
	public static final int  TEXT_PASSWORD = 134;
	public static final int  TEXT_LOGIN = 135;
	public static final int  TEXT_REGISTER = 136;
	public  static final  int  TEXT_OLD_PASSWORD = 137 ;
	public  static final  int  TEXT_NEW_PASSWORD = 138 ;
	public  static final  int  TEXT_NEW_PASSWORD2 = 139 ;
	public static final int   TEXT_EMAIL =140;
	public static final int   TEXT_COMPANY = 141;
	public static final int   TEXT_GPS_GPS = 142;
	
	public  static  final  int     TEXT_OPERATION_RECORD	= 143;
	public  static  final  int	   TEXT_WARRIT_DEUID_ERROR = 144;
	public 	static  final  int	   TEXT_MONITORY_EMPTY		= 145;
	public 	static  final  int	   TEXT_USER_AND_PSD_INCORRECT = 146;
	public  static  final  int	   TEXT_LOADING_DATA_PLEASE_WAIT = 147;
	public  static  final  int	   TEXT_NETWORK_FAIL_PLEASE_CHECK = 148;
	public  static  final  int	   TEXT_LOGIN_TIMEOUT = 149;
	public  static  final  int	   TEXT_CONNECT_SERVER_SUCCESS = 150;
	public  static  final  int	   TEXT_USER_NOT_EMPTY	= 151;
	public  static  final  int	   TEXT_PROGRAM_LOAD_PLEASE_CHECK = 152;
	public  static  final  int 	   TEXT_SOCKET_CONNECT_LATER_QUIT = 153;
	public  static  final  int 	   TEXT_ARE_YOU_SURE =	154;
	public  static  final  int	   TEXT_ENTER_PSD_OLD_PSD = 155;
	public  static  final  int	   TEXT_ENTER_TWICE_PASSWORD = 156;
	public  static  final  int	   TEXT_RUN_PLEASE_WAIT =157;
	public  static  final  int	   TEXT_CHECK_NETWORK = 158;
	public  static  final  int	   TEXT_SELECT_VEHICLE = 159;
	public  static  final  int	   TEXT_ONLY_TICK_CAR  = 160;
	public  static  final  int	   TEXT_NUMBER_SUB_RULE = 161;
	public  static  final  int     TEXT_WARING_NO_ACCOUNT = 162;
	public  static  final  int	   TEXT_REMEMBER = 163;
	public  static  final  int 	   TEXT_PLEASE_WAIT = 164;
	public  static  final int 	   TEXT_ADD_VEHICLE_NOW = 165;
	public  static  final  int 	   TEXT_SPEED_KMH = 166;
	public  static  final  int 	   TEXT_LATITUDE = 167;
	public  static  final  int 	   TEXT_LONGITUDE = 168;
	public  static  final  int 	   TEXT_NAME = 169;
	public  static  final  int 	   TEXT_DRIVING_TIME = 170;
	public  static  final  int	   TEXT_ZOOMALL = 171;
	public  static  final  int 	   TEXT_VEHICLE_HISTORY_TRACE = 172;
	public  static  final  int 	   TEXT_PLAY= 173;
	public  static  final  int 	   TEXT_PAUSE= 174;
	public  static  final  int 	   TEXT_STOP= 175;
	public  static  final  int 	   TEXT_RANGE_EXCEED_DAYS = 176;
	public  static  final  int     TEXT_REFRESH = 177;
	public  static  final  int 	   TEXT_OTHER = 178;
	public  static  final  int 	   TEXT_CLICK_ME = 179;
	//**************************************************
	//
	public static  String  getLangStr( int nId){		
				
		if( m_sLang ==  LANG_CN ){	//中文
			
			return ArrayLanguage_CN.getLanguage(nId);
		}
		else{						//英文
			
			return ArrayLanguage_EN.getLanguage(nId);
		}
	}
	//**************************************************
	//
	public static  void  setLang( int   nLang ){
		
		m_sLang = nLang;
	}
	//**************************************************
	//
	public static int   getLang( ){
		
		return m_sLang;
	}
}


