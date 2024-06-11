package com.demo.folder.api;


import com.demo.folder.broker.message.FailureLogPayLoad;
import com.demo.folder.service.TestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/log-summary")
    public void logSummary(@RequestBody String summary) {
        testTypeService.publishLog(summary);
    }

    @PostMapping("/test_failure")
    public void failureLog(@RequestBody FailureLogPayLoad payLoad) {
        testTypeService.publishError(payLoad.toString());
    }

}
