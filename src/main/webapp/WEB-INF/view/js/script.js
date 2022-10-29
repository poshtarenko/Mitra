window.onload = () => {

    const transitionEl = document.querySelector(".transition-2");
    const anchors = document.querySelectorAll('.jump');

    setTimeout(() => {
        transitionEl.classList.remove("isActive");
    },200)


    for(let i = 0; i < anchors.length; i++) {
        const anchor = anchors[i];
        anchor.addEventListener("click",e=>{
            
            e.preventDefault();
        
            let target = e.target.href;
            transitionEl.classList.add("isActive");
            setTimeout(() => {
                window.location.href = target;
            },200)
        });

    }
}