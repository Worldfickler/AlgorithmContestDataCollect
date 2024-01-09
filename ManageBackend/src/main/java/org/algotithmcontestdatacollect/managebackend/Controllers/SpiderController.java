package org.algotithmcontestdatacollect.managebackend.Controllers;


import org.algotithmcontestdatacollect.managebackend.Services.SpiderStatusService;
import org.algotithmcontestdatacollect.managebackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpiderController {
    @Autowired
    SpiderStatusService spiderStatusService;

    @GetMapping("/api/GetAllSpiderStatus")
    public String getAllSpiderStatus() {
        return ResponseUtil.JSONReturn(200,spiderStatusService.getAllCrawlerStatus());
    }

    @GetMapping("/api/GetSpiderLogger")
    public String getSpiderLog(@RequestParam(required = false,defaultValue = "1") int page,@RequestParam(required = false,defaultValue = "10") int pageSize) {
        return ResponseUtil.JSONReturn(200,spiderStatusService.getSpiderLogPage(page - 1,pageSize));
    }
}
