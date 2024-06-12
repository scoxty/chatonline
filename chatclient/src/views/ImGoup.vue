<template>
    <div>
        <div class="main-body-content">
            <div style="display: flex; align-items: flex-start">
                <div style="
            width: 200px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 15px;
          ">
                    <div style="
              padding: 20px 10px;
              border-bottom: 1px solid #ddd;
              color: #000;
              border-radius: 15px 15px 0 0;
              background-color: #eee;
            ">
                        在线用户({{ users.length }})
                    </div>
                    <div class="user-list-box">
                        <div class="user-list-item" v-for="(item, index) in users" :key="index">
                            <img :src="item.avatar" style="width: 30px; height: 30px; border-radius: 50%" />
                            <span style="margin-left: 10px">{{ item.name }}</span>
                        </div>
                    </div>
                </div>

                <!--  中间部分  -->
                <div style="
            width: 80%;
            border: 1px solid #ddd;
            border-radius: 15px;
            background-color: #f1f1f1;
          ">
                    <div style="
              padding: 20px 0;
              text-align: center;
              border-bottom: 1px solid #ddd;
              color: #000;
              border-radius: 15px 15px 0 0;
              background-color: #eee;
            ">
                        聊天室
                    </div>
                    <div class="im-message-box">
                        <div v-for="item in messages" :key="item.id">
                            <!--  右边的气泡 -->
                            <div style="
                  display: flex;
                  flex-direction: row-reverse;
                  align-items: flex-start;
                " v-if="item.name === user.name">
                                <img :src="item.avatar" alt="" style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-left: 10px;
                  " />
                                <div class="im-message im-message-right" v-html="item.content" v-if="item.type === 'text'"></div>
                                <div class="im-message" style="padding: 0" v-if="item.type === 'img'" @click="dakai(item)">
                                    <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
                                    <el-image v-if="item.content == 'http://localhost:8080/api/files/cai.png'" style="width: 200px"
                                              :src="item.content" alt="" :preview-src-list="[url]" @load="scrollToBottom"></el-image>
                                    <el-image v-else style="width: 100px" :src="item.content" alt="" :preview-src-list="[item.content]"
                                              @load="scrollToBottom"></el-image>
                                </div>
                                <div class="im-message im-message-download" v-if="item.type === 'file'" @click="download(item.content)">
                                    <div>
                                        <i class="el-icon-folder-opened"></i>
                                        <span>{{
                                            item.content.substring(item.content.indexOf("-") + 1)
                                            }}</span>
                                    </div>
                                </div>
                            </div>

                            <!--  左边的气泡 -->
                            <div style="display: flex; align-items: flex-start" v-else>
                                <img :src="item.avatar" alt="" style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-right: 10px;
                  " />
                                <div style="width: 100%">
                                    <div style="color: #888; font-size: 12px; width: 50%">
                                        {{ item.name }}
                                    </div>
                                    <div class="im-message im-message-left" v-html="item.content" v-if="item.type === 'text'"></div>
                                    <div class="im-message" style="padding: 0" v-if="item.type === 'img'" @click="dakai(item)">
                                        <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
                                        <el-image v-if="item.content == 'http://localhost:8080/api/files/cai.png'
              " style="width: 200px" :src="item.content" alt="" :preview-src-list="[url]"
                                                  @load="scrollToBottom"></el-image>

                                        <el-image v-else style="width: 100px" :src="item.content" alt="" :preview-src-list="[item.content]"
                                                  @load="scrollToBottom"></el-image>
                                    </div>
                                    <div class="im-message im-message-download" v-if="item.type === 'file'"
                                         @click="download(item.content)">
                                        <div>
                                            <i class="el-icon-folder-opened"></i>
                                            <span>{{
                                                item.content.substring(item.content.indexOf("-") + 1)
                                                }}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 输入区域 -->
                    <div style="
              padding: 10px 5px;
              border-top: 1px solid #ddd;
              display: flex;
              align-items: center;
              width: 100%;
            ">
                        <el-popover placement="top" width="300" trigger="click">
                            <div class="emoji-box">
                <span v-for="(item, index) in emojis" :key="index"
                      style="margin-right: 5px; font-size: 20px; cursor: pointer" v-html="item"
                      @click="clickEmoji(item)"></span>
                            </div>
                            <i slot="reference" class="fa fa-smile-o" style="font-size: 22px; color: #666"></i>
                        </el-popover>
                        <div style="margin-left: 5px">
                            <el-upload action="http://localhost:8080/api/files/upload" :show-file-list="false"
                                       :on-success="handleFile">
                                <i class="fa fa-folder-open-o" style="font-size: 20px; color: #666"></i>
                            </el-upload>
                        </div>

                        <!-- <div style="margin-left: 5px">
                          <el-image :src="hongbao" style="width: 27px;height: 27px;" @click="facai"></el-image>
                        </div> -->

                        <div id="im-content" contenteditable style="
                flex: 1;
                background-color: #fff;
                margin: 0 5px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 2px;
                outline: none;
                font-size: 14px;
              "></div>
                        <!-- <el-button type="primary" @click="don" style="border: none"
                          >下载聊天记录</el-button
                        > -->
                        <el-button type="primary" @click="send" style="border: none">发送</el-button>
                    </div>
                </div>
                <!--  中间部分结束  -->
            </div>
        </div>

        <!-- 添加或修改服务活动管理对话框 -->
        <el-dialog title="红包信息" :visible.sync="hongbaoopen" width="50%" append-to-body>
            <el-form ref="form" label-width="90px">
                <el-form-item label="红包金额" prop="money">
                    <el-input oninput="value=value.replace(/[^0-9.]/g,'')" v-model="money" placeholder="请输入红包金额" />
                </el-form-item>
                <el-form-item label="领取人数" prop="shuliang">
                    <el-input oninput="value=value.replace(/[^\d]/g,'')" v-model="shuliang" placeholder="请输入领取人数" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";
import emojis from "@/assets/emoji";
import hongbao from "@/assets/hongbao.png";
import axios from 'axios';

let client;
export default {
    data() {
        return {
            jiluopen: false,
            money: "",
            shuliang: "",
            hongbaoopen: false,
            url: "http://localhost:8080/api/files/kai.png",
            hongbao: hongbao,
            user: {},
            permission: [],
            emojis: [],
            messages: [],
            users: [],
        };
    },

    // 页面加载的时候，做一些事情，在created里面
    mounted() {
        this.emojis = emojis.split(",");
        this.user = JSON.parse(localStorage.getItem("user") || "{}");

        let name = window.btoa(encodeURI(this.user.name));
        let avatar = window.btoa(encodeURI(this.user.avatar));

        client = new WebSocket(`ws://localhost:8080/imserver/${name}/${avatar}`);
        client.onopen = () => {
            console.log("websocket open");
        };
        client.onclose = () => {
            // 页面刷新的时候和后台websocket服务关闭的时候
            console.log("websocket close");
        };
        client.onmessage = (msg) => {
            if (msg.data) {
                let json = JSON.parse(msg.data);
                if (json.name && json.content) {
                    // 聊天消息
                    this.messages.push(json);
                    this.scrollToBottom(); // 滚动页面到最底部
                }
                if (json.users && json.users.length) {
                    this.users = json.users;
                    console.log("onmessage", this.users);
                }
            }
        };
        // 加载聊天数据
        this.load();
    },
    beforeDestroy() {
        if (client) {
            client.close();
        }
    },
    // 定义一些页面上控件出发的事件调用的方法
    methods: {
        submitForm() {
            let xxx = JSON.parse(localStorage.getItem("user")).zongmoney;
            let yyy = JSON.parse(localStorage.getItem("user"));
            console.log("this.money", this.money);
            console.log("this.shuliang", this.shuliang);
            console.log("xxx", xxx);
            if (this.money && this.shuliang) {
                if (this.money * 1 > xxx * 1) {
                    this.$message.error("账户余额不够");
                } else {
                    if (client) {
                        let message = {
                            name: this.user.name,
                            content: "http://localhost:8080/api/files/cai.png",
                            avatar: this.user.avatar,
                            type: "img",
                            money: this.money,
                            shuliang: this.shuliang,
                        };
                        client.send(JSON.stringify(message));
                        this.$message.success("红包发送成功！");

                        yyy.zongmoney = yyy.zongmoney * 1 - this.money * 1;
                        request.post("/user", yyy).then((res) => {
                            if (res.code === "0") {
                                // this.$message.success("操作成功");
                                localStorage.setItem("user", JSON.stringify(yyy));
                            } else {
                                this.$message.error(res.msg);
                            }
                        });

                        const now = new Date();
                        const dateString =
                            now.getFullYear() +
                            "-" +
                            (now.getMonth() + 1).toString().padStart(2, "0") +
                            "-" +
                            now.getDate().toString().padStart(2, "0");

                        request
                            .post("/logs", {
                                name: this.user.name,
                                content: this.money,
                                avatar: this.user.avatar,
                                type: 0,
                                time: dateString,
                            })
                            .then((res) => {
                                if (res.code === "0") {
                                    // this.$message.success("操作成功");
                                    // localStorage.setItem("user", JSON.stringify(yyy))
                                } else {
                                    this.$message.error(res.msg);
                                }
                            });

                        this.hongbaoopen = false;
                    }
                }
            } else {
                this.$message.error("请填写完整红包信息");
            }
        },
        cancel() {
            this.hongbaoopen = false;
        },
        getRandomNumber(min, max) {
            var result;
            do {
                // 生成0到1之间的随机数
                var randomNumber = Math.random();
                // 将随机数映射到指定范围内
                var scaledNumber = randomNumber * (max - min) + min;
                // 保留两位小数
                result = parseFloat(scaledNumber.toFixed(2));
            } while (result == min || result == max); // 如果随机数等于min或max，则重新生成
            return result;
        },
        dakai(e) {
            console.log("e", e);

            // 示例用法：生成0到10之间的随机数（包括0和10）

            if (e.content == "http://localhost:/api/files/cai.png") {
                // request.get('/logs').then(res => {
                //   console.log("logslogs",res)
                //
                // })
                request.get("/logs").then((res) => {
                    console.log("logslogs", res);
                    if (res.code === "0") {
                        let xxx = [];
                        let yyy = 0;
                        let zzz = 0;
                        res.data.forEach((item) => {
                            if (item.type == e.id) {
                                xxx.push(item);
                                yyy = yyy * 1 + item.content * 1;
                                if (
                                    item.name == JSON.parse(localStorage.getItem("user")).name
                                ) {
                                    zzz = 1;
                                }
                            }
                        });
                        //限领一次
                        if (zzz == 1) {
                            this.$message.error("您已经领过该红包！");
                        } else {
                            if (xxx.length < e.shuliang) {
                                const now = new Date();
                                const dateString =
                                    now.getFullYear() +
                                    "-" +
                                    (now.getMonth() + 1).toString().padStart(2, "0") +
                                    "-" +
                                    now.getDate().toString().padStart(2, "0");

                                if (xxx.length == e.shuliang * 1 - 1) {
                                    request
                                        .post("/logs", {
                                            name: this.user.name,
                                            content: e.money * 1 - yyy * 1,
                                            avatar: this.user.avatar,
                                            type: e.id,
                                            time: dateString,
                                        })
                                        .then((res) => {
                                            console.log("领红包", res);
                                            if (res.code === "0") {
                                                let userinfo = JSON.parse(localStorage.getItem("user"));
                                                userinfo.zongmoney =
                                                    userinfo.zongmoney * 1 + e.money * 1 - yyy * 1;
                                                request.post("/user", userinfo).then((res2) => {
                                                    if (res2.code === "0") {
                                                        // this.$message.success("操作成功");
                                                        localStorage.setItem(
                                                            "user",
                                                            JSON.stringify(userinfo)
                                                        );
                                                        this.$message.success(
                                                            "红包领取成功!金额：" + res.data.content + "元"
                                                        );
                                                    } else {
                                                        this.$message.error(res2.msg);
                                                    }
                                                });
                                            } else {
                                                this.$message.error(res.msg);
                                            }
                                        });
                                } else {
                                    var randomNum = this.getRandomNumber(
                                        0,
                                        e.money * 1 - yyy * 1
                                    );
                                    console.log(randomNum);
                                    request
                                        .post("/logs", {
                                            name: this.user.name,
                                            content: randomNum,
                                            avatar: this.user.avatar,
                                            type: e.id,
                                            time: dateString,
                                        })
                                        .then((res) => {
                                            console.log("领红包", res);
                                            if (res.code === "0") {
                                                let userinfo = JSON.parse(localStorage.getItem("user"));
                                                userinfo.zongmoney =
                                                    userinfo.zongmoney * 1 + randomNum * 1;
                                                request.post("/user", userinfo).then((res2) => {
                                                    if (res2.code === "0") {
                                                        // this.$message.success("操作成功");
                                                        localStorage.setItem(
                                                            "user",
                                                            JSON.stringify(userinfo)
                                                        );
                                                        this.$message.success(
                                                            "红包领取成功!金额：" + res.data.content + "元"
                                                        );
                                                    } else {
                                                        this.$message.error(res2.msg);
                                                    }
                                                });
                                            } else {
                                                this.$message.error(res.msg);
                                            }
                                        });
                                }
                            } else {
                                this.$message.error("红包已领完");
                            }
                        }

                        // this.$message.success('欢迎进入群组聊天室')
                    } else {
                        this.$message.error(res.msg);
                    }
                });
                // this.$message.success('恭喜抢到红包！')
            }
        },
        facai() {
            this.money = "";
            this.shuliang = "";
            this.hongbaoopen = true;
        },
        download(file) {
            window.open(file);
        },
        handleFile(file) {
            if (client) {
                let message = {
                    name: this.user.name,
                    content: file.data,
                    avatar: this.user.avatar,
                };
                let extName = file.data.substring(file.data.lastIndexOf(".") + 1);
                if (
                    ["png", "jpg", "jpeg", "gif", "bmp", "tiff", "svg", "webp"].includes(
                        extName
                    )
                ) {
                    message.type = "img";
                } else {
                    message.type = "file";
                }
                client.send(JSON.stringify(message));
            }
        },
        load() {
            request.get("/imGroup").then((res) => {
                if (res.code === "0") {
                    this.messages = res.data;
                    this.scrollToBottom(); // 滚动条滚动到最底部
                    this.$message.success("欢迎进入群组聊天室");
                } else {
                    this.$message.error(res.msg);
                }
            });
        },
        send() {
            let inputBox = document.getElementById("im-content");
            const content = inputBox.innerHTML;
            if (!content) {
                this.$notify.error("请输入聊天内容");
                return;
            }
            if (client) {
                let message = {
                    name: this.user.name,
                    content: content,
                    avatar: this.user.avatar,
                    type: "text",
                };
                client.send(JSON.stringify(message));
            }
            inputBox.innerHTML = ""; // 清空输入框
        },
        clickEmoji(emoji) {
            document.getElementById("im-content").innerHTML += emoji;
        },
        scrollToBottom() {
            this.$nextTick(() => {
                // 设置聊天滚动条到底部
                let imMessageBox = document.getElementsByClassName("im-message-box")[0];
                //设置滚动条到最底部
                imMessageBox.scrollTop = imMessageBox.scrollHeight;
            });
        },
        don() {

            axios({
                url: "http://localhost:8080/api/imGroup/don",
                method: 'GET',
                responseType: 'blob', // 重要：设置响应类型为blob
            }).then((response) => {
                const blob = new Blob([response.data], { type: 'application/octet-stream' });
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = '群组的聊天记录.xls'; // 设置下载文件名
                link.click();
            }).catch((error) => {
                console.error('下载文件时发生错误:', error);
            });

        },
    },
};
</script>

<style scoped>
.im-message-box {
    height: 65vh;
    padding: 10px;
    overflow-y: auto;
    background-color: white;
}

.emoji-box {
    height: 200px;
    overflow-y: scroll;
    line-height: 30px;
}

.user-list-box {
    height: calc(65vh + 60px);
    overflow-y: scroll;
    background-color: white;
}

.im-message-box::-webkit-scrollbar,
.emoji-box::-webkit-scrollbar,
.user-list-box::-webkit-scrollbar {
    width: 4px;
}

.im-message-box::-webkit-scrollbar-thumb,
.emoji-box::-webkit-scrollbar-thumb,
.user-list-box::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: rgba(0, 0, 0, 0);
}

.im-message-box::-webkit-scrollbar-track,
.emoji-box::-webkit-scrollbar-track,
.user-list-box::-webkit-scrollbar-track {
    border-radius: 0;
    background: rgba(0, 0, 0, 0.1);
}

.im-message {
    font-size: 14px;
    padding: 10px;
    margin: 5px 0;
    border-radius: 5px;
    color: #000;
    max-width: 50%;
    line-height: 20px;
    width: fit-content;
}

.im-message-left {
    background-color: #fff;
}

.im-message-right {
    background-color: #26d470;
}

.im-message-download {
    background-color: #dbedff;
    cursor: pointer;
}

.user-list-item {
    padding: 10px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #eee;
    cursor: default;
    font-size: 14px;
}

.user-list-item:nth-last-child(1) {
    border: none;
}
</style>
