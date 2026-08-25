package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.EmployeeGroupDto;
import com.akranta.perfex_sb.model.GenTlMomGroupmst;

public interface EmployeeGroupService {

EmployeeGroupDto saveEmployeeGroup(EmployeeGroupDto dto) throws Exception;

List<Map<String,Object>> getGrid();
List<Map<String,Object>> createGrid(String functional,String mstKeyId);
List<Map<String,Object>> detailGrid(String mgrmKeyId);
void deleteGrid(String mgrdKeyId);
GenTlMomGroupmst viewGrid(String mgrmKeyId);


}