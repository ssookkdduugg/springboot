package com.kosta.bank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	
	// 계좌개설
	@GetMapping("/makeaccount")
	public String makeaccount() {
		return "makeaccount";
	}
	@PostMapping("/makeaccount")
	public ModelAndView makeAccount(@ModelAttribute Account acc) {
		ModelAndView mav = new ModelAndView();
		try {
			accountService.makeAccount(acc);
			Account sacc = accountService.accountInfo(acc.getId());
			mav.addObject("acc", sacc);
			mav.setViewName("accountinfo");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}

	
	// 입금
	@GetMapping("/deposit")
	public String deposit() {
		return "deposit";
	}
	@PostMapping("/deposit")
	public ModelAndView deposit(@RequestParam("id") String id, @RequestParam("money") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			Account acc = accountService.deposit(id, money);			
			
			// mav.addObject("acc", acc);
			// mav.setViewName("accountinfo");
			
			mav.setViewName("forward:/accountinfo"); 
			
			/*
			 mav.setViewName("forward:/accountinfo");하면 /accountinfo로 매핑된 계좌조회 POST 메서드를 호출한다
			 뷰이름 대신 forward:/URI를 지정하면 /accountinfo로 포워드하는것임
			 포워드는 URL변경이 일어나지 않는다.(즉 브라우저에서 URL재요청하지 않는다) 따라서 /accountinfo로 매핑된 메서드가 호출됐으나 URI는 여전히 /deposit이다
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	
	// 출금
	@GetMapping("/withdraw")
	public String withdraw() {
		return "withdraw";
	}
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam("id") String id, @RequestParam("money") Integer money, Model model) {
		try {
			Account acc = accountService.withdraw(id, money);
			
			// model.addAttribute("acc", acc);
			// return "accountinfo";

			
			// model.addAttribute("새로운 파라미터", 새로운 파라미터);
			return "forward:/accountinfo"; // id를 mav에 넣고 포워드하지 않아도 /accountinfo로 URI매핑된 accountinfo POST메서드가 id를 받으면서 호출된다
			// return "redirect:/accountinfo"; // cf. forward:/URI했을때는 POST메서드로 찾아가고 redirect:/URI했을때는 GET메서드로 찾아감
			
			/*
			 포워드는 파라미터를 공유한다. 따라서 withdraw메서드가 받은 id를 포워드되어 호출되는 accountinfo메서드 역시 사용할 수 있다. 즉 id를 mav에 담아 포워드하지 않아도 id 파라미터를 받는다,
			 추가적으로 전달하는 새로운 파라미터만 Model 또는 ModelAndView에 담아서 포워드하면 된다.
			 
			 POST메서드에서 포워드할경우 해당URI로 매핑된 POST메서드를 찾아간다. GET메서드에서 포워드하면 GET메서드를 찾아감
			 
			 리다이렉트는 무조건 GET방식 요청이며 따라서 많은 파라미터 전달에 부적절하다. 리다이렉트는 URL재요청으로 URL변경이 일어난다. 리다이렉트는 GET방식 요청이기 때문에 파라미터를 공유하고자하는 경우에는 쿼리스트링으로 붙이면 된다. 
			 페이지 이동 단계가 많고 파라미터를 모두 공유해야하는 경우에는 포워드 방식이 훨씬 유리하다. 리다이렉트 요청의 경우 파라미터를 URL에 달고 요청하게되며 그렇게되면 보안상의 이슈가 발생한다.
			 
			 리다이렉트는 가볍고 데이터노출돼도 괜찮고 간단한 이동의 경우에 적합
			 포워드는 전달할 데이터가 많고 보안이 중요한 경우에 적합
			 */ 
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	
	// 계좌조회
	@GetMapping("/accountinfo")
	public String accountinfo() {
		return "accountinfoform";
	}
	@PostMapping("/accountinfo")
	public ModelAndView accountinfo(@RequestParam("id") String id) {
		System.out.println("----계좌조회 POST 메서드 호출");
		ModelAndView mav = new ModelAndView();
		try {
			Account acc = accountService.accountInfo(id);
			mav.addObject("acc", acc);
			mav.setViewName("accountinfo");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	
	// 전체계좌조회
	@GetMapping("/allaccountinfo")
	public ModelAndView allaccountinfo() {
		ModelAndView mav = new ModelAndView();
		try {
			List<Account> accs = new ArrayList<>();
			accs = accountService.allAccountInfo();
			mav.addObject("accs", accs);
			mav.setViewName("allaccountinfo");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}

}