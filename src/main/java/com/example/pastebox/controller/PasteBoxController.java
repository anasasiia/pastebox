package com.example.pastebox.controller;

import com.example.pastebox.request.PasteBoxRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
public class PasteBoxController {

    @GetMapping("/{hash}")
    public String getByHash(@PathVariable String hash) {
        return hash;
    }

    @GetMapping("/")
    public Collection<String> getPublicPasteList() {
        return Collections.emptyList();
    }

    @PostMapping("/")
    public String add(@RequestBody PasteBoxRequest request) {
        return request.getData();
    }
}
