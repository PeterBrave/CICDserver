package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback
public class ProjectControllerUpdateTest {

    @Autowired
    private ProjectController projectController;

    @Test
    public void updateProject() {
        CICDProject project = new CICDProject();
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.getCICDProjectByName("testProject")).andReturn(project);
        EasyMock.expect(mock.updateCICDProject(EasyMock.anyObject())).andReturn(-1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);
        try {
            projectController.updateProject("testProject", 1, true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);

    }
}