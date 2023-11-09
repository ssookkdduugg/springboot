package com.kosta.bank.service;

import org.springframework.stereotype.Service;

import com.kosta.bank.dto.Member;

public interface MemberService {
	
	// 컨트롤러에서 세션에 넣기 위해 void가 아니라 Member를 반환해야한다
	Member login(String id, String password) throws Exception;
	
	void join(Member member) throws Exception;

}