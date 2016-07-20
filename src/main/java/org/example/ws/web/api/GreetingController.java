package org.example.ws.web.api;

import org.example.ws.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GreetingController {

    public ResponseEntity<Collection<Greeting>> getGreetings() {
        return null;
    }
}
