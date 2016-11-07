package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;

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

        public void setName(String name) {
                this.name = name;
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
                return name.hashCode();
        }

        @Override
        public String toString() {
                return "Position{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
        }
}
