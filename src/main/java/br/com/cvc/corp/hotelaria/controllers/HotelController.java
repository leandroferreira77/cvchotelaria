package br.com.cvc.corp.hotelaria.controllers;

//import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.corp.hotelaria.converter.LocalDateAttributeConverter;
import br.com.cvc.corp.hotelaria.dto.HotelDTO;
import br.com.cvc.corp.hotelaria.exceptions.BaseException;
import br.com.cvc.corp.hotelaria.model.Hotel;
import br.com.cvc.corp.hotelaria.model.Travel;
import br.com.cvc.corp.hotelaria.service.impl.PriceServiceImpl;
import br.com.cvc.corp.hotelaria.client.broker.BrokerServiceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin("*")
@Validated
@RestController
@RequestMapping(path = "/api/v1/hotels", produces = "application/json")
@Api(value = "Hotels", tags = {"Hotels"})
public class HotelController {

	@Autowired
	private PriceServiceImpl priceService;
	
	@Autowired
    private BrokerServiceClient brokerServiceClient;
	
    @ApiOperation(value = "Get total prices by codeCity", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
                           @ApiResponse(code = 200, message = "List of Hotels total prices"),
                           @ApiResponse(code = 404, message = "Hotels not found"),
                           @ApiResponse(code = 500, message = "Internal server error"),
                           @ApiResponse(code = 502, message = "Error when try to communicate with partner"),
    })
    @GetMapping(path = "/calculate/avail/{codeCity}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<HotelDTO>> calculateAvails(@PathVariable("codeCity") @Valid Integer codeCity,
			@ApiParam(required = true, example = "21/02/2020") @RequestParam @Valid String dataCheckin,
			@ApiParam(required = true, example = "25/02/2020") @RequestParam @Valid String dataCheckout,
			@RequestParam @Valid Integer adults, @RequestParam @Valid Integer childs) throws BaseException {
	    
	    List<Hotel> hotelsAvails = brokerServiceClient.hotelsAvails(codeCity);
	    
	    LocalDate checkin = LocalDateAttributeConverter.convertStringToLocalDate(dataCheckin);
	    LocalDate checkout = LocalDateAttributeConverter.convertStringToLocalDate(dataCheckout);
		
		priceService = new PriceServiceImpl(new Travel(checkin, checkout, adults, childs, hotelsAvails));
		List<HotelDTO> hotels = priceService.calculateAvails();
		
		return ResponseEntity.ok(hotels);

	}
	
    @ApiOperation(value = "Get total price type of Hotels by hotelId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
                           @ApiResponse(code = 200, message = "List of Rooms and Categories by hotelId"),
                           @ApiResponse(code = 404, message = "Rooms not found"),
                           @ApiResponse(code = 500, message = "Internal server error"),
                           @ApiResponse(code = 502, message = "Error when try to communicate with partner"),
    })
    @GetMapping(path = "/calculate/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<HotelDTO> calculate(@PathVariable("hotelId") @Valid Integer hotelId) throws BaseException {
		
		Hotel hotel = brokerServiceClient.hotelDetails(hotelId);
		HotelDTO hotelDTO = priceService.calculateDetails(hotel);
		
		return ResponseEntity.ok(hotelDTO);
	} 
}
