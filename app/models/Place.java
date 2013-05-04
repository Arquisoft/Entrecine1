package models;

import java.sql.Date;

public class Place {

	private int id, id_session, id_customer, seat;
	private String creditCard;
	private double amount;
	private Date paymentDate;

	
	public Place(int id_session, int seat) {
		this.id_session = id_session;
		this.seat = seat;
	}
	
	public Place(int id_session, int id_customer, int seat, String creditCard,
			double amount, Date paymentDate) {
		super();
		this.id_session = id_session;
		this.id_customer = id_customer;
		this.seat = seat;
		this.creditCard = creditCard;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	public int getId_session() {
		return id_session;
	}

	public void setId_session(int id_session) {
		this.id_session = id_session;
	}

	public int getId_customer() {
		return id_customer;
	}

	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
