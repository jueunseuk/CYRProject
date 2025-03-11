package com.junsu.cyr.domain.cheers;

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
@Table(name = "cheer")
public class Cheer {
    @EmbeddedId
    private CheerId cheerId;
}
