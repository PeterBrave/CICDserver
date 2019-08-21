package org.citrix.controller.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

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
        if (jenkins == null) {
            if (type == 1 || type == 2) {
                jenkins = new JenkinsServer(new URI("http://3.15.149.72:8080"), "admin123", "zxcvfdsa321");
            }else if (type == 3) {
                jenkins = new JenkinsServer(new URI("http://13.125.214.112:30002/"), "citrix", "zxcvfdsa321");
            }
        }
        return jenkins;
    }

    @PostMapping("/build")
    public RespBean buildProject(@RequestParam(value = "jobName") String jobName,
                                 @RequestParam(value = "type") int type) throws IOException{
        log.info("jobName = " + jobName);
        try {
            if (jobName == null) {
                throw new IllegalArgumentException("jobName is null");
            }
            JenkinsServer jenkins = getJenkins(type);
            if (jenkins != null) {
                JobWithDetails job = jenkins.getJob(jobName);
                job.build();
                return RespBean.ok("Compile Successfully!");
            }
        } catch (Exception e) {
            return RespBean.error("Failed to Compile");
        }
        return RespBean.error("Failed to Compile");
    }

    @PostMapping("/create")
    public RespBean createJob(@RequestParam(value = "projectName") String projectName,
                              @RequestParam(value = "repo") String repo,
                              @RequestParam(value = "githubName") String githubName,
                              @RequestParam(value = "type") int type) throws Exception {
        log.info("projectName = " + projectName + ", type = " + type + ",repo =" + repo);
        if (projectName == null || repo == null || githubName == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("Failed to Create Jenkins Job!");
    }

    @PostMapping("/output")
    public String getConsoleOutput(@RequestParam(value = "projectName") String projectName,
                                   @RequestParam(value = "type") int type) throws IOException{
        if (projectName == null) {
            throw new IllegalArgumentException("projectName is null");
        }
        try {
            JenkinsServer jenkins = getJenkins(type);
            if (jenkins != null) {
                JobWithDetails job = jenkins.getJob(projectName);
                return job.getLastBuild().details().getConsoleOutputText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to Get Building Result!";
        }
        return "Failed to Get Building Result!";
    }
}
