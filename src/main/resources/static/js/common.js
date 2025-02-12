function onLoad(){
    document.addEventListener("DOMContentLoaded",function(){
        visit()
    })

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
            document.querySelector(".visitCount").textContent = data.visitCount;
            document.querySelector(".nickname").textContent = data.nickname;
            document.querySelector(".message").textContent = data.message;
        }catch (e){
            document.querySelector(".visit").textContent = "에러 발생! 관리자에게 문의 요구";
        }
    }
}