package com.akranta.perfex_sb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.qtm_tl_qpointDto;
import com.akranta.perfex_sb.model.QpointModel;
import com.akranta.perfex_sb.model.QpointdtlsModel;
import com.akranta.perfex_sb.repository.qtm_tl_qpointRepository;
import com.akranta.perfex_sb.repository.qtm_tl_qpointdtlsRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.qtm_tl_qpointService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service

public class qtm_tl_qpointServiceImpl implements qtm_tl_qpointService {

    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Autowired
    private qtm_tl_qpointdtlsRepository detailRepository;

    @Autowired
    private qtm_tl_qpointRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(qtm_tl_qpointServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "TBL_QTM_TL_QPOINT";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "QPTS";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "QTM_TL_QPOINTDTLS";
    private static final String PREFIX_DTL = "QPTD";

    // @Transactional
    // public qtm_tl_qpointDto saveQpoint(qtm_tl_qpointDto dto) throws Exception {

    //     logger.info("ENTERED INTO THE SERVICE");
    //     QpointModel mst = dto.getQtmTlmst();
    //     QpointdtlsModel dtl = dto.getQtmTldtl();

    //     if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
    //         logger.info("ENTERED INTO THE CREATE");
    //         String newMstKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
    //                 FORMAT_RESET, DATE_FORMAT);

    //         if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
    //             logger.info("Failed To Generate the Key Id");
    //             throw new RuntimeException("Failed to generate Master Key ID");
    //         }

    //         logger.info("Generated new Key ID: {} Master Keyid", newMstKeyid);
    //         mst.setKeyid(newMstKeyid);

    //     } else {

    //         logger.info("ENTERED INTO THE UPDATE");
    //         if (repository.existsById(mst.getKeyid())) {
    //             QpointModel updateMst = repository.save(mst);
    //             qtm_tl_qpointDto updateDto = new qtm_tl_qpointDto();
    //             updateDto.setQtmTlmst(updateMst);

    //             if (dtl != null) {
    //                 if (mst.getKeyid().equals(dtl.getQptm_keyid())) {

    //                     logger.info("ENTERED INTO THE DETAIL CREATE IN MST UPDATE");

    //                     if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
    //                         String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
    //                                 PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
    //                         if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
    //                             logger.info("Failed To Generate the Detail Key Id");
    //                             throw new RuntimeException("Failed to generate Detail Key ID");
    //                         }

    //                         logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
    //                         dtl.setKeyid(newDetailKeyId);
    //                         dtl.setQptm_keyid(updateMst.getKeyid());
    //                         QpointdtlsModel value = detailRepository.save(dtl);
    //                         updateDto.setQtmTldtl(value);
    //                     }
    //                 } else {

    //                     logger.info("ENTERED INTO THE DETAIL UPDATE IN MST UPDATE");
    //                     dtl.setQptm_keyid(updateMst.getKeyid());
    //                     QpointdtlsModel updateDetail = detailRepository.save(dtl);
    //                     updateDto.setQtmTldtl(updateDetail);
    //                 }
    //             }
    //             return updateDto;
    //         }
    //     }

    //     qtm_tl_qpointDto resultDto = new qtm_tl_qpointDto();
    //     QpointModel saveMst = repository.save(mst);
    //     resultDto.setQtmTlmst(saveMst);

    //     if (dtl != null) {
    //         if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
    //             String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
    //                     PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
    //             if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
    //                 logger.info("Failed To Generate the Detail Key Id");
    //                 throw new RuntimeException("Failed to generate Detail Key ID");
    //             }

    //             logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
    //             dtl.setKeyid(newDetailKeyId);
    //             dtl.setQptm_keyid(saveMst.getKeyid());
    //             QpointdtlsModel value = detailRepository.save(dtl);
    //             resultDto.setQtmTldtl(value);
    //         } else {
    //             dtl.setQptm_keyid(saveMst.getKeyid());
    //             QpointdtlsModel updateDetail = detailRepository.save(dtl);
    //             resultDto.setQtmTldtl(updateDetail);
    //         }
    //     }

    //     return resultDto;
    // }

    @Transactional
    public qtm_tl_qpointDto saveQpoint(qtm_tl_qpointDto dto) throws Exception {

        logger.info("ENTERED INTO THE SERVICE");
        QpointModel mst = dto.getQtmTlmst();
        QpointdtlsModel dtl = dto.getQtmTldtl();

        if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
            logger.info("ENTERED INTO THE CREATE");
            String newMstKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {} Master Keyid", newMstKeyid);
            mst.setKeyid(newMstKeyid);

        } else {

            logger.info("ENTERED INTO THE UPDATE");
            if (repository.existsById(mst.getKeyid())) {
                QpointModel updateMst = repository.save(mst);
                qtm_tl_qpointDto updateDto = new qtm_tl_qpointDto();
                updateDto.setQtmTlmst(updateMst);

                if (dtl != null) {
                    if (mst.getKeyid().equals(dtl.getQptm_keyid())) {

                        logger.info("ENTERED INTO THE DETAIL CREATE IN MST UPDATE");

                        if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                            String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                    PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                            if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                                logger.info("Failed To Generate the Detail Key Id");
                                throw new RuntimeException("Failed to generate Detail Key ID");
                            }

                            logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                            dtl.setKeyid(newDetailKeyId);
                            dtl.setQptm_keyid(updateMst.getKeyid());
                            QpointdtlsModel value = detailRepository.save(dtl);
                            updateDto.setQtmTldtl(value);
                        } else {

                            logger.info("ENTERED INTO THE DETAIL UPDATE IN MST UPDATE");
                            dtl.setQptm_keyid(updateMst.getKeyid());
                            QpointdtlsModel updateDetail = detailRepository.save(dtl);
                            updateDto.setQtmTldtl(updateDetail);

                        }

                    } else {

                        logger.info("ENTERED INTO THE DETAIL UPDATE IN MST UPDATE");
                        dtl.setQptm_keyid(updateMst.getKeyid());
                        QpointdtlsModel updateDetail = detailRepository.save(dtl);
                        updateDto.setQtmTldtl(updateDetail);

                    }

                }
                return updateDto;
            }
        }

        qtm_tl_qpointDto resultDto = new qtm_tl_qpointDto();
        QpointModel saveMst = repository.save(mst);
        resultDto.setQtmTlmst(saveMst);

        if (dtl != null) {
            if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                        PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                    logger.info("Failed To Generate the Detail Key Id");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                dtl.setKeyid(newDetailKeyId);
                dtl.setQptm_keyid(saveMst.getKeyid());
                QpointdtlsModel value = detailRepository.save(dtl);
                resultDto.setQtmTldtl(value);
            } else {
                dtl.setQptm_keyid(saveMst.getKeyid());
                QpointdtlsModel updateDetail = detailRepository.save(dtl);
                resultDto.setQtmTldtl(updateDetail);
            }
        }

        return resultDto;
    }


    @Override
    public List<QpointModel> getAll() {
        return repository.findAll();
    }

    @Override
    public QpointModel save(QpointModel model) {
        return repository.save(model);
    }

    @Override
    public QpointModel getById(String keyid) {
        return repository.findById(keyid).orElse(null);
    }

    @Override
    public QpointModel deleteById(String keyid) {
        QpointModel model = repository.findById(keyid).orElse(null);
        if (model != null) {
            repository.delete(model);
        }
        return model;
    }

}
