package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
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
public class ProjectControllerAddFunctionTest {

    @Autowired
    private ProjectController projectController;


    @Test
    public void addProjectErrorTest() {
        CICDProjectMapper mock = EasyMock.createMock(CICDProjectMapper.class);
        EasyMock.expect(mock.addCICDProject(EasyMock.anyObject())).andReturn(-1);
        EasyMock.replay(mock);
        projectController.setCicdProjectMapper(mock);

        try {
            projectController.addProject("testProject", "tester", "Java", 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertNotNull(e);
        }
        EasyMock.verify(mock);
    }
}