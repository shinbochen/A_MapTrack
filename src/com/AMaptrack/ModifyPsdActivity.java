package com.AMaptrack;

import com.Data.GlobalData;
import com.Language.Language;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyPsdActivity extends Activity {

	public 	EditText			m_editOldPsd = null;
	public  EditText			m_editnewPsd = null;
	public  EditText			m_editnewPsd2 = null;
	
	public  TextView			m_txOldPsd;
	public  TextView			m_txNewPsd;
	public  TextView			m_txNewPsd2;
	
	public  Button				m_bthOK = null;
	public  Button				m_bthCancel = null;
	
	//**************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView( R.layout.modifypsd );
		
		initEventObj();
		initEvent();
		initLabels();
	}
	//*************************************************
	//
	public  void  initEventObj(){
		
		m_editOldPsd = (EditText)findViewById(R.id.edit_OldPsd );
		m_editnewPsd = (EditText)findViewById(R.id.edit_newPsd );
		m_editnewPsd2 = (EditText)findViewById(R.id.edit_newPsd2 );
		
		m_txOldPsd = (TextView)findViewById(R.id.text_OldPsd );
		m_txNewPsd = (TextView)findViewById(R.id.text_newPsd );
		m_txNewPsd2 = (TextView)findViewById(R.id.text_newPsd2 );
		
		m_bthOK = (Button)findViewById(R.id.OKButton );
		m_bthCancel = (Button)findViewById(R.id.cancelButton );
	}
	//*************************************************
	//
	public void  initLabels(){
		
		m_txOldPsd.setText( Language.getLangStr( Language.TEXT_OLD_PASSWORD) );
		m_txNewPsd.setText( Language.getLangStr( Language.TEXT_NEW_PASSWORD) );
		m_txNewPsd2.setText( Language.getLangStr( Language.TEXT_NEW_PASSWORD2) );
						
		m_bthOK.setText( Language.getLangStr( Language.TEXT_OK) );
		m_bthCancel.setText( Language.getLangStr( Language.TEXT_CANCEL) );		
	}
	//*************************************************
	//
	public  void  initEvent(){
		
		m_bthOK.setOnClickListener( eventOk );
		m_bthCancel.setOnClickListener( eventCancel );
	}
	//********************************************
	//
	OnClickListener  eventOk = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			String		strOldPsd = m_editOldPsd.getText().toString();
			String		strNewPsd = m_editnewPsd.getText().toString();
			String		strNewPsd2 = m_editnewPsd2.getText().toString();
			
			// 旧密码不同
			if( GlobalData.getUserInfo().getUserPsd().equals(strOldPsd) == false ){
				
				showWarringDialog( Language.getLangStr(Language.TEXT_ENTER_PSD_OLD_PSD));
				return ;
			}
			// 两次输入密码不同
			if( strNewPsd.equals(strNewPsd2) == false ){
				
				showWarringDialog( Language.getLangStr(Language.TEXT_ENTER_TWICE_PASSWORD));
				return ;
			}
			Bundle bunde = new Bundle();
			Intent	intent = new Intent(); 
			
			bunde.putString(ConfigKey.KEY_OLDPSD , strOldPsd );
			bunde.putString(ConfigKey.KEY_NEWPSD , strNewPsd );
			bunde.putString(ConfigKey.KEY_NEWPSD2 , strNewPsd2 );				
			intent.putExtras( bunde );			
			setResult(RESULT_OK, intent );
			finish();
		}
	};
	//********************************************
	//
	OnClickListener  eventCancel = new OnClickListener(){

		@Override
		public void onClick(View v) {

			finish();
		}
	};

	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(ModifyPsdActivity.this);
		
		builder.setTitle( Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//**************************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	
	//**************************************************
	//
	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
