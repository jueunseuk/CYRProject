package com.junsu.cyr.domain.empathys;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empathy")
public class Empathy extends BaseTime {
    @EmbeddedId
    @Column(name = "empathy_id")
    private EmpathyId empathyId;
}
