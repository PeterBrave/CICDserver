package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.bean.RespBean;
import org.citrix.mapper.CICDProjectMapper;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback
public class projectControllerTest {

    @Autowired
    private ProjectController projectController;

    @Test
    public void addProjectOKTest() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.addCICDProject(EasyMock.anyObject())).andReturn(1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        Boolean result = projectController.addProject("testProject", "tester", "Java", 0);
        Assert.assertEquals(true, result);
        EasyMock.verify(mock);
    }

    @Test
    public void getAllProject() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        List<CICDProject> allProjects = new ArrayList<>();
        CICDProject project = new CICDProject();
        allProjects.add(project);
        EasyMock.expect(mock.getCICDProjectByAuthor("tester")).andReturn(allProjects);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        RespBean result = projectController.getAllProject("tester");
        Assert.assertNotNull(result);
        EasyMock.verify(mock);
    }

    @Test
    public void getAllProjectError() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        List<CICDProject> allProjects = new ArrayList<>();
        EasyMock.expect(mock.getCICDProjectByAuthor("tester")).andReturn(allProjects);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        RespBean result = projectController.getAllProject("tester");
        Assert.assertNotNull(result);
        EasyMock.verify(mock);
    }

    @Test
    public void getProjectDetail() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(null);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        try {
            projectController.getProjectDetail("tester");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);
    }

    @Test
    public void updateProject() {
        CICDProject project = new CICDProject();
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(project);
        EasyMock.expect(mock.updateCICDProject(project)).andReturn(1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        boolean result = projectController.updateProject("tester", 1, true);

        Assert.assertEquals(true, result);
        EasyMock.verify(mock);
    }

    @Test
    public void updateProjectNotFound() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(null);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        try {
            projectController.updateProject("tester", 1, true);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);
    }

    @Test
    public void updateProjectWithoutType() {
        CICDProject project = new CICDProject();
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(project);
        EasyMock.expect(mock.updateCICDProject(project)).andReturn(1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        boolean result = projectController.updateProject("tester", 0, true);

        Assert.assertEquals(true, result);
        EasyMock.verify(mock);
    }

    @Test
    public void updateProjectWithoutEnable() {
        CICDProject project = new CICDProject();
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(project);
        EasyMock.expect(mock.updateCICDProject(project)).andReturn(1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        boolean result = projectController.updateProject("tester", 1, false);

        Assert.assertEquals(true, result);
        EasyMock.verify(mock);
    }

    @Test
    public void updateProjectError() {
        CICDProject project = new CICDProject();
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("tester")).andReturn(project);
        EasyMock.expect(mock.updateCICDProject(project)).andReturn(-2);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        try {
            projectController.updateProject("tester", 1, true);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);
    }

    @Test
    public void deleteProject() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.deleteCICDProject("tester")).andReturn(1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        boolean result = projectController.deleteProject("tester");

        Assert.assertEquals(true, result);
        EasyMock.verify(mock);
    }

    @Test
    public void deleteProjectError() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.deleteCICDProject("tester")).andReturn(-1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        try {
            projectController.deleteProject("tester");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);
    }

}