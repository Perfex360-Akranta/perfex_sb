package com.akranta.perfex_sb.service;



import com.akranta.perfex_sb.dto.QtmTlCapaWorkflowDTO;

public interface QtmTlCapaWorkflowService {
    QtmTlCapaWorkflowDTO save(QtmTlCapaWorkflowDTO dto) throws Exception;

    int updateCapaWorkflow(String empId, String remarks, String status, String date, String capaId);
}
