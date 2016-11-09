package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

        @Column(name = "ING_NAME")
        private String name;

        @Column(name = "UNIT")
        private String unit;

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

        public String getUnit() {
                return unit;
        }

        public void setUnit(String unit) {
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
                result = 31 * result + unit.hashCode();
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

