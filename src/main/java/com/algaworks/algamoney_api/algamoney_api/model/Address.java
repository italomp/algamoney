package com.algaworks.algamoney_api.algamoney_api.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String street;
    private String number;
    private String neighbor;
    private String city;
    private String state;
    private String cep;
}
