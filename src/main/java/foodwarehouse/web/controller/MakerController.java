package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.service.AddressService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.MakerService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.create.CreateMakerRequest;
import foodwarehouse.web.request.update.UpdateMakerRequest;
import foodwarehouse.web.response.DeleteResponse;
import foodwarehouse.web.response.maker.MakerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producer")
public class MakerController {

    private final MakerService makerService;
    private final AddressService addressService;
    private final ConnectionService connectionService;

    public MakerController(
            MakerService makerService,
            AddressService addressService,
            ConnectionService connectionService) {
        this.makerService = makerService;
        this.addressService = addressService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<MakerResponse>> getMakers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var makers = makerService
                .findMakers()
                .stream()
                .map(MakerResponse::fromMaker)
                .collect(Collectors.toList());

        return new SuccessResponse<>(makers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<MakerResponse> getMakerById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return makerService
                .findMakerById(id)
                .map(MakerResponse::fromMaker)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find producer with this ID."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<MakerResponse> createMaker(@RequestBody CreateMakerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //create address
        Address address = addressService.createAddress(
                request.createAddressRequest().country(),
                request.createAddressRequest().town(),
                request.createAddressRequest().postalCode(),
                request.createAddressRequest().buildingNumber(),
                request.createAddressRequest().street(),
                request.createAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to create a new address."));

        return makerService
                .createMaker(
                        address,
                        request.createMakerData().firmName(),
                        request.createMakerData().phoneNumber(),
                        request.createMakerData().email())
                .map(MakerResponse::fromMaker)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new producer."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<MakerResponse> updateMaker(@RequestBody UpdateMakerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //update address
        Address address = addressService.updateAddress(
                request.updateAddressRequest().addressId(),
                request.updateAddressRequest().country(),
                request.updateAddressRequest().town(),
                request.updateAddressRequest().postalCode(),
                request.updateAddressRequest().buildingNumber(),
                request.updateAddressRequest().street(),
                request.updateAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to update an address."));

        return makerService
                .updateMaker(
                        request.updateMakerData().makerId(),
                        address,
                        request.updateMakerData().firmName(),
                        request.updateMakerData().phoneNumber(),
                        request.updateMakerData().email()
                )
                .map(MakerResponse::fromMaker)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update producer."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deleteMakers(@RequestBody List<Integer> request) {
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
                            makerService.deleteMaker(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deleteMakerById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        makerService.deleteMaker(id)));
    }
}
