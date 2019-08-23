package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class ServerControllerTest {

    @Autowired
    private ServerController serverController;

    @Test
    public void runBash() {
        String result = serverController.runBash(2, "pwd");
        Assert.assertNotNull(result);
    }

    @Test
    public void runBashServer1() {
        String result = serverController.runBash(1, "pwd");
        Assert.assertNotNull(result);
    }

    @Test
    public void runBashServer3() {
        String result = serverController.runBash(3, "pwd");
        Assert.assertNotNull(result);
    }

}