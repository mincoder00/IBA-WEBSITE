package com.iba.springbootdeveloper.controller;

import com.iba.springbootdeveloper.dto.CreateAccessTokenRequest;
import com.iba.springbootdeveloper.dto.CreateAccessTokenResponse;
import com.iba.springbootdeveloper.service.RefreshTokenService;
import com.iba.springbootdeveloper.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }
}
