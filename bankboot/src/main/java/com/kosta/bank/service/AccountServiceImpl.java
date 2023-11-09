package com.kosta.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dao.AccountDao;
import com.kosta.bank.dto.Account;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;

	
	// 계좌개설
	@Override
	public void makeAccount(Account acc) throws Exception {
		Account sacc = accountInfo(acc.getId());
		if(sacc!=null) throw new Exception("계좌번호 중복");
		accountDao.insertAccount(acc);
	}

	// 계좌조회
	@Override
	public Account accountInfo(String id) throws Exception {
		return accountDao.selectAccount(id);
	}

	// 입금
	@Override
	public Account deposit(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		if(acc==null) throw new Exception("계좌번호 오류");
		// accountDao.updateBalance(id, money);
		acc.deposit(money);
		accountDao.updateBalance(id, acc.getBalance());
		return acc;
	}

	// 출금
	@Override
	public Account withdraw(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		if(acc==null) throw new Exception("계좌번호 오류");
		acc.withdraw(money);
		accountDao.updateBalance(id, acc.getBalance());
		return acc;
	}
	
	// 전체계좌조회
	@Override
	public List<Account> allAccountInfo() throws Exception {
		return accountDao.selectAccountList();
	}


}