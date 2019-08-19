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
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class projectControllerTest {

    @Autowired
    CICDProjectMapper cicdProjectMapper;

    @Test
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
    public void getAllProject() {
        CICDProject project = new CICDProject();
        project.setName("testProject");
        project.setAuthor("tester");
        project.setLanguage("Java");
        project.setType(0);
        project.setEnabled(true);
        cicdProjectMapper.addCICDProject(project);
        List<CICDProject> allProjects = cicdProjectMapper.getCICDProjectByAuthor("tester");
        Assert.assertNotEquals(0, allProjects.size());
    }

    @Test
    public void updateProject() {
        CICDProject new_project = new CICDProject();
        new_project.setName("testProject");
        new_project.setAuthor("tester");
        new_project.setLanguage("Java");
        new_project.setType(0);
        new_project.setEnabled(true);
        cicdProjectMapper.addCICDProject(new_project);
        CICDProject project = cicdProjectMapper.getCICDProjectByName("testProject");
        project.setType(1);
        project.setEnabled(true);
        cicdProjectMapper.updateCICDProject(project);
        CICDProject project_after = cicdProjectMapper.getCICDProjectByName("testProject");
        Assert.assertEquals(1, project_after.getType());
    }

    @Test
    public void getProjectDetail() {
        CICDProject new_project = new CICDProject();
        new_project.setName("testProject");
        new_project.setAuthor("tester");
        new_project.setLanguage("Java");
        new_project.setType(0);
        new_project.setEnabled(true);
        cicdProjectMapper.addCICDProject(new_project);
        CICDProject project = cicdProjectMapper.getCICDProjectByName("testProject");
        Assert.assertEquals("testProject", project.getName());

    }

    @Test
    public void deleteProject() {
        CICDProject new_project = new CICDProject();
        new_project.setName("testProject");
        new_project.setAuthor("tester");
        new_project.setLanguage("Java");
        new_project.setType(0);
        new_project.setEnabled(true);
        cicdProjectMapper.addCICDProject(new_project);
        int result = cicdProjectMapper.deleteCICDProject("testProject");
        Assert.assertNotNull(result);
    }

}