package com.AMaptrack;



import java.util.Timer;

import com.Data.GlobalData;
import com.Data.ProgramType;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Protocol.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	
	public  	TextView	m_txtUserInfo = null;
	public  	TextView	m_txtPsd = null;
	public  	TextView 	m_txtFName = null;
	public  	TextView 	m_txtLName = null;
	public  	TextView 	m_txtTel = null;
	public  	TextView 	m_txtEMail = null;
	public  	TextView 	m_txtCompany = null;
	public  	TextView 	m_txtKey = null;
	
	public 		EditText	m_editUser = null;
	public 		EditText	m_editPsd = null;
	public 		EditText	m_editFName = null;
	public 		EditText	m_editLName = null;
	public 		EditText	m_editTel = null;
	public    	EditText	m_editEmail = null;
	public 		EditText	m_editCompany = null;
	public 		EditText	m_editKey = null;
	
	public 		int			m_nWorkMode = 0;	//0:增加  1:修改  2:查看

	public 	    Button		m_bthOK = null;
	public 		Button		m_bthCancel = null;
	

	public 		Timer 		m_oUserInfoTimer = null;	
	public 		Dialog 		m_oUserInfoPromptDlg = null;
	private 	Handler 	m_oProgressHandler = null;

	//*******************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView( R.layout.userinfo );
		initEventObj();
		initEvent();
		initLables();
		getIntentData();
		ProgressMessageHandler();
	}
	//********************************************
	//
	public  void  initEventObj(){
				
		m_bthOK = (Button)findViewById(R.id.OkButton);
		m_bthCancel = (Button)findViewById(R.id.cancelButton);
		
		m_txtUserInfo = (TextView)findViewById( R.id.Text_UserInfo);
		m_txtPsd = (TextView)findViewById( R.id.Text_Psd );
		m_txtFName = (TextView)findViewById( R.id.Text_FName);
		m_txtLName = (TextView)findViewById( R.id.Text_LName);
		m_txtTel = (TextView)findViewById( R.id.Text_Tel);
		m_txtEMail = (TextView)findViewById( R.id.Text_EMail);
		m_txtCompany = (TextView)findViewById( R.id.Text_Company);
		m_txtKey = (TextView)findViewById( R.id.Text_Key);
		
		m_editUser =   (EditText)findViewById( R.id.Edit_User );
		m_editPsd =    (EditText)findViewById( R.id.Edit_psd);
		m_editFName =  (EditText)findViewById( R.id.Edit_FName);
		m_editLName =  (EditText)findViewById( R.id.Edit_LName);
		m_editTel =    (EditText)findViewById( R.id.Edit_Tel );
		m_editEmail =  (EditText)findViewById( R.id.Edit_EMail );
		m_editCompany =(EditText)findViewById( R.id.Edit_Company);
		m_editKey =    (EditText)findViewById( R.id.Edit_Key );
	}
	//********************************************************
	//
	public void initLables(){
		
		m_txtUserInfo.setText( Language.getLangStr( Language.TEXT_ACCOUNT) );
		m_txtPsd.setText( Language.getLangStr( Language.TEXT_PASSWORD) );
		m_txtFName.setText( Language.getLangStr( Language.TEXT_FNAME) );
		m_txtLName.setText( Language.getLangStr( Language.TEXT_LNAME) );
		m_txtTel.setText( Language.getLangStr( Language.TEXT_TELNO) );
		m_txtEMail.setText( Language.getLangStr( Language.TEXT_EMAIL) );
		m_txtCompany.setText( Language.getLangStr( Language.TEXT_COMPANY) );
		m_txtKey.setText( "Key" );
		
		m_bthOK.setText( Language.getLangStr( Language.TEXT_OK) );
		m_bthCancel.setText( Language.getLangStr( Language.TEXT_CANCEL) );
	}
	//************************************************
	//
	public void  initEvent(){		
		
		m_bthOK.setOnClickListener( eventOK );
		m_bthCancel.setOnClickListener( eventCancel );
	}
	//***************************************************
	//
	public void  getIntentData(){
		
		Bundle bunde = this.getIntent().getExtras();

		m_nWorkMode = bunde.getInt( ConfigKey.KEY_WORKMODE );
		switch( m_nWorkMode ){
		case 0:		//增加
			m_editUser.setEnabled(true);
			m_editPsd.setEnabled(true);
			this.setTitle( Language.getLangStr(Language.TEXT_ADD));
			break;
		case 1:		//修改
			m_editUser.setEnabled(false);
			m_editPsd.setEnabled(false);
			this.setTitle( Language.getLangStr(Language.TEXT_MODIFY));
			break;
		case 2:		//查看
			m_editUser.setEnabled(false);
			m_editPsd.setEnabled(false);
			this.setTitle( Language.getLangStr(Language.TEXT_VIEW));
			break;
		}	
		if( m_nWorkMode != 0 ){
			m_editUser.setText( bunde.getString( ConfigKey.KEY_USER ) );
			m_editPsd.setText( bunde.getString( ConfigKey.KEY_PSD ) );
			m_editFName.setText( bunde.getString( ConfigKey.KEY_FNAME ) );
			m_editLName.setText( bunde.getString( ConfigKey.KEY_LNAME ) );
			m_editTel.setText( bunde.getString( ConfigKey.KEY_TEL ) );
			m_editEmail.setText( bunde.getString( ConfigKey.KEY_EMAIL ) );
			m_editCompany.setText( bunde.getString( ConfigKey.KEY_COMPANY ) );
			m_editKey.setText( bunde.getString( ConfigKey.KEY_KEY ) );
		}
	}
	
	//***************************************************
	//
	public UserInfo  addUserInfo( ){
		
		UserInfo		oUserInfo = null;
		
		if( m_editUser.getText().length() <= 0 ){
			
			showWarringDialog(Language.getLangStr(Language.TEXT_USER_NOT_EMPTY));
			return null;
		}
		oUserInfo = new UserInfo();
		oUserInfo.setUserName( m_editUser.getText().toString() );
		oUserInfo.setUserPsd(m_editPsd.getText().toString() );
		oUserInfo.setFName( m_editFName.getText().toString() );
		oUserInfo.setLName(m_editLName.getText().toString() );
		oUserInfo.setTelNum( m_editTel.getText().toString() );
		oUserInfo.setEmail(m_editEmail.getText().toString() );
		oUserInfo.setCoName( m_editCompany.getText().toString() );
		oUserInfo.setkey( m_editKey.getText().toString() );
		
		return  oUserInfo;
	}
	//****************************************************
	//
	OnClickListener	 eventOK = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			if( m_nWorkMode == 0 ){		
				UserInfo			oUserInfo = null;
				ServerCommand		oCmd = null;
				
				oUserInfo = addUserInfo();
				if ( oUserInfo == null ){
					return ;
				}
				oCmd = new ServerCommand();
				oCmd.RegisterUserCmd( oUserInfo );
				GlobalData.addSendServerData( oCmd );
				if( m_oUserInfoTimer == null ){
					m_oUserInfoTimer = new Timer();
				}
				// 一秒检测一次是否登陆成功			
				m_oUserInfoTimer.schedule(new UserInfoTask(), 200, 1000 );
				m_oUserInfoPromptDlg = onCreateDialog( 1 );
			}	
			else if( m_nWorkMode == 1  ){   //修改
				
				 Bundle 	bundle = new Bundle();
				 Intent		intent = new Intent();	
				 
				 bundle.putString( ConfigKey.KEY_USER ,m_editUser.getText().toString() );
				 bundle.putString( ConfigKey.KEY_PSD ,m_editPsd.getText().toString() );
				 bundle.putString( ConfigKey.KEY_FNAME,m_editFName.getText().toString() );
				 bundle.putString( ConfigKey.KEY_LNAME ,m_editLName.getText().toString() );
				 bundle.putString( ConfigKey.KEY_TEL ,m_editTel.getText().toString() );
				 bundle.putString( ConfigKey.KEY_EMAIL ,m_editEmail.getText().toString() );
				 bundle.putString( ConfigKey.KEY_COMPANY,m_editCompany.getText().toString() );
				 bundle.putString( ConfigKey.KEY_KEY ,m_editKey.getText().toString() );
				 intent.putExtras(bundle);
				 setResult(RESULT_OK, intent );
				 finish();
			}
			else if( m_nWorkMode == 2 ){
				
				finish();
				return ;
			}
		}		
	};
	//***********************************************************
	//
	@Override
	protected Dialog onCreateDialog(int id) {

		if( id ==  1 ){			
			ProgressDialog  dialog = new ProgressDialog(UserInfoActivity.this);
		    dialog.setIndeterminate(true);
		    dialog.setMessage(Language.getLangStr(Language.TEXT_RUN_PLEASE_WAIT));
		    dialog.setCancelable(true);
		    dialog.show();
		    return dialog;
		}
		else{
			return super.onCreateDialog(id);
		}
	}
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(UserInfoActivity.this);
		
		builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//******************************************************
	//
	public  void   showWarringDialogEx( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(UserInfoActivity.this);
		
		builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//****************************************************
	//
	OnClickListener	 eventCancel = new OnClickListener(){

		@Override
		public void onClick(View v) {
			finish();
		}
	};	
	//*******************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	//*******************************************
	//
	@Override
	protected void onDestroy() {
		
		if( m_oUserInfoTimer != null ){
			m_oUserInfoTimer.cancel();
			m_oUserInfoTimer = null;
		}
		if( m_oUserInfoPromptDlg != null ){
			m_oUserInfoPromptDlg.cancel();
			m_oUserInfoPromptDlg = null;
		}
		super.onDestroy();
	}	//******************************************************
	//  进程消息处理
	public  void  ProgressMessageHandler(){
		
		m_oProgressHandler = new Handler() {
    		@Override  
            public void handleMessage(Message msg) {
    			int nMessageType = msg.getData().getInt("ProgrameType");
    			switch( nMessageType ){	    			
    			case ProgramType.GENERAL_TIMEOUT:
    				m_oUserInfoPromptDlg.cancel();
    				showWarringDialog( Language.getLangStr(Language.TEXT_CHECK_NETWORK));
    				break;
    			case ProgramType.REGISTER_USER_SUCCEED:			//增加
    				((ProgressDialog)m_oUserInfoPromptDlg).setMessage( Language.getLangStr(Language.TEXT_REGISTER_OK));
    				break;
    			case ProgramType.MODIFY_USER_SUCCEED:	//修改
    				((ProgressDialog)m_oUserInfoPromptDlg).setMessage(Language.getLangStr(Language.TEXT_SUCCEED));
    				break;
    			case ProgramType.REGISTER_USER_FAIL:				//失败
    				((ProgressDialog)m_oUserInfoPromptDlg).setMessage(Language.getLangStr(Language.TEXT_REGISTER_FAILED));
    				break;				
    			}
    		}
    	};
	}
	//********************************************
    // 定时器的引用
    class UserInfoTask extends java.util.TimerTask{
    	
    	// 10次为登陆超时返回, 间隔为一秒一次
    	public final  int		MAX_CNT = 10;		
    	public  	  int		m_nCnt = 0;
    	public 		  boolean	m_bFlag = false;
    	    	
		//**********************************************************
		@Override
		public void run() {
			
			Message 		msg = null;			
					
			if( m_nCnt++ >= MAX_CNT ){	// 超时				
				m_nCnt = 0;
				msg = new Message();	//通过句柄发送信息
				msg.getData().putInt("ProgrameType", ProgramType.GENERAL_TIMEOUT );  
				m_oProgressHandler.sendMessage(msg);
				Log.e("adduser", "增加用户超时");
				cancel();
			}
			if( m_bFlag == true ){
				if( m_nCnt == 1 ){
					if( m_oUserInfoPromptDlg != null ){
						m_oUserInfoPromptDlg.cancel();
						m_oUserInfoPromptDlg = null;
						cancel();
					}
				}
			}			
			switch(GlobalData.getRecvChkUserInfo() ){
			case ProgramType.REGISTER_USER_SUCCEED:			//增加
				m_nCnt = 0;
				m_bFlag = true;
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.REGISTER_USER_SUCCEED );  
				m_oProgressHandler.sendMessage(msg); 
				break;
			case ProgramType.MODIFY_USER_SUCCEED:	//修改
				m_nCnt = 0;
				m_bFlag = true;
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.MODIFY_USER_SUCCEED );  
				m_oProgressHandler.sendMessage(msg);
				break;
			case ProgramType.REGISTER_USER_FAIL:				//失败
				m_nCnt = 0;
				m_bFlag = true;
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.REGISTER_USER_FAIL );  
				m_oProgressHandler.sendMessage(msg); 
				break;			
			}
		}
    }
}
