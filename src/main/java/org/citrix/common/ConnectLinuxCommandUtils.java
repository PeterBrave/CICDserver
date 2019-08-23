package org.citrix.common;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.citrix.bean.RemoteConnect;

import java.io.*;

/**
 * @author kavin
 * @date 2019-07-18 09:54
 */
@Slf4j
public class ConnectLinuxCommandUtils {

    private static String DEFAULTCHARTSET = "UTF-8";
    private static Connection conn;

    /**
     *
     * @Title: login
     * @Description: 用户名密码方式  远程登录linux服务器
     * @return: Boolean
     * @throws
     */
    public static Boolean login(RemoteConnect remoteConnect) {
        boolean flag = false;
        try {
            conn = new Connection(remoteConnect.getIp());
            conn.connect();// 连接
            flag = conn.authenticateWithPassword(remoteConnect.getUserName(), remoteConnect.getPassword());// 认证
            if (flag) {
                log.info("认证成功！");
            } else {
                log.info("认证失败！");
                conn.close();
            }
        } catch (IOException e) {
            log.info("error", e);
        }
        return flag;
    }


    /**
     *
     * @Title: execute
     * @Description: 远程执行shll脚本或者命令
     * @param cmd 脚本命令
     * @return: result 命令执行完毕返回结果
     * @throws
     */
    public static String execute(String cmd){
        String result = "";
        try {
            Session session = conn.openSession();// 打开一个会话
            session.execCommand(cmd);// 执行命令
            result = processStdout(session.getStdout(), DEFAULTCHARTSET);
            // 如果为得到标准输出为空，说明脚本执行出错了
            if (StringUtils.isBlank(result)) {
                log.info("Oops!Some thing wrong happened!");
                result = processStdout(session.getStderr(), DEFAULTCHARTSET);
            }
            conn.close();
            session.close();
        } catch (IOException e) {
            log.info("error", e);
        }
        return result;
    }

    /**
     *
     * @Title: processStdout
     * @Description: 解析脚本执行的返回结果
     * @param in 输入流对象
     * @param charset 编码
     * @return String 以纯文本的格式返回
     * @throws
     */
    public static String processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            log.info("error", e);
        } catch (IOException e) {
            log.info("error", e);
        }
        return buffer.toString();
    }

    /**
     *
     * @Title: ConnectLinux
     * @Description: 通过用户名和密码关联linux服务器
     * @return
     * @return String
     * @throws
     */
    public static String connectLinux(String ip,String userName,String password,String commandStr) {

        log.info(" scpGet===" + "ip:" + ip + "  userName:" + userName + "  commandStr:" + commandStr);

        String returnStr = "";

        RemoteConnect remoteConnect = new RemoteConnect();
        remoteConnect.setIp(ip);
        remoteConnect.setUserName(userName);
        remoteConnect.setPassword(password);
        try {
            if (login(remoteConnect)) {
                returnStr = execute(commandStr);
                log.info(returnStr);
            }
        } catch (Exception e) {
            log.info("error", e);
        }

        if (StringUtils.isBlank(returnStr)) {
            returnStr = "false";
        }
        return returnStr;
    }


}
