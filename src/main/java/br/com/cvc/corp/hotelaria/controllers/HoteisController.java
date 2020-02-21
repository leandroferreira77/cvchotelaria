package br.com.cvc.corp.hotelaria.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.corp.hotelaria.converter.LocalDateAttributeConverter;
import br.com.cvc.corp.hotelaria.dto.HotelDTO;
import br.com.cvc.corp.hotelaria.exceptions.BaseException;
import br.com.cvc.corp.hotelaria.model.Hotel;
import br.com.cvc.corp.hotelaria.model.Travel;
import br.com.cvc.corp.hotelaria.service.BrokerServiceClient;
import br.com.cvc.corp.hotelaria.service.PriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@Api
@RequestMapping(value = "/api/hotel", produces = "application/json")
public class HoteisController {

	@Autowired
	private PriceService priceService;
	
	@Autowired
	private BrokerServiceClient brokerService;
	
	@ApiOperation(value = "Return total price hotels")
	@GetMapping(value = "/calculate/avail/{codeCity}")
	public List<HotelDTO> calculateAvails(@PathVariable("codeCity") @Valid Integer codeCity,
			@ApiParam(required = true, example = "21/02/2020") @RequestParam @Valid String dataCheckin,
			@ApiParam(required = true, example = "25/02/2020") @RequestParam @Valid String dataCheckout,
			@RequestParam @Valid Integer adults, @RequestParam @Valid Integer childs) throws BaseException {
	    
	    
	    LocalDate checkin = LocalDateAttributeConverter.convertStringToLocalDate(dataCheckin);
	    LocalDate checkout = LocalDateAttributeConverter.convertStringToLocalDate(dataCheckout);
		
		List<Hotel> hotelsAvails = brokerService.hotelsAvails(codeCity);
		priceService = new PriceService(new Travel(checkin, checkout, adults, childs, hotelsAvails));
		List<HotelDTO> hotels = priceService.calculateAvails();
		
		return hotels;
	}
	
	@ApiOperation(value = "Return total price hotel")
	@GetMapping(value = "/calculate/{hotelId}")
	public HotelDTO calculate(@PathVariable("hotelId") @Valid Integer hotelId) throws BaseException {
		
		Hotel hotel = brokerService.hotelDetails(hotelId);
		HotelDTO hotelDTO = priceService.calculateDetails(hotel);
		
		return hotelDTO;
	} 
}
