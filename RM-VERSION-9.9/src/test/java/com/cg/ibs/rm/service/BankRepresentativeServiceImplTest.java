package com.cg.ibs.rm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import com.cg.ibs.rm.bean.Beneficiary;
import com.cg.ibs.rm.bean.CreditCard;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.ui.Type;

class BankRepresentativeServiceImplTest {
	BankRepresentativeService object = new BankRepresentativeServiceImpl();
	BeneficiaryAccountServiceImpl object1 = new BeneficiaryAccountServiceImpl();
	Beneficiary beneficiary = new Beneficiary();

	@Test
	public void testShowRequests1() {
		assertEquals(1, object.showRequests().size());
	}

	@Test
	public void testShowRequests2() {
		assertNotNull(object.showRequests());
	}

	@Test
	public void testShowUnapprovedCreditCards1() {
		assertEquals(1, object.showUnapprovedCreditCards("123456").size());
	}

	@Test
	public void testShowUnapprovedCreditCards2() {
		assertNotNull(object.showUnapprovedCreditCards("123456"));
	}

	@Test
	public void testShowUnapprovedBeneficiaries1() {
		assertEquals(1, object.showUnapprovedBeneficiaries("123456").size());
	}

	@Test
	public void testShowUnapprovedBeneficiaries2() {
		assertNotNull(object.showUnapprovedCreditCards("123456"));
	}

	@Test
	public void testSaveCreditCardDetails1() {
		CreditCard card = new CreditCard();
		card.setcreditCardNumber(new BigInteger("1234567890123456"));
		card.setcreditDateOfExpiry("17/12/2020");
		card.setnameOnCreditCard("saima");
		try {
			object.saveCreditCardDetails("123456", card);
		} catch (IBSExceptions exception) {
			Throwable throwable = assertThrows(IBSExceptions.class, () -> {
				throw new IBSExceptions(exception.getMessage());
			});
			assertSame("Card not added", throwable.getMessage());
		}
	}

	@Test
	public void testSaveBeneficiaryDetails1() {
		beneficiary.setAccountNumber(new BigInteger("123456789013"));
		beneficiary.setAccountName("Ayush Handsome");
		beneficiary.setBankName("SBSC");
		beneficiary.setIfscCode("SBSC5623487");
		beneficiary.setType(Type.OTHERSINOTHERS);
		object.saveBeneficiaryDetails("123456", beneficiary);
		assertEquals(2, object1.showBeneficiaryAccount("123456").size());
	}

}
