package com.ahonen.FoodProducts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ahonen.FoodProducts.domain.Category;
import com.ahonen.FoodProducts.domain.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void findByNameShouldReturnCategory() {
        List<Category> categories = repository.findByName("Jäätelöt");
        //check that the list has one item and it's the right item
        assertThat(categories).hasSize(1);
    }
    
    @Test
    public void createNewCategory() {
    	Category category = new Category("Leivät");
    	repository.save(category);
    	//check if new category was created
    	assertThat(category.getCategoryid()).isNotNull();
    }
    
    @Test
    public void deleteCategory() {
    	//delete the category with the name "Jäätelöt"
		List<Category> categories = repository.findByName("Jäätelöt");
		Category category = categories.get(0);
		repository.delete(category);
		//check that category with the name "Jäätelöt" no longer exists
		List<Category> newCategories = repository.findByName("Jäätelöt");
		assertThat(newCategories).hasSize(0);
    }
    
    @Test
    public void editCategory() {
    	//find the category
    	List<Category> categories = repository.findByName("Jäätelöt");
    	Category category = categories.get(0);
		//change the name of the category
    	category.setName("Jäätelötuutit");
    	repository.save(category);
    	//check that there is no longer a category called "Jäätelöt"
		List<Category> newCategories = repository.findByName("Jäätelöt");
		assertThat(newCategories).hasSize(0);
    	//check that there is now a category called "Jäätelötuutit"
		List<Category> newerCategories = repository.findByName("Jäätelötuutit");
		assertThat(newerCategories).hasSize(1);
    }

}