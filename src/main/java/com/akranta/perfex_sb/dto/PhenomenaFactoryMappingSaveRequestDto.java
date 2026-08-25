package com.akranta.perfex_sb.dto;

import java.util.List;

public class PhenomenaFactoryMappingSaveRequestDto {
    private List<PhenomenaFactoryLinkItemDto> links;
    private String createdBy;

    public List<PhenomenaFactoryLinkItemDto> getLinks() {
        return links;
    }

    public void setLinks(List<PhenomenaFactoryLinkItemDto> links) {
        this.links = links;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
