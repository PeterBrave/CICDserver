package org.citrix.bean;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CICDProjectTest {

    @Test
    public void getId() {
        CICDProject project = new CICDProject();
        project.setId(new Long(100));
        project.setName("job");
        project.getName();
        project.getId();
        project.setAuthor("tester");
        project.getAuthor();
        project.setLanguage("java");
        project.getLanguage();
        project.setType(1);
        project.getType();
        project.setEnabled(true);
        project.isEnabled();
        project.setCreate_time(new Date());
        project.getCreate_time();
    }
}