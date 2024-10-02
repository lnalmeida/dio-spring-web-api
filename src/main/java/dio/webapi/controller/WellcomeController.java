package dio.webapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wellcome")
public class WellcomeController {

    @GetMapping
    public String wellcome(){
        return "<h1> Wellcome to my system </h1>" + "<br> <p> Click <a href='/home'>HERE</a> to get Home page</p>";
    }

    @GetMapping("/home")
    public String home(){
        return "<h1> Wellcome to Home of my system </h1>";
    }

    @GetMapping("/admin")
    public String onlyAdmin() {
        return "<h1> Only Admins can access this page </h1>";
    }

    @GetMapping("/user")
    public String onlyUser() {
        return "<h1> Only Users can access this page </h1>";
    }

}
