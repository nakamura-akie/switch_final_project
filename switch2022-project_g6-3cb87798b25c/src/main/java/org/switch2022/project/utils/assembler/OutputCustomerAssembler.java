package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

/**
 * The OutputCustomerAssembler class is responsible for converting customers into OutputCustomerDTOs.
 */
public final class OutputCustomerAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputCustomerAssembler class.
     */
    private OutputCustomerAssembler() {
    }

    /**
     * Creates an OutputCustomerDTO from a domain Customer object
     *
     * @param domainCustomer the domain object that will provide the data for the OutputCustomerDTO
     * @return the created OutputCustomerDTO
     */
    public static OutputCustomerDTO createOutputCustomerDTO(Customer domainCustomer) {
        String taxIdentificationNumber = domainCustomer.getTaxIdentificationNumber().getTaxIdentificationNumber();
        String customerName = domainCustomer.getCustomerName().getCustomerNameValue();
        String country = domainCustomer.getCountry().getCountryName();

        OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
        outputCustomerDTO.taxIdentificationNumber = taxIdentificationNumber;
        outputCustomerDTO.customerName = customerName;
        outputCustomerDTO.country = country;

        return outputCustomerDTO;
    }
}
