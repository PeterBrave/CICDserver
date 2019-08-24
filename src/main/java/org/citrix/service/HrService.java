package org.citrix.service;

import org.citrix.bean.Hr;
import org.citrix.enums.ResultEnum;
import org.citrix.exception.CICDException;
import org.citrix.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by citrix on 2017/12/28.
 */
@Service
@Transactional
public class HrService implements UserDetailsService {

    @Autowired
    private HrMapper hrMapper;

    public void setHrMapper(HrMapper hrMapper) {
        this.hrMapper = hrMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
        if (hr == null) {
            throw new UsernameNotFoundException("The username is error!");
        }
        return hr;
    }

    public int hrReg(String username, String password) {
        //如果用户名存在，返回错误
        if (hrMapper.loadUserByUsername(username) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        hrMapper.hrReg(username, encode);
        Hr hr = hrMapper.getLatestHr();
        Long rids[] = {2l,3l,4l};
        hrMapper.addRolesForHr(hr.getId(), rids);
        return 1;
    }

    public int updateHr(Hr hr) {
        int result = hrMapper.updateHr(hr);
        if (result == 1) {
            return result;
        }else {
            throw new CICDException(ResultEnum.UPDATE_DATA_ERROR);
        }
    }

}