const express = require("express");
const cors = require("cors");
const path = require("path");

// Express 인스턴스 초기화
const app = express();

// Enable CORS
app.use(
  cors({
    origin: "heart-ing.com",
    // origin: "*",
    methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
    allowedHeaders: ["Content-Type", "Authorization", "X-Requested-With"],
    credentials: true,
  })
);

// HTTP 서버 초기화 -> socket.io가 필요로 함
const http = require("http");
const server = http.createServer(app);

// socket.io 인스턴스 초기화
const { Server } = require("socket.io");
const io = new Server(server, {
  path: "/ws",
  cors: {
    origin: "heart-ing.com",
    // origin: "*",
    methods: ["GET", "POST"],
    allowedHeaders: ["X-Requested-With", "content-type"],
    credentials: true,
  },
});

// REDIS 부분
const { createClient } = require("redis");
const { createAdapter } = require("@socket.io/redis-adapter");

const pubClient = createClient({
  url: "redis://hearting-redis-cluster:6380",
});
const subClient = pubClient.duplicate();

Promise.all([pubClient.connect(), subClient.connect()]).then(() => {
  io.adapter(createAdapter(pubClient, subClient));
});

pubClient.on("error", (err) => {
  console.error("Error with the Redis pubClient:", err);
});

subClient.on("error", (err) => {
  console.error("Error with the Redis subClient:", err);
});

// 샘플 FE 코드 위치 설정
app.use("/wssample", express.static(`${__dirname}/public`));

// 웹소켓 연결에 대한 Event Listener 설정
io.on("connection", (socket) => {
  console.log("User connected:", socket.id);

  // 유저가 로그인할때 보내는 event
  socket.on("join-room", (userId) => {
    console.log("User joined room:", userId);

    // 해당 클라이언트의 socket을 userId라는 이름을 가진 방에 추가한다
    // 이제부터 이 클라이언트는 이 방으로 들어오는 모든 event를 감지한다
    socket.join(userId);
  });

  // 유저가 성공적으로 메시지를 보냈다는 SpringBoot의 확인 후 클라이언트에서 해당 event 발송
  socket.on("send-message", (userId, data) => {
    console.log("Message sent to:", userId, "\nData:", data);
    // 메시지 수신하는 유저에게 receive-message event 발송
    socket.to(userId).emit("receive-message", data);
  });

  // 웹소켓 연결 해제
  socket.on("disconnect", () => {
    console.log("User disconnected:", socket.id);
  });
});

module.exports = server;
