package br.com.cvc.corp.hotelaria.service.impl;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.cvc.corp.hotelaria.dto.HotelDTO;
import br.com.cvc.corp.hotelaria.dto.PriceDetailDTO;
import br.com.cvc.corp.hotelaria.dto.RoomsDTO;
import br.com.cvc.corp.hotelaria.exceptions.ValidationException;
import br.com.cvc.corp.hotelaria.model.Hotel;
import br.com.cvc.corp.hotelaria.model.Travel;
import br.com.cvc.corp.hotelaria.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceImpl.class);

    private static final String MSG_EXCEPTION_REQUERID_ADULTS = "Adults field not menor zero";
    private static final String MSG_EXCEPTION_REQUERID_CHILDS = "Childs field not menor zero";
    private static final String MSG_EXCEPTION_REQUERID_CHECKIN = "Checkin field invalide";
    private static final String MSG_EXCEPTION_REQUERID_CHECKOUT = "Checkout field invalide";
    private static final String MSG_EXCEPTION_CHECKIN_BEFORE_TODAY = "Checkin is before Today";
    private static final String MSG_EXCEPTION_CHECKIN_AFTER_CHECKOUT = "Checkin is after Checkout";
    private static final double COMMISSION = 0.70;
    
    private Integer adults = 1;
    private Integer childs = 1;
    private Long diffDays = 1l;
    private Travel travel;
    
    public PriceServiceImpl() {
    }

    public PriceServiceImpl(Travel pTravel) {
        travel = pTravel;
        validateObjectNotNull(pTravel);
    }

    @Override
    public List<HotelDTO> calculateAvails() {
        LOGGER.info("Begin of calculateAvails Total Prices");
        
        List<HotelDTO> hotels = new ArrayList<HotelDTO>();

        travel.getHotels().forEach(hotel -> {
            hotels.add(calculateDetails(hotel));
        });
        
        LOGGER.info("Final of calculateAvails Total Prices");

        return hotels;
    }

    @Override
    public HotelDTO calculateDetails(Hotel hotel) {
        
        LOGGER.info("Begin of calculateDetails Total Prices");
        
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
        
        LOGGER.info("Final of calculateDetails Total Prices");

        return hotelDTO;
    }
    
    private void validateObjectNotNull(Travel pTravel) {

        if (isNull(pTravel.getAdults()) || pTravel.getAdults() <= 0) {
            throw new ValidationException(MSG_EXCEPTION_REQUERID_ADULTS);
        }

        if (isNull(pTravel.getChilds()) || pTravel.getChilds() <= 0 ) {
            throw new ValidationException(MSG_EXCEPTION_REQUERID_CHILDS);
        }

        if (isNull(pTravel.getCheckin())) {
            throw new ValidationException(MSG_EXCEPTION_REQUERID_CHECKIN);
        }

        if (isNull(pTravel.getCheckout())) {
            throw new ValidationException(MSG_EXCEPTION_REQUERID_CHECKOUT);
        }

        adults = pTravel.getAdults();
        childs = pTravel.getChilds();

        validateCheckinIsBefore(pTravel.getCheckin(), pTravel.getCheckout());

        diffDays = ChronoUnit.DAYS.between(pTravel.getCheckin(), pTravel.getCheckout());

    }

    private void validateCheckinIsBefore(LocalDate checkin, LocalDate checkout) {
        LocalDate today = LocalDate.now();

        if (checkin.isBefore(today)) {
            throw new ValidationException(MSG_EXCEPTION_CHECKIN_BEFORE_TODAY);
        }

        if (checkin.isAfter(checkout)) {
            throw new ValidationException(MSG_EXCEPTION_CHECKIN_AFTER_CHECKOUT);
        }
    }
}
