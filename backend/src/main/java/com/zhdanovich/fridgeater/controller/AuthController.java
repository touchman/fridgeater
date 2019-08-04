package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.dto.AuthDto;
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

    @RequestMapping(value = {"/login", "/register"}, method = RequestMethod.POST)
    public ResponseEntity saveProduct(@RequestBody final AuthDto authDto) {
        authDto.setToken("testToken123");
        return ResponseEntity.status(HttpStatus.OK).body(authDto);
    }
}
