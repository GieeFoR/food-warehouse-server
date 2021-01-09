package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.storage.CreateStorageRequest;
import foodwarehouse.web.request.storage.UpdateStorageRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.storage.StorageResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;
    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final ConnectionService connectionService;

    public StorageController(
            StorageService storageService,
            EmployeeService employeeService,
            AddressService addressService,
            ConnectionService connectionService) {
        this.storageService = storageService;
        this.employeeService = employeeService;
        this.addressService = addressService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<StorageResponse>> getStorages() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var cars = storageService
                .findAllStorages()
                .stream()
                .map(StorageResponse::fromStorage)
                .collect(Collectors.toList());

        return new SuccessResponse<>(cars);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<StorageResponse> getStorageById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return storageService
                .findStorage(id)
                .map(StorageResponse::fromStorage)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find storage with this ID."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<StorageResponse> createStorage(@RequestBody CreateStorageRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee manager = employeeService
                .findEmployeeById(request.managerId())
                .orElseThrow(() -> new RestException("Cannot find manager."));

        //create address
        Address address = addressService.createAddress(
                request.createAddressRequest().country(),
                request.createAddressRequest().town(),
                request.createAddressRequest().postalCode(),
                request.createAddressRequest().buildingNumber(),
                request.createAddressRequest().street(),
                request.createAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to create a new address."));

        return storageService
                .createStorage(
                        address,
                        manager,
                        request.createStorageData().storageName(),
                        request.createStorageData().capacity(),
                        request.createStorageData().isColdStorage())
                .map(StorageResponse::fromStorage)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new storage."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<StorageResponse> updateStorage(@RequestBody UpdateStorageRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee manager = employeeService
                .findEmployeeById(request.managerId())
                .orElseThrow(() -> new RestException("Cannot find manager."));

        //create address
        Address address = addressService.updateAddress(
                request.updateAddressRequest().addressId(),
                request.updateAddressRequest().country(),
                request.updateAddressRequest().town(),
                request.updateAddressRequest().postalCode(),
                request.updateAddressRequest().buildingNumber(),
                request.updateAddressRequest().street(),
                request.updateAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable update an address."));

        return storageService
                .updateStorage(
                        request.updateStorageData().storageId(),
                        address,
                        manager,
                        request.updateStorageData().storageName(),
                        request.updateStorageData().capacity(),
                        request.updateStorageData().isColdStorage())
                .map(StorageResponse::fromStorage)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a storage."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deleteStorages(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<DeleteResponse> result = new LinkedList<>();
        for(int i : request) {
            result.add(
                    new DeleteResponse(
                            storageService.deleteStorage(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deleteStorageById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        storageService.deleteStorage(id)));
    }
}
