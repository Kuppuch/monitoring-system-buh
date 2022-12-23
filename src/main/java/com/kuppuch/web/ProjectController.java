package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.interseptor.RequestInterceptor;
import com.kuppuch.model.*;
import com.lowagie.text.pdf.BaseFont;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProjectController {

    @Value("${baseurl}")
    String baseurl;

    @Value("${filepath}")
    private String resourcePath;

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
                                @RequestParam boolean pub, @RequestParam String iteration, ModelMap modelMap, HttpServletRequest request) {
        Project project = new Project(name, description, Integer.parseInt(manager), 1, pub);
        User[] users = getApiUser(request);
        modelMap.addAttribute("users", users);
        return "project/addproject";
    }

    public User[] getApiUser(HttpServletRequest request) {
        String address = "http://localhost:25595/api/users/";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        Gson gson = new Gson();

        return gson.fromJson(apiResponse.getSb().toString(), User[].class);
    }

    @GetMapping("/timespents")
    public String getIterationTimespent(@RequestParam String id, @RequestParam(required = false) boolean collapse,
                                        ModelMap modelMap, HttpServletRequest request) {
        String address = "http://127.0.0.1:25595/budgets/" + id + "/timespent";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        Gson gson = new Gson();
        TimespentReport[] timespentReports = gson.fromJson(apiResponse.getSb().toString(), TimespentReport[].class);

        address = "http://127.0.0.1:25595/api/budgets?id=" + id;
        apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(address, request);
        gson = new Gson();
        Iteration iteration = gson.fromJson(apiResponse.getSb().toString(), Iteration.class);

        String reportFile = getFile(timespentReports, iteration.getName());

        if (collapse) {
            Timespent ts = new Timespent();
            TimespentReport[] timespentReportsCollapse = ts.collapse(timespentReports);
            modelMap.addAttribute("timespents", timespentReportsCollapse);
            modelMap.addAttribute("collapse", true);
        } else {
            modelMap.addAttribute("timespents", timespentReports);
            modelMap.addAttribute("collapse", false);
        }
        modelMap.addAttribute("resourcePath", resourcePath);
        modelMap.addAttribute("reportFile", reportFile);

        return "project/iterationtimespent";
    }

    public String getFile(TimespentReport[] timespentReports, String iterationName) {

        Timespent ts = new Timespent();
        TimespentReport[] timespentReportsCollapse = ts.collapse(timespentReports);

        File inputHTML = new File("./src/main/resources/doc_sample/report.html");
        Long uidfile = 0L;
        try {
            Document document = Jsoup.parse(inputHTML, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

            Element mainDiv = document.body().getElementById("main-div");
            mainDiv.prepend("<h1>Отчёт о проведённой работе за итерацию " + iterationName + "</h1>");

            Element table = document.body().getElementById("timespent");

            for (TimespentReport t : timespentReportsCollapse) {
                table.append("<tr>\n" +
                        "            <td>" + t.getIssueName() + "</td>\n" +
                        "            <td>" + t.getRole() + "</td>\n" +
                        "            <td>" + t.getSpent() + "</td>\n" +
                        "        </tr>");
            }

            uidfile = Instant.now().getEpochSecond();
            OutputStream outputStream = new FileOutputStream(resourcePath + "/report" + uidfile + ".pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("./src/main/resources/static/Roboto-Thin.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.toString());
            renderer.layout();
            renderer.createPDF(outputStream);

        } catch (IOException e) {
            System.out.println(e.toString());
        }


        return "report"+uidfile+".pdf";
    }


}
