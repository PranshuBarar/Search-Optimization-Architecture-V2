package com.searchoptimization.resourceservice.controllers;

import com.searchoptimization.resourceservice.services.QueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @PutMapping("/query")
    public ResponseEntity<?> searchByUsername(@RequestParam("keyword") String keyword) {
        Object response = queryService.searchBasedOnKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
