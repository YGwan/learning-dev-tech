package org.example.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    private static final int ONE_MB = 1024 * 1024;
    private final List<byte[]> testList = new ArrayList<>();
    private final List<Connection> connections = new ArrayList<>();
    private final DataSource dataSource;

    public TestService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int memoryTest() {
        try {
            for (int i = 0; i < 100; i++) {
                byte[] bytes = new byte[ONE_MB];
                testList.add(bytes);
            }

        } catch (OutOfMemoryError e) {
            log.error("Out of Memory!");
            throw new OutOfMemoryError("Out of heap memory");
        }

        return testList.size();
    }

    public int cpuTest() {
        while (true) {
            System.out.println("cpu test");
        }
    }

    public int dbConnectOpenTest() {
        try {
            Connection conn = dataSource.getConnection();
            connections.add(conn);
            return connections.size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int dbConnectCloseTest() {
        try {
            Connection conn = connections.get(0);
            conn.close();
            connections.remove(conn);
            return connections.size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
