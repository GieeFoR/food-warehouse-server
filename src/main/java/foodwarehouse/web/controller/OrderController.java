package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.order.CreateOrderRequest;
import foodwarehouse.web.request.order.ProductInOrderData;
import foodwarehouse.web.response.order.CancelOrderResponse;
import foodwarehouse.web.response.order.OrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ComplaintService complaintService;
    private final StorageService storageService;

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
            ComplaintService complaintService,
            StorageService storageService,
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
        this.complaintService = complaintService;
        this.storageService = storageService;
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

        List<List<ProductBatch>> productBatchesMemoryList = new LinkedList<>();
        List<List<Integer>> productBatchQuantityMemoryList = new LinkedList<>();

        for(ProductInOrderData product : products) {
            int productId = product.productId();
            int productBatchId = product.discountId();
            int quantityOrdered = product.quantity();

            List<ProductBatch> productBatchesTempList = new LinkedList<>();
            List<Integer> productBatchesQuantityTempList = new LinkedList<>();

            float productPrice;

            if(productBatchId == -1) {
                List<ProductInStorage> productInStorageAllBatches = productInStorageService.findProductInStorageAllByProductId(productId);

                productPrice = productInStorageService.findProductPrice(productInStorageAllBatches.get(0).batch().batchId());

                int tempQuantity = 0;
                for(ProductInStorage productInStorage : productInStorageAllBatches) {
                    productBatchesTempList.add(productInStorage.batch());
                    if(productInStorage.quantity() < quantityOrdered - tempQuantity) {
                        tempQuantity += productInStorage.quantity();
                        productBatchesQuantityTempList.add(productInStorage.quantity());

                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                    }
                    else if (productInStorage.quantity() == quantityOrdered - tempQuantity) {
                        productBatchesQuantityTempList.add(productInStorage.quantity());
                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                        break;
                    }
                    else {
                        productBatchesQuantityTempList.add(quantityOrdered - tempQuantity);
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
                    productBatchesTempList.add(productInStorage.batch());

                    if(productInStorage.quantity() < quantityOrdered - tempQuantity) {
                        tempQuantity += productInStorage.quantity();
                        productBatchesQuantityTempList.add(productInStorage.quantity());

                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                    }
                    else if (productInStorage.quantity() == quantityOrdered - tempQuantity) {
                        productBatchesQuantityTempList.add(productInStorage.quantity());
                        productInStorageService.updateProductInStorage(productInStorage.batch(), productInStorage.storage(), 0);
                        break;
                    }
                    else {
                        productBatchesQuantityTempList.add(quantityOrdered - tempQuantity);
                        productInStorageService.updateProductInStorage(
                                productInStorage.batch(),
                                productInStorage.storage(),
                                productInStorage.quantity()-(quantityOrdered - tempQuantity));
                        break;
                    }
                }
            }
            valueToPay += productPrice * quantityOrdered;
            productBatchesMemoryList.add(productBatchesTempList);
            productBatchQuantityMemoryList.add(productBatchesQuantityTempList);
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

        Order order = orderService
                .createOrder(payment, customer, delivery, request.comment())
                .orElseThrow(() -> new RestException("Cannot create order."));

        //List<ProductBatch> productBatchesToReturn = new LinkedList<>();

        for(int i = 0; i < productBatchesMemoryList.size(); i++) {
            for(int j = 0; j < productBatchesMemoryList.get(i).size(); j++) {
                //productBatchesToReturn.add(productBatchesMemoryList.get(i).get(j));
                productOrderService.createProductOrder(order, productBatchesMemoryList.get(i).get(j), productBatchQuantityMemoryList.get(i).get(j));
            }
        }

        return new SuccessResponse<>(
                OrderResponse.from(order, productBatchesMemoryList.stream().flatMap(List::stream).collect(Collectors.toList()))
        );
    }

    @GetMapping("/order")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<OrderResponse>> getOrders(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        List<Order> orders = orderService.findCustomerOrders(customer.customerId());
        List<OrderResponse> result = new LinkedList<>();

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
            List<ProductBatch> productBatches = new LinkedList<>();

            for (ProductOrder po : productOrders) {
                productBatches.add(po.batch());
            }
            result.add(OrderResponse.from(order, productBatches));
        }

        return new SuccessResponse<>(result);
    }

    @PutMapping("/order/{id}")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CancelOrderResponse> cancelOrder(Authentication authentication, @PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        Order order = orderService.findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this ID."));

        if(order.customer().customerId() != customer.customerId()) {
            throw new RestException("Cannot cancel this order.");
        }

        paymentService.updatePaymentState(order.payment().paymentId(), PaymentState.CANCELED);

        List<ProductOrder> productsInOrder = productOrderService.findProductOrderByOrderId(order.orderId());

        for(ProductOrder po : productsInOrder) {
            Storage storage = storageService
                    .findStorageByBatchId(po.batch().batchId())
                    .orElseThrow(() -> new RestException("Cannot find storage."));

            ProductInStorage productInStorage = productInStorageService
                    .findProductInStorageById(po.batch(), storage)
                    .orElseThrow(() -> new RestException("Cannot find product batch in storage."));

            productInStorageService
                    .updateProductInStorage(
                            po.batch(),
                            storage,
                            po.quantity() + productInStorage.quantity());

            productOrderService.deleteProductOrder(po.order().orderId(), po.batch().batchId());
        }

        orderService.deleteOrder(order.orderId());
        deliveryService.deleteDelivery(order.delivery().deliveryId());
        return new SuccessResponse<>(new CancelOrderResponse(true));
    }
}
