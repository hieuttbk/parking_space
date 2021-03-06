package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service_impl.GoogleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@AllArgsConstructor
@Controller
public class SocialController {

    private final JWTService jwtService;
    private final GoogleService googleService;

//    @GetMapping("login-google")
//    public String loginGoogle() {
//        System.out.println(googleService.createAuthorizationURL());
//        return String.format("redirect:%s", googleService.createAuthorizationURL());
//    }
//
//    @GetMapping("google-callback")
//    public ResponseEntity<Object> googleCallback(@RequestParam(name = "code") String code) throws Exception {
//        String accessToken = googleService.createAccessToken(code);
//        User user = googleService.getUser(accessToken);
//        System.out.println("code" + code);
//        System.out.println("accessToken" + accessToken);
//        return ResponseEntity.ok(jwtService.getToken(user.getEmail()));
//    }

    @GetMapping("api/login-google")
    @ResponseBody
    public ResponseEntity<Object> createAndUpdate(@RequestParam(name = "token") String token) throws Exception {
        User user = googleService.getUser(token);
        return ResponseEntity.ok(MyResponse.success(jwtService.getToken(user.getEmail())));
    }
}
