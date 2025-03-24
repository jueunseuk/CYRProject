package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.model.auth.OAuthUserInfoRequest;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    public String getNaverAccessToken(String code, String state) {
        String NAVER_ACCESSTOKEN_URL = "https://nid.naver.com/oauth2.0/token";
        String naverState = "cyr-project";

        if(!naverState.equals(state)) {
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
        ResponseEntity<Map> response = restTemplate.postForEntity(NAVER_ACCESSTOKEN_URL, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new BaseException(AuthExceptionCode.INVALID_NAVER_AUTH_CODE);
        }
    }

    public OAuthUserInfoRequest getUserInfoFromNaver(String accessToken) {
        String NAVER_USERINFO_URL = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                NAVER_USERINFO_URL,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            Object responseObj = body.get("response");

            if (responseObj instanceof Map<?, ?> responseMap) {
                String email = (String) responseMap.get("email");
                String name = (String) responseMap.get("name");
                String profileImage = (String) responseMap.get("profile_image");

                if (email != null && name != null) {
                    return new OAuthUserInfoRequest(name, email, profileImage, Method.NAVER);
                }
            }
        }

        throw new BaseException(AuthExceptionCode.FAILED_TO_FETCH_USER_INFO);
    }

}
