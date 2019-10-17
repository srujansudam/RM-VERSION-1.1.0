package com.cg.ibs.rm.service;

import java.util.Set;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.dao.BankRepresentativeDAO;
import com.cg.ibs.rm.dao.BankRepresentativeDAOImpl;
import com.cg.ibs.rm.exception.IBSExceptions;

public class BankRepresentativeServiceImpl implements BankRepresentativeService {
	private BankRepresentativeDAO bankRepresentativeDAO = new BankRepresentativeDAOImpl();

	@Override
	public Set<String> showRequests() {
		return bankRepresentativeDAO.getRequests();
		
	}

	@Override
	public Set<CreditCard> showUnapprovedCreditCards(String uci) {
		return bankRepresentativeDAO.getCreditCardDetails(uci);

	}

	@Override
	public Set<Beneficiary> showUnapprovedBeneficiaries(String uci) {
		return bankRepresentativeDAO.getBeneficiaryDetails(uci);
	}

	@Override
	public boolean saveCreditCardDetails(String uci, CreditCard card) throws IBSExceptions {
		return bankRepresentativeDAO.copyCreditCardDetails(uci, card);
	}

	@Override
	public void saveBeneficiaryDetails(String uci, Beneficiary beneficiary) {
		bankRepresentativeDAO.copyBeneficiaryDetails(uci, beneficiary);
	}
}
