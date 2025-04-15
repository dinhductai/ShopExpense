package com.trangshop.shopexpense.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectService {
    Connection getConnection() throws SQLException;

}
