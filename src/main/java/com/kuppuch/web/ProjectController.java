package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.interseptor.RequestInterceptor;
import com.kuppuch.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Controller
public class ProjectController {

    @Value("${baseurl}")
    String baseurl;
    public static Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @GetMapping("/projects")
    public String projects(@RequestParam(required = false) String id, ModelMap modelMap, HttpServletRequest request) {

        if (id != null) {
            String address = "http://localhost:25595/budgets?project_id="+id;
            ApiResponse apiResponse = new ApiResponse();
            apiResponse = apiResponse.sendRequest(address, request);
            Gson gson = new Gson();
            Iteration[] iterations = gson.fromJson(apiResponse.getSb().toString(), Iteration[].class);
            modelMap.addAttribute("iterations", iterations);
            return "project/iterations";
        }

        String address = baseurl + "/api/projects/";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        Gson gson = new Gson();
        if (!address.equals(apiResponse.getResponseAddress())) {
            return "login";
        }

        Project[] projects = gson.fromJson(apiResponse.getSb().toString(), Project[].class);
        modelMap.addAttribute("projects", projects);

        return "project/projects";
    }

    @GetMapping("/projects/add")
    public String getProjectAddPage(ModelMap modelMap, HttpServletRequest request) {

        User[] users = getApiUser(request);
        modelMap.addAttribute("users", users);
        return "project/addproject";
    }

    @PostMapping("/projects/add")
    public String createProject(@RequestParam String name, @RequestParam String description, @RequestParam String manager,
                                @RequestParam boolean pub, ModelMap modelMap, HttpServletRequest request) {
        Project project = new Project(name, description, Integer.parseInt(manager), 1, pub);
        User[] users = getApiUser(request);
        modelMap.addAttribute("users", users);
        return "project/addproject";
    }

    public User[] getApiUser(HttpServletRequest request) {
        String address = "http://localhost:25595/api/user/";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        Gson gson = new Gson();

        return gson.fromJson(apiResponse.getSb().toString(), User[].class);
    }

    @GetMapping("/timespents")
    public String getIterationTimespent(@RequestParam String id, ModelMap modelMap, HttpServletRequest request) {
        String address = "http://127.0.0.1:25595/budgets/" + id + "/timespent";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        Gson gson = new Gson();
        TimespentReport[] timespentReports = gson.fromJson(apiResponse.getSb().toString(), TimespentReport[].class);
        modelMap.addAttribute("timespents", timespentReports);

        return "project/iterationtimespent";
    }


}
