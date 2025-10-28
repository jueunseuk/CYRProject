package com.junsu.cyr.model.apply;

import com.junsu.cyr.domain.applys.PreferenceMethod;
import com.junsu.cyr.domain.applys.PreferenceRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyUploadRequest {
    private String title;
    private String motive;
    private String primaryTime;
    private String weeklyHour;
    private PreferenceRole preferenceRole = PreferenceRole.NONE;
    private PreferenceMethod preferenceMethod = PreferenceMethod.EMAIL;
    private String contact;
}
