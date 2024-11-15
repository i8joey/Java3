package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.D387SampleCodeApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    @GetMapping
    public String getMessages() {
        return String.join("\n", D387SampleCodeApplication.messages);

    }
}
