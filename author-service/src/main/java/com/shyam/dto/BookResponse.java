package com.shyam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private long id;
    private int stock;
    private String name;
    private String category;
    private String description;
}
