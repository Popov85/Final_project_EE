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
        @OneToOne(fetch = FetchType.EAGER)
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
        public String toString() {
                return "\n StoreHouse{" +
                        "ingredient=" + ingredient.getName() +"\n" +
                        "quantity='" + quantity + "\n" +
                        '}';
        }
}
