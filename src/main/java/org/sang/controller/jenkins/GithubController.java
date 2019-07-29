package org.sang.controller.jenkins;

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
import org.sang.bean.RespBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author kavin
 * @date 2019-07-10 15:36
 */
@Slf4j
@RestController
@RequestMapping("/github")
public class GithubController {

    @PostMapping("/content")
    public String getContent(@RequestParam(value = "repo") String repo) throws IOException {
        log.info("/github/content  repo = " + repo);
        try {
            String uri = "https://api.github.com/repos/PeterBrave/" + repo + "/contents/Jenkinsfile";
            HttpResponse response = getJenkinsFileContent(repo, uri);
//            int code = response.getStatusLine().getStatusCode();
//            log.info("code = " + code);
            String rev = EntityUtils.toString(response.getEntity());
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(rev);
            String r = jsonObject.getString("content");
            //base64转string
            Base64 base64 = new Base64();
            byte[] b = base64.decode(r);
            String content = new String(b);
            return content;
        } catch (Exception e) {
            return "\n" +
                    "pipeline {\n" +
                    "    agent any\n" +
                    "\n" +
                    "    stages {\n" +
                    "        stage('Build') {\n" +
                    "            steps {\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }
    }

    @PostMapping("/commit")
    public RespBean commitFile(@RequestParam(value = "codeContent") String codeContent,
                               @RequestParam(value = "repo") String repo) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            String uri = "https://api.github.com/repos/PeterBrave/" + repo + "/contents/Jenkinsfile";
            HttpResponse response = getJenkinsFileContent(repo, uri);
            int code = response.getStatusLine().getStatusCode();
            HttpPut httpPut = new HttpPut(uri);
            //添加http头信息
            httpPut.addHeader("Authorization", "Basic UGV0ZXJCcmF2ZTo2NzAxYmQ3NTgwODBjMGMxZDQ5OGYyOTVmYjQ4MmMzMDUwZDQ0NGQz"); //认证token
            httpPut.addHeader("Content-Type", "application/json");
            //String转Base64
            byte[] b = codeContent.getBytes();
            Base64 base64 = new Base64();
            b = base64.encode(b);
            String s = new String(b);

            if (code == 200) {
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
                if (code == 200) {
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
                if (code == 201) {
                    RespBean.ok("Add JenkinsFile Successfully");
                } else {
                    RespBean.error("Add JenkinsFile Failed");
                }
            }
            log.info("error code = " + code);
            return RespBean.error("Cannot Find File");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("Failed to Modify File");
    }

    private HttpResponse getJenkinsFileContent(String repo, String uri) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Authorization", "Basic UGV0ZXJCcmF2ZTo2NzAxYmQ3NTgwODBjMGMxZDQ5OGYyOTVmYjQ4MmMzMDUwZDQ0NGQz");
        httpGet.addHeader("Content-Type", "application/json");
        HttpResponse response;
        response = httpclient.execute(httpGet);
        return response;
    }


}
