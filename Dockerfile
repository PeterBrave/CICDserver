FROM centos 
#基础镜像

#运行脚本，通过脚本配置环境，以及下载war包
RUN yum update -y \
&& yum install -y java \
&& yum install -y tomcat\
&& yum install -y tomcat-webapps tomcat-admin-webapps \
&& systemctl enable tomcat \
&& wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm \
&& rpm -ivh mysql-community-release-el7-5.noarch.rpm \
&& yum install mysql-server \
&& systemctl enable mysqld.service 
COPY /target/*.war  /usr/share/tomcat/webapps/
