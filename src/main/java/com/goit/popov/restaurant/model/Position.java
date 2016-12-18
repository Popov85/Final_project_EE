package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Positions class, positions available at restaurant
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "emp_position")
public class Position {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "POS_ID")
        private int id;

        @NotEmpty(message = "Position cannot be empty!")
        @Column(name = "POS_NAME")
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

        public Position setName(String name) {
                this.name = name;
                return this;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Position position = (Position) o;

                return name.equals(position.name);
        }

        @Override
        public int hashCode() {
                System.out.println("I'm trying to use hashcode");
                System.out.println("Position object is"+this.toString());
                return name.hashCode();
                //return 1;
        }

        @Override
        public String toString() {
                return "Position{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
        }
}
