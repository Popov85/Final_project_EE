package com.goit.popov.restaurant.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 11/4/2016.
 */
@Entity
//@DiscriminatorValue("3")
public class Waiter extends Employee {

        public Waiter() {}

        public Waiter(Employee e) {
                super(e.id, e.login, e.password, e.name, e.dob, e.phone, e.position, e.salary, e.photo);
        }

        @OneToMany(fetch = FetchType.EAGER, mappedBy = "waiter", cascade = CascadeType.REMOVE)
        private List<Order> orders;

        @Transient
        private String role = "ROLE_WAITER";

        public String getRole() {
                return role;
        }

        public List<Order> getOrders() {
                return orders;
        }

        public void setOrders(List<Order> orders) {
                this.orders = orders;
        }

        @Override
        public String toString() {
                return "Waiter{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", dob=" + dob +
                        ", phone='" + phone + '\'' +
                        ", position=" + position +
                        ", salary=" + salary +
                        ", role=" + role +
                        '}';
        }


}
