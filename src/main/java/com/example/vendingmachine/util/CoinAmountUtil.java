package com.example.vendingmachine.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CoinAmountUtil {

	public final static List<Integer> acceptableAmounts = Arrays.asList(100, 50, 20, 10, 5);

	CoinAmountUtil() {
	}

	public static Boolean isValidCoinAmount(Integer money) {
		return acceptableAmounts.contains(money);
	}

	public static Boolean isValidAmount(Integer deposit) {
		return (deposit % 5 == 0);
	}

	public static List<Integer> calculateCoinChange(int deposit, int price) {
		Integer calculateResult = deposit - price;
		List<Integer> changeResult = new ArrayList<>();

		for (int amount : CoinAmountUtil.acceptableAmounts) {
			while (calculateResult >= amount) {
				changeResult.add(amount);
				calculateResult -= amount;
			}
		}
		return changeResult;
	}
}
