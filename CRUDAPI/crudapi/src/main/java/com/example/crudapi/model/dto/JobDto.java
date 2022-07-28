package com.example.crudapi.model.dto;

import com.example.crudapi.enums.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {
    private String title;

    private String description;

    private Location location;

    private int minSalary;

    private int maxSalary;

    private String emailTo;

}