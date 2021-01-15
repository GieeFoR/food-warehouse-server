package foodwarehouse.web.controller;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.core.service.MessageService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.message.CreateMessageRequest;
import foodwarehouse.web.response.message.MessageResponse;
import foodwarehouse.web.response.message.MessageSentResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final EmployeeService employeeService;
    private final ConnectionService connectionService;


    public MessageController(
            MessageService messageService,
            EmployeeService employeeService,
            ConnectionService connectionService) {
        this.messageService = messageService;
        this.employeeService = employeeService;
        this.connectionService = connectionService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<MessageResponse>> getMessages() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var messages = messageService
                .findAllMessages()
                .stream()
                .map(MessageResponse::fromMessage)
                .collect(Collectors.toList());

        return new SuccessResponse<>(messages);
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Supplier') || hasRole('Employee')")
    public SuccessResponse<List<MessageResponse>> getUnreadMessages(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        final var messages = messageService
                .findEmployeeMessages(employee.employeeId())
                .stream()
                .map(MessageResponse::fromMessage)
                .collect(Collectors.toList());

        return new SuccessResponse<>(messages);
    }

    @GetMapping("/unread")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Supplier') || hasRole('Employee')")
    public SuccessResponse<Integer> getAmountOfUnreadMessages(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        return new SuccessResponse<>(
                messageService
                        .countUnreadReceivedMessages(employee.employeeId()));
    }

    @PutMapping("/read/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Supplier') || hasRole('Employee')")
    public SuccessResponse<Void> readMessage(Authentication authentication, @PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        Message message = messageService
                .findMessageById(id)
                .orElseThrow(() -> new RestException("Cannot find message."));

        if(message.receiver().employeeId() != employee.employeeId()) {
            throw new RestException("Cannot update this message.");
        }

        messageService
                .updateMessageRead(id);

        return new SuccessResponse<>(null);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<MessageResponse> getMessageById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return messageService
                .findMessageById(id)
                .map(MessageResponse::fromMessage)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot get message."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<MessageSentResponse> createMessage(Authentication authentication, @RequestBody CreateMessageRequest createMessageRequest) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee sender = employeeService
                .findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        Employee receiver = employeeService
                .findEmployeeById(createMessageRequest.receiver())
                .orElseThrow(() -> new RestException("Cannot find receiver."));

        return new SuccessResponse<>(
                MessageSentResponse.fromBoolean(
                        messageService
                                .createMessage(
                                        sender,
                                        receiver,
                                        createMessageRequest.content())
                                .isPresent()));
    }
}
