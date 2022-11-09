package com.ahonen.FoodProducts.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String brand;
	private String country;
	private Integer weight;
	private Integer kcal;
	
	@ManyToOne
	@JoinColumn(name = "categoryid")
	private Category category;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getKcal() {
		return kcal;
	}
	public void setKcal(Integer kcal) {
		this.kcal = kcal;
	}
	
	public Food() {
		
	}
	
	public Food(String name, String brand, String country, Integer weight, Integer kcal, Category category) {
		super();
		this.name = name;
		this.brand = brand;
		this.country = country;
		this.weight = weight;
		this.kcal = kcal;
		this.category = category;
	}
	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", brand=" + brand + ", country=" + country + ", weight=" + weight
				+ ", kcal=" + kcal + ", category=" + category + "]";
	}

}
