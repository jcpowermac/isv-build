FROM centos

RUN yum install -y vim curl && \
    yum clean all

