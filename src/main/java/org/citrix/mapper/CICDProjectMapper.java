package org.citrix.mapper;

import org.apache.ibatis.annotations.Param;
import org.citrix.bean.CICDProject;

import java.util.List;

public interface CICDProjectMapper {


    List<CICDProject> getCICDProjectByAuthor(@Param("author") String author);

    CICDProject getCICDProjectByName(@Param("name") String name);

    int deleteCICDProject(@Param("name") String name);

//    int addCICDProject(@Param("name") String name, @Param("author") String author, @Param("language") String language, @Param("type") int type);
    int addCICDProject(CICDProject project);

    int updateCICDProject(CICDProject project);
}
