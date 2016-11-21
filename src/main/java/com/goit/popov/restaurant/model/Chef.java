package com.goit.popov.restaurant.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Andrey on 11/4/2016.
 */
@Entity
//@DiscriminatorValue("2")
public class Chef extends Employee {

        @OneToMany(mappedBy="chef")
        List<PreparedDish> preparedDishes;

        @Override
        public String toString() {
                return "Chef{" +
                        "id='" + id + '\'' +
                        "name='" + name + '\'' +
                        ", dob=" + dob +
                        ", phone='" + phone + '\'' +
                        ", position=" + position +
                        ", salary=" + salary +
                        '}';
        }
}
