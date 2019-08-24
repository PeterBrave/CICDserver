package org.citrix.bean;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RespBeanTest {


    @Test
    public void setStatus() {
        RespBean  respBean = RespBean.ok("ok", null);
        respBean.setStatus(300);
    }


    @Test
    public void setMsg() {
        RespBean  respBean = RespBean.ok("ok", null);
        respBean.setMsg("test_mag");
    }

    @Test
    public void setObj() {
        RespBean  respBean = RespBean.ok("ok", null);
        respBean.setObj(null);
    }
}