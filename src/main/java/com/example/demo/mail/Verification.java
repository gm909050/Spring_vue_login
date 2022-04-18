package com.example.demo.mail;

public class Verification {
	public StringBuffer genAuthCode(char array[]) {
		//�]�w�� char 48~57 = 0~9
		//�]�w�� char 65~90 = A~Z
		//�]�w�� char 97~123 = a~z
		char x = 48;
		char q = 48;
		char r = 48;
		char y = 65;
		char z = 97;
			//�}�C0~9 ����0~9
			for(int i = 1 ;i<10;i++) {
				array[i] = x;
				x++;
			}
			//�}�C10~35 ����A~Z
			for(int i = 10;i<36;i++) {
				array[i] = y;
				y++;
			}
			//�}�C��36~61 ����a~z
			for(int i = 36;i<62;i++) {
				array[i] = z;
				z++;
			}
			//�}�C62~71 ����0~9
			for(int i = 62 ;i<72;i++) {
				array[i] = q;
				q++;
			}
			//�}�C0~9 ����0~9
			for(int i = 72 ;i<81;i++) {
				array[i] = r;
				r++;
			}
			StringBuffer sb = new StringBuffer();
			//�}�C�a�J�j��8��
			for(int i = 0 ; i<8;i++) {
				int a = (int)(Math.random()*81+1);
				sb.append((char)array[a]);
				
			}
			return sb;
	}
}
