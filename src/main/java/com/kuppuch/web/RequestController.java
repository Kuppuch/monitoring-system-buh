package com.kuppuch.web;

import com.kuppuch.model.Request;
import com.kuppuch.model.Work;
import com.kuppuch.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/requests")
    public String requests(ModelMap modelMap) {
        Iterable<Request> requests = requestRepository.findAll();
        modelMap.addAttribute("requests", requests);
        return "request/requests";
    }

    @PostMapping("/requests")
    public String add(@RequestBody Request request) {
//        Request request = new Request(name, description, user, phone, email);
        requestRepository.save(request);
        return "request/requests";
    }

}
