package bankAccountEnhanced;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class History {
	private float transaction;
	final GregorianCalendar today;
	boolean accepted;
	final SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd.MM.yyyy");

	public History(float transaction, boolean accepted) {
		this.transaction = transaction;
		this.today = (GregorianCalendar) Calendar.getInstance();
		this.accepted = accepted;
	}

	public String getTransaction(){
		if (this.accepted) {
			if (this.transaction > 0) {
				return String.format("%s%+16.2f%s", Colors.ANSI_GREEN, this.transaction, Colors.ANSI_RESET);
			} else {
				return String.format("%s%+16.2f%s", Colors.ANSI_RED, this.transaction, Colors.ANSI_RESET);
			}
		} else {
			return String.format("%-1s%-9.2f %s%s", Colors.ANSI_RED, this.transaction, "CANCEL", Colors.ANSI_RESET);
		}
	}

	public float getUnformattedTransaction(){
		return this.transaction;
	}

	public void setTransaction(float transaction) {
		this.transaction = transaction;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public String getDate(){
		return dateFormat.format(this.today.getTime());
	}

	public String getUnformattedDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd.MM.yyyy");
		return sdf.format(this.today.getTime());
	}


	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
}
