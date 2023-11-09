package com.kosta.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@GetMapping("/makeaccount")
	public String makeaccount() {
		return "makeaccount";
	}
	
	@GetMapping("/deposit")
	public String deposit() {
		return "deposit";
	}
	
	@GetMapping("/withdraw")
	public String withdraw() {
		return "withdraw";
	}
	
	@GetMapping("/accountinfo")
	public String accountinfo() {
		return "accountinfoform";
	}
}
