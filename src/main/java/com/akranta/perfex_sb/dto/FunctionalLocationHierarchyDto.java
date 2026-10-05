package com.akranta.perfex_sb.dto;

public record FunctionalLocationHierarchyDto(
        String companyId,
        String companyLabel,

        String locationId,
        String locationLabel,

        String sbuId,
        String sbuLabel,

        String pbuId,
        String pbuLabel,

        String sectionId,
        String sectionLabel,

        String cellId,
        String cellLabel,

        String machineId,
        String machineLabel,

        String flid,
        String elementId,
        String displayCode,
        String functionalLocationText) {
}