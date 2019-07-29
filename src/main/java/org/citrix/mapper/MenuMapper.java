package org.citrix.mapper;

import org.citrix.bean.Menu;

import java.util.List;

/**
 * Created by citrix on 2017/12/28.
 */
public interface MenuMapper {
    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
