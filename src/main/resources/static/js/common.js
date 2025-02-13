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
            document.querySelector(".yesterdayVisitCount").textContent = data.yesterdayVisitCount;
            document.querySelector(".nickname").textContent = "안녕하세요" + data.nickname + "님! 오늘도 반갑습니다!";
            document.querySelector(".hits").textContent = data.hits;
        }catch (e){
            document.querySelector(".visit").textContent = "에러 발생! 관리자에게 문의 요구";
        }
    }
}