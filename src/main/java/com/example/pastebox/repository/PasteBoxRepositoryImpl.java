package com.example.pastebox.repository;

import com.example.pastebox.entity.PasteBoxEntity;
import com.example.pastebox.exceptions.NotFoundEntityException;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PasteBoxRepositoryImpl implements PasteBoxRepository {
    private final Map<String, PasteBoxEntity> vault = new ConcurrentHashMap<>();

    @Override
    public PasteBoxEntity getPasteBoxByHash(String hash) {
        PasteBoxEntity entity = vault.get(hash);
        if (entity == null) {
            throw new NotFoundEntityException("PasteBox not found with hash = " + hash);
        }
        return entity;
    }

    @Override
    public List<PasteBoxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime now = LocalDateTime.now();
        return vault.values().stream()
                .filter(PasteBoxEntity::isPublic)
                .filter(entity -> entity.getLifetime().isAfter(now))
                .sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .limit(amount)
                .toList();
    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        vault.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}
