package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.OtherLossEntryDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
//import java.util.List;

@Repository
public class OtherLossRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OtherLossRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(OtherLossEntryDto d) {
        String sql = """
                INSERT INTO pcs_tl_otherlossentry (
                    olse_keyid, olse_flid, olse_elementid, olse_date,
                    olse_lossid, olse_lossdate, olse_lossvalue,
                    olse_tempfield1, olse_tempfield2, olse_tempfield3, olse_tempfield4,
                    olse_tempfield5, olse_tempfield6, olse_tempfield7, olse_tempfield8,
                    olse_active, olse_createdby, olse_createdon, olse_modifiedon
                ) VALUES (
                    :olse_keyid, :olse_flid, :olse_elementid, :olse_date,
                    :olse_lossid, :olse_lossdate, :olse_lossvalue,
                    :olse_tempfield1, :olse_tempfield2, :olse_tempfield3, :olse_tempfield4,
                    :olse_tempfield5, :olse_tempfield6, :olse_tempfield7, :olse_tempfield8,
                    :olse_active, :olse_createdby, :olse_createdon, :olse_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, params(d));
    }

    public int update(OtherLossEntryDto d) {
        String sql = """
                UPDATE pcs_tl_otherlossentry SET
                    olse_flid = :olse_flid,
                    olse_elementid = :olse_elementid,
                    olse_date = :olse_date,
                    olse_lossid = :olse_lossid,
                    olse_lossdate = :olse_lossdate,
                    olse_lossvalue = :olse_lossvalue,
                    olse_tempfield1 = :olse_tempfield1,
                    olse_tempfield2 = :olse_tempfield2,
                    olse_tempfield3 = :olse_tempfield3,
                    olse_tempfield4 = :olse_tempfield4,
                    olse_tempfield5 = :olse_tempfield5,
                    olse_tempfield6 = :olse_tempfield6,
                    olse_tempfield7 = :olse_tempfield7,
                    olse_tempfield8 = :olse_tempfield8,
                    olse_active = :olse_active,
                    olse_createdby = :olse_createdby,
                    olse_createdon = :olse_createdon,
                    olse_modifiedon = :olse_modifiedon
                WHERE olse_keyid = :olse_keyid
                """;
        return jdbcTemplate.update(sql, params(d));
    }

    public int updateLossValue(String keyId, BigDecimal lossValue) {
        return jdbcTemplate.update(
                "UPDATE pcs_tl_otherlossentry SET olse_lossvalue = :val WHERE olse_keyid = :id",
                new MapSqlParameterSource()
                        .addValue("val", lossValue)
                        .addValue("id", keyId)
        );
    }

    public int delete(String keyId) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_otherlossentry WHERE olse_keyid = :id",
                new MapSqlParameterSource("id", keyId)
        );
    }

    private MapSqlParameterSource params(OtherLossEntryDto d) {
        return new MapSqlParameterSource()
                .addValue("olse_keyid", d.getOlseKeyid())
                .addValue("olse_flid", d.getOlseFlid())
                .addValue("olse_elementid", d.getOlseElementid())
                .addValue("olse_date", toTs(d.getOlseDate()))
                .addValue("olse_lossid", d.getOlseLossid())
                .addValue("olse_lossdate", toTs(d.getOlseLossdate()))
                .addValue("olse_lossvalue", d.getOlseLossvalue())
                .addValue("olse_tempfield1", d.getOlseTempfield1())
                .addValue("olse_tempfield2", d.getOlseTempfield2())
                .addValue("olse_tempfield3", d.getOlseTempfield3())
                .addValue("olse_tempfield4", d.getOlseTempfield4())
                .addValue("olse_tempfield5", d.getOlseTempfield5())
                .addValue("olse_tempfield6", d.getOlseTempfield6())
                .addValue("olse_tempfield7", d.getOlseTempfield7())
                .addValue("olse_tempfield8", d.getOlseTempfield8())
                .addValue("olse_active", d.getOlseActive())
                .addValue("olse_createdby", d.getOlseCreatedby())
                .addValue("olse_createdon", toTs(d.getOlseCreatedon()))
                .addValue("olse_modifiedon", toTs(d.getOlseModifiedon()));
    }

    private Timestamp toTs(LocalDateTime ldt) {
        return ldt == null ? null : Timestamp.valueOf(ldt);
    }
}
