package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.service.CustomerService;
import org.switch2022.project.utils.*;
import org.switch2022.project.utils.assembler.CustomerValueObjectAssembler;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewCustomerDTO;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Instantiates a new Customer Controller
     * @param customerService the customer Service
     * @throws IllegalArgumentException when customer service is null
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        if (customerService == null) {
            throw new IllegalArgumentException("Customer Service cannot be null.");
        }

        this.customerService = customerService;
    }

    /**
     * Creates new Customer
     * @param newCustomerDTO the DTO with the information for the new customer
     * @return ResponseEntity with the HTTP status created and the OutputCustomerDTO that corresponds
     * to the new Customer created if successful
     *
     * Returns ResponseEntity with the HTTP status CONFLICT (409)
     * and the error message when it fails
     */
    @PostMapping("")
    public ResponseEntity<Object> createCustomer(@RequestBody NewCustomerDTO newCustomerDTO) {
        try {
            CustomerValueObjectDTO customerValueObjectDTO =
                    CustomerValueObjectAssembler.createCustomerValueObjectDTO(newCustomerDTO);

            OutputCustomerDTO outputCustomerDTO = customerService.createCustomer(customerValueObjectDTO);

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(
                    outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).createCustomer(newCustomerDTO)).withRel("collection"),
                    linkTo(methodOn(CustomerController.class).getCustomer(
                            newCustomerDTO.taxIdentificationNumber,
                            newCustomerDTO.country)).withSelfRel());

            return ResponseEntity.created(entityModel
                    .getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a customer by its tax identification number.
     * @param taxIdentificationNumber the tax identification number string
     * @param country the country string
     * @return a ResponseEntity with the retrieved customer when successful
     *
     * Returns HTTP status NOT FOUND when customer does not exist
     */
    @GetMapping()
    public ResponseEntity<Object> getCustomer(@RequestParam("taxIdentificationNumber") String taxIdentificationNumber,
                                              @RequestParam("country") String country) {

        try {
            OutputCustomerDTO outputCustomerDTO = customerService.findCustomer(taxIdentificationNumber, country);

            EntityModel<OutputCustomerDTO> entityModel = EntityModel.of(outputCustomerDTO,
                    linkTo(methodOn(CustomerController.class).getCustomer(taxIdentificationNumber, country))
                            .withSelfRel());
            return ResponseEntity.ok(entityModel);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }
}
