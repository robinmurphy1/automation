package za.co.pifarm.automate.powerchecker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.services.power.PowerChecker;

@RestController
public class HomeController {

    @Autowired
    PowerChecker powerChecker;

    @GetMapping(path = "/power/{location}/{adhoc}")
    public ResponseEntity<PowerStatus> checkPower(@PathVariable("location") RemoteLocation location, @PathVariable("adhoc") Boolean adhoc){

       return ResponseEntity.ok(powerChecker.checkPower(location, adhoc));
    }


}
