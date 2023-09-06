package com.example.pastebox;

import com.example.pastebox.entity.PasteBoxEntity;
import com.example.pastebox.exceptions.NotFoundEntityException;
import com.example.pastebox.repository.PasteBoxRepository;
import com.example.pastebox.response.PasteBoxResponse;
import com.example.pastebox.service.PasteBoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteBoxServiceTest {
    @Autowired
    private PasteBoxService pasteBoxService;
    @MockBean
    private PasteBoxRepository pasteBoxRepository;

    @Test
    public void notExistHash() {
        when(pasteBoxRepository.getPasteBoxByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, () -> pasteBoxService.getByHash("djsfksdjlfkj"));
    }

    @Test
    public void getExistHash() {
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setHash("1");
        pasteBoxEntity.setData("paste box");
        pasteBoxEntity.setId(1);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(1000));
        pasteBoxEntity.setPublic(true);
        when(pasteBoxRepository.getPasteBoxByHash("1")).thenReturn(pasteBoxEntity);

        PasteBoxResponse expected = new PasteBoxResponse("paste box", true);
        PasteBoxResponse actual = pasteBoxService.getByHash("1");
        assertEquals(expected, actual);
    }
}
