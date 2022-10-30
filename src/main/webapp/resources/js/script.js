const transitionEl = document.querySelector(".transition-2");

window.onload = () => {
    console.log('window.onload');

    // const transitionEl = document.querySelector(".transition-2");

    setTimeout(() => {
        transitionEl.classList.remove("isActive");
    },200)

}


document.addEventListener('DOMContentLoaded', function(){
    console.log('document');
    setTimeout(() => {
        transitionEl.classList.remove("isActive");
    },200)
});

// document.onreadystatechange = () => {
//     if (document.readyState === 'complete') {
//       // document ready
//     }
//   };