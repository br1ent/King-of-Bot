<template>
    <ContentField>
        <div class="home-container">
            <div class="main-content">
                <!-- 游戏介绍 -->
                <div class="intro-section">
                    <h1 class="intro-title">
                        <span class="title-text">King Of Bot</span>
                    </h1>
                    <p class="intro-subtitle">智能对战贪吃蛇</p>
                    <div class="intro-content">
                        <p>
                            基于微服务架构的多人实时对战平台，支持玩家使用编写的Bot代码进行竞技。
                            游戏采用回合制玩法，双方玩家在15×16的地图中操控贪吃蛇进行对战。
                        </p>
                        <p>
                            <strong>游戏介绍：</strong>
                        </p>
                        <ul>
                            <li>实时多人匹配</li>
                            <li>天梯排名系统</li>
                            <li>自定义Bot出战</li>
                        </ul>
                        <p>
                            <strong>游戏规则：</strong> WASD控制移动，双方输入后同时移动，撞墙、撞蛇身或者5s后任意一方没有输入即失败。
                        </p>
                    </div>
                </div>

                <!-- 项目演示 -->
                <div class="demo-section">
                    <h2 class="section-title">项目演示</h2>
                    <div class="demo-grid">
                        <div class="demo-item">
                            <h3>对战演示</h3>
                            <img src="../../assets/gif/对战演示.gif" alt="对战演示" class="demo-image">
                            <p>实时多人对战界面展示</p>
                        </div>
                        <div class="demo-item">
                            <h3>录像演示</h3>
                            <img src="../../assets/gif/录像演示.gif" alt="录像演示" class="demo-image">
                            <p>游戏录像回放功能</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script setup>
import ContentField from '@/components/ContentField.vue'
</script>

<style scoped>
.home-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
}

.main-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 60px;
    align-items: start;
    margin: 40px 0;
}

.intro-section {
    padding: 0;
}

.intro-title {
    line-height: 2;
}

.title-text {
    font-size: 3rem;
    font-weight: 500;
    background: lightcoral;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    display: inline-block;
}

.intro-subtitle {
    font-size: 1.8rem;
    color: #666;
    margin-bottom: 30px;
    font-weight: 500;
}

.intro-content {
    font-size: 1.2rem;
    line-height: 1.8;
    color: #333;
}

.intro-content p {
    margin-bottom: 25px;
}

.intro-content ul {
    margin: 25px 0;
    padding-left: 25px;
}

.intro-content li {
    margin-bottom: 12px;
    font-size: 1.1rem;
    line-height: 1.6;
}

/* 项目演示 */
.demo-section {
    padding: 0;
}

.section-title {
    font-size: 2.8rem;
    font-weight: 700;
    color: #333;
    margin-bottom: 40px;
    text-align: left;
}

.demo-grid {
    display: flex;
    flex-direction: column;
    gap: 40px;
}

.demo-item {
    text-align: center;
    background: #f8f9fa;
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
    transition: transform 0.3s ease;
}

.demo-item:hover {
    transform: translateY(-5px);
}

.demo-item h3 {
    font-size: 1.8rem;
    font-weight: 600;
    color: #333;
    margin-bottom: 25px;
}

.demo-image {
    width: 100%;
    max-width: 500px;
    height: auto;
    border-radius: 15px;
    margin-bottom: 20px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
    border: 3px solid #fff;
}

.demo-item p {
    color: #666;
    font-size: 1.1rem;
    font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 1200px) {
    .main-content {
        gap: 40px;
    }

    .title-text {
        font-size: 3.5rem;
    }

    .demo-image {
        max-width: 450px;
    }
}

@media (max-width: 992px) {
    .main-content {
        grid-template-columns: 1fr;
        gap: 60px;
    }

    .intro-section {
        text-align: center;
    }

    .intro-content {
        text-align: left;
    }

    .section-title {
        text-align: center;
    }

    .demo-image {
        max-width: 500px;
    }
}

@media (max-width: 768px) {
    .title-text {
        font-size: 2.8rem;
    }

    .intro-subtitle {
        font-size: 1.5rem;
    }

    .section-title {
        font-size: 2.2rem;
    }

    .demo-item {
        padding: 30px 20px;
    }

    .demo-image {
        max-width: 100%;
    }

    .quick-access .btn {
        display: block;
        width: 100%;
        max-width: 300px;
        margin: 15px auto;
        padding: 12px 30px;
        font-size: 1.2rem;
    }
}

@media (max-width: 480px) {
    .title-text {
        font-size: 2.2rem;
    }

    .intro-subtitle {
        font-size: 1.2rem;
    }

    .section-title {
        font-size: 1.8rem;
    }

    .intro-content {
        font-size: 1.1rem;
    }

    .demo-item {
        padding: 25px 15px;
    }
}
</style>