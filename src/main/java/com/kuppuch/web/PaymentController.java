package com.kuppuch.web;

import com.google.gson.Gson;
import com.kuppuch.model.*;
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

    @PostMapping("/payone")
    public String pay(@RequestParam int id, @RequestParam String email, @RequestParam double coast,
                      HttpServletRequest request, ModelMap modelMap) {
        String address = "http://127.0.0.1:25596/email";
        ApiResponse apiResponse = new ApiResponse();
        Smtp smtp = new Smtp();
        smtp.setEmail(email);
        Gson gson = new Gson();
        String jsonInputString = gson.toJson(smtp);
        apiResponse = apiResponse.sendRequest(MethodAPI.POST, jsonInputString, address, request);

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("coast", coast);
        return "pay/paycode";
    }

    @GetMapping("/paycode")
    public String paycodePage() {
        return "pay/paycode";
    }

    @PostMapping("/paycode")
    public String paycode(@RequestParam String email, @RequestParam long id, @RequestParam double coast,
                          @RequestParam String code, HttpServletRequest request, ModelMap modelMap) {
        String address = "http://127.0.0.1:25596/email_code";
        ApiResponse apiResponse = new ApiResponse();

        Smtp smtp = new Smtp();
        smtp.setEmail(email);
        smtp.setCode(code);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(smtp);

        apiResponse = apiResponse.sendRequest(MethodAPI.POST, jsonInputString, address, request);
        Valid valid = gson.fromJson(apiResponse.getSb().toString(), Valid.class);

        if (valid.isValid()) {
            Optional<Payment> paymentOptional = paymentRepository.findById(id);
            Payment payment = paymentOptional.get();
            payment.setId(id);
            if (coast + payment.getPaid() >= payment.getFullCost())
                payment.setStatus("Оплачено");
            payment.setIterationId(payment.getIterationId());
            payment.setPaid(coast + payment.getPaid());
            payment.setFullCost(payment.getFullCost());
            paymentRepository.save(payment);

            address = "http://127.0.0.1:25595/api/budgets/?id=" + payment.getIterationId() + "&status=Work";
            apiResponse = apiResponse.sendRequest(MethodAPI.POST, "", address, request);
            ErrorResponse er = gson.fromJson(apiResponse.getSb().toString(), ErrorResponse.class);
            if (er.getCode() != 200) {
                modelMap.addAttribute("error", "Ошибка");
            }

            return "pay/pay_done";
        }

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("coast", coast);
        return "pay/paycode";
    }

    @GetMapping("/pay_done")
    public String payDone() {
        return "pay/pay_done";
    }
}
