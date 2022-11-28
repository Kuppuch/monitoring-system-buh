package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class ProjectController {
    @GetMapping("/projects")
    public String projects(ModelMap modelMap) {
        String address = "http://localhost:25595/api/project/";
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            System.out.println();
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

        } catch (IOException e){
            System.out.println(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        Project[] projects = gson.fromJson(sb.toString(), Project[].class);
        modelMap.addAttribute("projects", projects);

        return "projects";
    }

}
