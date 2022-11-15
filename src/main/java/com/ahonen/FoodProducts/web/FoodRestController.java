package com.ahonen.FoodProducts.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahonen.FoodProducts.domain.Category;
import com.ahonen.FoodProducts.domain.CategoryRepository;
import com.ahonen.FoodProducts.domain.Food;
import com.ahonen.FoodProducts.domain.FoodRepository;

@RestController
public class FoodRestController {
	@Autowired
	private FoodRepository repository;
	@Autowired
	private CategoryRepository crepository;

	// gets a list of all foods
	@GetMapping("/api/foods")
	List<Food> all() {
		return (List<Food>) repository.findAll();
	}

	// save new food
	@PostMapping("/api/foods")
	Food newFood(@RequestBody Food newFood) {
		return repository.save(newFood);
	}

	// find food by id
	@GetMapping("/api/foods/{id}")
	Optional<Food> one(@PathVariable Long id) {

		return repository.findById(id);
	}

	// editing a food
	@PutMapping("/api/foods/{id}")
	Food replaceFood(@RequestBody Food newFood, @PathVariable Long id) {

		return repository.findById(id).map(food -> {
			food.setName(newFood.getName());
			food.setBrand(newFood.getBrand());
			food.setCountry(newFood.getCountry());
			food.setWeight(newFood.getWeight());
			food.setKcal(newFood.getKcal());
			food.setCategory(newFood.getCategory());
			return repository.save(food);
		}).orElseGet(() -> {
			newFood.setId(id);
			return repository.save(newFood);
		});
	}

	// delete a food from the database
	@DeleteMapping("api/foods/{id}")
	void deleteFood(@PathVariable Long id) {
		repository.deleteById(id);
	}

	// find a list of all categories
	@GetMapping("/api/categories")
	List<Category> allCategories() {
		return (List<Category>) crepository.findAll();
	}

	// save a new entry to categories
	@PostMapping("/api/categories")
	Category newCategory(@RequestBody Category newCategory) {
		return crepository.save(newCategory);
	}

	// find a specific category by id
	@GetMapping("/api/categories/{id}")
	Optional<Category> oneCategory(@PathVariable Long id) {
		return crepository.findById(id);
	}

	// edit an entry in categories
	@PutMapping("/api/categories/{id}")
	Category replaceCategory(@RequestBody Category newCategory, @PathVariable Long id) {

		return crepository.findById(id).map(category -> {
			category.setName(newCategory.getName());
			return crepository.save(category);
		}).orElseGet(() -> {
			newCategory.setCategoryid(id);
			return crepository.save(newCategory);
		});
	}

	// delete a category
	@DeleteMapping("api/categories/{id}")
	void deleteCategory(@PathVariable Long id) {
		crepository.deleteById(id);
	}
}
