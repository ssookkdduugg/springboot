package com.kosta.bank.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosta.bank.dto.Account;

@Mapper // 매퍼xml에 namespace지정된 DAO인터페이스를 자동 매핑시켜주는 어노테이션
@Repository
public interface AccountDao { // account.xml에 등록해둔 인터페이스 <mapper namespace="com.kosta.bank.dao.AccountDao">에 해당
// 

	// 계좌개설
	void insertAccount(Account acc) throws Exception;
	
	// 계좌조회
	Account selectAccount(String id) throws Exception;
	
	// 입금, 출금
	/* 파라미터가 여러개일때 DAO인터페이스에서 @Param어노테이션을 통해 지정해주면 매퍼xml의 parameterType를 작성해두지 않아도 된다
	   cf. 원래 매퍼에 여러타입 파라미터를 전달할 수 없다(primitive타입 하나 혹은 객체 타입 하나 혹은 Map을 전달하는 식이었음) */
	void updateBalance(@Param("id") String id, @Param("balance") Integer balance) throws Exception;
	
	// 전체계좌조회
	List<Account> selectAccountList() throws Exception;
}