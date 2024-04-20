<template>
  <div>
    <h2>Chat Room</h2>
    <ul v-for="msg in messages" :key="msg.id">
      <li>{{ msg.sender }}: {{ msg.content }}</li>
    </ul>
    <input v-model="newMessage" placeholder="Type a message..." @keyup.enter="sendMessage">
    <button @click="sendMessage">Send</button>
    <button @click="loadHistory">Load History</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const messages = ref([]);
const newMessage = ref('');
const receiver = 'receiverUsername'; // Set the receiver's username
const sender = 'senderUsername'; // Set the sender's username
let stompClient = null;

const connect = () => {
  const socket = new SockJS('http://localhost:8080/chat');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    stompClient.subscribe(`/user/queue/messages`, chatMessage => {
      messages.value.push(JSON.parse(chatMessage.body));
    });
  });
};

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    const chatMessage = { sender: sender, receiver: receiver, content: newMessage.value };
    stompClient.send("/app/chat.send", JSON.stringify(chatMessage), {});
    newMessage.value = '';
  }
};

const loadHistory = async () => {
  const response = await fetch(`http://localhost:8080/messages/receiver/${receiver}`);
  const historyMessages = await response.json();
  messages.value = historyMessages.concat(messages.value);
};

onMounted(connect);
</script>