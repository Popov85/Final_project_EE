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

        public Chef() {}

        public Chef(Employee e) {
                super(e.id, e.login, e.password, e.name, e.dob, e.phone, e.position, e.salary, e.photo);
        }

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
