package com.loyalty.service;

import com.loyalty.exception.KioskException;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by kabachko on 6/25/2016.
 */
public class HttpClient {


    private CloseableHttpClient httpclient;
    private HttpClientContext context;
    private BasicCookieStore cookieStore;
    private RequestAddCookies addCookies;
    private HttpHost httpHost;


    private int port;
    private String user;
    private String pass;
    private String host;
    private String scheme;


    private void initClient(){
        addCookies = new RequestAddCookies();
        context = HttpClientContext.create();
        httpHost = new HttpHost(host,port,scheme);
        cookieStore = new BasicCookieStore();
        httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .addInterceptorLast(addCookies)
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .build();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void login() throws IOException, HttpException {
        try {
            HttpUriRequest login = RequestBuilder.post()
                    .setUri(new URI(httpHost.getSchemeName(),null,httpHost.getHostName(), httpHost.getPort(),"/api/authentication",null,null))
                    .addParameter("j_username", user)
                    .addParameter("j_password", pass)
                    .addParameter("remember-me", "true")
                    .addParameter("submit", "Login")
                    .addHeader(HttpHeaders.CONNECTION, "keep-alive")
                    .build();
//            addCookies.process(login,context);
            CloseableHttpResponse loginResponse = httpclient.execute(httpHost,login,context);
            loginResponse.close();
            if (loginResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new HttpException("Login Failed");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public CloseableHttpResponse post(String path, ContentType contentType,  NameValuePair... params) throws IOException {
         try {
            HttpUriRequest post = RequestBuilder.post()
                    .setUri(new URI(httpHost.getSchemeName(), null, httpHost.getHostName(), httpHost.getPort(), path, null, null))
                    .addParameters(params)
                    .addHeader(HttpHeaders.CONNECTION, "keep-alive")
                    .addHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType())
                    .build();
            return httpclient.execute(httpHost, post);
        } catch (URISyntaxException e) {
            throw  new IOException(e);
        }
    }


//    public String post(String path, ContentType contentType,  NameValuePair... params) throws IOException, HttpException {
//        HttpUriRequest post = null;
//        try {
//            post = RequestBuilder.post()
//                    .setUri(new URI(httpHost.getSchemeName(), null, httpHost.getHostName(), httpHost.getPort(), path, null, null))
//                    .addParameters(params)
//                    .addHeader(HttpHeaders.CONNECTION, "keep-alive")
//                    .addHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType())
//                    .build();
//        } catch (URISyntaxException e) {
//            return null;
//        }
////        addCookies.process(post, context);
//        try (CloseableHttpResponse loginRespone = httpclient.execute(httpHost, post)){
//            if (loginRespone.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
//                throw new KioskException(loginRespone.getStatusLine().getStatusCode(),"Not successful POST request");
//            }
//            String result = EntityUtils.toString(loginRespone.getEntity());
//            EntityUtils.consume(loginRespone.getEntity());
//            return result;
//        }
//    }

    public static String getCookieValue(CookieStore cookieStore, String cookieName) {
        String value = null;
        for (Cookie cookie: cookieStore.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                value = cookie.getValue();
            }
        }
        return value;
    }


    public static void main(String[] args) throws IOException, HttpException {
        HttpClient service = new HttpClient();
//        server.host=192.163.242.239
//        server.port=8080
//        server.login=user
//        server.password=user
//        server.scheme=http
//        server.license=iaxlxnxGBRgwOvHRwlLU
        service.setHost("192.163.242.239");
        service.setPort(8080);
        service.setPass("user");
        service.setUser("user");
        service.setScheme("http");
        service.initClient();
        service.login();
//        System.out.println("Post1"+service.post("/public-api/activate", new BasicNameValuePair("kioskLicense","sHZyEsqaZgZyDsrKIHqt")));
//        System.out.println("Post2"+service.post("/public-api/activate", new BasicNameValuePair("kioskLicense","OLTSmnzxYNcpwgfxkwjz11")));
    }
}
