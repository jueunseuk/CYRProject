package com.junsu.cyr.model.event;

import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import com.junsu.cyr.model.global.BaseCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventConditionRequest extends BaseCondition {
    private Type type;
    private Status status;
}
