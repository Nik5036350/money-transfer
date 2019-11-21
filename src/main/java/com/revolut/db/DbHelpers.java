package com.revolut.db;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;
import org.skife.jdbi.v2.DBI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class DbHelpers {
    private static Logger log = Logger.getLogger(DbHelpers.class);

    public void populateTestData(DBI jdbi) {
        log.info("Init DB with data");

        Connection conn = jdbi.open().getConnection();
        try {
            RunScript.execute(conn, new FileReader("src/test/resources/db/dropAll.sql"));
            RunScript.execute(conn, new FileReader("src/test/resources/db/initDb.sql"));
            RunScript.execute(conn, new FileReader("src/test/resources/db/setTestData.sql"));
        } catch (SQLException e) {
            log.error("populateTestData(): Error populating user data: ", e);
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            log.error("populateTestData(): Error finding test script file ", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }

        log.info("DB initialized");
    }
}
