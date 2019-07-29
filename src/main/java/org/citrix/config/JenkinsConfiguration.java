package org.citrix.config;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author kavin
 * @date 2019-07-11 21:32
 */
@Configuration
public class JenkinsConfiguration {

    @Bean(name = "jenkinsProperties")
    public JenkinsProperties jenkinsProperties(
            @Value("${project.jenkins.server-uri}") String serverUri,
            @Value("${project.jenkins.username}") String username,
            @Value("${project.jenkins.password}") String password) {
        JenkinsProperties jenkinsProperties = new JenkinsProperties();
        jenkinsProperties.setServerUri(serverUri);
        jenkinsProperties.setUsername(username);
        jenkinsProperties.setPassword(password);
        return jenkinsProperties;
    }

    /*注入jenkinsHttpClient对象*/
    @Bean(name = "jenkinsHttpClient")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JenkinsHttpClient getJenkinsHttpClient(@Qualifier("jenkinsProperties") JenkinsProperties jenkinsProperties) throws URISyntaxException {
        return new JenkinsHttpClient(
                new URI(jenkinsProperties.getServerUri()),
                jenkinsProperties.getUsername(),
                jenkinsProperties.getPassword());
    }

    /*注入jenkinsServer对象*/
    @Bean(name = "jenkinsServer")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JenkinsServer getJenkinsServer(@Qualifier("jenkinsHttpClient") JenkinsHttpClient jenkinsHttpClient) {
        return new JenkinsServer(jenkinsHttpClient);
    }
}

