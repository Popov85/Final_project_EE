package com.goit.popov.restaurant.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Andrey on 13.02.2017.
 */
@Entity
@Table(name = "emp_position")
public class Position {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "POS_ID")
        private Long id;

        @NotEmpty(message = "Position cannot be empty!")
        @Size(min=2, max=30, message = "Position has from 2 to 30 characters")
        @Column(name = "POS_NAME", unique=true)
        private String name;

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="R_ID", unique=false, nullable=false, updatable=false)
        private Role role;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        @Override
        public String toString() {
                return "Position{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", role=" + role +
                        '}';
        }
}
