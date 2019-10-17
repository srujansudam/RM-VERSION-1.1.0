package com.cg.ibs.rm.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.FinalCustomer;
import com.cg.ibs.rm.bean.TemporaryCustomer;
import com.cg.ibs.rm.exception.ExceptionMessages;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.ui.Type;
import com.cg.ibs.rm.util.ConnectionProvider;

public class BeneficiaryDAOImpl implements BeneficiaryDAO {
	private Map<String, TemporaryCustomer> tempMap = DataStoreImpl.getTempMap();
	private Map<String, FinalCustomer> finalMap = DataStoreImpl.getFinalMap();
	private Iterator<Beneficiary> it;

	public Set<Beneficiary> getDetails(String uci) {
		Set<Beneficiary> beneficiaries = new HashSet<>();
		try {
			Connection con = ConnectionProvider.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT Account_Number, Account_Name,Ifsc_code,bank_name, Type, UCI FROM FinalBeneficiarytable");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setAccountName(resultSet.getString("Account_Name"));
				beneficiary.setAccountNumber(new BigInteger(resultSet.getString("Account_Number")));
				beneficiary.setBankName(resultSet.getString("bank_name"));
				beneficiary.setIfscCode(resultSet.getString("Ifsc_code"));
				beneficiary.setType(Type.valueOf(resultSet.getString("Type")));
				beneficiaries.add(beneficiary);
			}			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return beneficiaries;
	}

	@Override
	public void copyDetails(String uci, Beneficiary beneficiary) throws IBSExceptions, SQLException, IOException {
		if (finalMap.get(uci).getSavedBeneficiaries().contains(beneficiary)) {
			throw new IBSExceptions(ExceptionMessages.ERROR3);
		} else {
			Connection con = ConnectionProvider.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("INSERT INTO FinalBeneficiaryTable VALUES(?,?,?,?,?,?,?)");
			statement.setBigDecimal(1, new BigDecimal(beneficiary.getAccountNumber()));
			statement.setString(2, beneficiary.getAccountName());
			statement.setString(3, beneficiary.getIfscCode());
			statement.setString(4, beneficiary.getBankName());
			statement.setString(5, beneficiary.getType().toString());
			statement.setBigDecimal(6, new BigDecimal(uci));
			
			
			
		}

	}

	@Override
	public Beneficiary getBeneficiary(String uci, BigInteger accountNumber) {
		Beneficiary beneficiary1 = null;
		it = finalMap.get(uci).getSavedBeneficiaries().iterator();
		while (it.hasNext()) {
			Beneficiary beneficiary = it.next();
			if (beneficiary.getAccountNumber().equals(accountNumber)) {
				beneficiary1 = beneficiary;
			}
		}
		return beneficiary1;
	}

	@Override
	public boolean updateDetails(String uci, Beneficiary beneficiary1) throws IBSExceptions {

		boolean result = false;
		if (!(finalMap.get(uci).getSavedBeneficiaries().contains(beneficiary1))) {
			throw new IBSExceptions(ExceptionMessages.ERROR4);

		}
		it = finalMap.get(uci).getSavedBeneficiaries().iterator();
		while (it.hasNext()) {
			Beneficiary beneficiary = it.next();
			if (beneficiary.getAccountNumber().equals(beneficiary1.getAccountNumber())) {
				it.remove();
				result = true;
			}
		}

		tempMap.get(uci).getUnapprovedBeneficiaries().add(beneficiary1);
		return result;
	}

	@Override
	public boolean deleteDetails(String uci, BigInteger accountNumber) throws IBSExceptions {
		boolean result = false;
		int count = 0;
		for (Beneficiary beneficiary : finalMap.get(uci).getSavedBeneficiaries()) {
			if (beneficiary.getAccountNumber().equals(accountNumber)) {
				count++;
			}
		}
		if (0 == count) {
			throw new IBSExceptions(ExceptionMessages.ERROR4);
		} else {
			it = finalMap.get(uci).getSavedBeneficiaries().iterator();
			while (it.hasNext()) {
				Beneficiary beneficiary = it.next();
				if (beneficiary.getAccountNumber().equals(accountNumber)) {
					it.remove();
					result = true;
				}
			}
		}
		return result;
	}

}
