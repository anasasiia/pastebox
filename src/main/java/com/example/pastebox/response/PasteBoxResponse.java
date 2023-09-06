package com.example.pastebox.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteBoxResponse {
    private String data;
    private boolean isPublic;
}
