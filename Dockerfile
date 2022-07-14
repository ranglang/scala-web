#FROM java:11 as build
FROM openjdk:11 as build
WORKDIR /usr/src/app
ENV MILL_VERSION 0.10.5
RUN \
  curl -L -o /usr/local/bin/mill https://github.com/lihaoyi/mill/releases/download/$MILL_VERSION/$MILL_VERSION && \
  chmod +x /usr/local/bin/mill

COPY punchclock ./punchclock
COPY circle ./circle
COPY jep ./jep
COPY build.sc ./build.sc
COPY dependencies.sc ./dependencies.sc
COPY env.sample.sc ./env.sc
COPY settings.sc ./settings.sc

RUN mill circle.assembly

#deno compile --unstable --allow-net app.ts -p 8080
FROM openjdk:11
ENV TZ=Asia/Chongqing

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app
COPY --from=build /usr/src/app/out/circle/assembly.dest/out.jar /app
EXPOSE 8080
ENTRYPOINT [ "java","-cp", "/app/out.jar", "e.M"]
