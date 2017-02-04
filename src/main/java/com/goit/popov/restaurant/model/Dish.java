package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goit.popov.restaurant.controller.converters.DishDeserializer;
import com.goit.popov.restaurant.controller.converters.ListToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Dish class
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "dish")
@JsonDeserialize(using = DishDeserializer.class)
public class Dish {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "D_ID")
        private int id;

        @NotEmpty(message = "Dish must have a name!")
        @Size(min=2, max=30)
        @Column(name = "DISH_NAME")
        private String name;

        @NotEmpty(message = "Dish must have a category!")
        @Size(min=2, max=30)
        @Column(name = "CATEGORY")
        private String category;

        @NotNull(message = "Specify the price of dish!")
        @DecimalMin(value = "0.01", message = "Minimal price is 0.01 $")
        @Column(name = "PRICE")
        private BigDecimal price;

        @NotNull(message = "Specify the weight of dish!")
        @DecimalMin(value = "5.00", message = "Minimal weight is 5 g")
        @DecimalMax(value = "100000", message = "Max weight is 100 kg")
        @Column(name = "WEIGHT")
        private Double weight;


        @JsonSerialize(using = ListToStringSerializer.class)
        @ManyToMany(fetch = FetchType.EAGER, mappedBy="dishes")
        private List<Menu> menus;

        @JsonIgnore
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "dish_ingredient",
                joinColumns = @JoinColumn(name = "D_ID"))
        @MapKeyJoinColumn(name = "ING_ID")
        @Column(name = "quantity")
        Map<Ingredient, Double> ingredients;

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public String getCategory() {
                return category;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public Double getWeight() {
                return weight;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        public void setWeight(Double weight) {
                this.weight = weight;
        }

        public List<Menu> getMenus() {
                return menus;
        }

        public void setMenus(List<Menu> menus) {
                this.menus = menus;
        }

        public Map<Ingredient, Double> getIngredients() {
                return ingredients;
        }

        public void setIngredients(Map<Ingredient, Double> ingredients) {
                this.ingredients = ingredients;
        }

        @Override
        public String toString() {
                return "Dish{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", category='" + category + '\'' +
                        ", price=" + price +
                        ", weight=" + weight +
                        ", menus=" + (menus!=null ? menus.size():"no menus") +
                        '}';
        }

}
