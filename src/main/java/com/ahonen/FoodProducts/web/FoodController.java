package com.ahonen.FoodProducts.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahonen.FoodProducts.domain.FoodRepository;
import com.ahonen.FoodProducts.domain.Category;
import com.ahonen.FoodProducts.domain.CategoryRepository;
import com.ahonen.FoodProducts.domain.Food;

@Controller
public class FoodController {
	@Autowired
	private FoodRepository repository;
	@Autowired
	private CategoryRepository crepository;
	
	// finds all saved foods, returns foodlist page at / and /foodlist endpoints
	// visible even when not signed in
	@RequestMapping(value= {"/", "/foodlist"})
	public String foodList(Model model) {
		model.addAttribute("foods", repository.findAll());
		return "foodlist";
	}
	
	// RESTful service to get all foods
    @RequestMapping(value="/foodproducts", method = RequestMethod.GET)
    public @ResponseBody List<Food> foodListRest() {	
        return (List<Food>) repository.findAll();
    }    

	// RESTful service to get food product by id
    @RequestMapping(value="/food/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Food> findFoodRest(@PathVariable("id") Long foodId) {	
    	return repository.findById(foodId);
    }
	
	// page where you can create a new food item
    // only usable if logged in
	@RequestMapping(value = "/add")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String addFood(Model model){
		model.addAttribute("food", new Food());
		model.addAttribute("categories", crepository.findAll());
		return "addfood";
	}
	
	// save new food after submitting
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String save(Food food){
		repository.save(food);
		return "redirect:foodlist";
	}
	
	// delete a food product by id
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String deleteFood(@PathVariable("id") Long foodId, Model model){
		repository.deleteById(foodId);
		return "redirect:../foodlist";
	}
	
	// edit a food product by id
	@RequestMapping(value = "/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editFood(@PathVariable("id") Long foodId, Model model){
		model.addAttribute("food", repository.findById(foodId));
		model.addAttribute("categories", crepository.findAll());
		return "editfood";
	}
	
	// page where you can create a new category
	@RequestMapping(value = "/addcategory")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String addCategory(Model model){
		model.addAttribute("category", new Category());
		return "addcategory";
	}
	
	// save the new category after submitting
	@RequestMapping(value = "/savecategory", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveCategory(Category category){
		crepository.save(category);
		return "redirect:categorylist";
	}
	
	// lists every category
	@RequestMapping(value= {"/categorylist"})
	public String categoryList(Model model) {
		model.addAttribute("categories", crepository.findAll());
		return "categorylist";
	}
	
	// delete a category by id
	@RequestMapping(value = "/deletecategory/{categoryid}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String deleteCategory(@PathVariable("categoryid") Long categoryId, Model model){
		crepository.deleteById(categoryId);
		return "redirect:../categorylist";
	}
	
	// edit a category by id
	@RequestMapping(value = "/editcategory/{categoryid}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editCategory(@PathVariable("categoryid") Long categoryId, Model model){
		model.addAttribute("category", crepository.findById(categoryId));
		return "editcategory";
	}
	
	// returns login page at /login endpoint
	@RequestMapping(value= {"/login"})
	public String login(Model model) {
		return "login";
	}

}
