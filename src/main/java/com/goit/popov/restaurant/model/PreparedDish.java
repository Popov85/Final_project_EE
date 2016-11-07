package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Andrey on 10/14/2016.
 */
@Entity
@Table(name = "prepared_dish")
public class PreparedDish {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "PD_ID")
        private int id;

        @Column(name = "WHEN_PREPARED")
        private Date whenPrepared;

        @ManyToOne
        @JoinColumn(name = "D_ID")
        private Dish dish;

        @ManyToOne
        @JoinColumn(name = "EMP_ID")
        private Employee chef;

        @ManyToOne
        @JoinColumn(name = "ORD_ID")
        private Order order;

        public int getId() {
                return id;
        }

        public Dish getDish() {
                return dish;
        }

        public Employee getChef() {
                return chef;
        }

        public Order getOrder() {
                return order;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setDish(Dish dish) {
                this.dish = dish;
        }

        public void setChef(Employee chef) {
                this.chef = chef;
        }

        public void setOrder(Order order) {
                this.order = order;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                PreparedDish that = (PreparedDish) o;

                if (id != that.id) return false;
                if (!dish.equals(that.dish)) return false;
                if (!chef.equals(that.chef)) return false;
                return order.equals(that.order);

        }

        @Override
        public int hashCode() {
                int result = id;
                result = 31 * result + dish.hashCode();
                result = 31 * result + chef.hashCode();
                result = 31 * result + order.hashCode();
                return result;
        }

        @Override
        public String toString() {
                return "PreparedDish{" +
                        "id=" + id +
                        ", dish=" + dish +
                        ", chef=" + chef +
                        ", order=" + order +
                        '}';
        }
}
