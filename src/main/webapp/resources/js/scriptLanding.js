const transitionEl = document.querySelector(".transition-2");

window.onload = () => {
    console.log('window.onload');

    // const transitionEl = document.querySelector(".transition-2");

    setTimeout(() => {
        transitionEl.classList.remove("isActive");
    },200)

}


// СКАЧКИ 
const link = document.querySelectorAll(".link");


link.forEach(function(elem) {

    elem.addEventListener("click",function(){
        let destination = elem.id;
        let block = document.getElementById("block"+destination);
        let rect = block.getBoundingClientRect();

        window.scroll({
                top: rect.top,
                behavior: 'smooth',
              }); 
        
        

    });
})
