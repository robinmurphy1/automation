package za.co.pifarm.automate.powerchecker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(path = "/power")
    public ResponseEntity<Boolean> checkPower(){

       return ResponseEntity.ok(null);
    }


}
