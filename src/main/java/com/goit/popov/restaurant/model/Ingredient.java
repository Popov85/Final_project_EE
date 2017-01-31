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
        private int id;

        @NotEmpty(message = "Ingredient must have a name!")
        @Column(name = "ING_NAME")
        private String name;

        @NotNull(message = "Unit must not be empty!")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "UNIT_ID")
        private Unit unit;

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

        public Unit getUnit() {
                return unit;
        }

        public void setUnit(Unit unit) {
                this.unit = unit;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Ingredient that = (Ingredient) o;

                if (!name.equals(that.name)) return false;
                return unit.equals(that.unit);

        }

        @Override
        public int hashCode() {
                int result = name.hashCode();
                result = 31 * result + ((unit == null) ? 0 : unit.hashCode());
                return result;
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

