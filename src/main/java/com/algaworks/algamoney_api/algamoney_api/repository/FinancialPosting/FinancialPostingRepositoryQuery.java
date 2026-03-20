package com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting;

import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;

import java.util.List;

public interface FinancialPostingRepositoryQuery {
    public List<FinancialPosting> filter(FinancialPostingFilter financialPostingFilter);
}
