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
            String result = ConnectLinuxCommandUtils.connectLinux("3.15.149.72", "root", "1114981272", commandStr);
            return result;
        }
        if (serverId == 1) {
            return "Coming Soon!";
        }
        return "Illegal Input!";

    }
}
