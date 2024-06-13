<template>
    <div>
        <div class="main-body-content">
            <div style="display: flex; align-items: flex-start">
                <div style="
              width: 200px;
              margin-right: 10px;
              border: 1px solid #ddd;
              border-radius: 5px;
            ">
                    <div style="
                padding: 20px 10px;
                border-bottom: 1px solid #ddd;
                color: #000;
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
              width: 50%;
              border: 1px solid #ddd;
              border-radius: 5px;
              background-color: #f1f1f1;
            ">
                    <div style="
                padding: 20px 0;
                text-align: center;
                border-bottom: 1px solid #ddd;
                color: #000;
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
                                <div class="im-message im-message-right" v-html="item.content"
                                    v-if="item.type === 'text'"></div>
                                <div class="im-message" style="padding: 0" v-if="item.type === 'img'"
                                    @click="dakai(item)">
                                    <el-image v-if="item.content == 'http://localhost:8080/api/files/cai.png'"
                                        style="width: 200px" :src="item.content" alt="" :preview-src-list="[url]"
                                        @load="scrollToBottom"></el-image>
                                    <el-image v-else style="width: 100px" :src="item.content" alt=""
                                        :preview-src-list="[item.content]" @load="scrollToBottom"></el-image>
                                </div>
                                <div class="im-message" v-if="item.type === 'video'">
                                    <video controls style="width: 100%" :src="item.content"
                                        @loadeddata="scrollToBottom"></video>
                                </div>
                                <div class="im-message im-message-download" v-if="item.type === 'file'"
                                    @click="download(item.content)">
                                    <div>
                                        <i class="el-icon-folder-opened"></i>
                                        <span>{{ item.content.substring(item.content.indexOf("-") + 1) }}</span>
                                    </div>
                                </div>
                                <div class="im-message" style="padding: 0" v-if="item.type === 'audio'">
                                    <audio controls :src="item.content"></audio>
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
                                    <div class="im-message im-message-left" v-html="item.content"
                                        v-if="item.type === 'text'"></div>
                                    <div class="im-message" style="padding: 0" v-if="item.type === 'img'"
                                        @click="dakai(item)">
                                        <el-image v-if="item.content == 'http://localhost:8080/api/files/cai.png'"
                                            style="width: 200px" :src="item.content" alt="" :preview-src-list="[url]"
                                            @load="scrollToBottom"></el-image>
                                        <el-image v-else style="width: 100px" :src="item.content" alt=""
                                            :preview-src-list="[item.content]" @load="scrollToBottom"></el-image>
                                    </div>
                                    <div class="im-message" v-if="item.type === 'video'">
                                        <video controls style="width: 100%" :src="item.content"
                                            @loadeddata="scrollToBottom"></video>
                                    </div>
                                    <div class="im-message im-message-download" v-if="item.type === 'file'"
                                        @click="download(item.content)">
                                        <div>
                                            <i class="el-icon-folder-opened"></i>
                                            <span>{{ item.content.substring(item.content.indexOf("-") + 1) }}</span>
                                        </div>
                                    </div>
                                    <div class="im-message" style="padding: 0" v-if="item.type === 'audio'">
                                        <audio controls :src="item.content"></audio>
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
                        <div style="margin-left: 5px">
                            <i class="fa fa-microphone" style="font-size: 22px; color: #666; cursor: pointer;"
                                @click="showRecord"></i>
                        </div>
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
                        <el-button type="primary" @click="send" style="border: none">发送</el-button>
                    </div>
                </div>
                <!--  中间部分结束  -->
            </div>
        </div>

        <!-- 录音弹出层 -->
        <div v-if="recordingStatus !== 'idle'" class="recording-overlay">
            <div class="recording-box">
                <div v-if="recordingStatus === 'ready'">
                    <i class="fa fa-microphone" style="font-size: 100px; color: #0e81de; cursor: pointer;"></i>
                    <p>按住空格键开始说话，按Esc键或点击<a @click="cancelRecording" class="cancel-link">退出</a></p>
                </div>
                <div v-if="recordingStatus === 'recording'">
                    <p>{{ recordingTime.toFixed(1) }}s</p>
                    <div class="microphone-animation">
                        <div class="wave" v-for="i in 10" :key="i" :style="{ height: getWaveHeight() + 'px' }"></div>
                    </div>
                    <p>松手发送，按Esc键或点击<a @click="cancelRecording" class="cancel-link">取消发送</a></p>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import request from "@/utils/request";
import emojis from "@/assets/emoji";
import axios from 'axios';

let client;
let mediaRecorder;
let audioChunks = [];
let audioContext, analyser, microphone, javascriptNode;
export default {
    data() {
        return {
            user: {},
            emojis: [],
            messages: [],
            users: [],
            recordingStatus: 'idle',
            recordingTime: 0,
            recordingInterval: null,
            audioLevels: []
        };
    },
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
            console.log("websocket close");
        };
        client.onmessage = (msg) => {
            if (msg.data) {
                let json = JSON.parse(msg.data);
                if (json.name && json.content) {
                    this.messages.push(json);
                    this.scrollToBottom();
                }
                if (json.users && json.users.length) {
                    this.users = json.users;
                }
            }
        };
        this.load();
        window.addEventListener('keydown', this.handleKeyDown);
        window.addEventListener('keyup', this.handleKeyUp);
    },
    beforeDestroy() {
        if (client) {
            client.close();
        }
        window.removeEventListener('keydown', this.handleKeyDown);
        window.removeEventListener('keyup', this.handleKeyUp);
    },
    methods: {
        load() {
            request.get("/imGroup").then((res) => {
                if (res.code === "0") {
                    this.messages = res.data;
                    this.scrollToBottom();
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
            inputBox.innerHTML = "";
        },
        clickEmoji(emoji) {
            document.getElementById("im-content").innerHTML += emoji;
        },
        scrollToBottom() {
            this.$nextTick(() => {
                let imMessageBox = document.getElementsByClassName("im-message-box")[0];
                imMessageBox.scrollTop = imMessageBox.scrollHeight;
            });
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
                } else if (
                    ["mp4", "avi", "mkv", "mov", "wmv", "flv"].includes(
                        extName
                    )
                ) {
                    message.type = "video";
                } else {
                    message.type = "file";
                }
                client.send(JSON.stringify(message));
            }
        },
        showRecord() {
            this.recordingStatus = 'ready';
        },
        getWaveHeight() {
            return Math.random() * 20 + 5;
        },
        cancelRecording() {
            this.recordingStatus = 'idle';
            clearInterval(this.recordingInterval);
            this.recordingInterval = null;
            if (audioContext && audioContext.state !== 'closed') {
                audioContext.close();
            }
            this.audioLevels = [];
        },
        startRecording() {
            this.recordingStatus = 'recording';
            this.recordingTime = 0;
            audioChunks = [];

            navigator.mediaDevices.getUserMedia({ audio: true })
                .then(stream => {
                    mediaRecorder = new MediaRecorder(stream);
                    mediaRecorder.start();
                    mediaRecorder.ondataavailable = e => audioChunks.push(e.data);

                    audioContext = new AudioContext();
                    analyser = audioContext.createAnalyser();
                    microphone = audioContext.createMediaStreamSource(stream);
                    javascriptNode = audioContext.createScriptProcessor(2048, 1, 1);

                    analyser.smoothingTimeConstant = 0.3;
                    analyser.fftSize = 1024;

                    microphone.connect(analyser);
                    analyser.connect(javascriptNode);
                    javascriptNode.connect(audioContext.destination);

                    javascriptNode.onaudioprocess = () => {
                        const array = new Uint8Array(analyser.frequencyBinCount);
                        analyser.getByteFrequencyData(array);
                        const average = array.reduce((a, b) => a + b) / array.length;
                        this.audioLevels = Array.from({ length: 10 }, () => average / 2);
                    };

                    this.recordingInterval = setInterval(() => {
                        this.recordingTime += 0.1;
                    }, 100);
                });
        },
        stopRecording() {
            if (this.recordingStatus !== 'recording') return;

            mediaRecorder.stop();
            clearInterval(this.recordingInterval);
            this.recordingInterval = null;

            if (audioContext && audioContext.state !== 'closed') {
                audioContext.close();
            }
            this.audioLevels = [];

            if (this.recordingTime < 1) {
                setTimeout(() => this.cancelRecording(), 1000);
                this.$notify.error("按键时间太短");
                return;
            }

            mediaRecorder.onstop = () => {
                const audioBlob = new Blob(audioChunks, { type: 'audio/mpeg' });
                const formData = new FormData();
                formData.append('file', audioBlob, 'audio.mp3');

                fetch('http://localhost:8080/api/files/upload', {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (!data.data) {
                            console.error("上传失败，服务器没有返回有效的URL");
                            this.$notify.error("上传失败，服务器没有返回有效的URL");
                            return;
                        }
                        let message = {
                            name: this.user.name,
                            content: data.data,
                            avatar: this.user.avatar,
                            type: 'audio',
                        };
                        client.send(JSON.stringify(message));
                        this.recordingStatus = 'idle';
                    })
                    .catch(error => {
                        console.error("上传失败", error);
                        this.$notify.error("上传失败");
                    });
            };
        },
        handleKeyDown(event) {
            if (event.key === ' ') {
                event.preventDefault();
                if (this.recordingStatus === 'ready') {
                    this.startRecording();
                }
            } else if (event.key === 'Escape') {
                this.cancelRecording();
            }
        },
        handleKeyUp(event) {
            if (event.key === ' ' && this.recordingStatus === 'recording') {
                this.stopRecording();
            }
        }
    },
};
</script>

<style scoped>
.im-message-box {
    height: 50vh;
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
    height: calc(50vh + 60px);
    overflow-y: scroll;
    background-color: #f8f8ff;
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
    background: rgba(0, 0, 0, 0.1);
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

.recording-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
}

.recording-box {
    background: white;
    padding: 20px;
    border-radius: 5px;
    text-align: center;
}

.microphone-icon {
    width: 24px;
    height: 36px;
    background-color: #0e81de;
    border-radius: 12px 12px 4px 4px;
    position: relative;
    margin: 0 auto;
}

.microphone-icon::before {
    content: '';
    position: absolute;
    bottom: -6px;
    left: 50%;
    width: 16px;
    height: 4px;
    background-color: #0e81de;
    border-radius: 2px;
    transform: translateX(-50%);
}

.microphone-icon::after {
    content: '';
    position: absolute;
    bottom: -20px;
    left: 50%;
    width: 4px;
    height: 14px;
    background-color: #0e81de;
    border-radius: 2px;
    transform: translateX(-50%);
}

.microphone-animation {
    width: 50px;
    height: 50px;
    margin: 0 auto;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: flex-end;
}

.wave {
    width: 5px;
    background-color: #3a8ee6;
    display: inline-block;
    margin: 0 1px;
    animation: wave 1s infinite;
}

@keyframes wave {
    0% {
        transform: scaleY(1);
    }

    50% {
        transform: scaleY(1.5);
    }

    100% {
        transform: scaleY(1);
    }
}

.cancel-link {
    color: #3a8ee6;
    cursor: pointer;
}
</style>