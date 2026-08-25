package com.akranta.perfex_sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class DbActionTemplate {

    private static final Logger logger = LoggerFactory.getLogger(DbActionTemplate.class);

    @Autowired
    private DataSource dataSource;

    public String getSequenceNumber(String tableName, int keyLength, String preFix, String dateFormat, String formatReset) throws Exception {
        Connection connection = null;
        CallableStatement stmt = null;

        try {
            String newSeqNumber = "";
            connection = dataSource.getConnection();

            // PostgreSQL function call syntax
            String procName = "{ ? = call RUNSEQUENCE(?,?,?,?,?) }";

            stmt = connection.prepareCall(procName);
            logger.info("Calling RUNSEQUENCE for Table Name: {} with keyLength: {}", tableName, keyLength);

            // Set input parameters
            stmt.setString(2, tableName);
            stmt.setInt(3, keyLength);
            stmt.setString(4, preFix);
            stmt.setString(5, dateFormat);
            stmt.setString(6, formatReset);

            // Register output parameter
            stmt.registerOutParameter(1, Types.VARCHAR);

            // Execute the stored procedure
            stmt.execute();

            // Get the result
            newSeqNumber = stmt.getString(1);
            logger.info("Generated sequence number: {}", newSeqNumber);

            return newSeqNumber;

        } catch (SQLException sqlException) {
            logger.error("SQL Exception in sequence generation: {}", sqlException.getMessage(), sqlException);
            throw new Exception("Sequence No. Generation SQL Error: " + sqlException.getMessage());
        } catch (Exception exception) {
            logger.error("Exception in sequence generation: {}", exception.getMessage(), exception);
            throw new Exception("Sequence No. Generation: " + exception.getMessage());
        } finally {
            closeConnection(null, null, stmt, null, connection);
        }
    }

    private void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement,
                                 CallableStatement callableStatement, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing database connections: {}", e.getMessage(), e);
        }
    }
}
