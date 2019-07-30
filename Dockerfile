FROM centos 
#基础镜像

#运行脚本，通过脚本配置环境，以及下载war包
RUN yum update -y \
&& yum install -y java \
&& yum install -y tomcat\
&& yum install -y tomcat-webapps tomcat-admin-webapps \
&& systemctl enable tomcat 
#&& yum install -y wget \
#&& mkdir /usr/share/tomcat \
#&& cd /usr/share/tomcat \
#&& wget http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.43/bin/apache-tomcat-8.5.43.tar.gz \
#&& tar -zxf apache-tomcat-8.5.43.tar.gz \
#&& ./apache-tomcat-8.5.43/bin/startup.sh
#&& wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm \
#&& rpm -ivh mysql-community-release-el7-5.noarch.rpm \
#&& yum install -y mysql-server \
#&& systemctl enable mysqld.service 

COPY /target/*.jar  /usr/share/tomcat/webapps/

RUN java -jar /usr/share/tomcat/webapps/*.jar
#COPY /target/*.war /usr/share/tomcat/apache-tomcat-8.5.43/webapps/

#COPY /target/*.war  /home/jenkins/workspace/zxp_test_slave_3/
