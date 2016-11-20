package com.goit.popov.restaurant.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
public class Employee {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "EMP_ID")
        protected int id;

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

        @NotNull
        @Min(value = 1000, message = "Minimal salary is $1000")
        @Column(name = "SALARY")
        protected BigDecimal salary;

        public int getId() {
                return id;
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

        public void setId(int id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
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

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Employee employee = (Employee) o;

                if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
                if (dob != null ? !dob.equals(employee.dob) : employee.dob != null) return false;
                if (phone != null ? !phone.equals(employee.phone) : employee.phone != null) return false;
                if (position != null ? !position.equals(employee.position) : employee.position != null) return false;
                return salary != null ? salary.equals(employee.salary) : employee.salary == null;

        }

        @Override
        public int hashCode() {
                int result = name != null ? name.hashCode() : 0;
                result = 31 * result + (dob != null ? dob.hashCode() : 0);
                result = 31 * result + (phone != null ? phone.hashCode() : 0);
                result = 31 * result + (position != null ? position.hashCode() : 0);
                result = 31 * result + (salary != null ? salary.hashCode() : 0);
                return result;
        }

        @Override
        public String toString() {
                return "Employee{" +
                        "id='" + id + '\'' +
                        "name='" + name + '\'' +
                        ", dob=" + dob +
                        ", phone='" + phone + '\'' +
                        ", position=" + position +
                        ", salary=" + salary +
                        '}';
        }
}
