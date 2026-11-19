package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.GenTlAllModuleImage;

@Repository 
public class AllModuleImageRepo {

     private final JdbcTemplate jdbcTemplate;

    public AllModuleImageRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Delete an existing image
    public int deleteImage(
            String refKeyId,
            String refDocType,
            String imageType) {

        String sql = """
            DELETE FROM gen_tl_allmoduleimgfile
            WHERE imfl_refkeyid = ?
              AND imfl_refdoctype = ?
              AND imfl_imagetype = ?
            """;

        return jdbcTemplate.update(
            sql,
            refKeyId,
            refDocType,
            imageType
        );
    }

    // Insert a new image
    public int insertImage(GenTlAllModuleImage image) {

        String sql = """
            INSERT INTO gen_tl_allmoduleimgfile (
                imfl_refkeyid,
                imfl_refdoctype,
                imfl_imagetype,
                imfl_blobimage,
                imfl_bloblength,
                imfl_filename,
                imfl_tempfield1,
                imfl_tempfield2,
                imfl_modifiedon
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

        return jdbcTemplate.update(
            sql,
            image.getRefkeyid(),
            image.getRefdoctype(),
            image.getRefdoctype(),
            image.getBlobimage(),
            image.getBloblength(),
            image.getFilename(),
            image.getTempfield1(),
            image.getTempfield2()
        );
    }

    public byte[] getImage(
        String refKeyId,
        String refDocType,
        String imageType) {

    String sql = """
        SELECT imfl_blobimage
        FROM gen_tl_allmoduleimgfile
        WHERE imfl_refkeyid = ?
          AND imfl_refdoctype = ?
          AND imfl_imagetype = ?
        """;

    List<byte[]> images = jdbcTemplate.query(
        sql,
        (rs, rowNum) -> rs.getBytes("imfl_blobimage"),
        refKeyId,
        refDocType,
        imageType
    );

    return images.isEmpty() ? null : images.get(0);
}

}
