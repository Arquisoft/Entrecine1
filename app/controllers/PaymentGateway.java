package controllers;

public class PaymentGateway {

	public boolean payment(String creditCard) {
		String cadena = creditCard.substring(0, 2);
		return cadena.equals("cc");
	}

}
