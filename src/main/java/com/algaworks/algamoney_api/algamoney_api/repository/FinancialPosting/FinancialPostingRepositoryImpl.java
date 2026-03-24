package com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting;

import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class FinancialPostingRepositoryImpl implements FinancialPostingRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<FinancialPosting> filter(FinancialPostingFilter financialPostingFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<FinancialPosting> criteria = builder.createQuery(FinancialPosting.class);
        Root<FinancialPosting> root = criteria.from(FinancialPosting.class);

        Predicate[] predicates = createConstraints(financialPostingFilter, builder, root);
        criteria.where(predicates);
        TypedQuery<FinancialPosting> query = manager.createQuery(criteria);

        handlePagination(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, getTotalPostings(financialPostingFilter));
    }

    private Long getTotalPostings(FinancialPostingFilter financialPostingFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<FinancialPosting> root = criteria.from(FinancialPosting.class);

        Predicate[] predicates = createConstraints(financialPostingFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private void handlePagination(TypedQuery<FinancialPosting> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalByPage = pageable.getPageSize();
        int firstPosting = currentPage * totalByPage;

        query.setFirstResult(firstPosting);
        query.setMaxResults(totalByPage);
    }

    public Predicate[] createConstraints(
            FinancialPostingFilter financialPostingFilter,
            CriteriaBuilder builder,
            Root<FinancialPosting> root
    ) {
        List<Predicate> predicates = new ArrayList<>();
        if (financialPostingFilter.getDescription() != null){
            predicates.add(
                    builder.like(
                            builder.lower(root.get("description")),
                            "%" + financialPostingFilter.getDescription().toLowerCase() + "%"
                    )
            );
        }
        if (financialPostingFilter.getExpirationDateFrom() != null){
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            root.get("expirationDate"),
                            financialPostingFilter.getExpirationDateFrom()
                    )
            );
        }
        if (financialPostingFilter.getExpirationDateUntil() != null){
            predicates.add(
                    builder.lessThanOrEqualTo(
                            root.get("expirationDate"),
                            financialPostingFilter.getExpirationDateUntil()
                    )
            );
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
