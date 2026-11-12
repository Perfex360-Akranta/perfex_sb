// package com.akranta.perfex_sb.dto;

// import com.akranta.perfex_sb.model.QtmTlCapaActionmst;

// public class QtmTlCapaActionmstDTO {
//     private QtmTlCapaActionmst actionmst;

//     public QtmTlCapaActionmst getActionmst() {
//         return actionmst;
//     }

//     public void setActionmst(QtmTlCapaActionmst actionmst) {
//         this.actionmst = actionmst;
//     }
// }


package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.QtmTlCapaActionmst;
import java.util.List;

public class QtmTlCapaActionmstDTO {

    private List<QtmTlCapaActionmst> actionList;
    private String createdBy;
    private String capaMasterKeyid;
    

    public List<QtmTlCapaActionmst> getActionList() {
        return actionList;
    }

    public void setActionList(List<QtmTlCapaActionmst> actionList) {
        this.actionList = actionList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCapaMasterKeyid() {
        return capaMasterKeyid;
    }

    public void setCapaMasterKeyid(String capaMasterKeyid) {
        this.capaMasterKeyid = capaMasterKeyid;
    }
}
