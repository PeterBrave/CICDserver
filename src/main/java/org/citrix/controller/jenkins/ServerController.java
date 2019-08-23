package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.common.ConnectLinuxCommandUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kavin
 * @date 2019-07-18 09:12
 */
@RestController
@RequestMapping("/server")
@Slf4j
public class ServerController {

    @PostMapping("/run")
    public String runBash(@RequestParam(value = "serverId") int serverId,
                          @RequestParam(value = "bashContent") String bashContent) {
        log.info("serverId = " + serverId + ", bashContent = " + bashContent);
        String commandStr = bashContent;

        if (serverId == 2) {
            return ConnectLinuxCommandUtils.connectLinux("18.217.25.160", "root", "zxcvfdsa321", commandStr);
        }
        if (serverId == 1) {
            return "Coming Soon!";
        }
        return "Illegal Input!";
    }
}
