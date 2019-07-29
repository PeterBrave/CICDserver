package org.sang.controller.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.sang.bean.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private JenkinsServer jenkinsServer;
    @Autowired
    private Configuration freemarkerConfiguration;

//    @GetMapping("/test")
//    public String jenkinsAPI() throws IOException, URISyntaxException {
//        try {
////            Map<String, Job> jobs = jenkinsServer.getJobs();
////            JobWithDetails job = jenkinsServer.getJob("single");
////
////            Build build = job.getFirstBuild();  /*获取某任务第一次构建的构建对象*/
////            BuildWithDetails buildWithDetails = build.details(); /*子类转型*/
////            String logs = buildWithDetails.getConsoleOutputText(); /*获取构建的控制台输出信息 ，即构建日志*/
//            String logs = jenkinsServer.getJobXml("ttt");
//
//            return logs;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "error";
//
//    }

    @PostMapping("/build")
    public RespBean buildProject(@RequestParam(value = "jobName") String jobName) throws IOException{
        log.info("jobName = " + jobName);
        System.out.println("jobName = " + jobName);
        try {
            JobWithDetails job = jenkinsServer.getJob(jobName);
            job.build();
            return RespBean.ok("Compile Successfully!");

        } catch (Exception e) {
            return RespBean.error("Failed to Compile");
        }
    }

    @PostMapping("/create")
    public RespBean createJob(@RequestParam(value = "projectName") String projectName,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "repo") String repo) throws Exception {
        log.info("projectName = " + projectName + ", description = " + description + ",repo =" + repo);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("repo", repo);
            Template template = freemarkerConfiguration.getTemplate("jenkins-config.ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            log.info(content);
            jenkinsServer.createJob(projectName, content);
            return RespBean.ok("Create Jenkins Job Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("Failed to Create Jenkins Job!");
    }

    @PostMapping("/output")
    public String getConsoleOutput(@RequestParam(value = "projectName") String projectName) throws IOException{
        try {
            JobWithDetails job = jenkinsServer.getJob(projectName);

            return job.getLastBuild().details().getConsoleOutputText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to Get Building Result!";
        }

    }

}
