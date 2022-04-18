package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.DbSpringBootService;
import com.example.demo.besa64.besa64toimg;
import com.example.demo.entity.UserVo;
import com.example.demo.mail.Verification;
import com.example.demo.mail.mail;


@CrossOrigin // 啟動CORS 跨網域
@RestController // 判斷此class 為controller 啟動 可直接將物件轉為json 判斷回傳回資料
@RequestMapping("/springbootusers") // 用來呼叫這個mapping
public class DbSpringBootController {

	private final DbSpringBootService dbspringbootservice;
	

	
	
	
	@Autowired
	public DbSpringBootController(DbSpringBootService dbspringbootservice) {
		this.dbspringbootservice = dbspringbootservice;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate; // jdbc使用

	@GetMapping("/getUsers") // 使用jdbc sql指定 呼叫方法
	public List<Map<String, Object>> getUsers() {
//		HttpSession session = null ;				

		String sql = "select * from TABLESPRING"; // SQL指令語法
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);// 將抓到資料放到LIST內

//		session.setAttribute("alluser", list);
		System.out.println(list.toString());
		return list; // 並回傳
	}

	@GetMapping("/getlist") // 抓取全部資料
	public List listUser(Model theModel) {
		List<UserVo> theUsers = dbspringbootservice.findAllUser(); // 抓取全部資料 並放在LIST內
//		theModel.addAttribute("theuser" ,theUsers); 
//		List list = (List)theModel.getAttribute("theuser");

		return theUsers;
	}

	@PostMapping("/oneuser") // 抓取單一資料 @RequestParam("前端回傳的key名稱")
	public Object oneuser(@RequestParam("userId") int userId, Model theModel) {
		UserVo user = dbspringbootservice.findById(userId); // 將抓到資料放入物件
		System.out.println(user.toString());

		return user; // 回傳抓到的資料
	}

	@PostMapping("/loginemail") // 以mail取得該物件 並檢查
	public List loginemail(@ModelAttribute("user") UserVo theUser, Model theModel, HttpSession s) {
		List<String> errormessage = new ArrayList<String>();
		List sureuser = new ArrayList();
		UserVo testuser = dbspringbootservice.findByEmail(theUser.getEmail());

		if (testuser == null) {
			System.out.println("查無此帳號");
			errormessage.add("請確認帳號");
			return errormessage;

		} else if (!theUser.getPassword().equals(testuser.getPassword())) {
			System.out.println("請確認密碼");
			errormessage.add("請確認密碼");
			return errormessage;

		} else {
			System.out.println("登入成功");
			s.setAttribute("usersession", testuser);
			sureuser.add(testuser);
			System.out.println(testuser.toString());
			return sureuser;
		}

	}

	@PostMapping("/save") // 儲存物件到資料庫
	public String seveUser(@ModelAttribute("user") UserVo theUser) {

		UserVo testuser = dbspringbootservice.findByEmail(theUser.getEmail());

		if (testuser == null) { // 判斷此email 是否有人使用 沒人使用儲存
			System.out.println("此帳號可使用");
			dbspringbootservice.saveUser(theUser);
			return "新增成功";
		} else {// 資料庫有這筆資料
			System.out.println("此帳號不可使用");
			return null;
		}

//		System.out.println(testuser.toString());
//		return "不能使用";
//		dbspringbootservice.saveUser(theUser);

	}

	@PostMapping("/updata")
	public List updataUser(@ModelAttribute("user") UserVo theUser) {
		List updatatrue = new ArrayList();

		dbspringbootservice.saveUser(theUser);
		updatatrue.add("更新成功");

		return updatatrue;
	}

	@PostMapping("/forgetpassword")
	public List forgetpassword(@ModelAttribute("user") UserVo theUser,HttpServletRequest req,HttpServletResponse res) {
		List forgettrue = new ArrayList();
		List errormessage = new ArrayList();
		char array[] = new char[82];
		String questionmatter = req.getParameter("questionmatter");
		String questioncontent = req.getParameter("questioncontent");
		
		System.out.println(questionmatter);
		System.out.println(questioncontent);
		
		UserVo testuser = dbspringbootservice.findByEmail(theUser.getEmail());
		

		if (testuser == null) {
			errormessage.add("查無此帳號");
			System.out.println("查無此帳號");
			return errormessage;
		}

		if (testuser.getEmail().equals(theUser.getEmail())) {
			if (testuser.getName().equals(theUser.getName())) {
				
				Verification ver = new Verification();
				StringBuffer strsb = ver.genAuthCode(array);
				String password =strsb.toString();
				
				System.out.println(password.replace(" ", ""));
				
				String subject = "會員您好";
				String messageText = "會員:" + testuser.getName() +"請使用此密碼登入:"+password.replace(" ", "").trim()+"  並更改密碼  "+"http://127.0.0.1:5500/logindb.html";
				
				System.out.println(messageText);
				UserVo user = new UserVo(testuser.getId(),testuser.getName(),testuser.getAge(),testuser.getEmail(),password);
				dbspringbootservice.saveUser(user);
				

			System.out.println(testuser.getEmail());
			System.out.println(subject);
			System.out.println(messageText);
			mail mail = new mail();
			mail.sendMail(testuser.getEmail(), subject, messageText);
				
			forgettrue.add("請至信箱收取密碼");
				
			} else {
				forgettrue.add("請確認身分");
				System.out.println("請確認身分");
			}
			return forgettrue;

		}
		
		return forgettrue;
	}

	@GetMapping("/delete") // 刪除資料庫這筆資料
	public String deleteUser(@RequestParam("userId") int userId, Model theModel) {
		dbspringbootservice.deleteById(userId);
		
		return "刪除成功";
	}
	
	@PostMapping("/picture")
	@ResponseBody
	public void updatapicture(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String picture = req.getParameter("image");
	    
		besa64toimg besa64 = new besa64toimg();
		
		if(picture.contains("/png")) {
			besa64.GenerateImage(picture.substring(22));
			System.out.println(picture);
			if(besa64.GenerateImage(picture.substring(22))) {
				System.out.println("已匯出");
			}else {
				System.out.println("未匯出");
			}
		}else {
			besa64.GenerateImage(picture.substring(23));
			System.out.println(picture);
			if(besa64.GenerateImage(picture.substring(23))) {
				System.out.println("已匯出");
			}else {
				System.out.println("未匯出");
			}
		}
		
		
	}
	@PostMapping("/word")
	public void reword(@RequestParam("formData") MultipartFile file) {
		System.out.println("12213");
//		System.out.println(file);
	}

}
