package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Order class. A restaurant order is made by its visitors, it may contain many dishes
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "orders")
public class Order {

        // Array of tables in the hall of the restaurant
        public static final int[] TABLE_SET = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12, 13, 14, 15};

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "ORD_ID")
        private int id;

        @Column(name = "IS_OPENED")
        private boolean isOpened;

        @Column(name = "OPEN_DATE")
        private Date openedTimeStamp;

        @Column(name = "CLOSE_DATE")
        private Date closedTimeStamp;

        @Column(name = "TABLE_NUMBER")
        private int table;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "EMP_ID")
        private Waiter waiter;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "order_dish",
                joinColumns = @JoinColumn(name = "ORD_ID"))
        @MapKeyJoinColumn(name = "D_ID")
        @Column(name = "quantity")
        Map<Dish, Integer> dishes;

        public int getId() {
                return id;
        }

        public boolean isOpened() {
                return isOpened;
        }

        public int getTable() {
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

        public void setTable(int table) {
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
                result = 31 * result + table;
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
                        ", dishes=" + dishes +
                        '}';
        }
}
