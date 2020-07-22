package bankAccountEnhanced;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class BankAccount {
	private String firstName, lastName;
	private int number;
	private float balance;
	private float limit = 0;
	final ArrayList<History> history = new ArrayList<>();


	public BankAccount(String firstName, String lastName, int number) {
		setFirstName(firstName);
		setLastName(lastName);
		setNumber(number);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setLimit(float limit) {
		this.limit = limit;
	}

	public void addMoney(float amount) {
		this.balance += amount;
		history.add(new History(amount, true));
		System.out.printf("%n%.2f %s %s %s %s%n%n", amount, "added to", this.firstName, this.lastName, "Account.");
	}

	public void widthDrawMoney(float amount) throws BankAccountNegativeException {
		if (this.balance - amount < this.limit) {
			history.add(new History(-amount, false));
			throw new BankAccountNegativeException("Bank account would be below credit limit! Transaction cancelled!");
		} else {
			this.balance -= amount;
			history.add(new History(-amount, true));
			System.out.printf("%n%.2f %s %s %s %s%n%n", amount, "withdrawn from", this.firstName, this.lastName, "Account.");
		}
	}

	public void printAccountData() {
		String pattern = "%s%-13s%-13s%13s%s%n";
		String line = "-".repeat(39);

		System.out.printf(pattern, Colors.ANSI_YELLOW, "Firstname", "Lastname", "Accountnumber", Colors.ANSI_RESET);
		System.out.printf(pattern, Colors.ANSI_BLUE, this.firstName, this.lastName, this.number, Colors.ANSI_RESET);
		System.out.println(line);
		for (History h : this.history) {
			System.out.printf("%-23s%16s%n", h.getDate(), h.getTransaction());
		}
		System.out.println(line);
		if (this.balance > 0) {
			System.out.printf("%-23s%s%16.2f%s%n", "BALANCE: ", Colors.ANSI_GREEN, this.balance, Colors.ANSI_RESET);
		} else if (this.balance < 0) {
			System.out.printf("%-23s%s%16.2f%s%n", "BALANCE: ", Colors.ANSI_RED, this.balance, Colors.ANSI_RESET);
		} else {
			System.out.printf("%-23s%16.2f%n", "BALANCE: ", this.balance);
		}
		System.out.println(line);
		System.out.println(line);
	}

	public void saveAccountDataToFile() {
		String type;
		StringBuilder stringHistory = new StringBuilder();
		String filename = String.valueOf(this.number);
		if (this instanceof DebitCardAccount) {
			type = "debitCardAccount";
		} else if (this instanceof CreditCardAccount) {
			type = "creditCardAccount";
		} else {
			type = "bankAccount";
		}

		for (History h : this.history) {
			stringHistory.append("\t").append(h.getUnformattedDate()).append("\t").append(h.getUnformattedTransaction()).append("\t").append(h.isAccepted());
		}


		try {
			FileWriter file = new FileWriter("./account_" + filename + ".txt");
			file.write(this.firstName + "\t"
					+ this.lastName + "\t"
					+ this.number + "\t"
					+ this.balance + "\t"
					+ type
					+ stringHistory

			);
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void readDataFromFile(String filename) {
		ArrayList<String[]> data = new ArrayList<>();
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			String line = file.readLine();
			while (line != null) {
				data.add(line.split("\t"));
				line = file.readLine();
			}
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		setFirstName(data.get(0)[0]);
		setLastName(data.get(0)[1]);
		setNumber(Integer.parseInt(data.get(0)[2]));

		this.balance = (Float.parseFloat(data.get(0)[3]));

		for (int i = 5; i < data.get(0).length; i += 3) {
			try {
				History temp = new History(Float.parseFloat(data.get(0)[i + 1]), (data.get(0)[i+2].equals("true")));
				Date d = temp.getDateFormat().parse(data.get(0)[i]);
				temp.today.setTime(d);
				this.history.add(temp);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
