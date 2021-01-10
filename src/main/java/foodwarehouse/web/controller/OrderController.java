package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.order.CreateOrderRequest;
import foodwarehouse.web.request.order.ProductInOrderData;
import foodwarehouse.web.response.order.OrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class OrderController {
    private final ProductOrderService productOrderService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final PaymentTypeService paymentTypeService;
    private final ProductInStorageService productInStorageService;
    private final ConnectionService connectionService;
    private final DeliveryService deliveryService;
    private final AddressService addressService;
    private final ProductBatchService productBatchService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public OrderController(
            ProductOrderService productOrderService,
            OrderService orderService,
            PaymentService paymentService,
            PaymentTypeService paymentTypeService,
            ProductInStorageService productInStorageService,
            ProductBatchService productBatchService,
            DeliveryService deliveryService,
            AddressService addressService,
            EmployeeService employeeService,
            CustomerService customerService,
            ConnectionService connectionService) {
        this.productOrderService = productOrderService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.paymentTypeService = paymentTypeService;
        this.productInStorageService = productInStorageService;
        this.productBatchService = productBatchService;
        this.deliveryService = deliveryService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.connectionService = connectionService;
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<OrderResponse> createOrder(Authentication authentication, @RequestBody CreateOrderRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        PaymentType paymentType = paymentTypeService
                .findPaymentTypeById(request.paymentTypeId())
                .orElseThrow(() -> new RestException("Cannot find payment type with this ID."));

        List<ProductInOrderData> products = request.products();

        float valueToPay = 0;

        for(ProductInOrderData product : products) {
            int productId = product.productId();
            int productBatchId = product.discountId();
            int quantityOrdered = product.quantity();

            float productPrice;

            if(productBatchId == -1) {
                List<ProductInStorage> productInStorageAllBatches = productInStorageService.findProductInStorageAllByProductId(productId);

                productPrice = productInStorageService.findProductPrice(productInStorageAllBatches.get(0).batch().batchId());

                int tempQuantity = 0;
                for(ProductInStorage productInStorage : productInStorageAllBatches) {
                    if(productInStorage.quantity() < quantityOrdered - tempQuantity) {
                        tempQuantity += productInStorage.quantity();

                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                    }
                    else if (productInStorage.quantity() == quantityOrdered - tempQuantity) {
                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                        break;
                    }
                    else {
                        productInStorageService.updateProductInStorage(
                                productInStorage.batch(),
                                productInStorage.storage(),
                                productInStorage.quantity()-(quantityOrdered - tempQuantity));
                        break;
                    }
                }
            }
            else {
                List<ProductInStorage> productInStorageAll = productInStorageService.findProductInStorageAllByBatchId(productBatchId);

                productPrice = productInStorageService.findProductPrice(productBatchId);

                int tempQuantity = 0;
                for(ProductInStorage productInStorage : productInStorageAll) {
                    if(productInStorage.quantity() < quantityOrdered - tempQuantity) {
                        tempQuantity += productInStorage.quantity();

                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                    }
                    else if (productInStorage.quantity() == quantityOrdered - tempQuantity) {
                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                        break;
                    }
                    else {
                        productInStorageService.updateProductInStorage(
                                productInStorage.batch(),
                                productInStorage.storage(),
                                productInStorage.quantity()-(quantityOrdered - tempQuantity));
                        break;
                    }
                }
            }
            valueToPay += productPrice * quantityOrdered;
        }

        Payment payment = paymentService
                .createPayment(paymentType, valueToPay)
                .orElseThrow(() -> new RestException("Cannot create a new payment."));

        Address address;
        if(request.isNewAddress()) {
            address = addressService
                    .createAddress(
                            request.newDeliveryAddress().country(),
                            request.newDeliveryAddress().town(),
                            request.newDeliveryAddress().postalCode(),
                            request.newDeliveryAddress().buildingNumber(),
                            request.newDeliveryAddress().street(),
                            request.newDeliveryAddress().apartmentNumber())
                    .orElseThrow(() -> new RestException("Cannot create a new Address."));
        }
        else {
            address = addressService
                    .findAddressById(request.existingDeliveryAddressId())
                    .orElseThrow(() -> new RestException("Cannot find an address with this ID."));
        }

        Employee supplier = employeeService
                .findSupplierWithMinDelivery()
                .orElseThrow(() -> new RestException("Cannot find supplier."));


        Delivery delivery = deliveryService
                .createDelivery(address, supplier)
                .orElseThrow(() -> new RestException("Cannot create a delivery."));

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        final var orderResponse = orderService
                .createOrder(payment, customer, delivery, request.comment());




        return null;
    }
}
