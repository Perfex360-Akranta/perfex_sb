// package com.akranta.perfex_sb.dto;

// import com.akranta.perfex_sb.model.QtmTlCapaWorkflow;

// public class QtmTlCapaWorkflowDTO {
//     private QtmTlCapaWorkflow workflow;

//     public QtmTlCapaWorkflow getWorkflow() {
//         return workflow;
//     }

//     public void setWorkflow(QtmTlCapaWorkflow workflow) {
//         this.workflow = workflow;
//     }
// }
package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.akranta.perfex_sb.model.QtmTlCapaWorkflow;

public class QtmTlCapaWorkflowDTO {

    private QtmTlCapaWorkflow workflow;
    private String capaId; // important (link to master)
    private String empId;
    private String remarks;
    private String status;
    private LocalDateTime date;

    public QtmTlCapaWorkflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(QtmTlCapaWorkflow workflow) {
        this.workflow = workflow;
    }

    public String getCapaId() {
        return capaId;
    }

    public void setCapaId(String capaId) {
        this.capaId = capaId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}