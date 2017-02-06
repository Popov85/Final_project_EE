package com.goit.popov.restaurant.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Created by Andrey on 11/5/2016.
 */
@Entity
public class Manager extends Employee {

        public Manager() {}

        public Manager(Employee e) {
                super(e.id, e.login, e.password, e.name, e.dob, e.phone, e.position, e.salary, e.photo);
        }

        @Transient
        private String role = "ROLE_ADMIN";

        public String getRole() {
                return role;
        }

        @Override
        public String toString() {
                return "Manager{" +
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
