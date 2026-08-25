package com.akranta.perfex_sb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

// import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DbFunctionTempleteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

@Transactional
public Map<String, Object> callFunction(String functionName, Map<String, Object> params) throws SQLException {
    return jdbcTemplate.execute((Connection con) -> {
        Map<String, Object> resultMap = new HashMap<>();
        //boolean origAutoCommit = con.getAutoCommit();
        //con.setAutoCommit(false); // keep cursor alive

System.out.println("before Start: " +LocalDateTime.now() );
        // 1. Prepare a statement to fetch the function
       try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM "+functionName+"(?, ?)")){
        stmt.setString(1, (String) params.get("vconditionparam"));
        stmt.setString(2, (String) params.get("vcommonparam"));

        try (ResultSet rs = stmt.executeQuery()) {
System.out.println("AFTER Start: " +LocalDateTime.now() );
        List<Map<String, Object>> cursorList = new ArrayList<>();
        int totalcnt = 0;

        if (rs.next()) {
            System.out.println("before RS Start: " +LocalDateTime.now() );
            totalcnt = rs.getInt(1);
            System.out.println("Return Row Count: " + totalcnt);
            
    

            // Get the refcursor name returned
            String refCursorName = rs.getString(2);

            System.out.println("Cursor name: " + refCursorName);
System.out.println("before CURSOR Start: " +LocalDateTime.now() );
            // Fetch the cursor results
            try (Statement curStmt = con.createStatement()) {
                // curStmt.setFetchSize(100);
                // System.out.println("Cursor name1: " + refCursorName);
                System.out.println("before CURSOR FETCHING Start: " +LocalDateTime.now() );
                // ResultSet curRs = curStmt.executeQuery("FETCH FORWARD 100 FROM \"" + refCursorName + "\"");
                ResultSet curRs = curStmt.executeQuery("FETCH ALL FROM \"" + refCursorName + "\"");
                // System.out.println("Cursor name2: " + refCursorName);
                System.out.println("aFTER CURSOR FETCHING Start: " +LocalDateTime.now() );
                ResultSetMetaData meta = curRs.getMetaData();
                int colCount = curRs.getMetaData().getColumnCount();
                System.out.println("Cursor Column count: " + colCount);
                while (curRs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    
                    for (int i = 1; i <= colCount; i++) {
                        row.put(meta.getColumnLabel(i), curRs.getObject(i));
                    }
                    cursorList.add(row);
                }
            }
            System.out.println("AFTER CURSOR Fetched: " +LocalDateTime.now() );

            // Close the cursor
            try (Statement curStmt = con.createStatement()) {
                curStmt.execute("CLOSE \"" + refCursorName + "\"");
            }
            System.out.println("AFTER CURSOR Start: " +LocalDateTime.now() );
        }

        resultMap.put("totalcnt", totalcnt);
        resultMap.put("cur", cursorList);
    }
}finally {
            //con.setAutoCommit(origAutoCommit); // restore auto-commit
        }
return resultMap;
    });
}

public Map<String, Object> callMultipeParamFunction(String functionName, Map<Integer, Object> params) throws SQLException {
    return jdbcTemplate.execute((Connection con) -> {
        Map<String, Object> resultMap = new HashMap<>();
        boolean origAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false); // keep cursor alive

        StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(functionName).append("(");

            for (int i = 0; i < params.size(); i++) {
                sql.append("?");
                if (i < params.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")"); 

            System.out.println("SQL : " + sql.toString());
        // 1. Prepare a statement to fetch the function
       try (PreparedStatement stmt = con.prepareStatement(sql.toString())){

        for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                    stmt.setObject(entry.getKey(), entry.getValue());

                    System.out.println(entry.getKey() +": value : " + entry.getValue());
            }

        //  for (int i = 1; i <= params.size(); i++) {
        //         stmt.setString(i, (String) params.get(i));
        //     }
        // stmt.setString(1, (String) params.get("vconditionparam"));
        // stmt.setString(2, (String) params.get("vcommonparam"));

        try (ResultSet rs = stmt.executeQuery()) {

        List<Map<String, Object>> cursorList = new ArrayList<>();
        int totalcnt = 0;

        if (rs.next()) {
            totalcnt = rs.getInt(1);
            System.out.println("Return Row Count: " + totalcnt);
            
    

            // Get the refcursor name returned
            String refCursorName = rs.getString(2);

            System.out.println("Cursor name: " + refCursorName);

            // Fetch the cursor results
            try (Statement curStmt = con.createStatement()) {
                // System.out.println("Cursor name1: " + refCursorName);
                ResultSet curRs = curStmt.executeQuery("FETCH ALL FROM \"" + refCursorName + "\"");
                // System.out.println("Cursor name2: " + refCursorName);
                int colCount = curRs.getMetaData().getColumnCount();
                System.out.println("Cursor Column count: " + colCount);
                while (curRs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    
                    for (int i = 1; i <= colCount; i++) {
                        row.put(curRs.getMetaData().getColumnLabel(i), curRs.getObject(i));
                    }
                    cursorList.add(row);
                }
            }

            // Close the cursor
            try (Statement curStmt = con.createStatement()) {
                curStmt.execute("CLOSE \"" + refCursorName + "\"");
            }
        }

        resultMap.put("totalcnt", totalcnt);
        resultMap.put("cur", cursorList);
    }
}finally {
            con.setAutoCommit(origAutoCommit); // restore auto-commit
        }
return resultMap;
    });
}


// public Map<String, Object> callBackFunction(String functionName, Map<String, Object> params) throws SQLException {
//     return jdbcTemplate.execute((Connection con) -> {
//         Map<String, Object> resultMap = new HashMap<>();
//         boolean origAutoCommit = con.getAutoCommit();
//         con.setAutoCommit(false); // keep cursor alive
//         CallableStatement cs = con.prepareCall("{  call "+functionName+"(?,?,?,?) }");

//         // 1. Prepare a statement to fetch the function
//        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM "+functionName+"(?, ?)")){
//         stmt.setString(1, (String) params.get("vconditionparam"));
//         stmt.setString(2, (String) params.get("vcommonparam"));

//         try (ResultSet rs = stmt.executeQuery()) {

//         List<Map<String, Object>> cursorList = new ArrayList<>();
//         int totalcnt = 0;

//         if (rs.next()) {
//             totalcnt = rs.getInt(1);
//             System.out.println("Return Row Count: " + totalcnt);
            
    

//             // Get the refcursor name returned
//             String refCursorName = rs.getString(2);

//             System.out.println("Cursor name: " + refCursorName);

//             // Fetch the cursor results
//             try (Statement curStmt = con.createStatement()) {
//                 System.out.println("Cursor name1: " + refCursorName);
//                 ResultSet curRs = curStmt.executeQuery("FETCH ALL FROM \"" + refCursorName + "\"");
//                 System.out.println("Cursor name2: " + refCursorName);
//                 while (curRs.next()) {
//                     Map<String, Object> row = new HashMap<>();
//                     int colCount = curRs.getMetaData().getColumnCount();
//                     System.out.println("Cursor count: " + colCount);
//                     for (int i = 1; i <= colCount; i++) {
//                         row.put(curRs.getMetaData().getColumnLabel(i), curRs.getObject(i));
//                     }
//                     cursorList.add(row);
//                 }
//             }

//             // Close the cursor
//             try (Statement curStmt = con.createStatement()) {
//                 curStmt.execute("CLOSE \"" + refCursorName + "\"");
//             }
//         }

//         resultMap.put("totalcnt", totalcnt);
//         resultMap.put("cur", cursorList);
//     }
// }finally {
//             con.setAutoCommit(origAutoCommit); // restore auto-commit
//         }
// return resultMap;
//     });
// }


}
