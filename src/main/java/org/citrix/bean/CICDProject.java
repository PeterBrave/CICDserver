package org.citrix.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author kavin
 * @date 2019-08-13 20:28
 */
@Data
public class CICDProject {
    private Long id;
    private String name;
    private String author;
    private String language;
    private int type;
    private Date create_time;
    private boolean enabled;

}
