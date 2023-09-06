package com.example.pastebox.repository;

import com.example.pastebox.entity.PasteBoxEntity;

import java.util.List;

public interface PasteBoxRepository {
    PasteBoxEntity getPasteBoxByHash(String hash);
    List<PasteBoxEntity> getListOfPublicAndAlive(int amount);
    void add(PasteBoxEntity pasteBoxEntity);
}
