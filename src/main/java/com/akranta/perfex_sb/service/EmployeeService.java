package com.akranta.perfex_sb.service;



import com.akranta.perfex_sb.dto.EmployeeDto;
import com.akranta.perfex_sb.dto.RecallEmployeeDto;

public interface EmployeeService 
{
    public EmployeeDto saveEmployee(EmployeeDto dto) throws Exception;

    RecallEmployeeDto findMstDtlById(String keyId) throws Exception;

}

