package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.dto.AuthDto;
import com.zhdanovich.fridgeater.service.impl.GetTokenServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final GetTokenServiceImpl getTokenService;

    @RequestMapping(value = {"/login", "/register"}, method = RequestMethod.POST)
    public ResponseEntity saveProduct(@RequestBody final AuthDto authDto) throws Exception {
        authDto.setToken(getTokenService.getToken(authDto.getEmail(), authDto.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(authDto);
    }
}
