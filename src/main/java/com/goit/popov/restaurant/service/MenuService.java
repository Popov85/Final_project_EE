package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.MenuDAO;
import com.goit.popov.restaurant.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 08.11.2016.
 */
public class MenuService {

        MenuDAO menuDAO;

        public void setMenuDAO(MenuDAO menuDAO) {
                this.menuDAO = menuDAO;
        }

        @Transactional
        public List<Menu> getAll() {
                return menuDAO.getAll();
        }

}
