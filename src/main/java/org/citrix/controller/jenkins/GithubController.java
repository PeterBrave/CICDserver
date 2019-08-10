package org.citrix.controller.jenkins;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.citrix.bean.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kavin
 * @date 2019-07-10 15:36
 */
@Slf4j
@RestController
@RequestMapping("/github")
public class GithubController {
    @Autowired
    private Configuration freemarkerConfiguration;

    @PostMapping("/content")
    public String getContent(@RequestParam(value = "repo") String repo,
                             @RequestParam(value = "language") String language,
                             @RequestParam(value = "githubName") String githubName,
                             @RequestParam(value = "githubToken") String githubToken) throws IOException, TemplateException {
        log.info("/github/content  repo = " + repo + "language = " + language);
        try {
            //拼接Base64Token
            String rawToken = githubName + ":" + githubToken;
            String middleToken = string2Base64(rawToken);
            String finalToken = "Basic " + middleToken;
            log.info("finalToken = " + finalToken);

            String uri = "https://api.github.com/repos/"+ githubName + "/" + repo + "/contents/Jenkinsfile";
            HttpResponse response = getJenkinsFileContent(repo, uri,finalToken);
            int code = response.getStatusLine().getStatusCode();
            log.info("code = " + code);
            String rev = EntityUtils.toString(response.getEntity());
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rev);
            String r = jsonObject.getString("content");
            //base64转string
            return base642String(r);
        } catch (Exception e) {
            e.printStackTrace();
            Template template = freemarkerConfiguration.getTemplate("pipeline-"+language+".ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, null);
            return content;
        }
    }

    @PostMapping("/commit")
    public RespBean commitFile(@RequestParam(value = "codeContent") String codeContent,
                               @RequestParam(value = "repo") String repo,
                               @RequestParam(value = "githubName") String githubName,
                               @RequestParam(value = "githubToken") String githubToken) {
        try {
            //拼接Base64Token
            String rawToken = githubName + ":" + githubToken;
            String middleToken = string2Base64(rawToken);
            String finalToken = "Basic " + middleToken;
            log.info("finalToken = " + finalToken);


            HttpClient httpclient = new DefaultHttpClient();
            String uri = "https://api.github.com/repos/" + githubName + "/" + repo + "/contents/Jenkinsfile";
            HttpResponse response = getJenkinsFileContent(repo, uri, finalToken);
            int code = response.getStatusLine().getStatusCode();
            HttpPut httpPut = new HttpPut(uri);
            //添加http头信息
            httpPut.addHeader("Authorization", finalToken); //认证token
            httpPut.addHeader("Content-Type", "application/json");
            //String转Base64
            String s = string2Base64(codeContent);

            if (code == 200 || code == 201) {
                String rev = EntityUtils.toString(response.getEntity());
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rev);
                String r = jsonObject.getString("sha");
                JSONObject obj = new JSONObject();
                obj.put("message", "Modify set up file");
                obj.put("content", s);
                obj.put("sha", r);
                httpPut.setEntity(new StringEntity(obj.toString()));
                response = httpclient.execute(httpPut);
                code = response.getStatusLine().getStatusCode();
//                System.out.println("code = " + code);
                if (code == 200 || code == 201) {
                    return RespBean.ok("JenkinsFile Updated Successfully");
                } else {
                    return RespBean.error("Update Failed");
                }

            } else if (code == 404) {
                JSONObject obj = new JSONObject();
                obj.put("message", "Add Set up file");
                obj.put("content", s);
                httpPut.setEntity(new StringEntity(obj.toString()));
                response = httpclient.execute(httpPut);
                code = response.getStatusLine().getStatusCode();
//                log.info("code = " + code);
                if (code == 201 ) {
                    return RespBean.ok("Add JenkinsFile Successfully");
                } else {
                    return RespBean.error("Add JenkinsFile Failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return RespBean.error("Failed to Modify File");
    }

    private HttpResponse getJenkinsFileContent(String repo, String uri, String token) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Authorization", token);
        httpGet.addHeader("Content-Type", "application/json");
        HttpResponse response;
        response = httpclient.execute(httpGet);
        return response;
    }

    private String string2Base64(String s) {
        byte[] b = s.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return new String(b);
    }

    private String base642String(String s) {
        Base64 base64 = new Base64();
        byte[] b = base64.decode(s);
        String content = new String(b);
        return content;
    }


}
