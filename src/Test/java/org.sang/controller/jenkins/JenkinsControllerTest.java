package org.sang.controller.jenkins;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class JenkinsControllerTest {

    @Test
    public void buildProject() {
        Assert.assertEquals("success", "aaa", "aaa");
    }

    @Test
    public void createJob() {
        Assert.assertEquals("success", "aaa", "aaa");
    }

    @Test
    public void getConsoleOutput() {
        Assert.assertEquals("success", "aaa", "aaa");

    }
}