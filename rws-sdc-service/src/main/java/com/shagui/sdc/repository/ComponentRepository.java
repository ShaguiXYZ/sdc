package com.shagui.sdc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {
	default Page<ComponentModel> filter(EntityManager em, String name, SquadModel squad, Range range,
			Pageable pageable) {
		TypedQuery<ComponentModel> query = findByQuery(em, name, squad, range);

		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();

		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);

		List<ComponentModel> resultList = query.getResultList();
		long totalReords = countBy(em, name, squad, range);

		return new PageImpl<ComponentModel>(resultList, pageable, totalReords);
	}

	default Page<ComponentModel> filter(EntityManager em, String name, SquadModel squad, Range range) {
		TypedQuery<ComponentModel> query = findByQuery(em, name, squad, range);

		return new PageImpl<ComponentModel>(query.getResultList());
	}

	private TypedQuery<ComponentModel> findByQuery(EntityManager em, String name, SquadModel squad, Range range) {
		TypedQuery<ComponentModel> query = filterQuery("SELECT cm FROM ComponentModel cm ", name, squad, range, em,
				ComponentModel.class);

		return query;
	}

	private long countBy(EntityManager em, String name, SquadModel squad, Range range) {
		TypedQuery<Long> query = filterQuery("SELECT count(cm) FROM ComponentModel cm", name, squad, range, em,
				Long.class);

		return query.getSingleResult();
	}

	private <T> TypedQuery<T> filterQuery(String sql, String name, SquadModel squad, Range range, EntityManager em,
			Class<T> clazz) {
		StringBuilder strQuery = new StringBuilder(sql).append(" WHERE ((:squad IS NULL) OR cm.squad IS :squad) AND ")
				.append("((:name IS NULL) OR cm.name like (:name)) AND ")
				.append("(((:coverageMin IS NULL) OR cm.coverage >= (:coverageMin)) AND ")
				.append("((:coverageMax IS NULL) OR cm.coverage <= (:coverageMax)))");

		TypedQuery<T> query = em.createQuery(strQuery.toString(), clazz);

		query.setParameter("name", name == null ? null : "%" + name + "%").setParameter("squad", squad)
				.setParameter("coverageMin", range == null ? null : range.getMin())
				.setParameter("coverageMax", range == null ? null : range.getMax());

		return query;
	}
}
