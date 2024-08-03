package com.shyam.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {
    private int stock;
    private String name;
    private String category;
    private String description;

    @JsonProperty(value = "author-ids")
    private Set<Long> authorIds;
}