package com.loyalty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loyalty.dto.CardDTO;
import com.loyalty.dto.KioskDTO;
import com.loyalty.dto.PromotionDTO;
import com.loyalty.exception.KioskException;
import org.apache.http.HttpException;


import java.io.IOException;
import java.util.List;

/**
 * Created by kabachko on 6/28/2016.
 */
public interface KioskService {

    HttpClient getHttpClient();

    ObjectMapper getMapper();

    KioskDTO activate() throws IOException;

    CardDTO cardDetails(String cardnumber) throws KioskException, IOException ;

    CardDTO checkIn(String cardnumber) throws KioskException, IOException;

    List<PromotionDTO> getPromotions() throws KioskException, IOException;

}
