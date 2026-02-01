package com.boot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * BankOperationController
 * 
 * This controller handles all bank-related operations like checking balance, viewing offers, and loan approval
 * It contains different endpoints with different access levels configured in SecurityConfig
 */
@Controller   //marks this class as Spring MVC controller, handles web requests and returns view names
public class BankOperationController {
	
	@GetMapping   //if we are not giving any request path it will consider as "/" - this is the home page
	public String showHome() {
		return "welcome";   //return LVN (Logical View Name) - welcome.jsp will be rendered
	}
	
	@GetMapping("/offers")   //this endpoint is for offers page, any authenticated user can access (configured in SecurityConfig)
	public String showOffers() {
		return "offers_page";   //return LVN - offers_page.jsp will be rendered
	}
	
	@GetMapping("/checkBalance")   //this endpoint is for checking balance, only USER and MANAGER roles can access
	public String showBalance() {
		return "balance_page";   //return LVN - balance_page.jsp will be rendered
	}
	
	@GetMapping("/approveloan")   //this endpoint is for loan approval, only MANAGER role can access, if USER tries to access it will show denied page
	public String loanApprove() {
		return "loanApprove_page";   //return LVN - loanApprove_page.jsp will be rendered
	}
	
	@GetMapping("/denied")   //this is access denied page, shown when user doesn't have permission to access certain page
	public String deniedPage() {
		return "denied";   //return LVN - denied.jsp will be rendered with message "You don't have permission"
	}
}

/*
 * 						Controller Endpoints Summary
 * 
 * URL: /
 * Access: Public (permitAll)
 * Role Required: None
 * View: welcome.jsp
 * Description: Home page of the application
 * 
 * URL: /offers
 * Access: Authenticated users only
 * Role Required: Any logged-in user
 * View: offers_page.jsp
 * Description: Shows available offers to logged-in users
 * 
 * URL: /checkBalance
 * Access: Role-based
 * Role Required: USER or MANAGER
 * View: balance_page.jsp
 * Description: Shows account balance
 * 
 * URL: /approveloan
 * Access: Role-based
 * Role Required: MANAGER only
 * View: loanApprove_page.jsp
 * Description: Loan approval functionality for managers
 * 
 * URL: /denied
 * Access: Public (permitAll)
 * Role Required: None
 * View: denied.jsp
 * Description: Access denied error page shown when user lacks permission
 * 
 * 
 * 						How It Works with Spring Security
 * 
 * 1. User requests a URL (example: /approveloan)
 * 2. Request goes through Spring Security filter chain
 * 3. Security checks if user is authenticated (logged in)
 * 4. If not authenticated -> redirect to login page
 * 5. If authenticated -> check user's role
 * 6. If user has required role -> forward to controller method
 * 7. Controller returns view name (LVN)
 * 8. ViewResolver resolves view name to actual JSP file
 * 9. JSP page is rendered and sent to browser
 * 
 * If user doesn't have required role:
 * - AccessDeniedException is thrown
 * - ExceptionHandler catches it
 * - User redirected to /denied page
 * 
 */