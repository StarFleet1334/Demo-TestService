package com.demo.folder.api;


import com.demo.folder.service.TestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestTypeController {

    @Autowired
    private TestTypeService testTypeService;

    @GetMapping("/publish-test-type")
    public String publishTestType() {
        testTypeService.publish();
        return "Test type published";
    }

}
