package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.model.ApiResponse;
import com.kuppuch.model.MethodAPI;
import com.kuppuch.model.Payment;
import com.kuppuch.model.Smtp;
import com.kuppuch.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/paydone")
    public String payDone(@RequestParam Payment payment) {
        paymentRepository.save(payment);
        return "pay/pay_done";
    }

    @GetMapping("/pay")
    public String paysPage(ModelMap modelMap) {
        Iterable<Payment> payments = paymentRepository.findAll();
        modelMap.addAttribute("payments", payments);
        return "pay/pay";
    }

    @GetMapping("/payone")
    public String payPage(@RequestParam(required = false) long id, ModelMap modelMap) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        Payment payment = paymentOptional.get();
        modelMap.addAttribute("payment", payment);
        return "pay/payone";
    }

    @PostMapping("/pay")
    public RedirectView pay(@RequestParam String email, @RequestParam double cost, HttpServletRequest request) {
        String address = "http://127.0.0.1:25596/email";
        ApiResponse apiResponse = new ApiResponse();

        Smtp smtp = new Smtp();
        smtp.setEmail(email);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(smtp);

        apiResponse = apiResponse.sendRequest(MethodAPI.POST, jsonInputString, address, request);

        return new RedirectView("pay/paycode");
    }

    @GetMapping("pay/paycode")
    public String paycodePage() {
        return "pay/paycode";
    }

    @PostMapping("/pay/paycode")
    public String paycode(@RequestParam String email, @RequestParam String code, HttpServletRequest request) {
        String address = "http://127.0.0.1:25596/email_code";
        ApiResponse apiResponse = new ApiResponse();

        Smtp smtp = new Smtp();
        smtp.setEmail(email);
        smtp.setCode(code);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(smtp);

        apiResponse = apiResponse.sendRequest(MethodAPI.POST, jsonInputString, address, request);

        System.out.println();


        return "pay/pay_done";
    }
}
