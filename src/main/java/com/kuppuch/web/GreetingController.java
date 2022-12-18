package com.kuppuch.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kuppuch.model.Auth;
import com.kuppuch.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GreetingController {

    @Value("${baseurl}")
    String baseurl;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String login, @RequestParam String password, HttpServletResponse response, RedirectAttributes attributes) {
        String address = baseurl + "/login";
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();

        Auth auth = new Auth();
        auth.setLogin(login);
        auth.setPassword(password);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(auth);

        String token = "";

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder responseBody = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseBody.append(responseLine.trim());
                }
                JsonObject jsonObject = gson.fromJson( responseBody.toString(), JsonObject.class);
                token = jsonObject.get("Authorization").getAsString();
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        if (token.length() > 1) {
            Cookie cookie = new Cookie("auth", token);
            response.addCookie(cookie);
            return new RedirectView("/");
        }
        return new RedirectView("login");
    }
}
