package foodwarehouse.web.controller;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.MessageService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.response.EmployeeResponse;
import foodwarehouse.web.response.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final ConnectionService connectionService;

    public MessageController(MessageService messageService, ConnectionService connectionService) {
        this.messageService = messageService;
        this.connectionService = connectionService;
    }

    @GetMapping
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
}
