package com.AMaptrack;

import com.Data.GlobalData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class RecvSMS extends BroadcastReceiver {

	private static final String strSMSRecv = "android.provider.Telephony.SMS_RECEIVED";
	
	@Override
	public void onReceive(Context oContext, Intent intent) {

		
	//	if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			
    //        Intent service=new Intent(oContext, MySMSServer.class);
    //        oContext.startService(service);
	//	}
		
		if( intent.getAction().equals( strSMSRecv)){

			Bundle		bundle = intent.getExtras();

			if( bundle == null ){
				return ;
			}
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] msg = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			for( SmsMessage curMsg : msg  ){	
				
				if( GlobalData.IsDESIM( curMsg.getDisplayOriginatingAddress() ) ){
											
					GlobalData.addRecvSMSData( curMsg.getPdu() );
					abortBroadcast();
				}
				
			//	Toast.makeText(oContext, 
			//			"ºÅÂë:"+curMsg.getDisplayOriginatingAddress()+"\r\n"+
			//			"ÄÚÈÝ:"+curMsg.getDisplayMessageBody(),
	        //            Toast.LENGTH_SHORT).show();
			}
		}
	}
}
