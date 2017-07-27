package com.shsc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shsc.model.User;
import com.shsc.model.UserProfile;
import com.shsc.service.UserProfileService;
import com.shsc.service.UserService;
import com.shsc.springsecurityconfig.RoleToUserProfileConverter;


@Controller
public class LoginController {
	
    @Autowired
    UserProfileService userProfileService;
     
    @Autowired
    UserService userService;
    
    @Autowired
    RoleToUserProfileConverter roleToUserProfileConverter;
    
	@RequestMapping(value = {"/","home"} , method = RequestMethod.GET)
	public String homePage(ModelMap model){
		model.addAttribute("greeting","Hi, welcome to Spring Demo Project !");
		System.out.println("Home Page");
		return "welcome";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        System.out.println("Admin Page");
        return "admin";
    }
 
    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        System.out.println("dba Page");
        return "dba";
    }
 
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        System.out.println("Access_Denied Page");
        return "accessDenied";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
    	System.out.println("Login Page");
        return "login";
    }
     
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        System.out.println("Logout Page");
        return "redirect:/login?logout";
    }
    
    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        User user = new User();
        //System.out.println("roleToUserProfileConverter.convert1() : "+roleToUserProfileConverter.convert1());
        model.addAttribute("user", user);
        //model.addAttribute("roles", roleToUserProfileConverter.convert1());
        return "newuser";
    }
 
    /*
     * This method will be called on form submission, handling POST request It
     * also validates the user input
     */
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user,
            BindingResult result, ModelMap model) {
 
        if (result.hasErrors()) {
            System.out.println("There are errors");
            return "newuser";
        }
        userService.save(user);
         
        System.out.println("First Name : "+user.getFirstName());
        System.out.println("Last Name : "+user.getLastName());
        System.out.println("SSO ID : "+user.getSsoId());
        System.out.println("Password : "+user.getPassword());
        System.out.println("Email : "+user.getEmail());
        System.out.println("Checking UsrProfiles....");
        if(user.getUserProfiles()!=null){
            for(UserProfile profile : user.getUserProfiles()){
                System.out.println("Profile : "+ profile.getType());
            }
        }
         
        model.addAttribute("success", "User " + user.getFirstName() + " has been registered successfully");
        return "registrationsuccess";
    }
    
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
            System.out.println("Username : "+userName);
        } else {
            userName = principal.toString();
            System.out.println("Username : "+userName);
        }
        return userName;
    }
    
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }
}
