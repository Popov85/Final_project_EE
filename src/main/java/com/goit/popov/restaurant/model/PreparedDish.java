package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Andrey on 10/14/2016.
 */
@Entity
@Table(name = "prepared_dish")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jsonId")
public class PreparedDish {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "PD_ID")
        private int id;

        //@DateTimeFormat(pattern="MM/dd/yyyy")
        @Column(name = "WHEN_PREPARED")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
        private Date whenPrepared;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "D_ID")
        private Dish dish;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "CHEF_ID")
        private Chef chef;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ORD_ID")
        private Order order;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
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

        public void setDish(Dish dish) {
                this.dish = dish;
        }

        public void setChef(Chef chef) {
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
                        ", dateTime=" + whenPrepared +
                        '}';
        }
}
