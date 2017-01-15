package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Andrey on 11/4/2016.
 */
@Entity
public class Chef extends Employee {

        @JsonIgnore
        @OneToMany(mappedBy="chef")
        List<PreparedDish> preparedDishes;

        public List<PreparedDish> getPreparedDishes() {
                return preparedDishes;
        }

        public void setPreparedDishes(List<PreparedDish> preparedDishes) {
                this.preparedDishes = preparedDishes;
        }

        @Transient
        private String role = "ROLE_CHEF";

        public String getRole() {
                return role;
        }

        @Override
        public String toString() {
                return "Chef{" +
                        "id='" + id + '\'' +
                        "name='" + name + '\'' +
                        ", dob=" + dob +
                        ", phone='" + phone + '\'' +
                        ", position=" + position +
                        ", salary=" + salary +
                        ", role=" + role +
                        '}';
        }
}
