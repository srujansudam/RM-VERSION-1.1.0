package com.cg.ibs.rm.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.ibs.rm.util.ConnectionProvider;

public class Test {

	public static void main(String[] args) {

		String SELECT_QRY = "SELECT uci,current_balance FROM accounts";

		try {
			Connection con = ConnectionProvider.getInstance().getConnection();
			PreparedStatement pst = con.prepareStatement(SELECT_QRY);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getBigDecimal("uci") + "\t" + rs.getDouble("current_balance"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}