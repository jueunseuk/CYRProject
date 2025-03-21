package com.junsu.cyr.domain.complaints;

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
@Table(name = "complaint_category")
public class ComplaintCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_category_id", nullable = false)
    private Integer complaint_category_id;

    @Column(name = "complaint_category_name", nullable = false)
    private String complaintCategoryName;
}
