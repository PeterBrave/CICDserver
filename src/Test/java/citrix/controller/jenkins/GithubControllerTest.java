package org.citrix.controller.jenkins;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GithubControllerTest {


    @Test
    public void getContent() {

        Assert.assertEquals("success", "aaa", "aaa");
    }
}
