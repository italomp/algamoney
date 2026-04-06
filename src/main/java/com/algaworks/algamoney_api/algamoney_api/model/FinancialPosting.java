package com.algaworks.algamoney_api.algamoney_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        FinancialPosting that = (FinancialPosting) object;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) &&
                Objects.equals(expirationDate, that.expirationDate) && Objects.equals(paymentDate, that.paymentDate) &&
                Objects.equals(price, that.price) && Objects.equals(note, that.note) && type == that.type &&
                Objects.equals(category, that.category) && Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, expirationDate, paymentDate, price, note, type, category, person);
    }
}
