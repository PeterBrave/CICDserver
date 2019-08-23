package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.bean.RespBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback
public class projectControllerTest {

    @Autowired
    projectController projectController;

    @Test
    public void addProjectOKTest() {
        Boolean result = projectController.addProject("testProject", "tester", "Java", 0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void getAllProject() {
        projectController.addProject("testProject1", "tester", "Java", 0);
        projectController.addProject("testProject2", "tester", "Java", 0);
        projectController.updateProject("testProject1", 1, true);
        projectController.updateProject("testProject2", 1, true);
        RespBean result = projectController.getAllProject("tester");
        Assert.assertNotNull(result);
    }

    @Test
    public void getAllProjectError() {
        try {
            projectController.getAllProject("tester_null");
        }catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void getProjectDetail() {
        try {
            projectController.getProjectDetail("test_null_exist");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
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
    public void updateProjectNotFound() {
        try {
            projectController.updateProject("testProject", 1, true);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void updateProjectWithoutType() {
        projectController.addProject("testProject", "tester", "Java", 0);
        projectController.updateProject("testProject", 0, true);
        RespBean result = projectController.getProjectDetail("testProject");
        CICDProject project = (CICDProject) result.getObj();
        Assert.assertEquals(0, project.getType());
    }

    @Test
    public void updateProjectWithoutEnable() {
        projectController.addProject("testProject", "tester", "Java", 0);
        projectController.updateProject("testProject", 1, false);
        RespBean result = projectController.getProjectDetail("testProject");
        CICDProject project = (CICDProject) result.getObj();
        Assert.assertEquals(false, project.isEnabled());
    }

    @Test
    public void deleteProject() {
        projectController.addProject("testProject", "tester", "Java", 0);
        Boolean result = projectController.deleteProject("testProject");
        Assert.assertEquals(true, result);
    }

    @Test
    public void deleteProjectError() {
        try {
            projectController.deleteProject("testProject");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }



}