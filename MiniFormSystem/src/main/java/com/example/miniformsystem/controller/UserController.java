package com.example.miniformsystem.controller;

import javax.validation.Valid;

import com.example.miniformsystem.entity.Form;
import com.example.miniformsystem.entity.User;
import com.example.miniformsystem.repository.FormRepository;
import com.example.miniformsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FormRepository formRepository;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult) {
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
            return "login";
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            return "/user/home";
        }
    }

    @GetMapping("/user/form") // Через thymeleaf можно вывести все анкеты
    public List<Form> userHome() {
        return formRepository.findAll();
    }

    @GetMapping("/admin/form") //Аналогично для админа, но на странице можно добавить форму для добавления анкет
    public String adminHome(Model model) {
        model.addAttribute("formList", formRepository.findAll());
        return "home_page";
    }

    @PostMapping("/admin/form") //Для отправки формы
    public int addForm(Form form) {
        Form newForm = formRepository.save(form);
        return newForm.getId();
    }

    @PostMapping("/admin/form/{id}") //Можно удалить созданную анкету
    public void deleteForm(@PathVariable int id, Model model) {
        for (Form form : formRepository.findAll()) {
            if (form.getId() == id) {
                 formRepository.deleteById(id);
            } else {
                model.addAttribute("error", String.format("Doesn't exist '%s' id", id));
            }
        }
    }

    @GetMapping("/admin/form/{id}") //Если анкет много -> найти нужную по id
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<Form> optionalForm = formRepository.findById(id);
        if (optionalForm.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalForm.get(), HttpStatus.OK);
    }


}