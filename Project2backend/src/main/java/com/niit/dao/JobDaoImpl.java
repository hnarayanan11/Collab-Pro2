package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.models.Job;
@Repository
@Transactional
public class JobDaoImpl implements JobDao {
@Autowired
private SessionFactory sessionFactory;
	public void saveJob(Job job) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(job);
	}
	public List<Job> getAllJobs() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();
		return jobs;
	}
	public void deleteJob(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Job job=(Job) session.get(Job.class, id);
		session.delete(job);		
	}
	public void updateJob(Job job) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();		
		session.update(job);		
	}
	public Job getJob(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Job job=(Job) session.get(Job.class, id);
		return job;
	}
	


}
