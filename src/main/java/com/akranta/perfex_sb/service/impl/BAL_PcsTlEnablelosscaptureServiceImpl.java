package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BAL_PcsTlEnablelosscapture;
import com.akranta.perfex_sb.repository.BAL_PcsTlEnablelosscaptureRepository;
import com.akranta.perfex_sb.service.BAL_PcsTlEnablelosscaptureService;
import com.akranta.perfex_sb.service.DbActionTemplate;

@Service
public class BAL_PcsTlEnablelosscaptureServiceImpl implements BAL_PcsTlEnablelosscaptureService {

    private static final Logger logger =
            LoggerFactory.getLogger(BAL_PcsTlEnablelosscaptureServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "BAL_PCS_TL_ENABLELOSSCAPTURE";
    private static final int KEY_LENGTH = 12;
    private static final String PREFIX = "PCS";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private final BAL_PcsTlEnablelosscaptureRepository repository;
    private final DbActionTemplate dbActionTemplate;

    public BAL_PcsTlEnablelosscaptureServiceImpl(
            BAL_PcsTlEnablelosscaptureRepository repository,
            DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public List<BAL_PcsTlEnablelosscapture> createorupdate(
            List<BAL_PcsTlEnablelosscapture> pcsTlEnablelosscaptureList) {

        if (pcsTlEnablelosscaptureList == null || pcsTlEnablelosscaptureList.isEmpty()) {
            return pcsTlEnablelosscaptureList;
        }

        List<BAL_PcsTlEnablelosscapture> savedList = new ArrayList<>();

        for (BAL_PcsTlEnablelosscapture item : pcsTlEnablelosscaptureList) {

            // logger.info("checking type {}",item.getType()+"*******");
            // Character j = item.getType();
            // logger.info("checking type length{}",item.getKeyid()+"*********");
            

            // skip records where the flag is null or blank
            Character enabled = item.getIspcsenabled();
            if (enabled == null || Character.isWhitespace(enabled)) {
                continue;
            }

            // CHECK IF INSERT OR UPDATE (same logic as Field Audit Sheet)
            String keyid = item.getKeyid();
            boolean isInsertMode = keyid == null
                    || keyid.trim().isEmpty()
                    || keyid.equals("{}")
                    || keyid.equals("undefined")
                    || !repository.existsById(keyid);

            if (isInsertMode) {
                // INSERT MODE
                String newKeyid;
                try {
                    newKeyid = dbActionTemplate.getSequenceNumber(
                            SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);
                } catch (Exception e) {
                    logger.error("Failed to generate Key ID", e);
                    throw new IllegalStateException("Failed to generate Key ID: " + e.getMessage(), e);
                }

                if (newKeyid == null || newKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Key ID - sequence returned null");
                    throw new IllegalStateException("Failed to generate Key ID");
                }

                item.setKeyid(newKeyid);
                if (item.getCreatedon() == null) {
                    item.setCreatedon(LocalDateTime.now());
                }
                item.setModifiedon(LocalDateTime.now());

                BAL_PcsTlEnablelosscapture saved = repository.save(item);
                savedList.add(saved);
                logger.info("INSERT - new Key ID: {}", newKeyid);

            } else {
                if(repository.existsById(keyid))
                    {
                         BAL_PcsTlEnablelosscapture saved = repository.save(item);
                savedList.add(saved);
                logger.info("UPDATE - existing Key ID: {}", keyid);

                    }
                // UPDATE MODE: load existing, copy fields, keep audit columns
                // BAL_PcsTlEnablelosscapture existing = repository.findById(keyid)
                //         .orElseThrow(() -> new ResourceNotFoundException(
                //                 "Record not found for Key ID: " + keyid));

                // BeanUtils.copyProperties(item, existing,
                //         "keyid", "createdon", "createdby");
                // existing.setModifiedon(LocalDateTime.now());

               
            }
        }

        return savedList;
    }
}