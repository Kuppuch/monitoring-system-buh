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
    public String payPage(@RequestParam int id) {

        return "pay/pay";
    }

    @PostMapping("/timespents")
    public RedirectView submitPostPayment(@RequestParam String id, /*@RequestParam(required = false) boolean collapse,*/
                                    ModelMap modelMap, HttpServletRequest request) {
        String address = "http://127.0.0.1:25595/api/budgets/?id=" + id + "&status=Close";
        ApiResponse apiResponse = new ApiResponse();
        apiResponse = apiResponse.sendRequest(MethodAPI.POST, "", address, request);
        Gson gson = new Gson();
        Payment payment = new Payment();
        payment.setStatus("Не оплачен");
        paymentRepository.save(payment);
        modelMap.addAttribute("reportFile", payment.getId());
        return new RedirectView("pay/pay");
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
