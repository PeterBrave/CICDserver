package org.citrix.controller.jenkins;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.citrix.bean.RespBean;
import org.citrix.mapper.HrMapper;
import org.citrix.service.HrService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    private GithubController githubController;

    @Autowired
    private HrMapper hrMapper;

    private String githubToken;

    @Before
    public void setGithubToken() {
        this.githubToken = hrMapper.getHrById(new Long(14)).getGithubToken();
    }

    @Test
    public void getContentCase1() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", githubToken,2);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentCase2() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", githubToken,3);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentCase3() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("CICDserver", "Java", "PeterBrave", githubToken,2);
        Assert.assertNotNull(respBean);
    }

    @Test
    public void getContentErrorCase() throws IOException, TemplateException {
        RespBean respBean = githubController.getContent("lmb", "Java", "PeterBrave", githubToken,4);
        Assert.assertNotNull(respBean);
    }





    @Test
    public void commitFile() throws IOException, TemplateException{
        RespBean respBean = githubController.getContent("CICDserver", "Java", "PeterBrave", githubToken,2);
        String codeContent = (String)respBean.getObj();
        RespBean result = githubController.commitFile(codeContent, "CICDserver", "PeterBrave", githubToken);
        Assert.assertNotNull(result);
    }

    @Test
    public void commitFileError() throws IOException{
        RespBean result = githubController.commitFile("this is for test", "lmb", "PeterBrave", githubToken);
        Assert.assertNotNull(result);
    }

}