	package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.repository.cdi.Eager;

@Entity
@Table(name="TABLESPRING")
public class UserVo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq") //orcale 使用
	// 這邊的sequenceName對應DB的sequence
	@SequenceGenerator(sequenceName = "courseseq", allocationSize = 1, name = "course_seq")//orcale 使用

	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID") // 欄位名稱
	private Integer id;

	@Column(name = "姓名")
	private String name;
	
	@Column(name="AGE")
	private Integer age;
	
	@Column(name="帳號")
	private String email;
	
	@Column(name="密碼")
	private String password;
	
	public UserVo() {
		
	}

	public UserVo(Integer id, String name, Integer age, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", password=" + password
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
		
}
