package com.goit.popov.restaurant.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Employee class
 * @Author: Andrey P.
 * @version 1.0
 */
@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="POS_ID", discriminatorType=DiscriminatorType.INTEGER)
public class Employee {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "EMP_ID")
        protected int id;

        @NotEmpty(message = "Login is a required field")
        @Size(min=4, max=25, message = "Login has from 4 to 25 characters")
        @Column(name = "EMP_LOGIN")
        protected String login;

        @NotEmpty(message = "Password is a required field")
        @Size(min=8, max=16, message = "Password must have from 8 to 16 characters")
        @Column(name = "EMP_PASSWORD")
        protected String password;

        @NotEmpty(message = "Please, provide name for an employee")
        @Size(min=5, max=50)
        @Column(name = "EMP_NAME")
        protected String name;

        @DateTimeFormat(pattern = "MM/dd/yyyy")
        @Column(name = "DOB")
        protected Date dob;

        @NotEmpty(message = "Phone field mustn't be empty")
        @Column(name = "PHONE")
        protected String phone;

        @NotNull(message = "Please, appoint a position for an employee")
        @ManyToOne
        @JoinColumn(name = "POS_ID")
        protected Position position;

        @NotNull(message = "Please, provide salary")
        @Min(value = 1000, message = "Minimal salary is $1000")
        @Column(name = "SALARY")
        protected BigDecimal salary;

        @Column(name = "PHOTO")
        protected byte[] photo;

        public int getId() {
                return id;
        }

        public String getLogin() {
                return login;
        }

        public String getPassword() {
                return password;
        }

        public String getName() {
                return name;
        }

        public Date getDob() {
                return dob;
        }

        public String getPhone() {
                return phone;
        }

        public Position getPosition() {
                return position;
        }

        public BigDecimal getSalary() {
                return salary;
        }

        public byte[] getPhoto() {
                return photo;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setDob(Date dob) {
                this.dob = dob;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public void setPosition(Position position) {
                this.position = position;
        }

        public void setSalary(BigDecimal salary) {
                this.salary = salary;
        }

        public void setPhoto(byte[] photo) {
                this.photo = photo;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Employee employee = (Employee) o;

                if (!login.equals(employee.login)) return false;
                if (!password.equals(employee.password)) return false;
                if (!name.equals(employee.name)) return false;
                if (!dob.equals(employee.dob)) return false;
                if (!phone.equals(employee.phone)) return false;
                if (!position.equals(employee.position)) return false;
                if (!salary.equals(employee.salary)) return false;
                return Arrays.equals(photo, employee.photo);

        }

        @Override
        public int hashCode() {
                int result = login.hashCode();
                result = 31 * result + password.hashCode();
                result = 31 * result + name.hashCode();
                result = 31 * result + dob.hashCode();
                result = 31 * result + phone.hashCode();
                result = 31 * result + position.hashCode();
                result = 31 * result + salary.hashCode();
                return result;
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
