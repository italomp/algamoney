package com.algaworks.algamoney_api.algamoney_api.controller;

import com.algaworks.algamoney_api.algamoney_api.events.CreatedResourceEvent;
import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.repository.filter.FinancialPostingFilter;
import com.algaworks.algamoney_api.algamoney_api.service.FinancialPostingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financial-posting")
public class FinancialPostingController {
    @Autowired
    private FinancialPostingService postingService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<Page<FinancialPosting>> getAll(FinancialPostingFilter financialPostingFilter, Pageable pageable) {
        Page<FinancialPosting> postingList = this.postingService.findAll(financialPostingFilter, pageable);
        return ResponseEntity.ok(postingList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialPosting> getById(@PathVariable Long id) {
        try {
            FinancialPosting posting = this.postingService.findById(id);
            return ResponseEntity.ok(posting);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FinancialPosting> create(@Valid @RequestBody FinancialPosting posting, HttpServletResponse response) {
        FinancialPosting savedPosting = this.postingService.create(posting);
        this.eventPublisher.publishEvent(new CreatedResourceEvent(this, response, savedPosting.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPosting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        this.postingService.delete(id);
        return ResponseEntity.ok().build();
    }
}
