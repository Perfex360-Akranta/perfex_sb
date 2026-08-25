package com.akranta.perfex_sb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenTlTradeRoleLinkRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findOtherActiveRoleForTrade(String tradeId, String roleId) {
        String sql = "SELECT gtrl_roleid FROM gen_tl_trade_role_link "
                + "WHERE gtrl_tradeid = ? AND gtrl_active = 'Y' AND gtrl_roleid <> ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, tradeId, roleId);
        return result.isEmpty() ? null : result.get(0);
    }

    public String findActiveFlag(String tradeId, String roleId) {
        String sql = "SELECT gtrl_active FROM gen_tl_trade_role_link "
                + "WHERE gtrl_tradeid = ? AND gtrl_roleid = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, tradeId, roleId);
        return result.isEmpty() ? null : result.get(0);
    }

    public void insertTradeRoleLink(String tradeId, String roleId, String createdBy) {
        String sql = "INSERT INTO gen_tl_trade_role_link "
                + "(gtrl_tradeid, gtrl_roleid, gtrl_active, gtrl_createdby, gtrl_createdon, gtrl_modifiedon) "
                + "VALUES (?, ?, 'Y', ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, tradeId, roleId, createdBy);
    }

    public void reactivateTradeRoleLink(String tradeId, String roleId) {
        String sql = "UPDATE gen_tl_trade_role_link SET "
                + "gtrl_active = 'Y', gtrl_modifiedon = CURRENT_TIMESTAMP "
                + "WHERE gtrl_tradeid = ? AND gtrl_roleid = ?";
        jdbcTemplate.update(sql, tradeId, roleId);
    }

    //public void deactivateTradeRoleLink(String tradeId, String roleId) {
    public int deactivateTradeRoleLink(String tradeId, String roleId) {
        String sql = "UPDATE gen_tl_trade_role_link SET "
                + "gtrl_active = 'N', gtrl_modifiedon = CURRENT_TIMESTAMP "
                //+ "WHERE gtrl_tradeid = ? AND gtrl_roleid = ?";
                + "WHERE gtrl_tradeid = ? AND gtrl_roleid = ? AND gtrl_active = 'Y'";
        //jdbcTemplate.update(sql, tradeId, roleId);
        return jdbcTemplate.update(sql, tradeId, roleId);
    }
}