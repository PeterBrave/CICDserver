package org.citrix.controller.jenkins;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class GithubControllerTest {

    @Autowired
    GithubController githubController;
    @Test
    public void getContentCase1() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7",2);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentCase2() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7",3);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentCase3() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("CICDserver", "Java", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7",2);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentErrorCase() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", "1d771cf26dbeb3327c5a5af979cb0601e5c18e03",4);
        Assert.assertNotNull(respBean);
    }





    @Test
    public void commitFile() throws IOException, TemplateException{
        RespBean respBean = githubController.getContent("CICDserver", "Java", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7",2);
        String codeContent = (String)respBean.getObj();
        RespBean result = githubController.commitFile(codeContent, "CICDserver", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7");
        Assert.assertNotNull(result);
    }

    @Test
    public void commitFileError() throws IOException{
        RespBean result = githubController.commitFile("this is for test", "lmb", "PeterBrave", "2983eacfa25279cfb37d15817d3b97d4c75266e7");
        Assert.assertNotNull(result);
    }
}