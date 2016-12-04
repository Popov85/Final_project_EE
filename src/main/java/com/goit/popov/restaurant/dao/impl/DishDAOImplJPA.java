package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
@Transactional
public class DishDAOImplJPA implements DishDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Override
        public int insert(Dish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Override
        public void update(Dish dish) {
                sessionFactory.getCurrentSession().update(dish);
        }

        @Override
        public List<Dish> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select d from Dish d").list();
        }

        @Override
        public Dish getById(int id) {
                return sessionFactory.getCurrentSession().get(Dish.class, id);
        }

        @Override
        public void delete(Dish dish) {
                sessionFactory.getCurrentSession().delete(dish);
        }
}
