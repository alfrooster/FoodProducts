package com.ahonen.FoodProducts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ahonen.FoodProducts.domain.Register;
import com.ahonen.FoodProducts.domain.User;
import com.ahonen.FoodProducts.domain.UserRepository;

import javax.validation.Valid;

@Controller
public class UserController {
	@Autowired
    private UserRepository repository; 
	
	//registering from login page
    @RequestMapping(value = "/register")
    public String register(Model model){
    	model.addAttribute("register", new Register());
        return "register";
    }	
    
    /**
     * Create new user
     * Check if user already exists & form validation
     * 
     * @param register
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String saveRegisteration(@Valid @ModelAttribute("register") Register register, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (register.getPassword().equals(register.getPasswordCheck())) { // check password match		
	    		String pwd = register.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(register.getUsername());
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(register.getUsername()) == null) { // Check if user exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "register";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");    	
    			return "register";
    		}
    	}
    	else {
    		return "register";
    	}
    	return "redirect:/login";    	
    }    
    
    //adding a new user as admin
    @RequestMapping(value = "/adduser")
	@PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(Model model){
    	model.addAttribute("register", new Register());
        return "adduser";
    }	
    
    /**
     * Create new user
     * Check if user already exists & form validation
     * 
     * @param register
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/saveadduser", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
    public String saveAddUser(@Valid @ModelAttribute("register") Register register, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (register.getPassword().equals(register.getPasswordCheck())) { // check password match		
	    		String pwd = register.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(register.getUsername());
		    	newUser.setRole(register.getRole());
		    	if (repository.findByUsername(register.getUsername()) == null) { // Check if user exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "adduser";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");    	
    			return "adduser";
    		}
    	}
    	else {
    		return "adduser";
    	}
    	return "redirect:/userlist";    	
    }    
    
}