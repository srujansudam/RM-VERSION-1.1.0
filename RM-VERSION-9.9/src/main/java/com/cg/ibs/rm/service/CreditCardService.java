package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.IBSExceptions;

public interface CreditCardService {
	public Set<CreditCard> showCardDetails(String UCI);

	public boolean validateCardNumber(String creditCardNumber);

	public boolean validateDateOfExpiry(String creditDateOfExpiry) ;

	public boolean validateNameOnCard(String nameOnCreditCard);

	public boolean deleteCardDetails(String uci, BigInteger creditCardNumber) throws IBSExceptions;

	public void saveCardDetails(String uci, CreditCard card) throws IBSExceptions;
}
