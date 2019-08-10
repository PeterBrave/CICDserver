package org.citrix.controller.jenkins;

import com.google.common.reflect.TypeToken;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Namespace;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author kavin
 * @date 2019-08-08 20:47
 */
@Slf4j
@RestController
@RequestMapping("/k8s")
public class K8sController {


    @GetMapping("/test")
    public String TestDemo() throws IOException, ApiException {
        try {
            log.info("run here ---------->");
            ApiClient client = Config.fromToken("https://13.125.214.112:6443", "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJkYXNoYm9hcmQtYWRtaW4tdG9rZW4td3ZtNDkiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGFzaGJvYXJkLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiYTJhNTFiYzgtYjljYy0xMWU5LWIxODQtMDI3MmQ2OTFlYjEwIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmRhc2hib2FyZC1hZG1pbiJ9.XYDXWxvYuqRPZn6uzJmUxk33TFQ3a9m5hagY130q_spDFjus00Gc4P6BwX_v50xYki0z5myzMNv8-hJRhJFJPBd-uLtgdy1fOhLtOYQN6n9DY3vOtIpEOUb_sqic_3w0aHcJjzav3rcwo3XM9oefF_XYTuTDdJENBTYYTETApc--ic9kYdE67PlhEC3TmDNV2tyuqlxjWX9uc-IfDX3Qi_dnaRs3QvuYwW1y-peknSAtN2T0cDNMo7IF8OD_XC6QshoVOiiNWIIb45kSaFXkrmElyfMRg7iMgNm9ZSzdgCD7WcGkC2hx9OGtXNY4OkDmHB4L9cyYCxu-pogNqYFsRQ", false);
            Configuration.setDefaultApiClient(client);
            log.info("and here!!!!");
            CoreV1Api api = new CoreV1Api();

            Watch<V1Namespace> watch = Watch.createWatch(
                    client,
                    api.listNamespaceCall(null, null, null, null, null, null, null, null, Boolean.TRUE, null, null),
                    new TypeToken<Watch.Response<V1Namespace>>(){}.getType());

            for (Watch.Response<V1Namespace> item : watch) {
                System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
            }
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fasle";
    }
}
