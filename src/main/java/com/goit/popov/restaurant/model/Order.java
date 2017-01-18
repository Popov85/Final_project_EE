package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goit.popov.restaurant.controller.converters.OrderDeserializer;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Order class. A restaurant order is made by its visitors, it may contain many dishes
 * @Author: Andrey P.
 * @version 1.0
 */

@Entity
@Table(name = "orders")
@JsonDeserialize(using = OrderDeserializer.class)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jsonId")
public class Order {

        // Array of tables in the hall of the restaurant
        public static final Integer[] TABLE_SET = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12, 13, 14, 15};

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "ORD_ID")
        private int id;

        @Column(name = "IS_OPENED")
        private boolean isOpened;

        @Column(name = "OPEN_DATE")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
        private Date openedTimeStamp;

        @Column(name = "CLOSE_DATE")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
        private Date closedTimeStamp;

        @Column(name = "TABLE_NUMBER")
        private String table;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "EMP_ID")
        private Waiter waiter;


        @JsonIgnore
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "order_dish",
                joinColumns = @JoinColumn(name = "ORD_ID"))
        @MapKeyJoinColumn(name = "D_ID")
        @Column(name = "quantity")
        Map<Dish, Integer> dishes;




        @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
        private List<PreparedDish> preparedDishes;

        public List<PreparedDish> getPreparedDishes() {
                return preparedDishes;
        }

        public void setPreparedDishes(List<PreparedDish> preparedDishes) {
                this.preparedDishes = preparedDishes;
        }



        public int getId() {
                return id;
        }

        public boolean isOpened() {
                return isOpened;
        }

        public String getTable() {
                return table;
        }

        public Waiter getWaiter() {
                return waiter;
        }

        public Map<Dish, Integer> getDishes() {
                return dishes;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setOpened(boolean opened) {
                isOpened = opened;
        }

        public void setTable(String table) {
                this.table = table;
        }

        public void setWaiter(Waiter waiter) {
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

        public BigDecimal getTotal() {
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
                int total = 0;
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()){
                        if (entry.getKey().equals(dish)) total+=entry.getValue();
                }
                return total;
        }

        public String getWaiterName() {
                return this.waiter.getName();
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Order order = (Order) o;

                if (isOpened != order.isOpened) return false;
                if (table != order.table) return false;
                if (!openedTimeStamp.equals(order.openedTimeStamp)) return false;
                if (closedTimeStamp != null ? !closedTimeStamp.equals(order.closedTimeStamp) : order.closedTimeStamp != null)
                        return false;
                if (!waiter.equals(order.waiter)) return false;
                return dishes.equals(order.dishes);

        }

        @Override
        public int hashCode() {
                int result = (isOpened ? 1 : 0);
                result = 31 * result + openedTimeStamp.hashCode();
                result = 31 * result + table.hashCode();
                return result;
        }

        @Override
        public String toString() {
                return "Order{" +
                        "id=" + id +
                        ", isOpened=" + isOpened +
                        ", openedTimeStamp=" + openedTimeStamp +
                        ", closedTimeStamp=" + closedTimeStamp +
                        ", table=" + table +
                        ", waiter=" + waiter +
                        ", dishes=" + dishes +
                        '}';
        }
}
