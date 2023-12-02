package interfaces.model.services;

import java.time.Duration;

import interfaces.model.entities.CarRental;
import interfaces.model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if (hours <= 12) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPayment = pricePerDay * Math.ceil(hours / 24);
			
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	
	

}
