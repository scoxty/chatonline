<template>
  <div>
    <div class="main-body-content">
      <div style="display: flex; align-items: flex-start; height: 80vh;">
        <div style="
            width: 200px;
            border: 1px solid #ddd;
            border-radius: 10px;
            height: 100%;">
          <div style="
              padding: 10px;
              border-bottom: 1px solid #ddd;
              color: #000;
              background-color: #eee;
              border-radius: 10px 10px 0 0;
            ">
            好友
          </div>
          <div class="user-list-box">
            <div class="user-list-item" v-for="item in users.admin" :key="item.id" @click="selectToUser(item)">
              <img :src="item.userAvatar" style="width: 30px; height: 30px; border-radius: 50%" />
              <span style="flex: 1; margin-left: 10px"
                :style="{ color: item.role + '_' + item.userName === toUser ? '#3a8ee6' : '', }">{{ item.userName }}
              </span>
              <div class="user-list-item-badge" v-if="unRead?.[item.role + '_' + item.userName]">
                {{ unRead?.[item.role + "_" + item.userName] }}
              </div>
            </div>
          </div>
        </div>
        <!--  中间部分  -->
        <div style="
            flex: 1;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f1f1f1;
            margin: 0 10px;
            height: 100%;
            display: flex;
            flex-direction: column;
            border-radius: 10px;
            ">
          <div style="
              padding: 20px 0;
              text-align: center;
              border-bottom: 1px solid #ddd;
              color: #000;
              background-color: #eee;
              height: 60px;
              border-radius: 10px 10px 0 0;
            ">
            {{ toUser?.substring(toUser.indexOf("_") + 1) }}
          </div>
          <div class="im-message-box">
            <div v-for="item in messages" :key="item.id">
              <!--  右边的气泡 -->
              <div style="
                  display: flex;
                  flex-direction: row-reverse;
                  align-items: flex-start;
                " v-if="item.fromuser === fromUser">
                <img :src="item.fromavatar" alt="" style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-left: 10px;
                  " />
                <div class="im-message im-message-right" v-html="item.content" v-if="item.type === 'text'"></div>
                <div class="im-message" style="padding: 0" v-if="item.type === 'img'">
                  <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
                  <el-image style="width: 100px" :src="item.content" alt="" :preview-src-list="[item.content]"
                    @load="scrollToBottom"></el-image>
                </div>
                <div class="im-message" v-if="item.type === 'video'">
                  <video controls style="width: 100%" :src="item.content" @loadeddata="scrollToBottom"></video>
                </div>
                <div class="im-message im-message-download" v-if="item.type === 'file'" @click="download(item.content)">
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
                <img :src="item.fromavatar" alt="" style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-right: 10px;
                  " />
                <div style="width: 100%">
                  <div style="color: #888; font-size: 12px; width: 50%">
                    {{ item.fromuser?.substring(item.fromuser.indexOf("_") + 1) }}
                  </div>
                  <div class="im-message im-message-left" v-html="item.content" v-if="item.type === 'text'"></div>
                  <div class="im-message" style="padding: 0" v-if="item.type === 'img'">
                    <!-- 注意  el-image 的load函数必须加上，否则无法触发滚动条到最底端 -->
                    <el-image style="width: 100px" :src="item.content" alt="" :preview-src-list="[item.content]"
                      @load="scrollToBottom"></el-image>
                  </div>
                  <div class="im-message" v-if="item.type === 'video'">
                    <video controls style="width: 100%" :src="item.content" @loadeddata="scrollToBottom"></video>
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
                  style="margin-right: 5px; font-size: 20px; cursor: pointer" v-html="item" @click="clickEmoji(item)">
                </span>
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
            <el-button type="primary" @click="don" style="border: none">下载聊天记录
            </el-button>
            <el-button type="primary" @click="send" style="border: none">发送
            </el-button>
          </div>
        </div>
        <!--  中间部分结束  -->
      </div>
    </div>

    <!-- 录音弹出层 -->
    <div v-if="recordingStatus !== 'idle'" class="recording-overlay">
      <div class="recording-box" style="margin-left: 30%;">
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
      permission: [],
      emojis: [],
      messages: [],
      users: {},
      fromUser: "",
      toUser: "",
      toAvatar: "",
      unRead: {},
      id: "",
      recordingStatus: 'idle',
      recordingTime: 0,
      recordingInterval: null,
      audioLevels: []
    };
  },
  // 页面加载的时候，做一些事情，在created里面
  mounted() {
    this.emojis = emojis.split(",");
    this.user = JSON.parse(localStorage.getItem("user") || "{}");
    this.fromUser = this.user.role + "_" + this.user.name;

    client = new WebSocket(`ws://localhost:8080/imserverSingle`);

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
        if (
          (json.content &&
            json.fromuser === this.fromUser &&
            json.touser === this.toUser) ||
          (json.touser === this.fromUser && json.fromuser === this.toUser)
        ) {
          // 聊天消息
          this.messages.push(json);
          this.scrollToBottom(); // 滚动页面到最底部
        }
        // 加载消息数字
        if (this.toUser === json.fromuser) {
          this.setUnReadNums(); // 清空正在聊天人的消息数字
        } else {
          this.loadUnReadNums();
        }
      }
    };

    // 加载聊天数据
    this.load();

    request.get("/hy/list", { params: { pageNum: 1, pageSize: 1000 } }).then((res) => {
      if (res.data && res.data.list) {
        this.$set(this.users, "admin", res.data.list);
      } else {
        console.error("Failed to load user list");
      }
    });

    // 添加键盘事件监听
    window.addEventListener('keydown', this.handleKeyDown);
    window.addEventListener('keyup', this.handleKeyUp);
  },

  beforeDestroy() {
    if (client) {
      client.close();
    }
    // 移除键盘事件监听
    window.removeEventListener('keydown', this.handleKeyDown);
    window.removeEventListener('keyup', this.handleKeyUp);
  },

  // 定义一些页面上控件触发的事件调用的方法
  methods: {
    load() {
      request
        .get("/imsingle?fromUser=" + this.fromUser + "&toUser=" + this.toUser)
        .then((res) => {
          if (res.code === "0") {
            console.log(res);
            this.messages = res.data;
            this.scrollToBottom(); // 滚动条滚动到最底部
          } else {
            this.$notify.error(res.msg);
          }
          this.loadUnReadNums();
        });
    },
    setUnReadNums() {
      request
        .get("/imsingle?fromUser=" + this.fromUser + "&toUser=" + this.toUser)
        .then((res) => {
          this.loadUnReadNums();
        });
    },
    loadUnReadNums() {
      // 查询未读数量
      request
        .get("/imsingle/unReadNums?toUsername=" + this.fromUser)
        .then((res) => {
          this.unRead = res.data;
        });
    },
    send() {
      if (!this.toUser) {
        this.$notify.error("请选择聊天用户");
        return;
      }
      if (client) {
        let message = this.getMessage("text");
        client.send(JSON.stringify(message));
      }
      document.getElementById("im-content").innerHTML = ""; // 清空输入框
    },
    selectToUser(item) {
      this.toUser = item.role + "_" + item.userName;
      this.toAvatar = item.userAvatar;
      //查询聊天记录
      this.load();
    },
    download(file) {
      window.open(file);
    },
    getMessage(type) {
      let inputBox = document.getElementById("im-content");
      const content = inputBox.innerHTML;
      if (!content && type === "text") {
        this.$notify.error("请输入聊天内容");
        return;
      }
      return {
        fromuser: this.fromUser,
        fromavatar: this.user.avatar,
        touser: this.toUser,
        toavatar: this.toAvatar,
        content: content,
        type: type,
      };
    },
    handleFile(file) {
      if (client) {
        let message = this.getMessage("img");
        message.content = file.data;
        console.log(message.content);
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
    clickEmoji(emoji) {
      document.getElementById("im-content").innerHTML += emoji;
    },
    scrollToBottom() {
      this.$nextTick(() => {
        // 设置聊天滚动条到底部
        let imMessageBox = document.getElementsByClassName("im-message-box")[0];
        //设置滚动条到最底部
        imMessageBox.scrollTop = imMessageBox.scrollHeight;
        console.log("触发滚动");
      });
    },
    don() {
      if (!this.toUser) {
        this.$notify.error("请选择聊天用户");
        return;
      } else {
        axios({
          url: "http://localhost:8080/api/imsingle/don?fromUser=" + this.fromUser + "&toUser=" + this.toUser,
          method: 'GET',
          responseType: 'blob', // 重要：设置响应类型为blob
          headers: {
            token: this.user.token  // 在这里添加你的token
          }
        }).then((response) => {
          const blob = new Blob([response.data], { type: 'application/octet-stream' });
          const link = document.createElement('a');
          link.href = URL.createObjectURL(blob);
          link.download = '聊天记录.xls'; // 设置下载文件名
          link.click();
        }).catch((error) => {
          console.error('下载文件时发生错误:', error);
        });
      }
    },
    showRecord() {
      if (!this.toUser) {
        this.$notify.error("请选择聊天用户");
        return;
      }
      this.recordingStatus = 'ready';
    },
    getWaveHeight() {
      return Math.random() * 20 + 5; // 示例，实际根据音频数据调整
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
            console.log(data);
            if (!data.data) {
              console.error("上传失败，服务器没有返回有效的URL");
              this.$notify.error("上传失败，服务器没有返回有效的URL");
              return;
            }
            let message = this.getMessage('audio');
            message.content = data.data;
            message.type = "audio";
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
  height: calc(100vh - 250px);
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
  height: calc(100vh - 185px);
  overflow-y: auto;
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
  display: flex;
  padding: 10px;
  align-items: center;
  border-bottom: 1px solid #eee;
  cursor: pointer;
  font-size: 14px;
}

.user-list-item-badge {
  padding: 2px 6px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: white;
  background-color: red;
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