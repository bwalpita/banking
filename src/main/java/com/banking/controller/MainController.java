package com.banking.controller;

import java.util.List;

import com.banking.dao.AppUserDAO;
import com.banking.dao.CountryDAO;
import com.banking.frombean.AppUserForm;
import com.banking.frombean.UserBalanceForm;
import com.banking.frombean.UserWithdrawForm;
import com.banking.model.AppUser;
import com.banking.model.Country;
import com.banking.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

   @Autowired
   private AppUserDAO appUserDAO;

   @Autowired
   private CountryDAO countryDAO;

   @Autowired
   private AppUserValidator appUserValidator;

   // Set a form validator
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
      // Form target
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      if (target.getClass() == AppUserForm.class) {
         dataBinder.setValidator(appUserValidator);
      }
      // ...
   }

   @RequestMapping("/")
   public String viewHome(Model model) {

      return "welcomePage";
   }

   @RequestMapping("/members")
   public String viewMembers(Model model) {

      List<AppUser> list = appUserDAO.getAppUsers();

      model.addAttribute("members", list);

      return "membersPage";
   }
   
   @RequestMapping("/members/deposit/{userId}")
   public String viewMemberDeposit(Model model, @PathVariable Long userId) {

          
      UserBalanceForm form = new UserBalanceForm();
      
      model.addAttribute("balanceForm", form);
      model.addAttribute("userId", userId);
      
      System.out.println(userId);

      return "depositPage";
   }
   
   @RequestMapping(value = "/members/deposit/{userId}", method=RequestMethod.POST)
   public String saveMemberDeposit(Model model, @PathVariable Long userId, @ModelAttribute("balanceForm") @Validated UserBalanceForm userBalanceForm,//
		   	BindingResult result, //
	        final RedirectAttributes redirectAttributes) {
	   
	   if (result.hasErrors()) {
	         
	         return "depositPage";
	      }

     System.out.println(userBalanceForm.getAccbalance());
     
     if(appUserDAO.userDeposit(userBalanceForm.getAccbalance(), userId)) {
    	
    	 return "redirect:/members";
     }
      return "depositPage";
   }
   
   
//withdrawals   
   @RequestMapping("/members/withdraw/{userId}")
   public String viewMemberWithdarw(Model model, @PathVariable Long userId) {

          
      UserWithdrawForm form = new UserWithdrawForm();
      
      model.addAttribute("withdrawForm", form);
      model.addAttribute("userId", userId);
      
      System.out.println(userId);

      return "withdrawPage";
   }
   
   @RequestMapping(value = "/members/withdraw/{userId}", method=RequestMethod.POST)
   public String saveMemberWithdarw(Model model, @PathVariable Long userId, @ModelAttribute("withdrawForm") @Validated UserWithdrawForm userWithdrawForm,//
		   	BindingResult result, //
	        final RedirectAttributes redirectAttributes) {
	   
	   if (result.hasErrors()) {
	         
	         return "withdrawPage";
	      }

     System.out.println(userWithdrawForm.getAccbalance());
     
     if(appUserDAO.userWithdraw(userWithdrawForm.getAccbalance(), userId)) {
    	
    	 return "redirect:/members";
     }
      return "withdrawPage";
   }  
   

   @RequestMapping("/registerSuccessful")
   public String viewRegisterSuccessful(Model model) {

      return "registerSuccessfulPage";
   }

   // Show Register page.
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String viewRegister(Model model) {

      AppUserForm form = new AppUserForm();
      List<Country> countries = countryDAO.getCountries();

      model.addAttribute("appUserForm", form);
      model.addAttribute("countries", countries);

      return "registerPage";
   }

   // This method is called to save the registration information.
   // @Validated: To ensure that this Form
   // has been Validated before this method is invoked.
   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public String saveRegister(Model model, //
         @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {

      // Validate result
      if (result.hasErrors()) {
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         return "registerPage";
      }
      AppUser newUser= null;
      try {
         newUser = appUserDAO.createAppUser(appUserForm);
      }
      // Other error!!
      catch (Exception e) {
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }

      redirectAttributes.addFlashAttribute("flashUser", newUser);
      
      return "redirect:/registerSuccessful";
   }

}