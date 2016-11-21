package com.goit.popov.restaurant.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Andrey on 11/5/2016.
 */
@Entity
//@DiscriminatorValue("1")
public class Manager extends Employee {
}
