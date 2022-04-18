package com.example.demo.Service;

import com.example.demo.dao.DbSpringBootDao;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserVo;
@Service
public class DbSpringBootServiceImpl implements DbSpringBootService {

	@Autowired //自動呼叫dao
	private DbSpringBootDao DbSpringBootDao; 
	
	@Override
	public List<UserVo> findAllUser() {   //取出全部資料 打包成物件
		
		return DbSpringBootDao.findAll();
	}

	@Override //查詢單筆資料
	public UserVo findById(int userId) {
		
		//可以用來判斷 或 get丟出東西
		Optional<UserVo> result = DbSpringBootDao.findById(userId);
		
		UserVo theuser = null;
		
		if(result.isPresent()) {	//判斷是否有物件 讀取到 
			theuser = result.get();	//有的話get到 theuser
		}else {
			throw new RuntimeException("Did not find user id"+userId);	//沒抓到此PK值
		}
		
		return theuser; //回傳抓到的物件
	}

	@Override //儲存單筆資料 or PK相同時 將取代原本資料
	public void saveUser(UserVo theUser) {
		DbSpringBootDao.save(theUser);
		
	}

	@Override //儲存多個物件
	public void saveAllUser(List list) {
		DbSpringBootDao.saveAll(list);
	}

	@Override //刪除單一pk所找到的物件
	public void deleteById(int userId) {
		DbSpringBootDao.deleteById(userId);
		
	}

	@Override //刪除全部資料
	public void deleteAllBatch() {
		DbSpringBootDao.deleteAllInBatch();
	}

	@Override
	public UserVo findByEmail(String email) {
		Optional<UserVo> result = DbSpringBootDao.findByEmail(email);
		
		UserVo theuser = null;
		
		if(result.isPresent()) {	//判斷是否有物件 讀取到 
			theuser = result.get();	//有的話get到 theuser
			return theuser; //回傳抓到的物件
		}else {
//			throw new RuntimeException("Did not find user id"+email);	//沒抓到此PK值
			return null;
		}
		
		
	}
	
	

}
