function card_minimize(){
    const card = document.querySelector('.card')
    const card_container = document.querySelector('.card-container')
    gsap.to(
        card,{
            opacity : 0,
            height : "20%",
            width : '4%',
            duration : 0.8,
            onComplete : () => {
                card.style.display = "none";

            // 최소화된 카드(버튼) 생성
            const minimizedCard = document.createElement("div");
            minimizedCard.classList.add('chat-header');
            minimizedCard.innerText = "열기";
            minimizedCard.style.cursor = 'pointer';
            minimizedCard.style.width = '60px';
            minimizedCard.style.height = '50px';
            minimizedCard.style.borderRadius = '10px';
            minimizedCard.style.backgroundColor = '#333';
            minimizedCard.style.display = "flex";
            minimizedCard.style.alignItems = "center";
            minimizedCard.style.justifyContent = "center";
            minimizedCard.style.fontSize = "20px";
            minimizedCard.style.position = "absolute";
            minimizedCard.style.bottom = "10px";
            minimizedCard.style.right = "10px";
            minimizedCard.style.opacity = "0";

            card_container.appendChild(minimizedCard);


            gsap.to(minimizedCard, { opacity: 1, scale: 1, duration: 0.5, ease: "power2.out" });

            minimizedCard.addEventListener('click' , function(){
                 card.style.display = "block";
                gsap.to(card,{
                    opacity :1,
                    height : '100%',
                    width : '30%',
                    duration : 0.8,
                    onStart : () =>{
                        minimizedCard.remove();
                    }
                }
                );
                }
            );

            }


        }
    )
}