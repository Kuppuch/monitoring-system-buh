package com.kuppuch;

import com.google.gson.Gson;
import com.kuppuch.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @PostMapping ("/login")
    public String login(@RequestParam String login, @RequestParam String password, ModelMap modelMap) {
        String address = baseurl+"/login";
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();

        Map params = new HashMap<>();
        params.put("v", "dQw4w9WgXcQ");

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
            con.setRequestMethod("POST");
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
        Project[] projects = gson.fromJson(sb.toString(), Project[].class);
        return "login";
    }
}
