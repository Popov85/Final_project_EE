package com.goit.popov.restaurant.service.messages;

/**
 * Created by Andrey on 2/26/2017.
 */
public class PreparedDishMessage {
        private String time;
        private String order;
        private String dish;
        private String quantity;
        private String action;
        private String byChef;
        private String toWaiter;

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

        public String getDish() {
                return dish;
        }

        public void setDish(String dish) {
                this.dish = dish;
        }

        public String getQuantity() {
                return quantity;
        }

        public void setQuantity(String quantity) {
                this.quantity = quantity;
        }

        public String getAction() {
                return action;
        }

        public void setAction(String action) {
                this.action = action;
        }

        public String getByChef() {
                return byChef;
        }

        public void setByChef(String byChef) {
                this.byChef = byChef;
        }

        public String getToWaiter() {
                return toWaiter;
        }

        public void setToWaiter(String toWaiter) {
                this.toWaiter = toWaiter;
        }

        @Override
        public String toString() {
                return "PreparedDishMessage{" +
                        "time='" + time + '\'' +
                        ", order='" + order + '\'' +
                        ", dish='" + dish + '\'' +
                        ", quantity='" + quantity + '\'' +
                        ", action='" + action + '\'' +
                        ", byChef='" + byChef + '\'' +
                        ", toWaiter='" + toWaiter + '\'' +
                        '}';
        }
}
