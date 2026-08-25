package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.model.GenTIControlandresponseplan;

public interface GenTIControlandresponseplanService {
    public List<GenTIControlandresponseplan> getAllGenTIControlandresponseplan();

    public GenTIControlandresponseplan saveGenTIControlandresponseplan(GenTIControlandresponseplan model)
            throws Exception;

    List<GenTIControlandresponseplan> getAll();

    public GenTIControlandresponseplan getById(String keyid);

    GenTIControlandresponseplan deleteById(String keyid);

    
}
