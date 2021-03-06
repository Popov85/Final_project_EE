package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goit.popov.restaurant.controller.converters.OrderDeserializer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Order class. A restaurant order is made by its visitors, it may contain many dishes
 * @Author: Andrey P.
 * @version 1.0
 */

@Entity
@Table(name = "orders")
@JsonDeserialize(using = OrderDeserializer.class)
public class Order {

        public static final Integer[] TABLE_SET = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12, 13, 14, 15};

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "ORD_ID")
        private Long id;

        @Column(name = "IS_OPENED")
        private boolean isOpened;

        @Column(name = "IS_CANCELLED")
        private boolean isCancelled;

        @Column(name = "OPEN_DATE")
        @Temporal(TemporalType.TIMESTAMP)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone="EET")
        private Date openedTimeStamp;

        @Column(name = "CLOSE_DATE")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="EET")
        private Date closedTimeStamp;

        @Column(name = "TABLE_NUMBER")
        private String table;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "EMP_ID")
        private Employee waiter;

        @JsonIgnore
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "order_dish",
                joinColumns = @JoinColumn(name = "ORD_ID"))
        @MapKeyJoinColumn(name = "D_ID")
        @Column(name = "quantity")
        Map<Dish, Integer> dishes;

        @Transient
        Map<Dish, Integer> previousDishes = null;

        public Map<Dish, Integer> getPreviousDishes() {
                return previousDishes;
        }

        public void setPreviousDishes(Map<Dish, Integer> previousDishes) {
                this.previousDishes = previousDishes;
        }

        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
        @Fetch(FetchMode.SELECT)
        private Set<PreparedDish> preparedDishes;

        public boolean isFulfilled() {
                if (!hasPreparedDishes()) return false;
                if (getDishesQuantity()!=preparedDishes.size()) return false;
                if (getDishesQuantity()!=getPreparedDishesQuantity()) return false;
                return true;
        }

        public boolean hasPreparedDishes() {
                if (preparedDishes==null || preparedDishes.size()==0) return false;
                return true;
        }

        public Long getId() {
                return id;
        }

        public boolean isOpened() {
                return isOpened;
        }

        public boolean isCancelled() {
                return isCancelled;
        }

        public String getTable() {
                return table;
        }

        public Employee getWaiter() {
                return waiter;
        }

        public String getWaiterName() {
                return waiter.getName();
        }

        public Map<Dish, Integer> getDishes() {
                return dishes;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setOpened(boolean opened) {
                isOpened = opened;
        }

        public void setCancelled(boolean cancelled) {
                isCancelled = cancelled;
        }

        public void setTable(String table) {
                this.table = table;
        }

        public void setWaiter(Employee waiter) {
                this.waiter = waiter;
        }

        public void setDishes(Map<Dish, Integer> dishes) {
                this.dishes = dishes;
        }

        public Date getOpenedTimeStamp() {
                return openedTimeStamp;
        }

        public void setOpenedTimeStamp(Date openedTimeStamp) {
                this.openedTimeStamp = openedTimeStamp;
        }

        public Date getClosedTimeStamp() {
                return closedTimeStamp;
        }

        public void setClosedTimeStamp(Date closedTimeStamp) {
                this.closedTimeStamp = closedTimeStamp;
        }

        public Set<PreparedDish> getPreparedDishes() {
                return preparedDishes;
        }

        public void setPreparedDishes(Set<PreparedDish> preparedDishes) {
                this.preparedDishes = preparedDishes;
        }

        public BigDecimal getTotalSum() {
                BigDecimal total = new BigDecimal(0);
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish dish = entry.getKey();
                        BigDecimal price = dish.getPrice();
                        Integer quantityOrdered = entry.getValue();
                        total = total.add(price.multiply(new BigDecimal(quantityOrdered)));
                }
                return total;
        }

        public int getDishesQuantity() {
                int total = 0;
                for (Integer value : dishes.values()) {
                        total+=value;
                }
                return total;
        }

        public int getDishesQuantity(Dish dish) {
                if (dishes==null || dishes.isEmpty()) return 0;
                int total = 0;
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()){
                        if (entry.getKey().equals(dish)) total+=entry.getValue();
                }
                return total;
        }

        public int getPreparedDishesQuantity() {
                if (preparedDishes==null || preparedDishes.isEmpty()) return 0;
                int total = 0;
                for (PreparedDish preparedDish : preparedDishes) {
                        if (!preparedDish.isCancelled()) total++;
                }
                return total;
        }

        public String getReadiness() {
                return ((preparedDishes!=null) ? Order.round((double) getPreparedDishesQuantity()/getDishesQuantity()*100, 1)+" %" : "0 %");
        }

        private static double round (double value, int precision) {
                int scale = (int) Math.pow(10, precision);
                return (double) Math.round(value * scale) / scale;
        }

        @Override
        public String toString() {
                return "\n Order{" +
                        "id=" + id +"\n"+
                        "isOpened=" + isOpened +"\n"+
                        "openedTimeStamp=" + openedTimeStamp +"\n"+
                        "closedTimeStamp=" + closedTimeStamp +"\n"+
                        "table=" + table +"\n"+
                        "waiter=" + ((waiter!=null) ? waiter.getName() : null) +"\n"+
                        "dishes=" + getDishesQuantity() +"\n"+
                        "preparedDishes=" + ((preparedDishes!=null) ? preparedDishes.size() : null) +"\n"+
                        "isFulfilled=" + isFulfilled() +"\n"+
                        "notPrepared=" + getPreparedDishesQuantity() +"\n"+
                        "readiness=" + getReadiness()+"\n"+
                        '}';
        }
}
