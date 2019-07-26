FROM centos 
#基础镜像

#运行脚本，通过脚本配置环境，以及下载war包
RUN yum update -y \
&& yum install -y java \
&& yum install httpd -y \
&& yum install -y tomcat \
&& yum install tomcat-webapps tomcat-admin-webapps \

COPY /target/*.war  /usr/share/tomcat/webapps/
CMD ["sh" "-c" "systemctl start tomcat"]
