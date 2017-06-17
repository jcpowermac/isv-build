FROM centos

RUN yum install -y vim && \
    yum clean all
    
