package bankAccountEnhanced;

public class CreditCardAccount extends BankAccount {
	public CreditCardAccount(String firstName, String lastName, int number) {
		super(firstName, lastName, number);
		float limit = -3500f;
		super.setLimit(limit);
	}
}
