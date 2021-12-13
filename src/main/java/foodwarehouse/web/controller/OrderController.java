package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.order.OrderState;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductStatistics;
import foodwarehouse.core.data.product.ProductSumStatistics;
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
import foodwarehouse.web.response.order.*;
import foodwarehouse.web.response.others.CancelResponse;
import foodwarehouse.web.response.payment.PaymentResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final ProductOrderService productOrderService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final PaymentTypeService paymentTypeService;
    private final ProductInStorageService productInStorageService;
    private final ConnectionService connectionService;
    private final DeliveryService deliveryService;
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final StorageService storageService;
    private final ComplaintService complaintService;
    private final ProductService productService;

    public OrderController(ProductOrderService productOrderService, OrderService orderService, PaymentService paymentService, PaymentTypeService paymentTypeService, ProductInStorageService productInStorageService, ConnectionService connectionService, DeliveryService deliveryService, AddressService addressService, EmployeeService employeeService, CustomerService customerService, StorageService storageService, ComplaintService complaintService, ProductService productService) {
        this.productOrderService = productOrderService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.paymentTypeService = paymentTypeService;
        this.productInStorageService = productInStorageService;
        this.connectionService = connectionService;
        this.deliveryService = deliveryService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.storageService = storageService;
        this.complaintService = complaintService;
        this.productService = productService;
    }

    @PostMapping("/store/order")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CustomerOrderResponse> createOrder(Authentication authentication, @RequestBody CreateOrderRequest request) {
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

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        valueToPay *= (float) (100 - customer.discount()) / 100;

        customer = customerService.updateCustomer(
                customer.customerId(),
                customer.user(),
                customer.address(),
                customer.name(),
                customer.surname(),
                customer.firmName(),
                customer.phoneNumber(),
                customer.taxId(),
                0)
        .orElseThrow(() -> new RestException("Cannot update user discount."));

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

        Order order = orderService
                .createOrder(payment, customer, delivery, request.comment())
                .orElseThrow(() -> new RestException("Cannot create order."));

        if(payment.paymentType().type().equals("Za pobraniem gotówką") || payment.paymentType().type().equals("Za pobraniem kartą")) {
            paymentService.updatePaymentState(
                    payment.paymentId(),
                    PaymentState.WAITING);

            orderService.updateOrderState(
                    order.orderId(),
                    order.payment(),
                    order.customer(),
                    order.delivery(),
                    order.comment(),
                    OrderState.REGISTERED);
        }

        for(int i = 0; i < productBatchesMemoryList.size(); i++) {
            for(int j = 0; j < productBatchesMemoryList.get(i).size(); j++) {
                productOrderService.createProductOrder(order, productBatchesMemoryList.get(i).get(j), productBatchQuantityMemoryList.get(i).get(j));
            }
        }

        return new SuccessResponse<>(
                CustomerOrderResponse.from(
                                order,
                                productBatchesMemoryList
                                        .stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList()),
                        new LinkedList<>(),
                        productBatchQuantityMemoryList
                                .stream()
                                .flatMap(List::stream)
                                .collect(Collectors.toList())));
    }

    @GetMapping("/order")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<List<EntireOrderResponse>> getOrders() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<Order> orders = orderService.findOrders();
        List<EntireOrderResponse> result = new LinkedList<>();

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
            List<ProductBatch> productBatches = new LinkedList<>();
            List<Integer> quantity = new LinkedList<>();

            List<Complaint> complaints = complaintService.findOrderComplaints(order.orderId());

            for (ProductOrder po : productOrders) {
                productBatches.add(po.batch());
                quantity.add(po.quantity());
            }
            result.add(EntireOrderResponse.from(order, productBatches, complaints, quantity));
        }

        return new SuccessResponse<>(result);
    }

    @GetMapping("/order/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<EntireOrderResponse> getOrdersById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService.findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this ID."));

        List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
        List<ProductBatch> productBatches = new LinkedList<>();
        List<Integer> quantity = new LinkedList<>();

        List<Complaint> complaints = complaintService.findOrderComplaints(order.orderId());

        for (ProductOrder po : productOrders) {
            productBatches.add(po.batch());
            quantity.add(po.quantity());
        }
        EntireOrderResponse result = EntireOrderResponse.from(order, productBatches, complaints, quantity);

        return new SuccessResponse<>(result);
    }

    @GetMapping("/account/orders")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<CustomerOrderResponse>> getCustomerOrders(Authentication authentication) {
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
        List<CustomerOrderResponse> result = new LinkedList<>();

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
            List<ProductBatch> productBatches = new LinkedList<>();
            List<Integer> quantity = new LinkedList<>();

            List<Complaint> complaints = complaintService.findOrderComplaints(order.orderId());

            for (ProductOrder po : productOrders) {
                productBatches.add(po.batch());
                quantity.add(po.quantity());
            }
            result.add(CustomerOrderResponse.from(order, productBatches, complaints, quantity));
        }

        return new SuccessResponse<>(result);
    }

    @GetMapping("/supplier/order")
    @PreAuthorize("hasRole('Supplier')")
    public SuccessResponse<List<SupplierOrderResponse>> getSupplierActiveOrders(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        List<Order> orders = orderService.findSupplierActiveOrders(employee.employeeId());
        List<SupplierOrderResponse> result = new LinkedList<>();

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
            List<ProductBatch> productBatches = new LinkedList<>();
            List<Integer> quantity = new LinkedList<>();

            for (ProductOrder po : productOrders) {
                productBatches.add(po.batch());
                quantity.add(po.quantity());
            }
            result.add(SupplierOrderResponse.from(order, productBatches, quantity));
        }
        return new SuccessResponse<>(result);
    }

    @GetMapping("/supplier/order/{id}")
    @PreAuthorize("hasRole('Supplier')")
    public SuccessResponse<SupplierOrderResponse> getSupplierActiveOrderById(Authentication authentication, @PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        Order order = orderService.findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this ID."));

        if(order.delivery().supplier().employeeId() != employee.employeeId()) {
            throw new RestException("Cannot show this order.");
        }

        List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
        List<ProductBatch> productBatches = new LinkedList<>();
        List<Integer> quantity = new LinkedList<>();

        for (ProductOrder po : productOrders) {
            productBatches.add(po.batch());
            quantity.add(po.quantity());
        }
        SupplierOrderResponse result = SupplierOrderResponse.from(order, productBatches, quantity);
        return new SuccessResponse<>(result);
    }

    @GetMapping("/employee/order")
    @PreAuthorize("hasRole('Employee')")
    public SuccessResponse<List<EmployeeOrderResponse>> getEmployeeActiveOrders() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<Order> orders = orderService
                .findOrders()
                .stream()
                .filter(o ->
                        o.state().equals(OrderState.REGISTERED) ||
                        o.state().equals(OrderState.COMPLETING) ||
                        o.state().equals(OrderState.READY_TO_DELIVER))
                .collect(Collectors.toList());
        List<EmployeeOrderResponse> result = new LinkedList<>();

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
            List<ProductBatch> productBatches = new LinkedList<>();
            List<Integer> quantity = new LinkedList<>();

            for (ProductOrder po : productOrders) {
                productBatches.add(po.batch());
                quantity.add(po.quantity());
            }
            result.add(EmployeeOrderResponse.from(order, productBatches, quantity));
        }
        return new SuccessResponse<>(result);
    }

    @GetMapping("/employee/order/{id}")
    @PreAuthorize("hasRole('Employee')")
    public SuccessResponse<EmployeeOrderResponse> getEmployeeActiveOrderById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .filter(o ->
                        o.state().equals(OrderState.REGISTERED) ||
                                o.state().equals(OrderState.COMPLETING) ||
                                o.state().equals(OrderState.READY_TO_DELIVER))
                .orElseThrow(() -> new RestException("Cannot find order with this ID."));

        List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
        List<ProductBatch> productBatches = new LinkedList<>();
        List<Integer> quantity = new LinkedList<>();

        for (ProductOrder po : productOrders) {
            productBatches.add(po.batch());
            quantity.add(po.quantity());
        }
        EmployeeOrderResponse result = EmployeeOrderResponse.from(order, productBatches, quantity);

        return new SuccessResponse<>(result);
    }

    @GetMapping("/account/order/{id}")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CustomerOrderResponse> getCustomerOrderById(Authentication authentication, @PathVariable int id) {
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
            throw new RestException("Cannot return order with this ID.");
        }

        List<ProductOrder> productOrders = productOrderService.findProductOrderByOrderId(order.orderId());
        List<ProductBatch> productBatches = new LinkedList<>();
        List<Integer> quantity = new LinkedList<>();

        List<Complaint> complaints = complaintService.findOrderComplaints(order.orderId());

        for (ProductOrder po : productOrders) {
            productBatches.add(po.batch());
            quantity.add(po.quantity());
        }
        CustomerOrderResponse result = CustomerOrderResponse.from(order, productBatches, complaints, quantity);

        return new SuccessResponse<>(result);
    }

    @PutMapping("/account/order/{id}")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CancelResponse> cancelOrder(Authentication authentication, @PathVariable int id) {
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
        return new SuccessResponse<>(new CancelResponse(true));
    }

    @GetMapping("/statistic/order")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin')")
    public SuccessResponse<OrderStatisticsResponse> getOrderStatistics() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat toDB = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toSend = new SimpleDateFormat("yyyy-MM");

        Calendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, -1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date endDate = gc.getTime();

        List<OrderStatisticsObject> objects = new LinkedList<>();
        List<Integer> amounts = new LinkedList<>();
        List<String> dates = new LinkedList<>();
        while(startDate.before(new Date())) {
            int ordersAmount = orderService.amountOfOrdersBetween(toDB.format(startDate), toDB.format(endDate));
            objects.add(new OrderStatisticsObject(toSend.format(startDate), ordersAmount));
            amounts.add(ordersAmount);
            dates.add(toSend.format(startDate));

            gc.add(Calendar.DAY_OF_MONTH, 1);
            startDate = gc.getTime();
            gc.add(Calendar.MONTH, 1);
            gc.add(Calendar.DAY_OF_MONTH, -1);
            endDate = gc.getTime();
        }

        return new SuccessResponse<>(
                new OrderStatisticsResponse(
                        amounts,
                        dates,
                        objects));
    }

    @GetMapping("/statistic/profit")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin')")
    public SuccessResponse<ProfitStatisticsResponse> getProfitStatistics() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat toDB = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toSend = new SimpleDateFormat("yyyy-MM");

        Calendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, -1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date endDate = gc.getTime();

        List<Float> profits = new LinkedList<>();
        List<String> dates = new LinkedList<>();



        while(startDate.before(new Date())) {

            List<Order> orders = orderService.findOrdersBetweenDates(toDB.format(startDate), toDB.format(endDate));

            float profit = 0;
            for(Order order : orders) {
                List<ProductOrder> batchesInOrder = productOrderService.findProductOrderByOrderId(order.orderId());

                float losses = 0;
                for(ProductOrder productOrder : batchesInOrder) {
                    ProductBatch productBatch = productOrder.batch();
                    Product product = productBatch.product();


                    losses += product.buyPrice() * productOrder.quantity();
                }
                profit += order.payment().value() - losses;
            }

            profits.add(profit);
            dates.add(toSend.format(startDate));

            gc.add(Calendar.DAY_OF_MONTH, 1);
            startDate = gc.getTime();
            gc.add(Calendar.MONTH, 1);
            gc.add(Calendar.DAY_OF_MONTH, -1);
            endDate = gc.getTime();
        }

        return new SuccessResponse<>(
                new ProfitStatisticsResponse(profits, dates));
    }

    @GetMapping("/statistic/product")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin')")
    public SuccessResponse<ProductStatisticsResponse> getProductStatistics() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat toDB = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Calendar gc = new GregorianCalendar();
        Date endDate = gc.getTime();

        gc.add(Calendar.MONTH, -1);
        Date startDate = gc.getTime();

        List<Integer> regularQuantities = new LinkedList<>();
        List<Integer> discountQuantities = new LinkedList<>();
        List<Integer> allQuantities = new LinkedList<>();
        List<String> labels = new LinkedList<>();

        List<Order> orders = orderService.findOrdersBetweenDates(toDB.format(startDate), toDB.format(endDate));

        List<ProductStatistics> productStatistics = new LinkedList<>();

        for(Order order : orders) {
            String date = toDB.format(order.orderDate());
            LocalDate orderDate = LocalDate.parse(date, formatter);

            List<ProductOrder> batchesInOrder = productOrderService.findProductOrderByOrderId(order.orderId());

            for(ProductOrder productOrder : batchesInOrder) {
                String eatByDate = toDB.format(productOrder.batch().eatByDate());
                LocalDate productEatByDate = LocalDate.parse(eatByDate, formatter);
                LocalDate startDiscount = productEatByDate.minusDays(15);

                if(orderDate.isBefore(startDiscount)) {
                    productStatistics.add(new ProductStatistics(productOrder.batch().product(), productOrder.quantity(), 0));
                }
                else {
                    productStatistics.add(new ProductStatistics(productOrder.batch().product(), 0, productOrder.quantity()));
                }
            }
        }

        List<ProductSumStatistics> p = new LinkedList<>();
        for(ProductStatistics productStatistic : productStatistics) {
            Optional<ProductSumStatistics> productSumStatistics = p.stream().filter(o -> o.getProduct().productId() == productStatistic.product().productId()).findFirst();
            if(productSumStatistics.isPresent()) {
                productSumStatistics.get().addQuantity(productStatistic.regularQuantity(), productStatistic.discountQuantity());
            }
            else {
                p.add(new ProductSumStatistics(productStatistic.product(), productStatistic.regularQuantity(), productStatistic.discountQuantity()));
            }
        }

        p.sort(Collections.reverseOrder());
        for(ProductSumStatistics productSumStatistics : p) {
            regularQuantities.add(productSumStatistics.getRegularQuantity());
            discountQuantities.add(productSumStatistics.getDiscountQuantity());
            allQuantities.add(productSumStatistics.getAllQuantity());
            labels.add(productSumStatistics.getProduct().name());
        }


        return new SuccessResponse<>(
                new ProductStatisticsResponse(
                        labels,
                        regularQuantities,
                        discountQuantities,
                        allQuantities));
    }

    @PutMapping("/order/completing/{id}")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin') || hasRole('Employee')")
    public SuccessResponse<Void> setOrderState_Completing(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this id."));

        if(!order.state().equals(OrderState.REGISTERED)) {
            throw new RestException("Cannot change state of this order.");
        }

        orderService.updateOrderState(
                order.orderId(),
                order.payment(),
                order.customer(),
                order.delivery(),
                order.comment(),
                OrderState.COMPLETING);

        return new SuccessResponse<>(null);
    }

    @PutMapping("/order/ready-to-deliver/{id}")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin') || hasRole('Employee')")
    public SuccessResponse<Void> setOrderState_ReadyToDeliver(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this id."));

        if(!order.state().equals(OrderState.COMPLETING)) {
            throw new RestException("Cannot change state of this order.");
        }

        orderService.updateOrderState(
                order.orderId(),
                order.payment(),
                order.customer(),
                order.delivery(),
                order.comment(),
                OrderState.READY_TO_DELIVER);

        return new SuccessResponse<>(null);
    }

    @PutMapping("/order/out-for-delivery/{id}")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin') || hasRole('Supplier')")
    public SuccessResponse<Void> setOrderState_OutForDelivery(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this id."));

        if(!order.state().equals(OrderState.READY_TO_DELIVER)) {
            throw new RestException("Cannot change state of this order.");
        }

        orderService.updateOrderState(
                order.orderId(),
                order.payment(),
                order.customer(),
                order.delivery(),
                order.comment(),
                OrderState.OUT_FOR_DELIVERY);

        return new SuccessResponse<>(null);
    }

    @PutMapping("/order/delivered/{id}")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin') || hasRole('Supplier')")
    public SuccessResponse<Void> setOrderState_Delivered(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this id."));

        if(!order.state().equals(OrderState.OUT_FOR_DELIVERY)) {
            throw new RestException("Cannot change state of this order.");
        }

        orderService.updateOrderState(
                order.orderId(),
                order.payment(),
                order.customer(),
                order.delivery(),
                order.comment(),
                OrderState.DELIVERED);

        return new SuccessResponse<>(null);
    }

    @PutMapping("/order/returned/{id}")
    @PreAuthorize("hasRole('Manager') || hasRole('Admin') || hasRole('Supplier')")
    public SuccessResponse<Void> setOrderState_Returned(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrderById(id)
                .orElseThrow(() -> new RestException("Cannot find order with this id."));

        if(!order.state().equals(OrderState.OUT_FOR_DELIVERY)) {
            throw new RestException("Cannot change state of this order.");
        }

        orderService.updateOrderState(
                order.orderId(),
                order.payment(),
                order.customer(),
                order.delivery(),
                order.comment(),
                OrderState.RETURNED);

        return new SuccessResponse<>(null);
    }
}
