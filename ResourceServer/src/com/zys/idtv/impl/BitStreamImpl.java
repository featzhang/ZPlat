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
import com.zys.idtv.entity.BitStreamDeviceEntity;
import com.zys.idtv.ifc.BitStreamDeviceIfc;

public class BitStreamImpl implements BitStreamDeviceIfc {

	@Override
	public void addDevice(BitStreamDeviceEntity user) {

		System.out.println("BitStreamDeviceEntity: " + user);
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}

	@Override
	public BitStreamDeviceEntity findDeviceById(int id) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			BitStreamDeviceEntity user = (BitStreamDeviceEntity) s.get(
					BitStreamDeviceEntity.class, id);// User.class�����ҵ�ӳ���ļ�
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public BitStreamDeviceEntity findDeviceByName(String name) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			Criteria c = s.createCriteria(BitStreamDeviceEntity.class);
			c.add(Restrictions.eq("name", name));
			BitStreamDeviceEntity user = (BitStreamDeviceEntity) c
					.uniqueResult();
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void removeDevice(BitStreamDeviceEntity user) {
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
	public void saveDevice(BitStreamDeviceEntity user) {
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
	public void updateDevice(BitStreamDeviceEntity user) {
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

	@Override
	public List<BitStreamDeviceEntity> getAllDevices() {
		Session s = null;
		List<BitStreamDeviceEntity> userEntities = null;
		try {
			s = HibernateUitl.getSession();
			String hql = "from BitStreamDeviceEntity";
			Query q = s.createQuery(hql);
			List<?> list = q.list();
			userEntities = new ArrayList<BitStreamDeviceEntity>();
			for (int i = 0; i < list.size(); i++) {
				BitStreamDeviceEntity o = (BitStreamDeviceEntity) list.get(i);
				userEntities.add(o);
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return userEntities;
	}

}
