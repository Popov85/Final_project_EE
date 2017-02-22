package com.goit.popov.restaurant.model;

import java.util.Date;

/**
 * Created by Andrey on 22.02.2017.
 */
public class OutputMessage extends Message {

        public OutputMessage(String from, String text, String when) {
                this.from = from;
                this.text = text;
                this.when = when;
        }

        private String when;

        public String getWhen() {
                return when;
        }

        public void setWhen(String when) {
                this.when = when;
        }

        @Override
        public String toString() {
                return "OutputMessage{" +
                        "when='" + when + '\'' +
                        '}';
        }
}
