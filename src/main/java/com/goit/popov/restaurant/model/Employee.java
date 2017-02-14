package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Andrey on 2/12/2017.
 */
@Entity
@Table(name = "employee")
public class Employee {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "EMP_ID")
        private int id;

        @NotEmpty(message = "Login is a required field")
        @Size(min=4, max=25, message = "Login must have from 4 to 25 characters!")
        @Column(name = "EMP_LOGIN", unique=true)
        private String login;

        @JsonIgnore
        @NotEmpty(message = "Password is a required field")
        @Size(min=8, max=60, message = "Password must have from 8 to 16 characters!")
        @Column(name = "EMP_PASSWORD")
        private String password;

        @NotEmpty(message = "Please, provide name for an employee!")
        @Size(min=5, max=50)
        @Column(name = "EMP_NAME")
        private String name;

        @NotNull(message = "DOB field must not be empty!")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past(message = "DOB must be a date in the past!")
        @Column(name = "DOB")
        private Date dob;

        @NotEmpty(message = "Phone field must not be empty")
        @Column(name = "PHONE", unique=true)
        private String phone;

        @NotNull(message = "Please, appoint a position for an employee")
        @ManyToOne
        @JoinColumn(name = "POS_ID")
        private Position position;

        @NotNull(message = "Please, provide salary")
        @Min(value = 1000, message = "Minimal salary is $1000")
        @Column(name = "SALARY")
        private BigDecimal salary;

        @JsonIgnore
        @Column(name = "PHOTO")
        private byte[] photo;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getLogin() {
                return login;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Date getDob() {
                return dob;
        }

        public void setDob(Date dob) {
                this.dob = dob;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public Position getPosition() {
                return position;
        }

        public void setPosition(Position position) {
                this.position = position;
        }

        public BigDecimal getSalary() {
                return salary;
        }

        public void setSalary(BigDecimal salary) {
                this.salary = salary;
        }

        public byte[] getPhoto() {
                return photo;
        }

        public void setPhoto(byte[] photo) {
                this.photo = photo;
        }

        public double getPhotoSize() {
                if (photo!=null) {
                        return this.photo.length;
                } else {
                        return 0;
                }
        }

        @Override
        public String toString() {
                return "Employee{" +
                        "id=" + id +
                        ", login='" + login + '\'' +
                        ", password='" + password + '\'' +
                        ", name='" + name + '\'' +
                        ", dob=" + dob +
                        ", phone='" + phone + '\'' +
                        ", position=" + position +
                        ", salary=" + salary +
                        '}';
        }
}
