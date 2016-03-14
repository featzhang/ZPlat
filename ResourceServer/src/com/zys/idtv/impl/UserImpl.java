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
import com.zys.idtv.entity.UserEntity;
import com.zys.idtv.entity.UserTypeEntity;
import com.zys.idtv.ifc.UserIfc;

public class UserImpl implements UserIfc {

	@Override
	public void addUser(UserEntity user) {
		System.out.println("UserEntity: " + user);
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}

	@Override
	public UserEntity findUserById(int id) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			UserEntity user = (UserEntity) s.get(UserEntity.class, id);// User.class�����ҵ�ӳ���ļ�
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public UserEntity findUserByName(String name) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			Criteria c = s.createCriteria(UserEntity.class);
			c.add(Restrictions.eq("name", name));
			UserEntity user = (UserEntity) c.uniqueResult();
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	public UserEntity findUserByName1(String name) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			String hql = "from UserEntity as user where user.name=:n";
			Query q = s.createQuery(hql);
			q.setString("n", name);
			UserEntity user = (UserEntity) q.uniqueResult();
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void remove(UserEntity user) {
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
	public void saveUser(UserEntity user) {
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
	public void updateUser(UserEntity user) {
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

	public List<UserEntity> getAllUsers() {
		Session s = null;
		List<UserEntity> userEntities = null;
		try {
			s = HibernateUitl.getSession();
			String hql = "from UserEntity";
			Query q = s.createQuery(hql);
			List<?> list = q.list();
			userEntities = new ArrayList<UserEntity>();
			for (int i = 0; i < list.size(); i++) {
				UserEntity o = (UserEntity) list.get(i);
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
	public List<UserTypeEntity> getUserTypeList() {
		Session s = null;
		List<UserTypeEntity> userEntities = null;
		try {
			s = HibernateUitl.getSession();
			String hql = "from UserTypeEntity";
			Query q = s.createQuery(hql);
			List<?> list = q.list();
			userEntities = new ArrayList<UserTypeEntity>();
			for (int i = 0; i < list.size(); i++) {
				UserTypeEntity o = (UserTypeEntity) list.get(i);
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
	public UserEntity getUserEntity(String name, String password) {
		Session s = null;
		try {
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			String hql = "from UserEntity as user where user.name=:n and user.password=:p";
			Query q = s.createQuery(hql);
			q.setString("n", name);
			q.setString("p", password);
			UserEntity user = (UserEntity) q.uniqueResult();
			return user;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	public List<UserEntity> getManagerUsers() {

		Session s = null;
		List<UserEntity> userEntities = null;
		try {
			s = HibernateUitl.getSession();
			String hql = "from UserTypeEntity as userType where userType.typeName=:t";
			Query q = s.createQuery(hql);
			q.setString("t", "manager");
			UserTypeEntity userTypeEntity = (UserTypeEntity) q.uniqueResult();
			if (userTypeEntity == null) {
				if (s != null) {
					s.close();
				}
				return null;
			}

			int id = userTypeEntity.getId();
			s = HibernateUitl.getSession();
			// Criteria c = s.createCriteria(User.class);
			// c.add(Restrictions.eq("name",name));
			hql = "from UserEntity as user where user.typeId=:tt";
			q = s.createQuery(hql);
			q.setInteger("tt", id);
			List<?> list = q.list();
			userEntities = new ArrayList<UserEntity>();
			for (int i = 0; i < list.size(); i++) {
				UserEntity o = (UserEntity) list.get(i);
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
