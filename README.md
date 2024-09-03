# Socket通信编程

## 程序功能

### 客户端

- 指定服务器的ip地址和端口号
- 实现TCP和UDP形式发送请求
- 使用用户名和密码登录账户
- 实现echo功能

### 服务端

- 指定服务器监听的端口
- 实现以TCP和UDP形式监听请求，能同时与多个客户端进行通信
- 显示与客户端的通信情况
- 查询在线用户
- 使某个用户下线

## 编译方式

下载项目，导入idea，使用Build - Build Project

生成的jar包在 ./out/artifacts 下

## 开发环境

```shell
openjdk 17.0.5 2022-10-18 LTS                                                                                                                                                  
OpenJDK Runtime Environment Microsoft-6841604 (build 17.0.5+8-LTS)                                                                                                             
OpenJDK 64-Bit Server VM Microsoft-6841604 (build 17.0.5+8-LTS, mixed mode)    
```

## 使用方式

## 客户端

### 启动命令

```shell
java -jar client.jar <protocol> [-h host] [-p port]
```

### 参数介绍

protocol: 通信协议，必选，TCP或UDP

host: 服务器ip地址，默认127.0.0.1

port: 服务器端口号，默认8080

### 使用方法

按提示输入即可

输入 exit 来退出客户端

## 服务端

### 启动命令

```shell
java -jar server.jar [-p port]
```

### 参数介绍

port: 服务器监听的端口号，默认8080

### 使用方法

输入 online 查看在线用户

输入 kick + 用户名 踢用户下线

输入 exit 来退出服务器

## 项目结构

```shell
socket
├─ README.md -- README文件
├─ client -- 客户端源码
│  └─ src
│     ├─ Main.java -- 程序入口
│ 		├─ Client.java
│     ├─ UDPClient.java
│     ├─ TCPClient.java
│     ├─ DefaultClientProcessor.java
│     └─ ClientProcessor.java
├─ server -- 服务端源码
│  └─ src
│     ├─ Main.java -- 程序入口
│     ├─ Server.java
│     ├─ AbstractServer.java
│     ├─ UDPServer.java
│     ├─ TCPServer.java
│     ├─ DefaultServerProcessor.java
│     └─ ServerProcessor.java
└─ out -- 编译输出

```

