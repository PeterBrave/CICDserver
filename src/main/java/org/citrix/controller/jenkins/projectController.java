package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.CICDProject;
import org.citrix.bean.RespBean;
import org.citrix.enums.ResultEnum;
import org.citrix.exception.CICDException;
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
    public RespBean getAllProject(@RequestParam("author") String author) {
        List<CICDProject> allProjects = cicdProjectMapper.getCICDProjectByAuthor(author);
        log.info("projectList = " + allProjects);
        if (allProjects != null && allProjects.size() != 0) {
            return RespBean.ok(null, allProjects);
        } else {
            return RespBean.ok("No project exists!");
        }
    }

    @PostMapping("/detail")
    public RespBean getProjectDetail(@RequestParam("name") String name){
        CICDProject project = cicdProjectMapper.getCICDProjectByName(name);
        if (project != null) {
            return RespBean.ok("success", project);
        } else {
            throw new CICDException(ResultEnum.GET_DATA_ERROR);
        }
    }

    @PostMapping("/delete")
    public Boolean deleteProject(@RequestParam("name") String name) {
        int result = cicdProjectMapper.deleteCICDProject(name);
        if (result == 1) {
            return true;
        }else {
            throw new CICDException(ResultEnum.DELETE_DATA_ERROR);
        }
    }

    @PostMapping("/add")
    public boolean addProject(@RequestParam("name") String name, @RequestParam("author") String author,
                                @RequestParam("language") String language, @RequestParam("type") int type) {
        CICDProject project = new CICDProject();
        project.setName(name);
        project.setAuthor(author);
        project.setLanguage(language);
        project.setType(type);
        project.setEnabled(false);
        int result = cicdProjectMapper.addCICDProject(project);
        if (result == 1) {
            return true;
        } else {
            throw new CICDException(ResultEnum.INSERT_DB_ERROR);
        }
    }
    @PostMapping("/update")
    public Boolean updateProject(@RequestParam("name") String name,
                                  @RequestParam("type") int type,
                                  @RequestParam("enable") boolean enable) {
        CICDProject project = cicdProjectMapper.getCICDProjectByName(name);
        if (project != null) {
            if (type != 0) {
                project.setType(type);
            }
            if (enable !=false) {
                project.setEnabled(enable);
            }
            int result = cicdProjectMapper.updateCICDProject(project);
            if (result == 1) {
                return true;
            } else {
                throw new CICDException(ResultEnum.UPDATE_DATA_ERROR);
            }
        } else {
            throw new CICDException(ResultEnum.GET_DATA_ERROR);
        }
    }

}
