@REM 《批处理脚本》

@REM `@echo on`:显示每一步执行命令的返回结果 `@echo off`:不显示
@echo on

@REM 允许变量延迟扩展，在for等语句中用%a%语法读取变量一直是初始值 ，即使你在里面改变了变量的值 ，变量延迟扩展用!a! 感叹号代替百分号读取变量，取出的是变量的实时值
setlocal enabledelayedexpansion


REM -------------------------------------------------------------------------------


@REM 设置变量
set APP_PROFILE=prod
set APP_NAME=small-tools-web
set APP_IMAGE_NAME=registry.cn-hangzhou.aliyuncs.com/zhengqing/%APP_NAME%
set APP_IMAGE=%APP_IMAGE_NAME%:%APP_PROFILE%
set APP_PORT=88



echo "delete-container"
for /f "tokens=1" %%i in ('docker ps -a ^| findstr "%APP_NAME%"') do @docker stop %%i
for /f "tokens=1" %%i in ('docker ps -a ^| findstr "%APP_NAME%"') do @docker rm %%i


echo "delete-image"
for /f "tokens=3" %%i in ('docker images ^| findstr "%APP_NAME%"') do @docker rmi %%i



echo "build-image"
docker build -t %APP_IMAGE% . --no-cache



echo "push-image"
REM pause
docker push %APP_IMAGE%



echo "run"
REM docker run -d -p %APP_PORT%:80 --restart=always --name %APP_NAME% %APP_IMAGE%
REM pause
