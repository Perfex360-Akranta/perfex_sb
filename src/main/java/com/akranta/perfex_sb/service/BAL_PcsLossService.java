package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BalPcsLossSaveDto;
import com.akranta.perfex_sb.model.BAL_PcsLossCapture;

public interface BAL_PcsLossService {

    BAL_PcsLossCapture saveLoss(BalPcsLossSaveDto request) throws Exception;

}
