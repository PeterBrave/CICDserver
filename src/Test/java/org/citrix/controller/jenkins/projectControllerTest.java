package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.bean.RespBean;
import org.citrix.mapper.CICDProjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class projectControllerTest {

    @Autowired
    CICDProjectMapper cicdProjectMapper;
    @Autowired
    projectController projectController;

    @Test
    public void addProjectOKTest() {
        Boolean result = projectController.addProject("testProject", "tester", "Java", 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void getAllProject() {
        projectController.addProject("testProject", "tester", "Java", 0);
        RespBean result = projectController.getAllProject("tester");
        Assert.assertNotNull(result);
    }

    @Test
    public void updateProject() {
        projectController.addProject("testProject", "tester", "Java", 0);
        projectController.updateProject("testProject", 1, true);
        RespBean result = projectController.getProjectDetail("testProject");
        CICDProject project = (CICDProject) result.getObj();
        Assert.assertEquals(1, project.getType());
    }

    @Test
    public void deleteProject() {
        projectController.addProject("testProject", "tester", "Java", 0);
        Boolean result = projectController.deleteProject("testProject");
        Assert.assertEquals(true, result);
    }

}