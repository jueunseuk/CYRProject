package com.junsu.cyr.domain.cheers;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CheerId extends BaseTime {
    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
