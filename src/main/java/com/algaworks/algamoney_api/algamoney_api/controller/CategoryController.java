package com.algaworks.algamoney_api.algamoney_api.controller;

import com.algaworks.algamoney_api.algamoney_api.events.CreatedResourceEvent;
import com.algaworks.algamoney_api.algamoney_api.model.Category;
import com.algaworks.algamoney_api.algamoney_api.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public List<Category> getAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category saved = this.repository.save(category);
        this.eventPublisher.publishEvent(new CreatedResourceEvent(this, response, saved.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
            return ResponseEntity.ok().body(category);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
