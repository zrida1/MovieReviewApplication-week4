package com.example.moviewreviewapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {

    private byte[] data;
    private String contentType;
}
