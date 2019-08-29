package org.citrix.controller.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kavin
 * @date 2019-07-11 20:51
 */
@RestController
@RequestMapping("/jenkins")
@Slf4j
public class JenkinsController {

    @Autowired
    private Configuration freemarkerConfiguration;
    private JenkinsServer jenkins;

    private JenkinsServer getJenkins(int type) throws URISyntaxException{
        if (type == 1 || type == 2) {
            jenkins = new JenkinsServer(new URI("http://13.124.100.245:8080/"), "citrix", "zxcvfdsa321");
        }else if (type == 3) {
            jenkins = new JenkinsServer(new URI("http://13.125.150.242:30002/"), "citrix", "zxcvfdsa321");
        }
        return jenkins;
    }

    @PostMapping("/build")
    public RespBean buildProject(@RequestParam(value = "jobName") String jobName,
                                 @RequestParam(value = "type") int type) throws IOException, URISyntaxException{
        log.info("jobName = " + jobName);
        if (jobName == null) {
            throw new IllegalArgumentException("jobName is null");
        }
        JenkinsServer jenkins = getJenkins(type);
        if (jenkins != null) {
            JobWithDetails job = jenkins.getJob(jobName);
            job.build();
            return RespBean.ok("Compile Successfully!");
        }
        return RespBean.error("Failed to Compile");
    }

    @PostMapping("/create")
    public RespBean createJob(@RequestParam(value = "projectName") String projectName,
                              @RequestParam(value = "repo") String repo,
                              @RequestParam(value = "githubName") String githubName,
                              @RequestParam(value = "type") int type) throws Exception {
        log.info("projectName = " + projectName + ", type = " + type + ",repo =" + repo + " githubName = " + githubName);
        if (projectName == null || repo == null || githubName == null) {
            throw new IllegalArgumentException("argument is null");
        }

        JenkinsServer jenkins = getJenkins(type);
        if (jenkins != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("repo", repo);
            map.put("githubName", githubName);
            Template template = freemarkerConfiguration.getTemplate("jenkins-config.ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            jenkins.createJob(projectName, content);
            return RespBean.ok("Create Jenkins Job Successfully!");
        }
        return RespBean.error("Failed to Create Jenkins Job!");
    }

    public void deleteJob(int type, String jobName) throws IOException, URISyntaxException{
        JenkinsServer jenkins = getJenkins(type);
        if (jenkins != null) {
            jenkins.deleteJob(jobName);
        }
    }
}
