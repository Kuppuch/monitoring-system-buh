package com.kuppuch.web;

import com.kuppuch.model.Work;
import com.kuppuch.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
public class WorkController {

    @Autowired
    private WorkRepository workRepository;

    @GetMapping("/works")
    public String getAllWorks(ModelMap modelMap) {
        Iterable<Work> works = workRepository.findAll();
        modelMap.addAttribute("works", works);
        return "work/works";
    }

    @GetMapping("/works/add")
    public String getWorkAddPage() {
        return "work/addwork";
    }

    @PostMapping("/works/add")
    public String add(@RequestParam String name, @RequestParam double coast, Map<String, Object> model) {
        Work work = new Work(name, coast);
        workRepository.save(work);
        return "work/addwork";
    }

}
