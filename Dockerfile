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
&& ./apache-tomcat-8.5.43/bin/startup.sh

COPY /target/*.war /usr/share/tomcat/apache-tomcat-8.5.43/webapps/
CMD /usr/share/tomcat/apache-tomcat-8.5.43/bin/catalina.sh run
#ENTRYPOINT ["/usr/share/tomcat/apache-tomcat-8.5.43/bin/startup.sh"]
