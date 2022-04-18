package com.example.demo.besa64;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class besa64toimg {
	public boolean GenerateImage(String imgStr) throws IOException{   //對位元組陣列字串進行Base64解碼並生成圖片
		Base64.Decoder decoder = Base64.getDecoder();
		String imgpath ="D:\\si1274\\Desktop\\html\\圖片\\new.jpg";
		OutputStream ops=null;
		
		try {
			byte[] b = decoder.decode(imgStr);
			
			ops = new FileOutputStream(imgpath);
			ops.write(b);
			
			return true;
		}catch (Exception e) {
			return false;
		}
		finally {
			ops.close();
		}
		
    }
}

