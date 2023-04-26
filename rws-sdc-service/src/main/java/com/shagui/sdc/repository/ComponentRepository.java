package com.shagui.sdc.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.util.JpaUtils;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {

	public List<ComponentModel> findBySquad_Id(int squadId, Sort sort);

	public Page<ComponentModel> findBySquad_Id(int squadId, Pageable pageable);

	public Optional<ComponentModel> findBySquad_IdAndName(int squadId, String name);

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
		List<String> orderBy = new ArrayList<>();
		orderBy.add("cm.coverage");
		orderBy.add("cm.name");

		TypedQuery<ComponentModel> query = filterQuery(em, "SELECT cm FROM ComponentModel cm ", name, squad, range,
				orderBy, ComponentModel.class);

		return query;
	}

	private long countBy(EntityManager em, String name, SquadModel squad, Range range) {
		TypedQuery<Long> query = filterQuery(em, "SELECT count(cm) FROM ComponentModel cm", name, squad, range,
				new ArrayList<>(), Long.class);

		return query.getSingleResult();
	}

	private <T> TypedQuery<T> filterQuery(EntityManager em, String sql, String name, SquadModel squad, Range range,
			List<String> orderBy, Class<T> clazz) {
		StringBuilder strQuery = new StringBuilder(sql);

		List<String> whereAnds = new ArrayList<>();

		if (StringUtils.hasText(name)) {
			whereAnds.add("LOWER(cm.name) like LOWER(:name)");
		}

		if (squad != null) {
			whereAnds.add("cm.squad IS (:squad)");
		}

		if (range != null) {
			if (range.getMin() != null) {
				whereAnds.add("(:coverageMin) < cm.coverage");
			}

			if (range.getMax() != null) {
				whereAnds.add("(:coverageMax) >= cm.coverage");
			}
		}

		String whereCondition = whereAnds.stream().collect(Collectors.joining(" AND "));
		
		if (StringUtils.hasText(whereCondition)) {
			strQuery.append(" WHERE ").append(whereCondition);
		}


		if (!orderBy.isEmpty()) {
			strQuery.append(" ORDER BY ").append(orderBy.stream().collect(Collectors.joining(",")));
		}

		TypedQuery<T> query = em.createQuery(strQuery.toString(), clazz);

		if (StringUtils.hasText(name)) {
			query.setParameter("name", JpaUtils.contains(name));
		}

		if (squad != null) {
			query.setParameter("squad", squad);
		}

		if (range != null) {
			if (range.getMin() != null) {
				query.setParameter("coverageMin", range.getMin());
			}

			if (range.getMax() != null) {
				query.setParameter("coverageMax", range.getMax());
			}
		}

		return query;
	}
}
