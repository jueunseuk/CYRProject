package com.junsu.cyr.model.event;

import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateRequest {
    private Status status;
    private Type type;
}
