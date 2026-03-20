package com.algaworks.algamoney_api.algamoney_api.service;

import com.algaworks.algamoney_api.algamoney_api.exceptionHandler.exceptions.PersonNotFoundException;
import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.model.Person;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPosting.FinancialPostingRepositoryImpl;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPostingRepository;
import com.algaworks.algamoney_api.algamoney_api.repository.PersonRepository;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<FinancialPosting> findAll(FinancialPostingFilter financialPostingFilter) {
        List<FinancialPosting> result;
        if (
                financialPostingFilter.getDescription()  != null ||
                financialPostingFilter.getExpirationDateFrom() != null ||
                financialPostingFilter.getExpirationDateUntil() != null
        ) {
                 result = this.financialPostingRepository.filter(financialPostingFilter);
        } else {
            result = this.postingRepository.findAll();
        }
        return result;
    }

    public FinancialPosting findById(Long id) {
        return this.postingRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Financial posting not found"));
    }

    public void delete(Long id) {
        this.postingRepository.deleteById(id);
    }
}
