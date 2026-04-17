# King Of Bot - 多人对战贪吃蛇游戏平台

## 📖 项目简介

King Of Bot 是一个基于微服务架构的多人对战贪吃蛇游戏平台，支持玩家实时匹配对战和AI机器人对战。项目采用前后端分离架构，提供完整的用户注册、登录、对战匹配、游戏记录等功能。

## 🎮 核心功能

### 游戏特性
- **双人贪吃蛇对战**：15×16网格地图，支持实时对战
- **智能匹配系统**：基于玩家天梯分来实现简单的匹配系统
- **AI机器人对战**：支持自定义AI代码，可编程对战机器人
- **实时对战记录**：完整的对战历史记录和回放功能
- **用户评级系统**：简单的分值加减，实现天梯排行榜功能

### 系统功能
- **用户管理**：注册、登录、个人信息管理
- **机器人管理**：创建、编辑、删除自定义AI机器人
- **对战匹配**：实时匹配系统，支持取消匹配
- **WebSocket通信**：实时游戏状态同步
- **游戏回放**：支持对战记录回放查看

## 系统架构

### 微服务组件

| 服务名称 | 端口 | 功能描述 |
|---------|------|----------|
| **Backend** | 3000 | 主后端服务，处理用户认证、游戏逻辑、WebSocket通信 |
| **MatchingSystem** | 3001 | 匹配系统服务，负责玩家匹配逻辑 |
| **BotRunningSystem** | 3002 | 机器人运行系统，执行AI代码 |
| **Web Frontend** | 5173 | Vue3前端界面 |

### 技术栈

#### 后端技术栈
- **Java 8** + **Spring Boot 2.6.13**
- **Spring Security** - 安全认证
- **MyBatis** - 数据持久层
- **MySQL** - 数据库
- **WebSocket** - 实时通信
- **Maven** - 项目管理

#### 前端技术栈
- **Vue 3** + **Vue Router 4** + **Vuex 4**
- **Bootstrap 5** - UI框架
- **Canvas** - 游戏渲染
- **Vite** - 构建工具
- **Ace Editor** - 代码编辑器

## 📁 项目结构

```
King Of Bot/
├── backendcloud/          # 后端微服务集群
│   ├── backend/           # 主后端服务 (端口:3000)
│   ├── matchingsystem/    # 匹配系统服务 (端口:3001)
│   ├── botrunningsystem/  # 机器人运行系统 (端口:3002)
│   └── pom.xml           # Maven父项目配置
├── web/                   # 前端项目
│   ├── src/
│   │   ├── components/    # Vue组件
│   │   ├── views/         # 页面视图
│   │   ├── store/         # Vuex状态管理
│   │   ├── assets/        # 静态资源
│   │   └── router/        # 路由配置
│   └── package.json       # 前端依赖配置
└── readme.md             # 项目说明文档
```

## 🚀 快速开始

### 环境要求
- Java 8
- Node.js 16+
- MySQL 5.7+
- Maven 3.6+

### 数据库配置
1. 创建数据库 `kob`
2. 导入数据库表结构（项目包含对应的实体类）

### 后端服务启动

```bash
# 进入后端项目目录
cd backendcloud

# 编译项目
mvn clean compile

# 启动各个服务（需要分别启动）
# 启动主后端服务
cd backend
mvn spring-boot:run

# 启动匹配系统（新终端）
cd ../matchingsystem
mvn spring-boot:run

# 启动机器人系统（新终端）
cd ../botrunningsystem
mvn spring-boot:run
```

### 前端服务启动

```bash
# 进入前端项目目录
cd web

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

## 🔧 配置说明

### 后端配置 (application.properties)
```properties
server.port=3000
spring.datasource.url=jdbc:mysql://localhost:3306/kob?serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=000000
```

### 数据库表结构
项目包含以下核心表：
- `user` - 用户信息表
- `bot` - 机器人信息表
- `record` - 对战记录表

## 🎯 游戏规则

### 基本规则
- 游戏地图：15行×16列网格
- 每条蛇初始长度为1
- 蛇每3步增长1格（前10步每步都增长）
- 碰撞到墙壁、其他蛇身体或自身即失败

### 胜负判定
- 平局：双方同时超时5s未操作
- 胜利：对方蛇死亡
- 失败：己方蛇死亡

## 🤖 AI机器人开发

### 机器人接口
AI机器人需要实现以下接口：
```java
public interface BotInterface {
    Integer nextMove(String input);
}
```

### 输入参数格式
输入为JSON字符串，包含游戏状态信息：
- 地图信息
- 双方蛇的位置和状态
- 障碍物位置

### 返回值
- 0: 上
- 1: 右
- 2: 下
- 3: 左

## 📊 API接口

### 用户相关
- `POST /user/account/login` - 用户登录
- `POST /user/account/register` - 用户注册
- `GET /user/account/info` - 获取用户信息

### 机器人相关
- `GET /user/bot/getlist` - 获取机器人列表
- `POST /user/bot/add` - 添加机器人
- `POST /user/bot/remove` - 删除机器人
- `POST /user/bot/update` - 更新机器人

### 对战相关
- `POST /pk/start` - 开始匹配
- `POST /pk/stop` - 停止匹配
- `GET /record/getlist` - 获取对战记录

## 🔒 安全特性
- JWT Token认证
- Spring Security权限控制
- WebSocket连接验证
- SQL注入防护

## 🐛 故障排除

### 常见问题
1. **端口冲突**：确保3000、3001、3002端口未被占用
2. **数据库连接失败**：检查MySQL服务是否启动
3. **依赖下载失败**：配置Maven镜像源

### 日志查看
各服务日志位于控制台输出，可通过日志级别调整详细程度。

## 📈 性能优化点
- 连接池配置优化
- WebSocket消息压缩
- 数据库索引优化
- 前端资源懒加载


项目页面:
<img width="1933" height="1081" alt="image" src="https://github.com/user-attachments/assets/69156a55-b173-4fff-aa59-9b494e2c6fa6" />

<img width="1243" height="997" alt="image" src="https://github.com/user-attachments/assets/79271ae9-b803-4f74-9df5-be1bcdbfc5ea" />

<img width="1909" height="681" alt="image" src="https://github.com/user-attachments/assets/4a18958d-0968-449a-8747-6aa121ad7b89" />

<img width="2023" height="742" alt="image" src="https://github.com/user-attachments/assets/30611748-42c2-49ad-a4d6-0dfa73a920cb" />

<img width="2118" height="1092" alt="image" src="https://github.com/user-attachments/assets/c3b813fc-7912-498f-ab83-cc653d59276f" />

<img width="1900" height="829" alt="image" src="https://github.com/user-attachments/assets/db826dfe-f732-4688-8a7e-ddd496cd4221" />
