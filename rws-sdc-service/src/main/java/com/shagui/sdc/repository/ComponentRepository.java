package com.shagui.sdc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {
	default Page<ComponentModel> findBy(EntityManager em, String name, SquadModel squad, Pageable pageable) {
		TypedQuery<ComponentModel> query = findByQuery(em, name, squad);

		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();

		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);

		List<ComponentModel> resultList = query.getResultList();
		long totalReords = countBy(em, name, squad);

		return new PageImpl<ComponentModel>(resultList, pageable, totalReords);
	}

	default Page<ComponentModel> findBy(EntityManager em, String name, SquadModel squad) {
		TypedQuery<ComponentModel> query = findByQuery(em, name, squad);

		return new PageImpl<ComponentModel>(query.getResultList());
	}

	private TypedQuery<ComponentModel> findByQuery(EntityManager em, String name, SquadModel squad) {
		StringBuilder sql = new StringBuilder("SELECT cm FROM ComponentModel cm ")
				.append("WHERE ((:squad IS NULL) OR cm.squad IS :squad) AND ")
				.append("((:name IS NULL) OR cm.name like (:name))");

		TypedQuery<ComponentModel> query = em.createQuery(sql.toString(), ComponentModel.class);

		query.setParameter("name", name == null ? null : "%" + name + "%").setParameter("squad", squad);

		return query;
	}

	private long countBy(EntityManager em, String name, SquadModel squad) {
		StringBuilder sql = new StringBuilder("SELECT count(cm) FROM ComponentModel cm ")
				.append("WHERE ((:squad IS NULL) OR cm.squad IS :squad) AND ")
				.append("((:name IS NULL) OR cm.name like (:name))");

		TypedQuery<Long> queryTotal = em.createQuery(sql.toString(), Long.class);

		queryTotal.setParameter("name", name == null ? null : "%" + name + "%").setParameter("squad", squad);

		return queryTotal.getSingleResult();
	}
}
