package com.loyalty.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.loyalty.dto.CardDTO;
import com.loyalty.dto.CheckInDTO;
import com.loyalty.dto.KioskDTO;
import com.loyalty.dto.PromotionDTO;
import com.loyalty.exception.KioskException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kabachko on 6/26/2016.
 */
public class RemoteService extends AbstractService {

    private String license;

    private KioskDTO kioskDTO;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public KioskDTO activate() throws IOException, KioskException {
        try (CloseableHttpResponse result = getHttpClient().post("/public-api/activate", ContentType.APPLICATION_FORM_URLENCODED, new BasicNameValuePair("kioskLicense",license))){
            if (result.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new KioskException(result.getStatusLine().getStatusCode(),"Can't activate Kiosk with provided license code: "+getLicense());
            }
            kioskDTO = getMapper().readValue(EntityUtils.toString(result.getEntity()),KioskDTO.class);
            return kioskDTO;
        }
    }

    public CardDTO cardDetails(String cardNumber) throws IOException, KioskException {
        try (CloseableHttpResponse result = getHttpClient().post("/public-api/card",ContentType.APPLICATION_FORM_URLENCODED, new BasicNameValuePair("number",cardNumber))){
            if (result.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new KioskException(result.getStatusLine().getStatusCode(),"Can't find card by card number: "+cardNumber);
            }
            final CardDTO cardDTO = getMapper().readValue(EntityUtils.toString(result.getEntity()),CardDTO.class);
            return  cardDTO;
        }
    }

    @Override
    public CardDTO checkIn(String cardNumber) throws IOException, KioskException {
        try (CloseableHttpResponse result = getHttpClient().post("/public-api/checkin",ContentType.APPLICATION_FORM_URLENCODED,new BasicNameValuePair("kioskLicense",getLicense()),new BasicNameValuePair("cardNumber",cardNumber))){
            if (result.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new KioskException(result.getStatusLine().getStatusCode(),"Can't find card by card number: "+cardNumber);
            }
            final CardDTO cardDTO = getMapper().readValue(EntityUtils.toString(result.getEntity()),CardDTO.class);
            return  cardDTO;
        }
    }


    @Override
    public List<PromotionDTO> getPromotions() throws KioskException, IOException {
        try (CloseableHttpResponse result = getHttpClient().post("/public-api/promotions",ContentType.APPLICATION_FORM_URLENCODED)){
            if (result.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new KioskException(result.getStatusLine().getStatusCode(),"Can't find receive promotions");
            }
            final List<PromotionDTO> promotionDTOs = getMapper().readValue(EntityUtils.toString(result.getEntity()),getMapper().getTypeFactory().constructCollectionType(List.class, PromotionDTO.class));
            return  promotionDTOs;
        }
    }
}
