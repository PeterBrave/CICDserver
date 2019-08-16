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
//    @Autowired
//    private JenkinsServer jenkinsServer;
    @Autowired
    private Configuration freemarkerConfiguration;
    private JenkinsServer jenkins;

    private JenkinsServer getJenkins(int type) {
        if (jenkins == null) {
            try {
                if (type == 1 || type == 2) {
                    jenkins = new JenkinsServer(new URI("http://3.15.149.72:8080"), "admin123", "zxcvfdsa321");
                }else if (type == 3) {
                    jenkins = new JenkinsServer(new URI("http://13.125.214.112:30002/"), "citrix", "zxcvfdsa321");
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return jenkins;
    }

    @PostMapping("/test")
    public String test() {
        try {
            JenkinsServer jenkins = getJenkins(3);

//            Map<String, Job> jobs = jenkinsServer.getJobs();
//            JobWithDetails job = jenkinsServer.getJob("single");
//
//            Build build = job.getFirstBuild();  /*获取某任务第一次构建的构建对象*/
//            BuildWithDetails buildWithDetails = build.details(); /*子类转型*/
//            String logs = buildWithDetails.getConsoleOutputText(); /*获取构建的控制台输出信息 ，即构建日志*/
            String logs = jenkins.getJobXml("cicdtest");
            return logs;

        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/build")
    public RespBean buildProject(@RequestParam(value = "jobName") String jobName,
                                 @RequestParam(value = "type") int type) throws IOException{
        log.info("jobName = " + jobName);
        System.out.println("jobName = " + jobName);
        try {
//            JenkinsServer jenkins = new JenkinsServer(new URI("http://13.125.214.112:30002/"), "citrix", "zxcvfdsa321");
            JenkinsServer jenkins = getJenkins(type);
//            JobWithDetails job = jenkinsServer.getJob(jobName);
            JobWithDetails job = jenkins.getJob(jobName);
            job.build();
            return RespBean.ok("Compile Successfully!");

        } catch (Exception e) {
            return RespBean.error("Failed to Compile");
        }
    }

    @PostMapping("/create")
    public RespBean createJob(@RequestParam(value = "projectName") String projectName,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "repo") String repo,
                              @RequestParam(value = "githubName") String githubName,
                              @RequestParam(value = "type") int type) throws Exception {
        log.info("projectName = " + projectName + ", type = " + type + ",repo =" + repo);
        try {
            JenkinsServer jenkins = getJenkins(type);
            Map<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("repo", repo);
            map.put("githubName", githubName);
            Template template = freemarkerConfiguration.getTemplate("jenkins-config.ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//            log.info(content);
//            jenkinsServer.createJob(projectName, content);
            jenkins.createJob(projectName, content);
            return RespBean.ok("Create Jenkins Job Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("Failed to Create Jenkins Job!");
    }

    @PostMapping("/output")
    public String getConsoleOutput(@RequestParam(value = "projectName") String projectName,
                                   @RequestParam(value = "type") int type) throws IOException{
        try {
            JenkinsServer jenkins = getJenkins(type);
//            JobWithDetails job = jenkinsServer.getJob(projectName);
            JobWithDetails job = jenkins.getJob(projectName);

            return job.getLastBuild().details().getConsoleOutputText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to Get Building Result!";
        }

    }

}
