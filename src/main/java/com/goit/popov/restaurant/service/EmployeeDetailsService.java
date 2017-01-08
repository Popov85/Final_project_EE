package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 1/8/2017.
 */
public class EmployeeDetailsService implements UserDetailsService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeDetailsService.class);

        @Autowired
        private EmployeeService employeeService;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                logger.info("EmployeeDetailsService in use!");
                Employee employee = employeeService.getEmployeeByLogin(username);
                if (employee!=null) {
                        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                        authorities.add( new SimpleGrantedAuthority(employee.getRole()));
                        UserDetails ud  = new org.springframework.security.core.userdetails.User(
                                employee.getLogin(), employee.getPassword(),
                                true, true, true, true, authorities);
                        logger.info("User details: "+ud);
                        return ud;
                } else {
                    logger.error("ERROR: User not found!");
                        throw new UsernameNotFoundException("No such an employee!");
                }
        }
}
