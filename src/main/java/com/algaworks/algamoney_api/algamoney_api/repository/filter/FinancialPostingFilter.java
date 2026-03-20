package com.algaworks.algamoney_api.algamoney_api.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class FinancialPostingFilter {
    private String description;

    // DateTimeFormat is used to bind date with non JSON sources like http parameters or form data.
    // Very used in queries with filters (this queries uses http parameters)
    @DateTimeFormat(pattern = "yyyy-MM--dd")
    private LocalDate expirationDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM--dd")
    private LocalDate expirationDateUntil;
}
