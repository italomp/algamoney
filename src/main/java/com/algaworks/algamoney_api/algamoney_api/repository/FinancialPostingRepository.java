package com.algaworks.algamoney_api.algamoney_api.repository;

import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting.FinancialPostingRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPostingRepository extends JpaRepository<FinancialPosting, Long>, FinancialPostingRepositoryQuery {
}
