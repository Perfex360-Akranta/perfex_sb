package com.akranta.perfex_sb.dto;

public class FishboneTreeRequest {
    private String id;          // current node id (or "0" for root)
    private String parentId;    // parent node id
    private String masterId;    // fishbone master key
    private String elementType; // kept for compatibility; not used directly
    private Object problem;     // root display text (may arrive as string or object)
    private String levelNo;     // optional level number
    private String lineId;      // optional flid fallback

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Object getProblem() {
        return problem;
    }

    public void setProblem(Object problem) {
        this.problem = problem;
    }

    /**
     * Returns the problem text as a string, handling cases where JSON sends an object instead of a bare string.
     */
    public String getProblemText() {
        if (problem == null) return null;
        if (problem instanceof String s) return s;
        return problem.toString();
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
