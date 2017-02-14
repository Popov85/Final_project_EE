package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;

/**
 * Created by Andrey on 24.11.2016.
 */
@Entity
@Table(name = "unit")
public class Unit {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "U_ID")
        protected Long id;

        @NotEmpty(message = "Please, provide name for a unit")
        @Length(min = 1, max = 15)
        @Column(name = "U_NAME")
        protected String name;

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

        @Override
        public String toString() {
                return "Unit{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
        }
}
