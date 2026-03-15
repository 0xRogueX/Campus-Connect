package com.campusconnect.courseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusDto {
    @Min(value = 1, message = "Week must be at least 1")
    private int week;

    private List<String> topics;
}