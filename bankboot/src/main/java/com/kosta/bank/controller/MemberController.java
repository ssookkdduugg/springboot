package com.kosta.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.dto.Account;
import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private HttpSession session;
	
	// 로그인
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
		try {
			System.out.println("----로그인 POST 메서드 호출");
			Member member = memberService.login(id, password);
			member.setPassword("");
			session.setAttribute("member", member);
			return "makeaccount";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("member");
		return "login";
	}
	
	// 회원가입
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	@PostMapping("/join")
	public String join(@ModelAttribute Member member, Model model) {
	// public String join(Member member, Model model) { // 이렇게 해도 됨(위는 명식적해준것)
		try {
			memberService.join(member);
			return "login";
			// return "redirect:/login"; // cf. 리다이렉트는 포워드와 달리 데이터 공유하지 않으며 GET방식 요청이므로 get /login으로 매핑된 메서드가 호출된다

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}

}