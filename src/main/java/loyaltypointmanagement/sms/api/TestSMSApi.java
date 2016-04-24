/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyaltypointmanagement.sms.api;

import java.util.*;
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author surajit
 */
public class TestSMSApi {

    public static final String ACCOUNT_SID = "AC6f0edff72532f2d6969e31dd52ca74d2";
    public static final String AUTH_TOKEN = "57541a7cc331fe7a4b9cd5714db98929";

    public static void main(final String[] args) throws TwilioRestException {

        /*final TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
         final Account mainAccount = client.getAccount();
         final SmsFactory messageFactory = mainAccount.getSmsFactory();
         final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
         messageParams.add(new BasicNameValuePair("To", "+918697425776")); // Replace with a valid phone number
         messageParams.add(new BasicNameValuePair("From", "+12028313299")); // Replace with a valid phone number in your account
         messageParams.add(new BasicNameValuePair("Body", "This is a test message!"));
         messageFactory.create(messageParams);*/
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("From", "+12028313299"));
        params.add(new BasicNameValuePair("To", "+918697425776")); // Replace with a valid phone number
        params.add(new BasicNameValuePair("Body", "This is a test message!"));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        System.out.println(message.getSid());

    }
}
