package com.ahonen.FoodProducts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ahonen.FoodProducts.domain.Food;
import com.ahonen.FoodProducts.domain.FoodRepository;
import com.ahonen.FoodProducts.domain.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FoodRepositoryTests {

    @Autowired
    private FoodRepository repository;
    @Autowired
    private CategoryRepository crepository;

    @Test
    public void findByNameShouldReturnFood() {
        List<Food> foods = repository.findByName("Suklaatuutti");
        //check that the list has one item and it's the right item
        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getBrand()).isEqualTo("Ingman");
    }
    
    @Test
    public void createNewFood() {
    	Food food = new Food("Keksisuklaa", "Pirkka", "Suomi", 58, 286, crepository.findByName("Suklaapatukat").get(0));
    	repository.save(food);
    	//check if new food was created
    	assertThat(food.getId()).isNotNull();
    }
    
    @Test
    public void deleteFood() {
    	//delete the food with the name "Suklaatuutti"
		List<Food> foods = repository.findByName("Suklaatuutti");
		Food food = foods.get(0);
		repository.delete(food);
		//check that food with the name "Suklaatuutti" no longer exists
		List<Food> newFoods = repository.findByName("Suklaatuutti");
		assertThat(newFoods).hasSize(0);
    }
    
    @Test
    public void editFood() {
    	//find the food
    	List<Food> foods = repository.findByName("Suklaatuutti");
		Food food = foods.get(0);
		//change the name of the food
		food.setName("Mansikkatuutti");
    	repository.save(food);
    	//check that there is no longer a food called "Suklaatuutti"
		List<Food> newFoods = repository.findByName("Suklaatuutti");
		assertThat(newFoods).hasSize(0);
    	//check that there is now a food called "Mansikkatuutti"
		List<Food> newerFoods = repository.findByName("Mansikkatuutti");
		assertThat(newerFoods).hasSize(1);
    }

}