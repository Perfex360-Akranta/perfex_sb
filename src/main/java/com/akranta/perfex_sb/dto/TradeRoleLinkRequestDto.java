package com.akranta.perfex_sb.dto;

public class TradeRoleLinkRequestDto {
   
    private String tradeId;
    private String roleId;
    private String createdBy;

    public String getTradeId()
    {
         return tradeId; 
    }
    public void setTradeId(String tradeId) 
    {
         this.tradeId = tradeId;
    }

    public String getRoleId()
    {
         return roleId; 
    }
    public void setRoleId(String roleId)
    {
         this.roleId = roleId; 
    }

    public String getCreatedBy() 
    {
         return createdBy; 
    }
    public void setCreatedBy(String createdBy) 
    { 
        this.createdBy = createdBy; 
    }

}
