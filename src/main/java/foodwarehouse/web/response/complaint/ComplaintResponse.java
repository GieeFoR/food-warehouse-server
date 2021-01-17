package foodwarehouse.web.response.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.web.response.order.EntireOrderResponse;

import java.text.SimpleDateFormat;

public record ComplaintResponse(
        @JsonProperty(value = "order", required = true)                 ComplaintOrderResponse complaintOrderResponse,
        @JsonProperty(value = "complaint", required = true)             ComplaintDataResponse complaintDataResponse) {

    public static ComplaintResponse fromComplaint(Complaint complaint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ComplaintResponse(
                ComplaintOrderResponse.fromOrder(complaint.order()),
                ComplaintDataResponse.fromComplaint(complaint));
    }
}
