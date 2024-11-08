package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.ConfigurationFileReader;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewCustomerDTO;

/**
 * The CustomerValueObjectAssembler class is responsible for converting a
 * NewCustomerDTOs into CustomerValueObjectDTOs.
 */
public final class CustomerValueObjectAssembler {

    /**
     * Private constructor to prevent instantiation of the CustomerValueObjectAssembler class.
     */
    private CustomerValueObjectAssembler() {
    }

    /**
     * Creates a CustomerValueObjectDTO from the newCustomerDTO provided by extracting the
     * values and instantiating the corresponding value objects. It uses the ConfigurationFileReader to
     * obtain the correct class to implement the tax identification number provided
     *
     * @param newCustomerDTO the new Customer DTO with the string values
     * @return the CustomerValueObjectDTO with only value objects as attributes
     * @throws RuntimeException when the creation of the tax identification number value object fails
     */
    public static CustomerValueObjectDTO createCustomerValueObjectDTO(NewCustomerDTO newCustomerDTO) {

        try {
            ConfigurationFileReader configList = new ConfigurationFileReader();

            String classNameString = configList.properties.getProperty(newCustomerDTO.country);

            TaxIdentificationNumber taxIdentificationNumber =
                    (TaxIdentificationNumber) Class.forName(classNameString).
                            getDeclaredConstructor(String.class).newInstance(newCustomerDTO.taxIdentificationNumber);

            CustomerValueObjectDTO customerValueObjectDTO = new CustomerValueObjectDTO();
            customerValueObjectDTO.customerName = new CustomerName(newCustomerDTO.customerName);
            customerValueObjectDTO.country = new Country(newCustomerDTO.country);
            customerValueObjectDTO.taxIdentificationNumber = taxIdentificationNumber;

            return customerValueObjectDTO;

        } catch (Exception e) {
            throw new RuntimeException("Invalid tax identification number or country");
        }
    }
}
