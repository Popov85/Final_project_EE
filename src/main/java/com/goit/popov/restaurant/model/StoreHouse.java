package com.goit.popov.restaurant.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * StoreHouse class keeps info about ingredients in stock and their quantity
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "store_house")
public class StoreHouse implements Serializable {

        @Id
        @OneToOne
        @JoinColumn(name="ING_ID", unique=true, nullable=false, updatable=false)
        private Ingredient ingredient;

        @Column(name = "QUANTITY")
        private double quantity;

        public Ingredient getIngredient() {
                return ingredient;
        }

        public double getQuantity() {
                return quantity;
        }

        public void setIngredient(Ingredient ingredient) {
                this.ingredient = ingredient;
        }

        public void setQuantity(double quantity) {
                this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                StoreHouse that = (StoreHouse) o;

                if (Double.compare(that.quantity, quantity) != 0) return false;
                return ingredient.equals(that.ingredient);

        }

        @Override
        public int hashCode() {
                int result;
                long temp;
                result = ingredient.hashCode();
                temp = Double.doubleToLongBits(quantity);
                result = 31 * result + (int) (temp ^ (temp >>> 32));
                return result;
        }

        @Override
        public String toString() {
                return "\n StoreHouse{" +
                        "ingredient=" + ingredient.getName() +"\n" +
                        "quantity='" + quantity + "\n" +
                        '}';
        }
}
