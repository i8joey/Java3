package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.D387SampleCodeApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/time")
public class TimeController {
    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public List<String> getTime() {
        return D387SampleCodeApplication.timeZones;
    }
}
