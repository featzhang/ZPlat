package com.zys.idtv.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.zys.idtv.dao.HibernateUitl;
import com.zys.idtv.entity.TimeTableEntity;
import com.zys.idtv.ifc.TimeTableIfc;

public class TimeTableImp implements TimeTableIfc {

	@Override
	public void addTimeTable(TimeTableEntity user) {
		System.out.println("TimeTableEntity: " + user);
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}

	@Override
	public TimeTableEntity findTimeTableById(int id) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			TimeTableEntity user = (TimeTableEntity) s.get(
					TimeTableEntity.class, id);
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public TimeTableEntity findTimeTableByName(String name) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			Criteria c = s.createCriteria(TimeTableEntity.class);
			c.add(Restrictions.eq("name", name));
			TimeTableEntity user = (TimeTableEntity) c.uniqueResult();
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public List<TimeTableEntity> getAllTimeTables() {
		Session s = null;
		List<TimeTableEntity> userEntities = null;
		try {
			s = HibernateUitl.getSession();
			String hql = "from TimeTableEntity";
			Query q = s.createQuery(hql);
			List<?> list = q.list();
			userEntities = new ArrayList<TimeTableEntity>();
			for (int i = 0; i < list.size(); i++) {
				TimeTableEntity o = (TimeTableEntity) list.get(i);
				userEntities.add(o);
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return userEntities;
	}

	@Override
	public void removeTimeTable(TimeTableEntity user) {
		Session s = null;
		Transaction tx;
		try {
			s = HibernateUitl.getSession();
			tx = s.beginTransaction();
			s.delete(user);
			tx.commit();
		} finally {
			if (s != null) {
				s.close();
			}

		}
	}

	@Override
	public void saveTimeTable(TimeTableEntity user) {
		Session s = null;
		Transaction tx;
		try {
			s = HibernateUitl.getSession();
			tx = s.beginTransaction();
			s.save(user);
			tx.commit();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void updateTimeTable(TimeTableEntity user) {
		Session s = null;
		Transaction tx;
		try {
			s = HibernateUitl.getSession();
			tx = s.beginTransaction();
			s.update(user);
			tx.commit();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

}
