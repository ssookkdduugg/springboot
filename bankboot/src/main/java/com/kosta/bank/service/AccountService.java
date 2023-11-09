package com.kosta.bank.service;

import java.util.List;

import com.kosta.bank.dto.Account;

public interface AccountService {
	
	// 계좌개설
	void makeAccount(Account acc) throws Exception;
	
	// 계좌조회
	Account accountInfo(String id) throws Exception;
	
	// 입금
	Account deposit(String id, Integer money) throws Exception;
	
	// 출금
	Account withdraw(String id, Integer money) throws Exception;
	
	// 전체계좌조회
	List<Account> allAccountInfo() throws Exception;

}