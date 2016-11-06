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

        @Column(name="ING_NAME")
        private String name;

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Ingredient that = (Ingredient) o;

                if (id != that.id) return false;
                return name.equals(that.name);

        }

        @Override
        public int hashCode() {
                int result = id;
                result = 31 * result + name.hashCode();
                return result;
        }

        @Override
        public String toString() {
                return "Ingredient{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
        }
}
