package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.mapper.CICDProjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class projectControllerTest {

    @Autowired
    CICDProjectMapper cicdProjectMapper;

    @Test
    @Transactional
    public void addProject() {
        CICDProject project = new CICDProject();
        project.setName("testProject");
        project.setAuthor("tester");
        project.setLanguage("Java");
        project.setType(0);
        project.setEnabled(false);
        int result = cicdProjectMapper.addCICDProject(project);
        Assert.assertNotNull(result);
    }

    @Test
    public void getAllProjecct() {
        List<CICDProject> allProjects = cicdProjectMapper.getCICDProjectByAuthor("tester");
        log.info("allProjectSize = " + allProjects.size());
        Assert.assertEquals(0, allProjects.size());
    }

    @Test
    public void updateProject() {
        int result = 0;
        CICDProject project = cicdProjectMapper.getCICDProjectByName("testProject");
        project.setType(1);
        project.setEnabled(true);
        cicdProjectMapper.updateCICDProject(project);
        CICDProject project_after = cicdProjectMapper.getCICDProjectByName("testProject");
        if (project_after.getType() == 1 && project_after.isEnabled() == true) {
            result = 1;
        }
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getProjectDetail() {
    }

    @Test
    public void deleteProject() {
    }




}