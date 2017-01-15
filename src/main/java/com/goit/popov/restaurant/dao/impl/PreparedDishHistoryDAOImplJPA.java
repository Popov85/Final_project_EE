package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.PreparedDish;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andrey on 10/28/2016.
 */
public class PreparedDishHistoryDAOImplJPA implements PreparedDishHistoryDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int addPreparedDish(PreparedDish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Transactional
        @Override
        public List<PreparedDish> getAll() {
                System.out.println("Session factory: "+sessionFactory);
                return sessionFactory.getCurrentSession().createQuery("select distinct pd from PreparedDish pd").list();
        }

        @Transactional
        @Override
        public List<PreparedDish> getAllChefToday(int chefId) {
                Chef chef = new Chef();
                chef.setId(chefId);
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                return sessionFactory.getCurrentSession().createQuery("select pd from PreparedDish pd join pd.chef " +
                        "where pd.chef = :chef and pd.order.openedTimeStamp >= :today")
                        .setParameter("chef", chef)
                        .setParameter("today", today, TemporalType.DATE)
                        .list();
        }
}
