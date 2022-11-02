const transitionEl = document.querySelector(".transition-2");

let stage = 1;

window.onload = () => {
    console.log('window.onload');
    // stage = 1;
    // const transitionEl = document.querySelector(".transition-2");

    setTimeout(() => {
        transitionEl.classList.remove("isActive");
    },200)

}
// INPUTS
const inputName = document.querySelector("#name-input-box");

const inputAge = document.querySelector("#age-input-box");

const inputSex = document.querySelector("#sex-input-box");

const inputText = document.querySelector("#text-input-box");

const inputLocation = document.querySelector("#location-input-box");


// TITLE 
const title = document.querySelector(".title");

// DESCRIPTION
const description = document.querySelector(".description");

// ICONS
const iconName = document.querySelector("#icon-name");

const iconAge = document.querySelector("#icon-age");

const iconSex = document.querySelector("#icon-sex");

const iconText = document.querySelector("#icon-text");

const iconLocation = document.querySelector("#icon-location");

// BUTTON 
const btnNext = document.querySelector(".main-button")


const changeStages = function (){
    if (stage ===1){
        transitionEl.classList.add("isActive");
        setTimeout(() => {
            iconName.classList.remove("display-true");
            iconName.classList.add("display-none");
            iconAge.classList.add("display-true");
            transitionEl.classList.remove("isActive");

            inputName.classList.remove("display-true");
            inputName.classList.add("display-none");

            inputAge.classList.add("display-true");

            title.textContent = "Скільки тобі років?";

            description.textContent = "Напиши свій вік. Твій вік допоможе нам краще підбирати для тебе анкети.";

        },400)
    }
    if (stage ===2){
        transitionEl.classList.add("isActive");
        setTimeout(() => {
            iconAge.classList.remove("display-true");
            iconAge.classList.add("display-none");
            iconSex.classList.add("display-true");
            transitionEl.classList.remove("isActive");

            inputAge.classList.remove("display-true");
            inputAge.classList.add("display-none");

            inputSex.classList.remove("display-none");
            inputSex.classList.add("display-true");


            title.textContent = "Якої ти статі?";

            description.textContent = "Вкажи свою стать.";
        },400)
    }
    if (stage ===3){
        transitionEl.classList.add("isActive");
        setTimeout(() => {
            iconSex.classList.remove("display-true");
            iconSex.classList.add("display-none");
            iconText.classList.add("display-true");
            transitionEl.classList.remove("isActive");

            inputSex.classList.remove("display-true");
            inputSex.classList.add("display-none")

            inputText.classList.remove("display-none");
            inputText.classList.add("display-true");

            title.textContent = "Напиши про себе";

            description.textContent = "Не хочеш нічого добавити? Дай іншим дізнатись тебе краще!";
        },400)
        
    }
    if (stage ===4){
        transitionEl.classList.add("isActive");
        setTimeout(() => {
            iconText.classList.remove("display-true");
            iconText.classList.add("display-none");
            iconLocation.classList.add("display-true");
            transitionEl.classList.remove("isActive");

            inputText.classList.remove("display-true");
            inputText.classList.add("display-none")

            inputLocation.classList.remove("display-none");
            inputLocation.classList.add("display-true");



            title.textContent = "Звідки ти?";

            description.textContent = "Вкажи де ти знаходишся. Твоє місцеположення допоможе нам краще підбирати для тебе анкети.";
        },400) 
    }

    if (stage <5){
        stage++;
    }

    if (stage >=5){
        console.log("OVER");
    }
    console.log(stage);
}



//BUTTON EVENT

btnNext.addEventListener("click", () => {
    changeStages();
});