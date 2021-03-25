FROM centos

COPY . /workspace

WORKDIR /workspace

RUN yum install -y maven
RUN export JWT="BrookeKiserPhillipCowanJoshBarthelmess"

WORKDIR /workspace/build/libs

ENTRYPOINT ["java", "-jar", "RevatureConnectPlus-all-0.0.1-SNAPSHOT.jar"]