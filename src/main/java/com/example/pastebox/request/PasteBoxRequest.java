package com.example.pastebox.request;

import lombok.Data;

@Data
public class PasteBoxRequest {
    private String data;
    private long expirationTimeSeconds;
    private PublicStatus publicStatus;
}
