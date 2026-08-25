package com.akranta.perfex_sb.service;



import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.model.Visualsopdtl;
import com.akranta.perfex_sb.model.Visualsopmst;


public interface VisualsopService {

    public Visualsopmst createorupdateVisualsopmst(Visualsopmst visualsopmst);

    public Visualsopdtl createorupdateVisualsopdtl(Visualsopdtl visualsopdtl, String masterkeyId);

    public Visualsopdtl getByKeyid(String keyid);

  //  public void deleteByRefKeyId(String keyId);

   // public void deleteById(String keyid);

   // public void deletebyImage(String keyId);

   
    public void deleteBydetailKeyId(String keyid);

    public Visualsopmst getByKeyidMst(String keyid);

    public List<Map<String,Object>> getdetails(String keyid);

     public Visualsopmst delete(Visualsopmst visualsopmst) throws Exception;




}
