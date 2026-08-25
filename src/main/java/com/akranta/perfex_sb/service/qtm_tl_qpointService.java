package com.akranta.perfex_sb.service;

import java.util.List;
import com.akranta.perfex_sb.model.QpointModel;

import com.akranta.perfex_sb.dto.qtm_tl_qpointDto;

public interface qtm_tl_qpointService {

    List<QpointModel> getAll();
    
    QpointModel save(QpointModel model);

    qtm_tl_qpointDto saveQpoint(qtm_tl_qpointDto dto) throws Exception;

    QpointModel getById(String keyid);

    QpointModel deleteById(String keyid);

}
