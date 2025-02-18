function onLoad(){
    document.addEventListener("DOMContentLoaded",function(){
        visit()

        /* Input 태그 엔터 키 eventListener */
        const messageInput = document.querySelector('.message-input')
        messageInput.addEventListener('keydown',function (event){
            if(event.key === "Enter"){
                event.preventDefault();
                sendChat();
            }
        });
        /* Input 태그 50자 이상 입력 불가능 */
        messageInput.addEventListener("input", function () {
            if (this.value.length > 50) {
                this.value = this.value.substring(0, 50);
                alert('50자 이상 입력할 수 없습니다.');
            }
        });
    });

    async function visit(){
        try{
            const response = await fetch("/visit",{
                method : 'GET'
            });
            if(!response.ok){
                document.querySelector(".visitCount").textContent = "에러 발생! 관리자에게 문의 요구";
                return;
            }
            const data = await response.json()
            document.querySelector(".yesterdayVisitCount").textContent = data.visitCount;
            document.querySelector(".nickname").textContent = data.nickname;
            document.querySelector(".hits").textContent = data.hits;
            document.querySelector(".loader").style.setProperty('--heart-beat-rate',heart_beat_rate(data.hits) + 's')
            document.querySelector(".today-comment").textContent = data.todayComment;

            for (let todayChatElement of data.todayChat) {
                add_chat_message(todayChatElement)
            }
        }catch (e){
            alert("에러 발생! 관리자에게 문의 요구")
        }
    }
}

/* 심장 박동 수 애니메이션 값 구하기*/
function heart_beat_rate(input) {
    if (input <= 0) {
        return 8;
    } else if (input >= 100) {
        return 0.5;
    } else {
        // 0~100 구간에서 8 → 0.5까지 선형적으로 감소
        return 8 - (7.5 * (input / 100));
    }
}

/* 채팅 ul 추가하기*/
function add_chat_message(chatObject){
    const chatWindow = document.querySelector(".chat-window");
    const messageList = document.createElement("ul");
    messageList.classList.add("message-list","mt-3","mb-3","ms-3","me-3","d-flex","align-items-center","justify-content-between","text-wrap");
    messageList.style.borderRadius = "7px";
    messageList.style.backgroundColor = "#ffefd5";
    const messageContent = document.createElement("span");
    messageContent.classList.add("ms-2","col-7","text-start");
    messageContent.style.fontSize = "0.9rem";
    const messageDate = document.createElement("span");
    messageDate.classList.add("ms-2","me-2","text-end");
    messageDate.classList.add("fw-lighter");
    messageDate.style.fontSize = "0.7rem";
    const crtDt = new Date(chatObject.crtDt);
    const year = crtDt.getFullYear();
    const month = crtDt.getMonth() +1;
    const date = crtDt.getDate();
    const hours = crtDt.getHours();
    const minutes = crtDt.getMinutes();
    const seconds = crtDt.getSeconds();
    messageContent.innerText = chatObject.content;
    messageDate.innerText = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
    messageList.append(messageContent,messageDate);
    chatWindow.appendChild(messageList)
    chatWindow.scrollTo({
        top : chatWindow.scrollHeight,
        behavior : "smooth"
    })
}