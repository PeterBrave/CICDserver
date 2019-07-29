package org.citrix.config;

import lombok.Data;

/**
 * @author kavin
 * @date 2019-07-11 21:31
 */
@Data
public class JenkinsProperties {
    private String serverUri;
    private String username;
    private String password;
}
