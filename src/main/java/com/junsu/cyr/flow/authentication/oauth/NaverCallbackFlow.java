package com.junsu.cyr.flow.authentication.oauth;

import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NaverCallbackFlow {

    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverClientSecret;

    public String naverCallback(String code, String state) {
        String NAVER_ACCESSTOKEN_URL = "https://nid.naver.com/oauth2.0/token";
        String naverState = "cyr-project";

        if (!naverState.equals(state)) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_NAVER_STATE);
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 2; i++) {
            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(NAVER_ACCESSTOKEN_URL, request, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return (String) response.getBody().get("access_token");
                }
            } catch (Exception e) {
                if (i == 1) {
                    throw new BaseException(AuthExceptionCode.INVALID_AUTH_CODE);
                }
            }
        }

        throw new BaseException(AuthExceptionCode.INVALID_AUTH_CODE);
    }
}
