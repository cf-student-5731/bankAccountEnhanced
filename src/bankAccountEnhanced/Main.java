package bankAccountEnhanced;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		BankAccount a = new BankAccount("John", "Doe", 10001);
		DebitCardAccount d = new DebitCardAccount("Jane", "Doe", 10002);
		CreditCardAccount c = new CreditCardAccount("James", "Dodo", 10003);

		a.addMoney(500f);
		a.addMoney(500f);
		widthDrawMoney(a, 400);
		widthDrawMoney(a, 1200);

		d.addMoney(500f);
		widthDrawMoney(d, 300);
		widthDrawMoney(d, 1600);
		widthDrawMoney(d, 200);

		c.addMoney(500f);
		widthDrawMoney(c, 100);
		widthDrawMoney(c, 2400);

		ArrayList<BankAccount> accounts = new ArrayList<>();
		accounts.add(a);
		accounts.add(d);
		accounts.add(c);

		System.out.println("PRINTING all ACCOUNTS");
		for (BankAccount acc : accounts) {
			acc.printAccountData();
			System.out.printf("%n%n%n");
		}


		for (BankAccount acc : accounts) {
			acc.saveAccountDataToFile();
		}

		BankAccount newA = new BankAccount("Happy", "Coding", 99999);
		newA.readDataFromFile("account_10001_old.txt");
		newA.printAccountData();

		ArrayList<BankAccount> autoAccounts = new ArrayList<>();
		restoreAllAccounts(autoAccounts, new String[]{"account_10001.txt", "account_10002.txt", "account_10003.txt", "account_10001_old.txt"});

		System.out.printf("%n%n%n");
		for (BankAccount acc : autoAccounts) {
			System.out.println("Automatically generated!");
			acc.printAccountData();
			System.out.printf("%n%n");
		}
		autoAccounts.get(0).addMoney(5000);
		System.out.println("auto account changed:");
		autoAccounts.get(0).printAccountData();

	}

	public static void widthDrawMoney(BankAccount account, float amount) {
		try {
			account.widthDrawMoney(amount);
		} catch (BankAccountNegativeException e) {
			account.printAccountData();
			System.out.println(e.getMessage());
			System.out.printf("%n%n%n");
		}
	}

	public static void restoreAllAccounts(ArrayList<BankAccount> accounts, String[] filenames) {
		ArrayList<String[]> data = new ArrayList<>();
		for (String s : filenames) {

			try {
				BufferedReader file = new BufferedReader(new FileReader(s));
				String line = file.readLine();
				while (line != null) {
					data.add(line.split("\t"));
					line = file.readLine();
					if (data.get(0)[4].equals("bankAccount")) {
						BankAccount temp = new BankAccount(data.get(0)[0], data.get(0)[1], Integer.parseInt(data.get(0)[2]));
						temp.readDataFromFile(s);
						accounts.add(temp);
					}
					if (data.get(0)[4].equals("debitCardAccount")) {
						BankAccount temp = new DebitCardAccount(data.get(0)[0], data.get(0)[1], Integer.parseInt(data.get(0)[2]));
						temp.readDataFromFile(s);
						accounts.add(temp);
					}
					if (data.get(0)[4].equals("creditCardAccount")) {
						BankAccount temp = new CreditCardAccount(data.get(0)[0], data.get(0)[1], Integer.parseInt(data.get(0)[2]));
						temp.readDataFromFile(s);
						accounts.add(temp);
					}
				}
				file.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
