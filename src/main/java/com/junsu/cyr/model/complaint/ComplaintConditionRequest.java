package com.junsu.cyr.model.complaint;

import com.junsu.cyr.domain.complaints.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintConditionRequest {
    private String complaintId;
    private Status status;

    private Integer page = 0;
    private Integer size = 30;
    private String sort = "complaintId";
    private String direction = "DESC";
}
