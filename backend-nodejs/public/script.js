const connectionStatus = document.getElementById("connection-status");
const connectBtn = document.getElementById("connect-btn");
const sendMessageBtn = document.getElementById("send-message-btn");
const myUserIdInput = document.getElementById("my-user-id");
const receiverIdInput = document.getElementById("receiver-id");
const notification = document.getElementById("notification");
const displayUserId = document.getElementById("display-user-id");
const dataInput = document.getElementById("data-input");

let socket;

myUserIdInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    connectBtn.click();
  }
});

receiverIdInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    sendMessageBtn.click();
  }
});

dataInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    sendMessageBtn.click();
  }
});

connectBtn.addEventListener("click", () => {
  if (!socket) {
    const myUserId = myUserIdInput.value;
    if (myUserId === "") {
      alert("내 id를 입력하세요.");
      return;
    }

    socket = io("https://heart-ing.com", { path: "/ws" });

    socket.on("connect", () => {
      console.log("웹소켓 서버에 연결");
      connectionStatus.textContent = "연결됨";
      connectionStatus.style.color = "blue";
      displayUserId.textContent = myUserId;
      socket.emit("join-room", myUserId);
    });

    socket.on("receive-message", (data) => {
      console.log("받은 메시지:", data);
      notification.innerHTML = `받은 메시지: ${JSON.stringify(data)}`;
    });

    socket.on("disconnect", () => {
      console.log("웹소켓 서버 연결 해제");
      connectionStatus.textContent = "연결 안됨";
      displayUserId.textContent = "N/A";
      connectionStatus.style.color = "red";
    });
  }
});

sendMessageBtn.addEventListener("click", () => {
  const userId = receiverIdInput.value;

  if (userId === "") {
    alert("메시지 수신 대상 유저의 id를 입력하세요.");
    return;
  }

  if (dataInput.value === "") {
    alert("데이터를 입력하세요.");
    return;
  }

  if (socket && socket.connected) {
    const data = dataInput.value;

    socket.emit("send-message", userId, data);
  } else {
    console.log("웹소켓 서버에 먼저 연걸하세요");
  }
});
