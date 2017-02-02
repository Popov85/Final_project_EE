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
        @Size(min=4, max=25, message = "Login must have from 4 to 25 characters!")
        @Column(name = "EMP_LOGIN", unique=true)
        protected String login;

        @JsonIgnore
        @NotEmpty(message = "Password is a required field")
        @Size(min=8, max=60, message = "Password must have from 8 to 16 characters!")
        @Column(name = "EMP_PASSWORD", unique=true)
        protected String password;

        @NotEmpty(message = "Please, provide name for an employee!")
        @Size(min=5, max=50)
        @Column(name = "EMP_NAME")
        protected String name;

        @NotNull(message = "DOB field must not be empty!")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past(message = "DOB must be a date in the past!")
        @Column(name = "DOB")
        protected Date dob;

        @NotEmpty(message = "Phone field must not be empty")
        @Column(name = "PHONE", unique=true)
        protected String phone;

        @NotNull(message = "Please, appoint a position for an employee")
        @ManyToOne
        @JoinColumn(name = "POS_ID")
        protected Position position;

        @NotNull(message = "Please, provide salary")
        @Min(value = 1000, message = "Minimal salary is $1000")
        @Column(name = "SALARY")
        protected BigDecimal salary;

        @JsonIgnore
        @Column(name = "PHOTO")
        protected byte[] photo;

        @Transient
        private String role = "ROLE_EMPLOYEE";

        public String getRole() {
                return role;
        }

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
                        ", role=" + role +
                        '}';
        }
}
