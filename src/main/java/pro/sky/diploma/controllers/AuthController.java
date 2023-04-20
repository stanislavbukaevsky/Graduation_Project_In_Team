package pro.sky.diploma.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.diploma.dto.LoginReq;
import pro.sky.diploma.dto.RegisterReq;
import pro.sky.diploma.dto.Role;
import pro.sky.diploma.services.AuthService;

import static pro.sky.diploma.constants.FrontServerUserConstants.*;
import static pro.sky.diploma.dto.Role.USER;

@Slf4j
@CrossOrigin(value = FRONT_ADDRESS)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            System.out.println("login ok");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("login false");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
