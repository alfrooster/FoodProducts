package com.ahonen.FoodProducts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ahonen.FoodProducts.domain.Category;
import com.ahonen.FoodProducts.domain.CategoryRepository;
import com.ahonen.FoodProducts.domain.User;
import com.ahonen.FoodProducts.domain.UserRepository;

@SpringBootApplication
public class FoodProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodProductsApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CategoryRepository crepository, UserRepository urepository){
		return (args) -> {
						
			crepository.save(new Category("Jäätelöt"));
			crepository.save(new Category("Karkit"));
			crepository.save(new Category("Suklaalevyt"));
			crepository.save(new Category("Suklaapatukat"));
			crepository.save(new Category("Pakastepizzat"));
			
			User user1 = new User("user", "$2a$10$OFn6F.J44qazXku6To04nOq1Gb3wdD2OK0dubT7nbHIsABLFc.Be.", "USER");
			User user2 = new User("admin", "$2a$10$1NRatnmKMeiz3ryhPrF/XuDAKeMNhSu.j6BCHiTrUEWvZC.3mF34W", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
		};
	}

}
