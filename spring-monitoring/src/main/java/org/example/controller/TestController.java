package org.example.controller;

import org.example.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/test")
@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/heap/memory")
    public int memoryTest() {
        return testService.memoryTest();
    }

    @GetMapping("/overload/cpu")
    public int cpuTest() {
        return testService.cpuTest();
    }

    @GetMapping("/db/open/connect")
    public int dbConnectOpenTest() {
        return testService.dbConnectOpenTest();
    }

    @GetMapping("/db/close/connect")
    public int dbConnectCloseTest() {
        return testService.dbConnectCloseTest();
    }
}
