package com.goit.popov.restaurant.service.messages;

import java.util.Date;

/**
 * Created by Andrey on 24.02.2017.
 */
public class OrderMessage {

        private String time;
        private String order;
        private String action;
        private String waiter;

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public String getOrder() {
                return order;
        }

        public void setOrder(String order) {
                this.order = order;
        }

        public String getAction() {
                return action;
        }

        public void setAction(String action) {
                this.action = action;
        }

        public String getWaiter() {
                return waiter;
        }

        public void setWaiter(String waiter) {
                this.waiter = waiter;
        }

        @Override
        public String toString() {
                return "OrderMessage{" +
                        "time=" + time +
                        ", order='" + order + '\'' +
                        ", action='" + action + '\'' +
                        ", waiter='" + waiter + '\'' +
                        '}';
        }
}
