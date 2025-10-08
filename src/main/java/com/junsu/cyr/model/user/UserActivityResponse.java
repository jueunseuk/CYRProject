package com.junsu.cyr.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserActivityResponse {
    private Long postCnt;
    private Long commentCnt;
    private Long empathyCnt;
    private Long imageCnt;
}
