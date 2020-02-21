package br.com.cvc.corp.hotelaria.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAttributeConverter {

	public static LocalDate convertStringToLocalDate(String date){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}
}
