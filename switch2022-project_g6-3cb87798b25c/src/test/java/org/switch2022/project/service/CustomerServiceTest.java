package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.customer.CustomerFactory;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.interfaces.CustomerRepository;
import org.switch2022.project.utils.assembler.OutputCustomerAssembler;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {


    @MockBean
    private CustomerRepository customerRepositoryDouble;

    @MockBean
    private CustomerFactory customerFactoryDouble;

    @Autowired
    private CustomerService customerService;

    private Customer customerOne;
    private TaxIdentificationNumberPortugalImplementation taxIdentificationNumberOneDouble;
    private TaxIdentificationNumber taxIdentificationNumberTwoDouble;
    private OutputCustomerDTO outputCustomerDTODouble;
    private MockedStatic<OutputCustomerAssembler> outputAssembler;

    @BeforeEach
    void init() {
        customerOne = mock(Customer.class);

        taxIdentificationNumberOneDouble = mock(TaxIdentificationNumberPortugalImplementation.class);
        taxIdentificationNumberTwoDouble = mock(TaxIdentificationNumber.class);

        outputCustomerDTODouble = mock(OutputCustomerDTO.class);

        outputAssembler = mockStatic(OutputCustomerAssembler.class);
    }

    @AfterEach
    void tearDown() {
        customerOne = null;
        outputAssembler.close();
    }


    @Test
    void constructor_checkIfExceptionIsThrownWhenCustomerRepositoryIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CustomerService(null, customerFactoryDouble));
        assertEquals("Customer Repository cannot be null", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenCustomerFactoryIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CustomerService(customerRepositoryDouble, null));
        assertEquals("Customer Factory cannot be null", exception.getMessage());
    }

    @Test
    void createCustomer_UniqueCustomer_True() {
        when(customerRepositoryDouble.existsById(any())).thenReturn(false);
        when(customerFactoryDouble.createCustomer(any(), any(), any())).thenReturn(customerOne);
        when(customerRepositoryDouble.save(customerOne)).thenReturn(customerOne);
        outputAssembler.when(() -> OutputCustomerAssembler.createOutputCustomerDTO(customerOne))
                .thenReturn(outputCustomerDTODouble);

        CustomerValueObjectDTO newCustomerDTO = new CustomerValueObjectDTO();
        newCustomerDTO.taxIdentificationNumber = new TaxIdentificationNumberPortugalImplementation("201966182");
        newCustomerDTO.customerName = new CustomerName("One");
        newCustomerDTO.country = new Country("portugal");

        OutputCustomerDTO result = customerService.createCustomer(newCustomerDTO);

        assertEquals(outputCustomerDTODouble, result);
    }


    @Test
    void createCustomer_ExistingCustomer_False() {
        when(customerRepositoryDouble.existsById(any())).thenReturn(true);

        CustomerValueObjectDTO newCustomerDTO = new CustomerValueObjectDTO();
        newCustomerDTO.taxIdentificationNumber = new TaxIdentificationNumberPortugalImplementation("201966182");
        newCustomerDTO.customerName = new CustomerName("One");
        newCustomerDTO.country = new Country("portugal");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.createCustomer(newCustomerDTO));

        assertEquals("Customer already exists", exception.getMessage());
    }
}
