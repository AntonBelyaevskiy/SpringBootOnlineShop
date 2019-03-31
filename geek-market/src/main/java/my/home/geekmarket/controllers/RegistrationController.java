package my.home.geekmarket.controllers;

import my.home.geekmarket.entites.SystemUser;
import my.home.geekmarket.entites.User;
import my.home.geekmarket.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser, BindingResult bindingResult, Model model) {
        String userName = systemUser.getUserName();
        LOGGER.debug("Processing registration form for: " + userName);
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "User with current username already exists");
            LOGGER.debug("User name already exists.");
            return "registration-form";
        }
        userService.save(systemUser);
        LOGGER.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }

}
