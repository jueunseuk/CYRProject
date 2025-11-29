package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.model.auth.OAuthUserInfoRequest;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService {

    public OAuthUserInfoRequest getUserInfo(String accessToken, String url, Method method) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            String email = null, name = null, profileImage = null;

            if (method == Method.NAVER) {
                Map<String, Object> responseMap = (Map<String, Object>) body.get("response");
                if (responseMap != null) {
                    email = (String) responseMap.get("email");
                    name = (String) responseMap.get("name");
                    profileImage = (String) responseMap.get("profile_image");
                }
            } else if (method == Method.GOOGLE) {
                email = (String) body.get("email");
                name = (String) body.get("name");
                profileImage = (String) body.get("picture");
            } else if (method == Method.KAKAO) {
                Map<String, Object> account = (Map<String, Object>) body.get("kakao_account");
                Map<String, Object> profile = account != null ? (Map<String, Object>) account.get("profile") : null;
                email = account != null ? (String) account.get("email") : null;
                name = profile != null ? (String) profile.get("nickname") : null;
                profileImage = profile != null ? (String) profile.get("profile_image_url") : null;
            }

            if (email != null && name != null) {
                return new OAuthUserInfoRequest(name, email, profileImage, method);
            }
        }

        throw new BaseException(AuthExceptionCode.FAILED_TO_FETCH_USER_INFO);
    }
}
