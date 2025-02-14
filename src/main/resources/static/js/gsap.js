document.addEventListener("DOMContentLoaded", (event) => {
  gsap.registerPlugin(ScrollTrigger,TextPlugin)

   gsap.from(".yesterday-message", {
      opacity: 0,
      y: -30,
      duration: 1,
      ease: "power2.out"
    });

   gsap.from(".welcome-message", {
      opacity: 0,
      x: 50,
      duration: 1,
      ease: "power2.out"
    });

   gsap.from(".today-message", {
      opacity: 0,
      y: 30,
      duration: 1,
      ease: "power2.out"
    });


   let sections = gsap.utils.toArray('.sec');

   let firstTop = sections[0].getBoundingClientRect().top;

   console.log(firstTop)





    sections.forEach((sec, i) => {
        if(i === 0){
             gsap.to(sec, {
                 opacity: 0,
                 scrollTrigger: {
                     trigger: sec,
                     start: `top ${firstTop}`,
                     end: "top top",
                     scrub: 1,
                     pin: true,
                     pinSpacing : false,
                     markers : true
                 }
            });
        }
        if(i !== 0){
            gsap.set(sec,{opacity : 0})
            gsap.to(sec,{
            opacity : 1,
            scrollTrigger :{
                 trigger: sec,
                 start: "top 50%",
                 end: `top ${firstTop}`,
                 scrub: 1,
                 markers : true
            }
            });
        }
     });
});