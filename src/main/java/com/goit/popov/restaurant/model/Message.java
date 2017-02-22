package com.goit.popov.restaurant.model;

/**
 * Created by Andrey on 22.02.2017.
 */
public class Message {
        protected String from;
        protected String text;

        public String getFrom() {
                return from;
        }

        public void setFrom(String from) {
                this.from = from;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        @Override
        public String toString() {
                return "Message{" +
                        "from='" + from + '\'' +
                        ", text='" + text + '\'' +
                        '}';
        }
}
