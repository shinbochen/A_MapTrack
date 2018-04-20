package com.smsmanager;

import java.util.Arrays;

import com.Protocol.AppData;



public class GSMPDU {

	public static final  byte		DCS_7BIT = 0x00;
	public static final  byte		DCS_8BIT = 0x01;
	public static final  byte		DCS_UNICODE = 0x02;
	

	//********************************************
	//
	public  GSMPDU(){
		
	}
	//********************************************
	//����: 0x68,0x31,0x08,0x00,0x75,0x80,0xF5
	//���: "8613800057085"
	public  static byte GSM_ParseTPOA(  byte[]	lpDest, 
									    int		nDestPos,
									    byte[]  lpSrc,
									    int		nSrcPos,
									    int		nLen){
	
		byte		nCnt = 0;
		byte		nTmp;
		
		for( nCnt = 0; nCnt < nLen; nCnt++){
			
			nTmp = lpSrc[nSrcPos+nCnt];
			lpDest[nDestPos+nCnt*2] = (byte)((nTmp&0x0F)+ 0x30);
			nTmp >>= 4;			
			nTmp &= 0x0F;
			if( nTmp == 0x0F ){
				lpDest[nDestPos+nCnt*2+1] = 0x00;
			}
			else{
				lpDest[nDestPos+nCnt*2+1] = (byte)(nTmp+0x30);
			}	
		}
		lpDest[nDestPos+nCnt*2] = 0x00;
		return 0;
	}
	//********************************************
	//���ű� �뷽ʽ	
	public static byte GSM_GetDCSType( byte nType ){
		
		byte	nResult = DCS_7BIT;
		if( (nType & 0xC0) == 0x00 ){
		
			switch( nType&0x0C ){
			case 0x00:
				nResult = DCS_7BIT;
				break;
			case 0x04:
				nResult = DCS_8BIT;
				break;
			case 0x08:
				nResult = DCS_UNICODE;
				break;	
			default:
			break;	
			}	
		}
		else if( (nType & 0xF0) == 0xF0 ){
			if( (nType & 0x04) > 0 ){
				nResult = DCS_8BIT;		
			}
			else{
				nResult = DCS_7BIT;		
			}	
		}
		return nResult;
	}
	//*****************************************************
	//����:0x08,0x91,0x68
	//from"0891683108705505F0040D91683135804115F9000080508241947523041BE08602"
	//
	//���:	
	//pBuf: 			TP_UD
	//pSMC: 			SMSC
	//pCallerNo: TP_OA
	//pDCSType:  DCS type
	//����: ���ݳ���
	public static SMSData  GSM_ParsePDUSMS(  byte[]	pSrc ){
	
		int			nLen = 0;
		int			nTmp = 0;
		int			nDataLen = 0;
		byte[]		nBuf = null;
		SMSData		oSMSData = null;
		
		
		oSMSData = new SMSData();
		// ����ʱ���봦��
		nBuf = new byte[20];
		Arrays.fill(nBuf, (byte)0x00);
		// ===============================================
		// TP_SMSC
		// ===============================================
		nTmp = pSrc[0];
		// SMSC��ַ��ʽ(TON/NPI) 
		// �����ʽ�����ʾ��0x91 = 10010001 
		// Bit7 = 1
		// ��ֵ���ͣ�Type of Number����000��δ֪��001�����ʣ�010������,111��������չ�� 
		// �������Numbering plan identification��:0000��δ֪��  
		// 0001��ISDN/�绰����(E.164/E.163)
		// TON Value
		// Definition	 
		//	0	 Unknown		 
		//	1	 International Number		 
		//	2	 National Number		 
		//	3	 Network Specific Number		 
		//	4	 Subscriber Number		 
		//	6	 Abbreviated Number		    
		//	7	 Reserved
		
		if( nTmp > 0 ){			
			if( (pSrc[1]&0x70) == 0x10 ){	
				
				nBuf[0] = '+';
				GSM_ParseTPOA( nBuf, 1, pSrc, 2, nTmp-1);		
			}	  
			else{
				GSM_ParseTPOA( nBuf, 0, pSrc, 2,  nTmp-1);		
			}
		}
		if( (nBuf[0] == '0') && (nBuf[1] == '0') ){
			
			nBuf[0] = '+';
			System.arraycopy( nBuf, 2, nBuf, 1, nBuf.length-2 );
		}	
		oSMSData.setCenterPhone( AppData.ByteToString(nBuf) );
		nDataLen = nTmp+1;
		
		// ===============================================
		// TP_MTI
		// ===============================================
		// BIT No.  7  6  5  4  3  2  1  0  
		// Name  TP-RP  TP-UDHI  TP-SPR  TP-VFP  TP-RD  TP-MTI  
		// Value  0  0  0  1  0  0  0  1
		nDataLen += 1;
		// ===============================================
		// TP-OA
		// ===============================================	
		nTmp = pSrc[nDataLen++];
		nLen = nTmp/2;
		if( (nTmp%2) > 0 ){
			nLen++;
		}
		Arrays.fill(nBuf, (byte)0x00);
		nTmp = pSrc[nDataLen++];
		if( (nTmp & 0x70 ) == 0x10 ){
			nBuf[0] = '+';
			GSM_ParseTPOA(nBuf, 1, pSrc, nDataLen, nLen );
		}
		else{
			GSM_ParseTPOA(nBuf, 0, pSrc, nDataLen, nLen );
		}
		if( (nBuf[0] == '0') && (nBuf[1] == '0') ){
			nBuf[0] = '+';
			System.arraycopy( nBuf, 2, nBuf, 1, nBuf.length-2 );
		}
		oSMSData.setPhone( AppData.ByteToString(nBuf) );
		nDataLen += nLen;
		
		// ===============================================
		//TP_PID����
		// ===============================================
		nDataLen += 1;
		
		// ===============================================
		// TP-DCS���뷽ʽ
		// ===============================================	
		oSMSData.setDCSType(pSrc[nDataLen++]);
		
		// ===============================================
		// TP-SCTS������
		// ===============================================
		// ʱ�� ��/��/�� ʱ:��:��
		//GSM_ParseTPTime( g_stDwonState.m_nTmpTime, pSrc, 6 );
		nDataLen += 7;
		
		// ===============================================
		// TP_DATA����
		// ===============================================
		nLen = pSrc[nDataLen++]; 
		oSMSData.setTPUDLen( (byte)(nLen&0xFF) );
		
		if( GSM_GetDCSType( oSMSData.getDCSType() ) == DCS_7BIT ){
			nLen *= 7;
			if( (nLen % 8) > 0 ){
				nLen /= 8;
				nLen++;			
			}
			else{
				nLen /= 8;
			}
		}
		oSMSData.setSMSData(pSrc, nDataLen, nLen);	
		return oSMSData;
	}
	///////////////////////////////////////////////////////////////////////////
	//7-bit����
	//pSrc:		Դ�ַ���ָ��
	//pDst:		Ŀ����봮ָ��
	//nSrcLength:  Դ�ַ�������
	//����:		Ŀ����봮����
	public static int P_Code8BitTo7bit( byte[] pDest, byte[] pSrc, int nSrcLength)
	{
		int 	nSrc;				// Դ�ַ����ļ���ֵ
		int 	nDst;				// Ŀ����봮�ļ���ֵ
		byte 	nChar;				// ��ǰ���ڴ���������ַ��ֽڵ���ţ���Χ��0-7
		byte 	nLeft=0;    			// ��һ�ֽڲ��������
		
		int 	nData = 0;
		
		nSrc = 0;
		nDst = 0;		
		while(nSrc <= nSrcLength){
		
			nData = pSrc[nSrc]&0xFF;			
			switch( nData ){
			case '@':
				nData = 0x00;
				break;
			case '$':
				nData = 0x02;
				break;
			default:
				break;
			}	
			nChar = (byte)(nSrc & 7);    
			if(nChar == 0){
				nLeft = (byte)(nData&0xFF);
			}
			else{			
				pDest[nDst] = (byte)((nData << (8-nChar)) | nLeft);    
				nLeft = (byte)(nData >> nChar);
				nDst++; 
			}         
			nSrc++;
		}    
		return nDst; 
	} 
	///////////////////////////////////////////////////////////////////////////
	//7-bit����
	//pSrc:		Դ���봮ָ��
	//pDest:		Ŀ���ַ���ָ��
	//nSrcLength:  Դ���봮����
	//����:		Ŀ���ַ�������
	public static int P_Decode7bitTo8Bit( byte[] pDest,  byte[] pSrc, int nSrcLength)
	{
		byte 		nSrc = 0;			// Դ�ַ����ļ���ֵ
		byte 		nDst = 0;			// Ŀ����봮�ļ���ֵ
		byte 		nByte= 0;			// ��ǰ���ڴ���������ֽڵ���ţ���Χ��0-6
		int 		nLeft;    			// ��һ�ֽڲ��������
		
		nSrc = 0;
		nDst = 0;
		
		nByte = 0;
		nLeft = 0;
		Arrays.fill(pDest, (byte)0x00);
		
		int  		nData = 0;
		
		while(nSrc<nSrcLength){		
			
			nData = pSrc[nSrc]&0xFF;			
			nData <<= nByte;
			
			nData |= nLeft;
			nData &= 0x7f;
			pDest[nDst] = (byte)(nData&0xFF);
			
			nLeft = pSrc[nSrc]&0xFF;
			nLeft = nLeft >> (7-nByte);
			
		    nDst++;    
		    nSrc++;
		    nByte++;    
		    if(nByte == 7){
			    pDest[nDst] = (byte)(nLeft&0xFF); 
		        nDst++;    
		        nByte = 0;
		        nLeft = 0;
		    }    
		}    
		pDest[nDst] = 0;    
		return nDst;
	}
	//////////////////////////////////////////////////////////////////////////
	//GSM����ASCII��
	public static int P_GSMToASCII(  byte[] lpDestBuf, byte[]  lpSrcBuf, byte nLen )
	{
	
		int				nCnt    = 0;
		int				nResult = 0;
		byte			nChar   = 0;
		
		for( nCnt = 0; nCnt < nLen; nCnt++ ){
		
			nChar = lpSrcBuf[nCnt];
			if( nChar == 0x1b){
				nCnt++;
				nChar = lpSrcBuf[nCnt];
				switch( nChar ){
				case 0x14:
				nChar = '^';
				break;
				case 0x28:
				nChar = '{';
				break;
				case 0x29:
				nChar = '}';
				break;
				case 0x2F:
				nChar = '\\';
				break;
				case 0x3C:
				nChar = '[';
				break;
				case 0x3D:
				nChar = '~';
				break;
				case 0x3E:
				nChar = ']';
				break;
				case 0x40:
				nChar = '|';
				break;
				default:
				nChar = ' ';
				break;
				}
			}
			else{
				// ת���������õ�
				if( nChar < 0x20 ){
					switch( nChar ){
					case 0:
						nChar ='@';
						break;
					case 2:
						nChar ='$';
						break;
					case 0x0D:
					case 0x0A:	
						break;
					default:
						nChar = ' ';
						break;
					}
				}	
				else{
					// ��O�ֲ�֧��
					if( nChar > 0 ){
						switch( nChar){
						case 0x24:
						case 0x40:
						case 0x60:	
						case 0x5B:		
						case 0x5C:	
						case 0x5D:	
						case 0x5E:	
						case 0x5F:	
						case 0x7B:		
						case 0x7C:	
						case 0x7D:	
						case 0x7E:	
						case 0x7F:
							nChar = ' ';
						default:
						break;
						}
					}
				}
			}
			lpDestBuf[nResult] = nChar; 
			nResult++;
		}
		lpDestBuf[nResult] = 0x00;
		return nResult;
	}
}
