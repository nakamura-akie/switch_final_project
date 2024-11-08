package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.interfaces.CustomerRepository;
import org.switch2022.project.service.CustomerService;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewCustomerDTO;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class CustomerControllerTest {

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class UnitTest {

        @Autowired
        CustomerController customerController;

        @MockBean
        CustomerService customerServiceDouble;

        String portugal;
        String taxIdentificationNumberOne;
        String customerNameOne;

        @BeforeEach
        void init() {
            portugal = "portugal";
            taxIdentificationNumberOne = "200061763";
            customerNameOne = "Tiago";
        }

        @AfterEach
        void tearDown() {
            portugal = null;
            taxIdentificationNumberOne = null;
            customerNameOne = null;
        }

        @Test
        void createCustomer_SuccessfulCreation_True() {
            NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
            newCustomerDTO.taxIdentificationNumber = taxIdentificationNumberOne;
            newCustomerDTO.customerName = customerNameOne;
            newCustomerDTO.country = portugal;

            CustomerValueObjectDTO customerValueObjectDTO = new CustomerValueObjectDTO();
            customerValueObjectDTO.taxIdentificationNumber =
                    new TaxIdentificationNumberPortugalImplementation(taxIdentificationNumberOne);
            customerValueObjectDTO.customerName = new CustomerName(customerNameOne);
            customerValueObjectDTO.country = new Country(portugal);

            OutputCustomerDTO outputDTO = new OutputCustomerDTO();
            outputDTO.customerName = customerNameOne;
            outputDTO.taxIdentificationNumber = taxIdentificationNumberOne;
            outputDTO.country = portugal;

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDTO)).withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDTO.taxIdentificationNumber,
                            newCustomerDTO.country)).withSelfRel());

            ResponseEntity<Object> expected = ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).
                            toUri()).body(entityModel);

            when(customerServiceDouble.createCustomer
                    (customerValueObjectDTO)).thenReturn(outputDTO);

            ResponseEntity<Object> result = customerController.createCustomer
                    (newCustomerDTO);

            assertEquals(expected, result);
        }

    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class IntegrationTest {
        @Autowired
        CustomerController customerController;
        @Autowired
        CustomerRepository customerRepository;
        String customerNameOne;
        String customerNameTwo;
        String taxIdentificationNumberPT;
        String taxIdentificationNumberES;
        String taxIdentificationNumberOTHER;
        String portugal;
        String spain;
        String other;
        NewCustomerDTO newCustomerDtoPT;
        NewCustomerDTO newCustomerDtoES;
        NewCustomerDTO newCustomerDtoOTHER;

        @BeforeEach
        void init() {
            customerNameOne = "Ana";
            customerNameTwo = "Manuel";

            taxIdentificationNumberPT = "200061763";
            taxIdentificationNumberES = "65703276G";
            taxIdentificationNumberOTHER = "123456789";

            portugal = "portugal";
            spain = "spain";
            other = "other";

            newCustomerDtoPT = new NewCustomerDTO();
            newCustomerDtoPT.taxIdentificationNumber = taxIdentificationNumberPT;
            newCustomerDtoPT.customerName = customerNameOne;
            newCustomerDtoPT.country = portugal;

            newCustomerDtoES = new NewCustomerDTO();
            newCustomerDtoES.taxIdentificationNumber = taxIdentificationNumberES;
            newCustomerDtoES.customerName = customerNameOne;
            newCustomerDtoES.country = spain;

            newCustomerDtoOTHER = new NewCustomerDTO();
            newCustomerDtoOTHER.taxIdentificationNumber = taxIdentificationNumberOTHER;
            newCustomerDtoOTHER.customerName = customerNameOne;
            newCustomerDtoOTHER.country = other;
        }

        @AfterEach
        void tearDown() {
            customerNameOne = null;
            customerNameTwo = null;
            taxIdentificationNumberPT = null;
            taxIdentificationNumberES = null;
            taxIdentificationNumberOTHER = null;
            portugal = null;
            spain = null;
            other = null;
            newCustomerDtoPT = null;
            customerRepository.deleteAll();
        }

        @Test
        void constructor_NullService_ThrowException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new CustomerController(null));
            assertEquals("Customer Service cannot be null.", exception.getMessage());
        }

        @Test
        void createCustomer_SuccessfulCreationWithPortugueseTaxIdentificationNumber_True() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberPT;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = portugal;

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDtoPT)).withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDtoPT.taxIdentificationNumber,
                            newCustomerDtoPT.country)).withSelfRel());

            ResponseEntity<Object> expected = ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).
                            toUri()).body(entityModel);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoPT);

            assertEquals(expected, result);
        }


        @Test
        void createCustomer_SuccessfulCreationWithSpanishTaxIdentificationNumber_True() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberES;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = spain;

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDtoES)).withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDtoES.taxIdentificationNumber,
                            newCustomerDtoES.country)).withSelfRel());

            ResponseEntity<Object> expected = ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).
                            toUri()).body(entityModel);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoES);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_SuccessfulCreationWithInternationalTaxIdentificationNumber_True() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberOTHER;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = other;

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDtoOTHER))
                            .withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDtoOTHER.taxIdentificationNumber,
                            newCustomerDtoOTHER.country)).withSelfRel());

            ResponseEntity<Object> expected = ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).
                            toUri()).body(entityModel);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoOTHER);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithInvalidPortugueseTaxIdentificationNumber_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Invalid tax identification number or country"));

            NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
            newCustomerDTO.taxIdentificationNumber = "123";
            newCustomerDTO.customerName = customerNameOne;
            newCustomerDTO.country = portugal;

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDTO);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithInvalidSpanishTaxIdentificationNumber_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Invalid tax identification number or country"));

            NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
            newCustomerDTO.taxIdentificationNumber = "not a tax identification number";
            newCustomerDTO.customerName = customerNameOne;
            newCustomerDTO.country = spain;

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDTO);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithInvalidInternationalTaxIdentificationNumber_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Invalid tax identification number or country"));

            NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
            newCustomerDTO.taxIdentificationNumber = "not a tax identification number";
            newCustomerDTO.customerName = customerNameOne;
            newCustomerDTO.country = other;

            ResponseEntity<Object> result = customerController.createCustomer
                    (newCustomerDTO);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithExistingPortugueseCustomer_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Customer already exists"));

            ResponseEntity<Object> firstCustomer = customerController.createCustomer(
                    newCustomerDtoPT);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoPT);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithExistingSpanishCustomer_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Customer already exists"));

            ResponseEntity<Object> firstCustomer = customerController.createCustomer(
                    newCustomerDtoES);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoES);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithExistingInternationalCustomer_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Customer already exists"));

            ResponseEntity<Object> firstCustomer = customerController.createCustomer(
                    newCustomerDtoOTHER);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoOTHER);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_FailedCreationWithNonExistingCountry_Conflict() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Invalid tax identification number or country"));

            NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
            newCustomerDTO.taxIdentificationNumber = taxIdentificationNumberOTHER;
            newCustomerDTO.customerName = customerNameOne;
            newCustomerDTO.country = "Not a country";

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDTO);

            assertEquals(expected, result);
        }

        @Test
        void createCustomer_SuccessfulCreationWithPortugueseTaxIdentificationNumberWithExistingCustomers_Created() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberPT;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = portugal;

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDtoPT)).withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDtoPT.taxIdentificationNumber,
                            newCustomerDtoPT.country)).withSelfRel());

            ResponseEntity<Object> expected = ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).
                            toUri()).body(entityModel);

            ResponseEntity<Object> firstCustomerES = customerController.createCustomer(
                    newCustomerDtoES);

            ResponseEntity<Object> firstCustomerOTHER = customerController.createCustomer(
                    newCustomerDtoOTHER);

            ResponseEntity<Object> result = customerController.createCustomer(
                    newCustomerDtoPT);

            assertEquals(expected, result);
        }

        @Test
        void getCustomer_SuccessfulReturnsLink_True() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberPT;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = portugal;

            customerController.createCustomer(newCustomerDtoPT);

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).getCustomer(taxIdentificationNumberPT, portugal))
                            .withSelfRel());
            ResponseEntity<Object> expected =
                    ResponseEntity.ok(entityModel);

            ResponseEntity<Object> result = customerController.getCustomer(taxIdentificationNumberPT, portugal);

            assertEquals(expected, result);
        }

        @Test
        void getCustomer_NonExistingCustomer_True() {
            OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
            outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumberPT;
            outputCustomerDTO.customerName = customerNameOne;
            outputCustomerDTO.country = portugal;

            ResponseEntity<Object> expected =
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Customer not found"));

            ResponseEntity<Object> result = customerController.getCustomer(taxIdentificationNumberPT, portugal);

            assertEquals(expected, result);
        }
    }
}
