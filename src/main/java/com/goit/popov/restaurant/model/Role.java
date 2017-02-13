package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Andrey on 13.02.2017.
 */
@Entity
@Table(name = "emp_role")
public class Role {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "R_ID")
        private int id;

        @NotEmpty(message = "Role cannot be empty!")
        @Size(min=2, max=30, message = "Role has from 2 to 30 characters")
        @Column(name = "R_NAME")
        private String name;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        @Override
        public String toString() {
                return "Role{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
        }
}
