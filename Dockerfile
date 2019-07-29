FROM centos 
#基础镜像

#运行脚本，通过脚本配置环境，以及下载war包
RUN yum update -y \
&& yum install -y java \
&& yum install -y tomcat\
&& yum install -y tomcat-webapps tomcat-admin-webapps \
&& systemctl enable tomcat \
&& yum install -y wget \
&& wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm \
&& rpm -ivh mysql-community-release-el7-5.noarch.rpm \
&& yum install -y mysql-server \
&& systemctl enable mysqld.service 

#COPY /target/*.jar  /usr/share/tomcat/webapps/
COPY /target/*.war  /home/jenkins/workspace/zxp_test_slave_3/
