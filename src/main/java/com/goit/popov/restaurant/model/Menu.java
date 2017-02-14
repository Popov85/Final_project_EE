package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goit.popov.restaurant.controller.converters.MenuDeserializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Menu class. A restaurant menu consists of a number of Dishes
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "menu")
@JsonDeserialize(using = MenuDeserializer.class)
public class Menu {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "M_ID")
        private int id;

        @NotEmpty(message = "Menu name is a required field")
        @Size(min=2, max=25, message = "Menu must have from 2 to 25 characters!")
        @Column(name = "MENU_NAME")
        private String name;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "menu_dish",
                joinColumns = @JoinColumn(name = "M_ID"),
                inverseJoinColumns = @JoinColumn(name = "D_ID")
        )
        private Set<Dish> dishes;

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public Set<Dish> getDishes() {
                return dishes;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setDishes(Set<Dish> dishes) {
                this.dishes = dishes;
        }

        public BigDecimal getPrice() {
                BigDecimal total = new BigDecimal(0);
                for (Dish dish : dishes) {
                        total = total.add(dish.getPrice());
                }
                return total;
        }

        public int calcDishes() {
                return this.dishes.size();
        }

        @Override
        public String toString() {
                return "Menu{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", dishes=" + dishes +
                        '}';
        }
}
