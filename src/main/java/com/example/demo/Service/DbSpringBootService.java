package com.example.demo.Service;

import java.util.List;

import com.example.demo.entity.UserVo;

public interface DbSpringBootService {
	public List<UserVo> findAllUser(); //尋找全部USER
	
	public UserVo findById(int userId); //尋找單一USER
	
	public UserVo findByEmail(String email);
	
	public void saveUser(UserVo theUser);//儲存單一比USER資料
	
	public void saveAllUser(List list); //儲存多筆資料
	
	public void deleteById(int userId);//刪除單筆USER
	
	public void deleteAllBatch(); //刪除全部資料
}
