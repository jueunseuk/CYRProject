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
public class KakaoCallbackFlow {

    @Value("${kakao.client.id}")
    private String kakaoClientId;
    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    public String getKakaoAccessToken(String code) {
        String KAKAO_ACCESSTOKEN_URL = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("client_secret", kakaoClientSecret);
        params.add("code", code);
        params.add("redirect_uri", kakaoRedirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 2; i++) {
            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_ACCESSTOKEN_URL, request, Map.class);

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
