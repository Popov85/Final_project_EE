package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.MenuDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
public class MenuDAOImplJPA implements MenuDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }
        @Transactional
        @Override
        public int insert(Menu menu) {
                return (int) sessionFactory.getCurrentSession().save(menu);
        }

        @Transactional
        @Override
        public void update(Menu menu) {
                sessionFactory.getCurrentSession().update(menu);
        }

        @Transactional
        @Override
        public List<Menu> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select m from Menu m").list();
        }

        @Transactional
        @Override
        public Menu getById(int id) {
                return sessionFactory.getCurrentSession().get(Menu.class, id);
        }

        @Transactional
        @Override
        public void delete(Menu menu) {
                sessionFactory.getCurrentSession().delete(menu);
        }

        @Transactional
        @Override
        public void addDish(Menu menu, Dish dish) {
                List<Dish> dishes = menu.getDishes();
                dishes.add(dish);
                update(menu);
        }

        @Transactional
        @Override
        public void deleteDish(Menu menu, Dish dish) {
                List<Dish> dishes = menu.getDishes();
                dishes.remove(dish);
                update(menu);
        }
}
