package com.algaworks.algamoney_api.algamoney_api.service;

import com.algaworks.algamoney_api.algamoney_api.exceptionHandler.exceptions.PersonNotFoundException;
import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.model.Person;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting.FinancialPostingRepositoryImpl;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPostingRepository;
import com.algaworks.algamoney_api.algamoney_api.repository.PersonRepository;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FinancialPostingService {
    @Autowired
    private FinancialPostingRepository postingRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FinancialPostingRepositoryImpl financialPostingRepository;

    public FinancialPosting create(FinancialPosting posting) {
        Person person = this.personRepository
                .findById(posting.getPerson().getId())
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        if (!person.getActive()) {
            throw new PersonNotFoundException("Person not found");
        }

        return this.postingRepository.save(posting);
    }

    public Page<FinancialPosting> findAll(FinancialPostingFilter financialPostingFilter, Pageable pageable) {
        return this.financialPostingRepository.filter(financialPostingFilter, pageable);
    }

    public FinancialPosting findById(Long id) {
        return this.postingRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Financial posting not found"));
    }

    public void delete(Long id) {
        this.postingRepository.deleteById(id);
    }
}
