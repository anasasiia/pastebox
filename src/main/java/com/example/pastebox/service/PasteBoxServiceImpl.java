package com.example.pastebox.service;

import com.example.pastebox.entity.PasteBoxEntity;
import com.example.pastebox.repository.PasteBoxRepository;
import com.example.pastebox.request.PasteBoxRequest;
import com.example.pastebox.request.PublicStatus;
import com.example.pastebox.response.PasteBoxResponse;
import com.example.pastebox.response.PasteBoxUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService {
    @Value("${target.host}")
    private String host;
    @Value("${public.list.size}")
    private int publicListSize;

    private final PasteBoxRepository repository;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getPasteBoxByHash(hash);
        PasteBoxResponse response = new PasteBoxResponse();
        response.setData(pasteBoxEntity.getData());
        response.setPublic(pasteBoxEntity.isPublic());
        return response;
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes() {
        List<PasteBoxEntity> entities = repository.getListOfPublicAndAlive(publicListSize);
        return entities.stream()
                .map(entity -> new PasteBoxResponse(entity.getData(), entity.isPublic()))
                .toList();
    }

    @Override
    public PasteBoxUrl create(PasteBoxRequest request) {
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());

        int hash = generateId();
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);

        return new PasteBoxUrl(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
