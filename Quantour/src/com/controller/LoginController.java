

package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LoginController {
	@RequestMapping(value="/toLogin.do")
	public String toLogin(Model model) throws Exception{
		System.out.println("登录成功"+System.currentTimeMillis());
		return "login";
	}
	@RequestMapping(value="/login.do")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model) throws Exception{
		String user = username;
		System.out.println(user + password);
		return "success";
	}

}