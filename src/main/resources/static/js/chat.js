const socket = new SockJS("/pulse-chat")
const stompClient = Stomp.over(socket);

stompClient.connect({},function(){
       stompClient.debug = () => {};

       stompClient.subscribe("/topic/public",function(message){
            const chatMessage = JSON.parse(message.body);
            console.log(message);
            /* TODO : 추가되는 메시지 추가하기*/
       })
});

function sendChat(){
     const inputElement = document.querySelector(".message-input");
      const messageContent = inputElement.value.trim();



       if (messageContent) {
              const chatMessage = {
                  content: messageContent
              }

        stompClient.send("/chat/sendChat", {}, JSON.stringify(chatMessage));
        inputElement.value = "";
       }
}