package foodwarehouse.web.controller;


import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.service.AddressService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.CustomerService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.response.address.AddressResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AddressService addressService;
    private final CustomerService customerService;
    private final ConnectionService connectionService;

    public AccountController(AddressService addressService, CustomerService customerService, ConnectionService connectionService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.connectionService = connectionService;
    }

    @GetMapping("/address")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<AddressResponse>> getAddress(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find user."));

        List<Address> addressList = new LinkedList<>();
        addressList.add(customer.address());

        return new SuccessResponse<>(addressList.stream().map(AddressResponse::fromAddress).collect(Collectors.toList()));
    }
}
