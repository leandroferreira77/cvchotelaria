package br.com.cvc.corp.hotelaria.model;

import java.util.Objects;

public class Rooms {

	private Integer roomID;
	private String categoryName;
	private Price price;

	public Integer getRoomID() {
		return roomID;
	}

	public void setRoomID(Integer roomID) {
		this.roomID = roomID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
	@Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Rooms rooms = (Rooms) o;
        return Objects.equals(roomID, rooms.roomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomID);
    }

}
