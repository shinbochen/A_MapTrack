package com.AMaptrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.Data.GlobalData;
import com.Data.ProgramType;
import com.Data.ServerCommand;
import com.Language.Language;
import com.Socket.ServerSocket;

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
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Login extends Activity {
	
	public final int					PROGRESS_DIALOG = 0x01;
	public final int					ACTIVITY_CONFIG = 0x02;
	
	public      String					m_strIP = "";
	public      String					m_strLoginUser = "";
	public      String					m_strLoginPsd = "";
	public 		boolean					m_bRecordFlag = false;
	public 		int						m_nPort = 0;
	
	private		EditText				m_editUser = null;
	private		EditText				m_editPsd = null;	
	
	private		Button					m_bthLogin = null;
	private		Button					m_bthRegister = null;
	private		Button					m_bthSetup  = null;
	
	private		CheckBox				m_chkRecordPsd = null;
	private		Spinner					m_coLang = null;
	public 		ArrayAdapter<String> 	m_LangAdapter = null ;
	public 		Timer 					m_oLoginTimer = null;	
	public 		Dialog 					m_oLogPromptDlg = null;
	private 	Handler 				m_oProgressHandler = null;  
	public 		TextView				m_loginEmpty = null;
	
	//****************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initEventObj();
		initWindow();
		initEvent();
		initLabels();	
		getIntentData();
		ProgressMessageHandler();
	}
	//***************************************************
	// 
	public void  initEventObj(){
				
		m_editUser = (EditText)findViewById(R.id.Edit_user);
		m_editPsd  = (EditText)findViewById(R.id.Edit_psd );
		m_bthLogin = (Button)findViewById(R.id.Login_button);
		m_bthRegister = (Button)findViewById(R.id.Register_button);
		m_bthSetup = (Button)findViewById(R.id.Setup_button);
		m_chkRecordPsd = (CheckBox)findViewById(R.id.Check_Recordpsd);
		m_coLang = (Spinner)findViewById(R.id.Spinner_Language);
        m_loginEmpty = (TextView)findViewById( R.id.login_image );
		
		List<String>	lstLang = new ArrayList<String>();
		lstLang.add("中文");
		lstLang.add("English");				
		m_LangAdapter = new ArrayAdapter<String>( this,
									        	  android.R.layout.simple_spinner_item,
									        	  lstLang);		
		m_LangAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		m_coLang.setAdapter(m_LangAdapter);
	}
	//***************************************************
	//
	public  void  initWindow(){		
		
		int		    nBottom = 0;
		int 		nWidth = 0;
		int			nHeight = 0;
		Display 	oDisplay = null;
						
		oDisplay = getWindowManager().getDefaultDisplay();
		nWidth = oDisplay.getWidth();
		nHeight = oDisplay.getHeight();

		Log.e("w-h", Integer.toString(nWidth)+ " "+Integer.toString(nHeight) );
		
		// User Edit--height和width
		RelativeLayout.LayoutParams linearUserSize = (RelativeLayout.LayoutParams)m_editUser.getLayoutParams();
        Log.e("linearUserSize-w-h", Integer.toString(linearUserSize.width)+ " "+Integer.toString(linearUserSize.height) );
        
        // Psd edit--height和width
        RelativeLayout.LayoutParams linearPsdSize = (RelativeLayout.LayoutParams)m_editPsd.getLayoutParams();
        Log.e("linearPsdSize-w-h", Integer.toString(linearPsdSize.width)+ " "+Integer.toString(linearPsdSize.height) );
        
        // Lang--height和width
        RelativeLayout.LayoutParams linearLangSize = (RelativeLayout.LayoutParams)m_coLang.getLayoutParams();
        Log.e("linearLangSize-w-h", Integer.toString(linearLangSize.width)+ " "+Integer.toString(linearLangSize.height) );
		
        // login--height和width
        RelativeLayout.LayoutParams linearLoginSize = (RelativeLayout.LayoutParams)m_bthLogin.getLayoutParams();
        Log.e("linearLoginSize-w-h", Integer.toString(linearLoginSize.width)+ " "+Integer.toString(linearLoginSize.height) );
        
        if( nHeight >= 900 ){
        	nBottom = 320;
        }
        else if( (nHeight < 900) && (nHeight > 800) ){
        	nBottom = 270;
        }
        else if( nHeight == 800 ){
        	nBottom = 230;
        }
        else if( (nHeight < 800) && (nHeight >= 700) ){
        	nBottom = 250;
        }
        else if( (nHeight < 700) && (nHeight >= 550) ){
        	nBottom = 230;
        }
        else{
        	nBottom = 110;
        }
		// 更改车辆列表的高度		
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) m_loginEmpty.getLayoutParams(); 
        linearParams.height = nHeight-linearUserSize.height-linearPsdSize.height-linearLangSize.height-linearLoginSize.height-nBottom;        
        m_loginEmpty.setLayoutParams(linearParams);        
	}
	//***************************************************
	//  
	public  void  initLabels(){	
		
		m_chkRecordPsd.setText(Language.getLangStr(Language.TEXT_REMEMBER));
		
		m_bthLogin.setText( Language.getLangStr(Language.TEXT_LOGIN) );
		m_bthRegister.setText( Language.getLangStr(Language.TEXT_REGISTER) );
		m_bthSetup.setText( Language.getLangStr(Language.TEXT_SETUP) );
		
	}
	//******************************************************
	//  事件初始化
	public  void  initEvent(){
		
		m_bthLogin.setOnClickListener( eventLogin );
		m_bthRegister.setOnClickListener( eventRegister );
		m_bthSetup.setOnClickListener(eventSetup );
		m_chkRecordPsd.setOnCheckedChangeListener( eventCheck );
		
		m_coLang.setOnItemSelectedListener( eventItemSlectedListener );
	}
	//******************************************************
	//
	OnItemSelectedListener eventItemSlectedListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(  AdapterView<?> arg0, View arg1, int arg2,
									 long arg3) {
			
			Language.setLang(arg2);
			initLabels();
			GlobalData.addRecvProgramType(ProgramType.UPDATE_LANGUAGE);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}		
	};
	//******************************************************
	//
	OnCheckedChangeListener eventCheck = new OnCheckedChangeListener(){		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			if( isChecked ){
				m_bRecordFlag = true;
			}
			else{
				m_bRecordFlag = false;	
			}
		}
	};
	//******************************************************
	//  进程消息处理
	public  void  ProgressMessageHandler(){
		
		m_oProgressHandler = new Handler() {
    		@Override  
            public void handleMessage(Message msg) {
    			int nMessageType = msg.getData().getInt("ProgrameType");
    			switch( nMessageType ){
    			case ProgramType.LOGIN_SUCCEED:			//登陆成功
    				m_oLogPromptDlg.cancel();
    				Log.e("login", "LOGIN_SUCCEED");
    				setResultData();    				
    				break;
    			case ProgramType.LOGIN_FAIL:			//登陆失败
    				m_oLogPromptDlg.cancel();
    				showWarringDialog( Language.getLangStr(Language.TEXT_USER_AND_PSD_INCORRECT));
    				break;
    			case ProgramType.LOGIN_LOAD_DATA:		//加载数据	
    				((ProgressDialog)m_oLogPromptDlg).setMessage(Language.getLangStr(Language.TEXT_LOADING_DATA_PLEASE_WAIT));
    				break;
    			case ProgramType.LOGIN_NETWORK_FAULT:	//网络故障
    				m_oLogPromptDlg.cancel(); 
    				showWarringDialog(Language.getLangStr(Language.TEXT_NETWORK_FAIL_PLEASE_CHECK));
    				break;
    			case ProgramType.GENERAL_TIMEOUT: 
    				m_oLogPromptDlg.cancel();
    				showWarringDialog(Language.getLangStr(Language.TEXT_LOGIN_TIMEOUT));
    				break; 
    			case ProgramType.LOGIN_CONNECT_SERVER:
    				((ProgressDialog)m_oLogPromptDlg).setMessage(Language.getLangStr(Language.TEXT_CONNECT_SERVER_SUCCESS) );
    				break;
    			}
    		}
    	};
	}
	//******************************************************
	//  设置返回数据
	public void setResultData(){
		
		Bundle bunde = new Bundle();
		
		Log.e("1", "0");
		bunde.putString( ConfigKey.KEY_IP, m_strIP );
		bunde.putInt( ConfigKey.KEY_PORT,  m_nPort );
		bunde.putInt( ConfigKey.KEY_LANG , Language.getLang() );
		bunde.putBoolean( ConfigKey.KEY_RECORDPSD, m_bRecordFlag );
		bunde.putString( ConfigKey.KEY_LOGIN_USER, m_strLoginUser );
		if( m_bRecordFlag == false ){
			m_strLoginPsd ="";
			bunde.putString( ConfigKey.KEY_LOGIN_PSD, m_strLoginPsd );
		}
		else{
			bunde.putString( ConfigKey.KEY_LOGIN_PSD, m_strLoginPsd );
		}
		Log.e("1", "1");
		bunde.putBoolean(ConfigKey.KEY_EXIT, false );
		Intent	intent = new Intent();
		intent.setClass(Login.this, MapTrack.class);   
		intent.putExtras( bunde );	
		Log.e("1", "2");
		setResult(RESULT_OK, intent );
		Log.e("1", "3");
		onClose();
	}
	//******************************************************
	//  获取主对话框参数
	public void getIntentData(){
		
	   Bundle bunde = this.getIntent().getExtras();
		   
	   m_strIP = bunde.getString( ConfigKey.KEY_IP );
	   m_nPort = bunde.getInt( ConfigKey.KEY_PORT );
	   Language.setLang( bunde.getInt( ConfigKey.KEY_LANG ) );	
	   m_bRecordFlag = bunde.getBoolean( ConfigKey.KEY_RECORDPSD );
	   m_strLoginUser = bunde.getString( ConfigKey.KEY_LOGIN_USER );
	   m_editUser.setText(m_strLoginUser);
	   m_strLoginPsd = bunde.getString( ConfigKey.KEY_LOGIN_PSD );
	   if( m_bRecordFlag == true){
		   m_editPsd.setText(m_strLoginPsd);
	   }
	   m_chkRecordPsd.setChecked(m_bRecordFlag);
	   m_coLang.setSelection( Language.getLang() );
	}
	//******************************************************
	//  注册
	OnClickListener	eventRegister = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			Bundle  bundle = new Bundle();
			
			bundle.putInt( ConfigKey.KEY_WORKMODE, 0 );
		    intent.setClass( Login.this, UserInfoActivity.class );
			intent.putExtras( bundle );
			startActivity( intent );
		}		
	};
	//******************************************************
	//  对话框返结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if( resultCode == RESULT_OK ){
        	if( ACTIVITY_CONFIG == requestCode ){
	    		Bundle bundle = data.getExtras();
	    		m_strIP = bundle.getString( ConfigKey.KEY_IP );
	    		m_nPort = bundle.getInt( ConfigKey.KEY_PORT );
	    		GlobalData.setIPPort( m_strIP, m_nPort );	    		
	    		Log.e("Login", m_strIP);
	    		Log.e("Login", Integer.toString(m_nPort));
        	}
        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	//******************************************************
	//  设置
	OnClickListener	eventSetup = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			Bundle  bundle = new Bundle();
			
			bundle.putString( ConfigKey.KEY_IP, m_strIP );
			bundle.putInt(ConfigKey.KEY_PORT, m_nPort );
		    intent.setClass( Login.this, ConfigActivity.class );
			intent.putExtras( bundle );
    		startActivityForResult(intent, ACTIVITY_CONFIG ); 
		}		
	};
	//******************************************************
	//  登陆
	OnClickListener	eventLogin = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			ServerCommand	oCmd = new ServerCommand();

			m_strLoginUser = m_editUser.getText().toString();
			m_strLoginPsd  = m_editPsd.getText().toString();
			if( m_strLoginUser.length() <= 0 ){
				
				showWarringDialog( Language.getLangStr(Language.TEXT_USER_NOT_EMPTY) );
				return ;
			}
			oCmd.setLoginCmd( m_editUser.getText().toString(), 
							  m_editPsd.getText().toString() );
			GlobalData.addSendServerData(oCmd);
			
			if( m_oLoginTimer == null ){
				m_oLoginTimer = new Timer();
			}
			// 一秒检测一次是否登陆成功			
			m_oLoginTimer.schedule(new LoginTask(), 1000, 1000 );
			m_oLogPromptDlg = onCreateDialog( PROGRESS_DIALOG );
		}
	};
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(Login.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//******************************************************
	//
	@Override
	protected Dialog onCreateDialog(int id) {

		if( id ==  PROGRESS_DIALOG ){
			
			ProgressDialog  dialog = new ProgressDialog(Login.this);
		    dialog.setIndeterminate(true);
		    dialog.setMessage(Language.getLangStr(Language.TEXT_PROGRAM_LOAD_PLEASE_CHECK));
		    dialog.setCancelable(true);
		    dialog.show();
		    return dialog;
		}
		else{
			return super.onCreateDialog(id);
		}
	}
	//******************************************************
	// 退出
	public void onClose(){		
		super.finish();
	}
	//******************************************************
	//
	@Override
	public void finish() {
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(this);
		
		if( ServerSocket.isOperationExit() == false ){
			
			builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
			builder.setMessage(Language.getLangStr(Language.TEXT_SOCKET_CONNECT_LATER_QUIT)).
				setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return ;
					}
			});	
		}		
		else{
			builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
			builder.setMessage( Language.getLangStr(Language.TEXT_ARE_YOU_SURE)).
				setPositiveButton(Language.getLangStr(Language.TEXT_OK), new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Bundle bunde = new Bundle();
						
						bunde.putBoolean(ConfigKey.KEY_EXIT, true );
						Intent	intent = new Intent();
						intent.setClass(Login.this, MapTrack.class);   
						intent.putExtras( bunde );	
						
						setResult(RESULT_OK, intent );
						onClose();
					}
				}).setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
						
					return ;
				}
			});	
		}
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//******************************************************
	//
	@Override
	protected void onDestroy() {
		
		Log.e("Login","onDestroy");
		super.onDestroy();
	}
	//********************************************
    // 定时器的引用
    class LoginTask extends java.util.TimerTask{
    	
    	// 登陆开始记, 30次为登陆超时返回, 间隔为一秒一次
    	public final  int		MAX_CNT = 30;		
    	public  	  int		m_nCnt = 0;
    	    	
		//**********************************************************
		@Override
		public void run() {
			
			Message 		msg = null;			
					
			if( m_nCnt++ >= MAX_CNT ){	// 登陆超时				
				
				msg = new Message();	//通过句柄发送信息
				msg.getData().putInt("ProgrameType", ProgramType.GENERAL_TIMEOUT );  
				m_oProgressHandler.sendMessage(msg);
				Log.e("Log", "登陆超时退出");
				cancel();
			}
			switch(GlobalData.getRecvLoginType() ){
			case ProgramType.LOGIN_SUCCEED:		//登陆成功
				Log.e("Log", "11111");
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.LOGIN_SUCCEED );  
				m_oProgressHandler.sendMessage(msg); 				
				cancel();
				break;
			case ProgramType.LOGIN_FAIL:		//登陆失败
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.LOGIN_FAIL );  
				m_oProgressHandler.sendMessage(msg); 				
				cancel();
				break;
			case ProgramType.LOGIN_LOAD_DATA:	//加载数据
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.LOGIN_LOAD_DATA );  
				m_oProgressHandler.sendMessage(msg); 	
				break;
			case ProgramType.LOGIN_NETWORK_FAULT:	//网络故障
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.LOGIN_NETWORK_FAULT );  
				m_oProgressHandler.sendMessage(msg); 				
				cancel();
				break;
			case ProgramType.LOGIN_CONNECT_SERVER:	//连接服务器成功
				msg = new Message();	
				msg.getData().putInt("ProgrameType", ProgramType.LOGIN_CONNECT_SERVER );  
				m_oProgressHandler.sendMessage(msg); 	
				break;
			}
		}
    }
}


