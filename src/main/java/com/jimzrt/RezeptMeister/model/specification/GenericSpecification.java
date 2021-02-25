package com.jimzrt.RezeptMeister.model.specification;

import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6797091748162234737L;
	private SearchCriteria searchCriteria;

	public GenericSpecification(final SearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		List<Object> arguments = searchCriteria.getArguments();
		if (arguments.isEmpty()) {
			criteriaBuilder.conjunction();
		}
		// Object arg = arguments.get(0);

		switch (searchCriteria.getSearchOperation()) {
		case EQUAL: {
			if (searchCriteria.getKey().contains(".")) {
				var split = searchCriteria.getKey().split(Pattern.quote("."));
				var joined = root.join(split[0]);
				for (int i = 1; i < split.length - 1; i++) {
					joined = root.join(split[i]);
				}

				return criteriaBuilder.equal(joined.get(split[split.length - 1]), arguments.get(0));
			}
			return criteriaBuilder.equal(root.get(searchCriteria.getKey()), arguments.get(0));
		}
		case NOT_EQUAL: {
			if (searchCriteria.getKey().contains(".")) {
				var split = searchCriteria.getKey().split(Pattern.quote("."));
				var joined = root.join(split[0], JoinType.LEFT);
				for (int i = 1; i < split.length - 1; i++) {
					joined = root.join(split[i], JoinType.LEFT);
				}
				joined.on(joined.get(split[split.length - 1]).in(arguments));
				criteriaQuery.where(criteriaBuilder.isNull(joined.get(split[split.length - 1])));
				return criteriaQuery.getRestriction();
			}
			var predicate = criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), arguments.get(0));
			for (int i = 1; i < arguments.size(); i++) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), arguments.get(i)));
			}
			return predicate;

		}
		case LIKE: {
			criteriaQuery.distinct(true);
			if (searchCriteria.getKey().contains(".")) {
				var split = searchCriteria.getKey().split(Pattern.quote("."));
				var joined = root.join(split[0]);
				for (int i = 1; i < split.length - 1; i++) {
					joined = root.join(split[i]);
				}

				return criteriaBuilder.like(criteriaBuilder.lower(joined.get(split[split.length - 1])), "%" + ((String) arguments.get(0)).toLowerCase() + "%");
			}
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())), "%" + ((String) arguments.get(0)).toLowerCase() + "%");
			
		}
		// TODO
//		case NOT_LIKE: {
//
//			var joined = root.join(searchCriteria.getKey(), JoinType.LEFT);
//			joined.on(criteriaBuilder.like(criteriaBuilder.lower(joined.get("name")),
//					"%" + ((String) arg).toLowerCase() + "%"));
//			criteriaQuery.where(criteriaBuilder.isNull(joined.get("id")));
//			return criteriaQuery.getRestriction();
//
////			criteriaQuery.distinct(true);
////			var joined = root.join(searchCriteria.getKey());
////			return criteriaBuilder.notLike(criteriaBuilder.lower(joined.get("name")), "%"+ ((String)arg).toLowerCase() +"%");
//
//		}
		case GREATER_THAN:
			return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), (int)arguments.get(0));
		case IN:
			return root.get(searchCriteria.getKey()).in(arguments);
		case LESS_THAN:
			return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), (int)arguments.get(0));
		case CONTAINS: {

			// this here should work, but it doesn't. GroupBy confuses pageable count
			// [similar: https://jira.spring.io/browse/DATAJPA-945]
			// took a long time to find and debug!

//			var join = root.join(searchCriteria.getKey());
//			var in = join.get("id").in(arguments);
//			criteriaQuery.groupBy(root.get("id"));
//			criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.countDistinct(join.get("id")), arguments.size()));
//			return in;

			var subQuery = criteriaQuery.subquery(root.getModel().getBindableJavaType());
			var entityTable = subQuery.from(root.getModel().getBindableJavaType());
			var joinedTable = entityTable.join(searchCriteria.getKey());

			var i = joinedTable.get("id").in(arguments);

			subQuery.select(entityTable.get("id")).where(i);
			subQuery.groupBy(entityTable.get("id"));
			subQuery.having(
					criteriaBuilder.equal(criteriaBuilder.countDistinct(joinedTable.get("id")), arguments.size()));
			return criteriaBuilder.in(root).value(subQuery);
		}

		case CONTAINS_LIKE: {

			var subQuery = criteriaQuery.subquery(root.getModel().getBindableJavaType());
			var entityTable = subQuery.from(root.getModel().getBindableJavaType());
			var joinedTable = entityTable.join(searchCriteria.getKey());

			subQuery.select(entityTable.get("id"));
			Predicate or_all = criteriaBuilder.like(criteriaBuilder.lower(joinedTable.get("name")),
					"%" + ((String) arguments.get(0)).toLowerCase() + "%");
			if (arguments.size() > 1) {
				for (int i = 1; i < arguments.size(); ++i) {
					or_all = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(joinedTable.get("name")),
							"%" + ((String) arguments.get(i)).toLowerCase() + "%"));
				}
			}
			subQuery.where(or_all);
			subQuery.groupBy(entityTable.get("id"));
			//subQuery.having(criteriaBuilder.equal(criteriaBuilder.count(joinedTable.get("id")), arguments.size()));
			return criteriaBuilder.in(root).value(subQuery);
		}
//		case NOT_CONTAINS_LIKE: {
//
//			var subQuery = criteriaQuery.subquery(root.getModel().getBindableJavaType());
//			var entityTable = subQuery.from(root.getModel().getBindableJavaType());
//			var joinedTable = entityTable.join(searchCriteria.getKey());
//
//			subQuery.select(entityTable.get("id"));
//			Predicate or_all = criteriaBuilder.like(criteriaBuilder.lower(joinedTable.get("name")),
//					"%" + ((String) arg).toLowerCase() + "%");
//			if (arguments.size() > 1) {
//				for (int i = 1; i < arguments.size(); ++i) {
//					or_all = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(joinedTable.get("name")),
//							"%" + ((String) arguments.get(i)).toLowerCase() + "%"));
//				}
//			}
//			subQuery.where(or_all);
//			subQuery.groupBy(entityTable.get("id"));
//			subQuery.having(criteriaBuilder.equal(criteriaBuilder.count(joinedTable.get("id")), arguments.size()));
//			return criteriaBuilder.in(root).value(subQuery).not();
//		}

		case NEGATION:
			break;
		case STARTS_WITH:
			break;

		default:
			break;
		}
		return null;
	}

}