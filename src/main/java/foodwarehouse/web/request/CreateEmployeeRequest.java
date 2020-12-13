package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.user.Account;
import foodwarehouse.core.user.employee.EmployeePersonalData;

public record CreateEmployeeRequest(
        @JsonProperty("account") Account account,
        @JsonProperty("personal_data") EmployeePersonalData employeePersonalData) {
}
