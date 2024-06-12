package com.demo.folder.api;


import com.demo.folder.broker.message.FailureLogPayLoad;
import com.demo.folder.service.TestTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTypeController.class);

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
        LOGGER.info("I am here with: {}", payLoad.toString());
        testTypeService.publishError(payLoad);
    }

}
