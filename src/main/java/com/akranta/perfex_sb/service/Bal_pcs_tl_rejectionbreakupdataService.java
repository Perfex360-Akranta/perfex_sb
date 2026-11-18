package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.model.Bal_pcs_tl_rejectionbreakupdata;

public interface Bal_pcs_tl_rejectionbreakupdataService {

    public List<Bal_pcs_tl_rejectionbreakupdata> saveOrUpdate(String pprdKeyid, String createdby,
            List<Bal_pcs_tl_rejectionbreakupdata> rows);

            public void deleteByKeyid(String keyid);

}
