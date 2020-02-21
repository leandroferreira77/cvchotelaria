package br.com.cvc.corp.hotelaria.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import br.com.cvc.corp.hotelaria.dto.HotelDTO;
import br.com.cvc.corp.hotelaria.dto.PriceDetailDTO;
import br.com.cvc.corp.hotelaria.dto.RoomsDTO;
import br.com.cvc.corp.hotelaria.model.Hotel;
import br.com.cvc.corp.hotelaria.model.Travel;

@Service
public class PriceService {

	private static final double COMMISSION = 0.70;
	private Integer adults = 1;
	private Integer childs = 1;
	private Long diffDays = 1l;
	private Travel travel;
	
	public PriceService() {
	}

	public PriceService(Travel pTravel) {
		travel = pTravel;
		
		validateObjectNotNull(pTravel);
			
	}

    private void validateObjectNotNull(Travel pTravel) {
        
        
        if (nonNull(pTravel.getAdults())) {
            adults = pTravel.getAdults();
        }

        if (nonNull(pTravel.getChilds())) {
            childs = pTravel.getChilds();
        }

        if (nonNull(pTravel.getCheckin()) && nonNull(pTravel.getCheckout())) {
            
            LocalDate today = LocalDate.now();
            
            if(pTravel.getCheckin().compareTo(today) < 0){
                System.out.println("data hoje com inicial tudo ok!");
            }
            
            if(pTravel.getCheckin().compareTo(pTravel.getCheckout()) < 0){
                System.out.println("data inicial com data final tudo ok!");
            } 
            
            diffDays = ChronoUnit.DAYS.between(pTravel.getCheckin(), pTravel.getCheckout());
        }
    }

	public List<HotelDTO> calculateAvails() {
		List<HotelDTO> hotels = new ArrayList<HotelDTO>();

		travel.getHotels().forEach(hotel -> {
			hotels.add(calculateDetails(hotel));
		});

		return hotels;
	}

	public HotelDTO calculateDetails(Hotel hotel) {
	    
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setId(hotel.getId());
		hotelDTO.setCityName(hotel.getCityName());

		List<RoomsDTO> rooms = new ArrayList<RoomsDTO>();
		hotel.getRooms().forEach(room -> {
			RoomsDTO roomsDTO = new RoomsDTO();
			roomsDTO.setRoomId(room.getRoomID());
			roomsDTO.setCategoryName(room.getCategoryName());

			PriceDetailDTO priceDetailDTO = new PriceDetailDTO();
			priceDetailDTO.setPricePerDayAdult(new BigDecimal(room.getPrice().getAdult().doubleValue() * diffDays)
					.setScale(2, RoundingMode.HALF_EVEN));
			priceDetailDTO.setPricePerDayChild(new BigDecimal(room.getPrice().getChild().doubleValue() * diffDays)
					.setScale(2, RoundingMode.HALF_EVEN));

			roomsDTO.setPriceDetail(priceDetailDTO);

			BigDecimal priceKickbackAdult = new BigDecimal(
					(priceDetailDTO.getPricePerDayAdult().doubleValue() * adults) / COMMISSION);
			BigDecimal priceKickbackChilds = new BigDecimal(
					(priceDetailDTO.getPricePerDayChild().doubleValue() * childs) / COMMISSION);

			roomsDTO.setTotalPrice(priceKickbackAdult.add(priceKickbackChilds).setScale(2, RoundingMode.HALF_EVEN));

			rooms.add(roomsDTO);
		});

		hotelDTO.setRooms(rooms);

		return hotelDTO;
	}
}
