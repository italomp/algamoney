package com.algaworks.algamoney_api.algamoney_api;

import com.algaworks.algamoney_api.algamoney_api.exceptionHandler.exceptions.PersonNotFoundException;
import com.algaworks.algamoney_api.algamoney_api.model.Category;
import com.algaworks.algamoney_api.algamoney_api.model.FinancialPosting;
import com.algaworks.algamoney_api.algamoney_api.model.FinancialPostingType;
import com.algaworks.algamoney_api.algamoney_api.model.Person;
import com.algaworks.algamoney_api.algamoney_api.repository.FinancialPostingRepository;
import com.algaworks.algamoney_api.algamoney_api.repository.PersonRepository;
import com.algaworks.algamoney_api.algamoney_api.service.FinancialPostingService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FinancialPostingServiceTests {
    static Category category;
    static Person person;
    static Person inactivePerson;
    static Long nonExistentPersonId;
    static FinancialPosting posting;
    static FinancialPosting postingWithInactivePerson;

    @Mock
    private FinancialPostingRepository postingRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private FinancialPostingService postingService;

    @BeforeAll
    public static void initEntities(){
        FinancialPostingServiceTests.category = Category.builder().id(1L).name("Food").build();

        FinancialPostingServiceTests.person = Person.builder()
                .id(1L)
                .name("Italo")
                .active(true)
                .build();

        FinancialPostingServiceTests.inactivePerson = Person.builder()
                .id(1L)
                .name("Maria")
                .active(false)
                .build();

        FinancialPostingServiceTests.nonExistentPersonId = 2L;

        FinancialPostingServiceTests.posting = FinancialPosting.builder()
                .description("Conta de luz")
                .expirationDate(LocalDate.of(2026, 4, 10))
                .price(new BigDecimal("150.00"))
                .type(FinancialPostingType.EXPENSE)
                .category(category)
                .person(person)
                .build();

        FinancialPostingServiceTests.postingWithInactivePerson = FinancialPosting.builder()
                .description("Conta de luz")
                .expirationDate(LocalDate.of(2026, 4, 10))
                .price(new BigDecimal("150.00"))
                .type(FinancialPostingType.EXPENSE)
                .category(category)
                .person(inactivePerson)
                .build();
    }

    @Test
    @DisplayName("Must create a posting successfully")
    public void createFinancialPosting() {
        when(this.personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(this.postingRepository.save(posting)).thenReturn(posting);

        FinancialPosting savedPosting = this.postingService.create(posting);
        Assertions.assertEquals(savedPosting, posting, "The saved posting is different of the given posting");
    }

    @Test
    @DisplayName("Must throw exception in posting creation because person isn't active")
    public void throwExceptionInPostingCreationWithPersonNotActive() {
        when(this.personRepository.findById(inactivePerson.getId())).thenReturn(Optional.of(inactivePerson));

        Assertions.assertThrows(
                PersonNotFoundException.class,
                () -> this.postingService.create(postingWithInactivePerson),
                "The posting was created with invalid user");
    }
}
