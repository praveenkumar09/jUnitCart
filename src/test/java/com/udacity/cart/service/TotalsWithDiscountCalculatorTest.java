package com.udacity.cart.service;

import com.udacity.cart.model.CartItem;
import com.udacity.cart.model.CartTotals;
import com.udacity.cart.model.User;
import com.udacity.cart.model.UserType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TotalsWithDiscountCalculatorTest {

	private static User user;

	private TotalsWithDiscountCalculator totalsWithDiscountCalculator;

	@BeforeAll
	static void initUser(){
		user = new User("UserPerson",UserType.REGULAR,100.00);
	}

	@BeforeEach
	void initTotalsWithDiscountCalculator(){
		totalsWithDiscountCalculator = new TotalsWithDiscountCalculator(user);
	}


	// Replace this with a Repeated test. Use a BeforeAll method to create a user whose credit
	// will be reduced with each repetition, and use a BeforeEach method to create a new TotalsWithDiscountCalculator
	// for each repetition.
	@RepeatedTest(3)
	public void totalsWithDiscount_getTotals_reducesUserCredit(RepetitionInfo repetitionInfo) {
		double expected;
		totalsWithDiscountCalculator.getTotals(List.of(new CartItem("Twenty dollar item", 20.0, 0)));
		expected = 100.0 - (repetitionInfo.getCurrentRepetition() * 20);
		assertEquals(expected, user.getCredit());
	}

	// Replace this with a parameterized test that uses a MethodSource to provide
	// a stream of arguments allowing you to test both regular and platinum users with the
	// same test.
	@ParameterizedTest
	@MethodSource("getUserArgumentProvider")
	public void totalsWithDiscounts_regularAndPlatinumUser_returnsDifferentSubtotal(User regularUser, CartItem item, CartTotals totals) {
		TotalsWithDiscountCalculator regularCalculator = new TotalsWithDiscountCalculator(regularUser);
		assertEquals(totals, regularCalculator.getTotals(List.of(item)));
	}

	public static Stream<Arguments> getUserArgumentProvider() {
		return Stream.of(
				Arguments.of(new User("Regular User", UserType.REGULAR, 0.0),new CartItem("Ten Dollar Item",10.0,1.0),
				new CartTotals(10.0,1.0)),
				Arguments.of(new User("Platinum User", UserType.PLATINUM, 0.0),new CartItem("Ten Dollar Item",10.0,1.0),
						new CartTotals(9.0,1.0))
		);
	}


}