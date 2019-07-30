FROM centos 
#基础镜像

#运行脚本，通过脚本配置环境，以及下载war包
RUN yum update -y \
&& yum install -y wget \
&& yum install -y java \
&& mkdir /usr/share/tomcat \
&& cd /usr/share/tomcat \
&& wget http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.43/bin/apache-tomcat-8.5.43.tar.gz \
&& tar -zxf apache-tomcat-8.5.43.tar.gz \
&& touch /usr/lib/systemd/system/tomcat.service \
&& echo "[Unit] \n Description=Tomcat  After=syslog.target network.target remote-fs.target nss-lookup.target  \n [Service] Type=oneshot ExecStart=/usr/share/tomcat/apache-tomcat-8.5.43/bin/startup.sh ExecStop=/usr/share/tomcat/apache-tomcat-8.5.43/bin/shutdown.sh ExecReload=/bin/kill -s HUP $MAINPID RemainAfterExit=yes [Install] WantedBy=multi-user.target" > /usr/lib/systemd/system/tomcat.service \
&& systemctl enable tomcat
#&& ./apache-tomcat-8.5.43/bin/startup.sh

COPY /target/*.war /usr/share/tomcat/apache-tomcat-8.5.43/webapps/

#COPY /target/*.war  /home/jenkins/workspace/zxp_test_slave_3/
