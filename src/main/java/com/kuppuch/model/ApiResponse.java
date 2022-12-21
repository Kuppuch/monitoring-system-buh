package com.kuppuch.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class ApiResponse {
    private StringBuilder sb;
    private String ResponseAddress;

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    public String getResponseAddress() {
        return ResponseAddress;
    }

    public void setResponseAddress(String responseAddress) {
        ResponseAddress = responseAddress;
    }

    public ApiResponse sendRequest(String address, HttpServletRequest request) {
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();

        Cookie[] cookies = request.getCookies();
        String token = "";

        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "auth")) {
                token = cookie.getValue();
            }
        }

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
            con.setRequestProperty("Cookie", "auth=" + token + ";");
            con.setRequestMethod("GET");
            con.connect();

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } else {
                System.out.println("failed: " + con.getResponseCode() + " error " + con.getResponseMessage());
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSb(sb);
        apiResponse.setResponseAddress(con.getURL().toString());
        return apiResponse;
    }
}
