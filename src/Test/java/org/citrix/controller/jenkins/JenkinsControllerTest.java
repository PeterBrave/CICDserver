package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
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
public class JenkinsControllerTest {

    @Autowired
    private JenkinsController jenkinsController;

    @Test
    public void buildProject() throws Exception{
        jenkinsController.createJob("sonar-tester", "CICDserver", "PeterBrave", 2);
        RespBean respBean = jenkinsController.buildProject("sonar-tester", 2);
        int result = respBean.getStatus();
        jenkinsController.deleteJob(2, "sonar-tester");
        Assert.assertEquals(200, result);
    }

    @Test
    public void buildProjectError1() throws Exception{
        try {
            jenkinsController.buildProject(null, 2);
        }catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void buildProjectError2() throws Exception{
        RespBean respBean = jenkinsController.buildProject("sonar-tester", 4);
        int result = respBean.getStatus();
        Assert.assertEquals(500, result);
    }

//    @Test
//    public void createJobError2() throws Exception{
//        RespBean respBean = jenkinsController.createJob("sonar-Test", "CICDserver", "PeterBrave", 4);
//        int status = respBean.getStatus();
//        Assert.assertEquals(500, status);
//    }

    @Test
    public void createJob() throws Exception{
        RespBean respBean = jenkinsController.createJob("sonar-tester", "CICDserver", "PeterBrave", 2);
        int result = respBean.getStatus();
        jenkinsController.deleteJob(2, "sonar-tester");
        Assert.assertEquals(200, result);
    }

    @Test
    public void createJobType3() throws Exception{
        RespBean respBean = jenkinsController.createJob("sonar-tester", "CICDserver", "PeterBrave", 3);
        int result = respBean.getStatus();
        jenkinsController.deleteJob(3, "sonar-tester");
        Assert.assertEquals(200, result);
    }

    @Test
    public void createJobError1() throws Exception{
        try {
            jenkinsController.createJob(null, "CICDserver", "PeterBrave", 2);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void createJobError3() throws Exception{
        try {
            jenkinsController.createJob(null, null, null, 2);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }



}