package com.goit.popov.restaurant.service.authentification;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * It needs to keep an employee's id in UserDetails object
 * Created by Andrey on 1/9/2017.
 */
public class Employee extends User {

        private int id;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public Employee(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities, int id) {
                super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
                setId(id);
        }
}
