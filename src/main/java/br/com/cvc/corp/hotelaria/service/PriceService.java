package br.com.cvc.corp.hotelaria.service;

import java.util.List;

import br.com.cvc.corp.hotelaria.dto.HotelDTO;
import br.com.cvc.corp.hotelaria.model.Hotel;

public interface PriceService {

    List<HotelDTO> calculateAvails();

    HotelDTO calculateDetails(final Hotel hotel);

}
