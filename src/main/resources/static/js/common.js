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
            document.querySelector(".yesterdayVisitCount").textContent = data.visitCount;
            document.querySelector(".nickname").textContent = data.nickname;
            document.querySelector(".hits").textContent = data.hits;
            document.querySelector(".loader").style.setProperty('--heart-beat-rate',heart_beat_rate(data.hits) + 's')
            document.querySelector(".today-comment").textContent = data.todayComment;
        }catch (e){
            document.querySelector(".visit").textContent = "에러 발생! 관리자에게 문의 요구";
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