package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.model.Project;
import com.kuppuch.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
public class ProjectController {

    @Value("${baseurl}")
    String baseurl;
    @GetMapping("/projects")
    public String projects(ModelMap modelMap, HttpServletRequest request) {
        String address = baseurl+"/api/projects";
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();
    public String projects(ModelMap modelMap) {
        String address = baseurl+"/project/";
        StringBuilder sb = sendRequest(address);
        Gson gson = new Gson();

        Project[] projects = gson.fromJson(sb.toString(), Project[].class);
        modelMap.addAttribute("projects", projects);

        return "project/projects";
    }

    @GetMapping("/projects/add")
    public String getProjectAddPage(ModelMap modelMap) {

        User[] users = getApiUser();
        modelMap.addAttribute("users", users);
        return "project/addproject";
    }

    @PostMapping("/projects/add")
    public String createProject(@RequestParam String name, @RequestParam String description, @RequestParam String manager, @RequestParam boolean pub, ModelMap modelMap) {
        Project project = new Project(name, description, Integer.parseInt(manager), 1, pub);
        User[] users = getApiUser();
        modelMap.addAttribute("users", users);
        return "project/addproject";
    }

    public User[] getApiUser() {
        String address = "http://localhost:25595/api/user/";
        StringBuilder sb = sendRequest(address);
        Gson gson = new Gson();

        return gson.fromJson(sb.toString(), User[].class);
    }

    public StringBuilder sendRequest(String address) {
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();

        try {
            con = (HttpURLConnection) new URL(address).openConnection();
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
        return sb;
    }


}
