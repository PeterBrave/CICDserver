package org.citrix.common;

import ch.ethz.ssh2.Connection;
import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RemoteConnect;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConnectLinuxCommandUtilsMockTest {

    @Resource
    private ConnectLinuxCommandUtils connectLinuxCommandUtils;

    @Test
    public void login() throws IOException {
        Connection mock = EasyMock.createMock(Connection.class);
        EasyMock.expect(mock.authenticateWithPassword("root", "123")).andThrow(new IOException());
        EasyMock.replay(mock);
        connectLinuxCommandUtils.setConnection(mock);
        try {
            RemoteConnect remoteConnect = new RemoteConnect();
            remoteConnect.setUserName("root");
            remoteConnect.setPassword("123");
            connectLinuxCommandUtils.login(remoteConnect);
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void executeException() throws IOException{
        Connection mock = EasyMock.createMock(Connection.class);
        EasyMock.expect(mock.openSession()).andThrow(new IOException());
        EasyMock.replay(mock);
        connectLinuxCommandUtils.setConnection(mock);

        try{
            connectLinuxCommandUtils.execute("pwd");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void connectLinuxMock() {

    }
}