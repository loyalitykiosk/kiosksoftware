package com.loyalty.dto;

/**
 * Created by kabachko on 6/18/2016.
 */
public class CheckInDTO {

    private final String kioskLicense;
    private final String cardNumber;

    public CheckInDTO(String kioskLicense, String cardNumber) {
        this.kioskLicense = kioskLicense;
        this.cardNumber = cardNumber;
    }

    public String getKioskLicense() {
        return kioskLicense;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
