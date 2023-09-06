package com.example.pastebox.service;

import com.example.pastebox.request.PasteBoxRequest;
import com.example.pastebox.response.PasteBoxResponse;
import com.example.pastebox.response.PasteBoxUrl;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBoxes();
    PasteBoxUrl create(PasteBoxRequest request);
}
