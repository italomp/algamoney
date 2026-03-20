package com.algaworks.algamoney_api.algamoney_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_posting")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FinancialPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    // Used to serialization and deserialization between java objects and json
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate expirationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate paymentDate;
    @NotNull
    private BigDecimal price;
    private String note;
    @NotNull
    @Enumerated(EnumType.STRING)
    private FinancialPostingType type;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
