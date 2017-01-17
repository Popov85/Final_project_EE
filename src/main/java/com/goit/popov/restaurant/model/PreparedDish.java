package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        public Date getWhenPrepared() {
                return whenPrepared;
        }

        public void setWhenPrepared(Date whenPrepared) {
                this.whenPrepared = whenPrepared;
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
