package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Ingredient class
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "ING_ID")
        private Long id;

        @NotEmpty(message = "Ingredient must have a name!")
        @Column(name = "ING_NAME")
        private String name;

        @NotNull(message = "Unit must not be empty!")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "UNIT_ID")
        private Unit unit;

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

        public Unit getUnit() {
                return unit;
        }

        public void setUnit(Unit unit) {
                this.unit = unit;
        }

        @Override
        public String toString() {
                return "Ingredient{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", unit='" + unit + '\'' +
                        '}';
        }
}

