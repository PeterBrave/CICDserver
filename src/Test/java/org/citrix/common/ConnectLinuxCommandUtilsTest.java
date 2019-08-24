package org.citrix.common;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RemoteConnect;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConnectLinuxCommandUtilsTest {


    @Test
    public void loginFailTest() {
        RemoteConnect remoteConnect = new RemoteConnect();
        remoteConnect.setIp("18.217.25.160");
        remoteConnect.setUserName("root");
        remoteConnect.setPassword("123");
        ConnectLinuxCommandUtils.login(remoteConnect);
    }

    @Test
    public void execute() {
        RemoteConnect remoteConnect = new RemoteConnect();
        remoteConnect.setIp("18.217.25.160");
        remoteConnect.setUserName("root");
        remoteConnect.setPassword("zxcvfdsa321");
        ConnectLinuxCommandUtils.login(remoteConnect);
        String commandStr = "#!/bin/bash\n" +
                "echo \"My First Script!";
        String result = ConnectLinuxCommandUtils.execute(commandStr);
        log.info(result);
    }

    @Test
    public void processStdout() {
    }

    @Test
    public void connectLinux() {
    }
}