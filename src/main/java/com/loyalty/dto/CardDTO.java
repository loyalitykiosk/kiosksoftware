package com.loyalty.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Card entity.
 */
public class CardDTO implements Serializable {

    private Long id;

    private String number;

    private String ownerName;

    private String ownerSurname;

    private String smsNumber;

    private String email;

    private String status;

    private String type;

    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }
    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CardDTO cardDTO = (CardDTO) o;

        if ( ! Objects.equals(id, cardDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", ownerName='" + ownerName + "'" +
            ", ownerSurname='" + ownerSurname + "'" +
            ", smsNumber='" + smsNumber + "'" +
            ", email='" + email + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
