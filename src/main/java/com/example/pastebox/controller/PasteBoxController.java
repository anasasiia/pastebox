package com.example.pastebox.controller;

import com.example.pastebox.request.PasteBoxRequest;
import com.example.pastebox.response.PasteBoxResponse;
import com.example.pastebox.response.PasteBoxUrl;
import com.example.pastebox.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasteBoxController {
    private final PasteBoxService service;

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return service.getByHash(hash);
    }

    @GetMapping("/")
    public List<PasteBoxResponse> getPublicPasteList() {
        return service.getFirstPublicPasteBoxes();
    }

    @PostMapping("/")
    public PasteBoxUrl add(@RequestBody PasteBoxRequest request) {
        return service.create(request);
    }
}
