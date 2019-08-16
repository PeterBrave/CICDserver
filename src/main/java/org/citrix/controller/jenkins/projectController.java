package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.bean.RespBean;
import org.citrix.mapper.CICDProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kavin
 * @date 2019-08-14 09:15
 */
@Slf4j
@RestController
@RequestMapping("/project")
public class projectController {

    @Autowired
    private CICDProjectMapper cicdProjectMapper;

    @PostMapping("/all")
    public RespBean getAllProjecct(@RequestParam("author") String author) {
        List<CICDProject> allProjects = cicdProjectMapper.getCICDProjectByAuthor(author);
        return RespBean.ok("success", allProjects);
    }

    @PostMapping("/detail")
    public RespBean getProjectDetail(@RequestParam("name") String name){
        CICDProject project = cicdProjectMapper.getCICDProjectByName(name);
        return RespBean.ok("success", project);
    }

    @PostMapping("/delete")
    public RespBean deleteProject(@RequestParam("name") String name) {
        cicdProjectMapper.deleteCICDProject(name);
        return RespBean.ok("success", null);
    }

    @PostMapping("/add")
    public RespBean addProjecct(@RequestParam("name") String name, @RequestParam("author") String author,
                                @RequestParam("language") String language, @RequestParam("type") int type) {
         cicdProjectMapper.addCICDProject(name, author, language, type);
        return RespBean.ok("success", null);
    }
    @PostMapping("/update")
    public RespBean updateProject(@RequestParam("name") String name,
                                  @RequestParam("type") int type,
                                  @RequestParam("enable") boolean enable) {
        CICDProject project = cicdProjectMapper.getCICDProjectByName(name);
        if (type != 0) {
            project.setType(type);
        }
        if (enable !=false) {
            project.setEnabled(enable);
        }
        try {
            cicdProjectMapper.updateCICDProject(project);
        } catch (Exception e){
            e.printStackTrace();

        }
        return RespBean.ok("success", null);
    }



}
