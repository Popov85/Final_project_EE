package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Menu class. A restaurant menu consists of a number of Dishes
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "menu")
public class Menu {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "M_ID")
        private int id;

        @Column(name = "MENU_NAME")
        private String name;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "menu_dish",
                joinColumns = @JoinColumn(name = "M_ID"),
                inverseJoinColumns = @JoinColumn(name = "D_ID")
        )
        private List<Dish> dishes;

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public List<Dish> getDishes() {
                return dishes;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setDishes(List<Dish> dishes) {
                this.dishes = dishes;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Menu menu = (Menu) o;

                if (id != menu.id) return false;
                if (!name.equals(menu.name)) return false;
                return dishes.equals(menu.dishes);
        }

        @Override
        public int hashCode() {
                int result = id;
                result = 31 * result + name.hashCode();
                result = 31 * result + dishes.hashCode();
                return result;
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
