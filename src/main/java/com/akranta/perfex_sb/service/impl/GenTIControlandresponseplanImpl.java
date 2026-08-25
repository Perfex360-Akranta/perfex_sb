package com.akranta.perfex_sb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.akranta.perfex_sb.util.ValidationUtil;
import org.springframework.stereotype.Service;
import com.akranta.perfex_sb.model.GenTIControlandresponseplan;
import com.akranta.perfex_sb.repository.GenTIControlandresponseplanRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.GenTIControlandresponseplanService;

@Service
public class GenTIControlandresponseplanImpl implements GenTIControlandresponseplanService {
    @Autowired
    private GenTIControlandresponseplanRepository repository;

    @Autowired
    private DbActionTemplate dbActionTemplate;
private static final Logger logger = LoggerFactory.getLogger(GenTIControlandresponseplanImpl.class);
    

    private static final String SEQ_IDENTIFIER = "GEN_TL_CONTROLANDRESPONSEPLAN";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "CARP";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    public List<GenTIControlandresponseplan> getAllGenTIControlandresponseplan() {

        throw new UnsupportedOperationException("Unimplemented method 'getAllGenTIControlandresponseplan'");
    }

    @Override
    public GenTIControlandresponseplan saveGenTIControlandresponseplan(GenTIControlandresponseplan model)
            throws Exception {
        if (!ValidationUtil.isValidKeyId(model.getKeyid())) {
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newKeyId == null || newKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {}", newKeyId);
            model.setKeyid(newKeyId);
        } else {
            if (repository.existsById(model.getKeyid())) {
                return repository.save(model);
            }
        }
        return repository.save(model);
    }

    @Override
    public List<GenTIControlandresponseplan> getAll() {
        return repository.findAll();
    }

    @Override
    public GenTIControlandresponseplan getById(String keyid) {
       return repository.findById(keyid).orElse(null);
    }
    @Override
    public GenTIControlandresponseplan deleteById(String keyid) {
        GenTIControlandresponseplan model = repository.findById(keyid).orElse(null);
        if (model != null) {
            repository.delete(model);
        }
        return model;
    }

   
}
