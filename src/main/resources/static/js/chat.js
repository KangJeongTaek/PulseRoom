const socket = new SockJS("/pulse-chat")
const stompClient = Stomp.over(socket);
stompClient.debug = null;
stompClient.connect({},function(){


       stompClient.subscribe("/topic/public",function(message){
            const chatMessage = JSON.parse(message.body);
           add_chat_message(chatMessage)
       })
});

function sendChat(){
     const inputElement = document.querySelector(".message-input");
     const messageContent = inputElement.value.trim();

     if(messageContent.length > 50){
         alert('50자 이상 입력할 수 없습니다.');
         return
     }

       if (messageContent) {
              const chatMessage = {
                  content: messageContent
              }

        stompClient.send("/chat/sendChat", {}, JSON.stringify(chatMessage));
        inputElement.value = "";
       }
}