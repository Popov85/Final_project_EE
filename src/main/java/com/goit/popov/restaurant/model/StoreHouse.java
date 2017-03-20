package com.goit.popov.restaurant.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.CascadeType.*;

/**
 * StoreHouse class keeps info about ingredients in stock and their quantity
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "store_house")
public class StoreHouse implements Serializable {

        public StoreHouse() {
        }

        public StoreHouse(Ingredient ingredient, double quantity) {
                this.ingredient = ingredient;
                this.quantity = quantity;
        }

        @Id
        @OneToOne(fetch = FetchType.EAGER)
        /*@Cascade({org.hibernate.annotations.CascadeType.ALL})
        @JoinColumn(name="ING_ID", unique=true, nullable=false, updatable=false)*/
        /*@Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
        @JoinColumn(name="ING_ID")*/
        @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
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
                        "quantity=" + quantity + "\n" +
                        '}';
        }
}
