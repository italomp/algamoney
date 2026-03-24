package com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting;

import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FinancialPostingRepositoryQuery {
    Page<FinancialPosting> filter(FinancialPostingFilter financialPostingFilter, Pageable pageable);
}
