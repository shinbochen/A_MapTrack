package com.Protocol;

public class SMSControlCode {

	public static final  byte CM_CTRL_NORMAL_CALLONCE = 0x00;		  //定位呼叫
	public static final  byte CM_CTRL_NORMAL_RESET = 0x01;			  //复位
	public static final  byte CM_CTRL_NORMAL_RESTORE_FACTORY = 0x02;  //恢复出厂设置
	public static final  byte CM_CTRL_NORMAL_TESETUP = 0x03;		  //(读取终端设置)
	public static final  byte CM_CTRL_NORMAL_FORTIFY = 0x04;		  //(设防)
	public static final  byte CM_CTRL_NORMAL_RESHUFFLE = 0x05;		  //(撤防)
}
