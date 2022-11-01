<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styleLanding.css">
    <title>Mitra</title>
</head>
<body>
    <div class="transition-2 isActive"></div>


    <header class="header">
        <div class="container">
<div class="innerHeader">    
    <div class="left">
        <a href="http://google.com" class="link Left Logo" id="logo">MITRA</a>
    </div>

    <div class="right">
        <a class="link Right" id="Home">Головна</a>
        <a class="link Right" id="About">Про нас</a>
        <a class="link Right" id="Services">Переваги</a>
        <a class="link Right" id="Contact">Контакти</a>
    </div>     
</div> 
</div>
    </header>

    
    <div class="firstBlock">

        <div class="background">
        </div>
        <div class="container">

            <div class="titlesFirstBlock">
                <div class="title">MITRA</div>
                <div class="subTitle">Наша мета - об'єднати!</div>

                <a href="${pageContext.request.contextPath}/app/register">
                    <button type="submit" class="btn-blue firstBlockBtn">Зареєструйтесь!</button>
                </a>

            </div>
            
        </div>

    </div>





    <div class="secondBlock" id="blockAbout">
        <div class="container">
            <div class="secondBlockInner">

                <!-- <div class="secondBlockTitle">О нас</div> -->

            
            <div class="secondBlockSub">
                <div class="secondBlockImg"></div>

                <div class="secondBlockSubText">

                    <div class="secondBlockSubTitle">Що таке <span class="blueword">&nbsp;Mitra?</span></div>
                    <div class="secondBlockSubSub">Mitra - це платформа для знайомств музикантів. <br><br>У дійсності, необхідних людей доволі непросто знайти,
                         на це може піти безліч вашого золотого часу.<br>Mitra допоможе розв'язати ці питання, заощадить ваш час та зусилля. Ми зануримо вас у творчу
                         спільноту людей, насправді відданих музиці!</div>
                    <!-- <button class="btn-red secondBlockBtn">LEARN MORE</button> -->

                </div>
            </div>
        </div>
        </div>
    </div>


    <!-- THIRD BLOCK  -->

    <div class="thirdBlock" id="blockServices">
        <div class="container">
            <div class="thirdBlockInner">
    
                <div class="thirdBlockTitle">Наші переваги</div>
    
    
    
    
                <div class="thirdBlockSub">
    
                    
                    <div class="thirdBlockSubPart">
                       
                        <i class="fa fa-clock-o"></i>
                        <div class="SubPartTitle">Швидкість</div>
    
                        <div class="SubPartText">У людей не вистачає часу на реальні знайомства через завантаженість робочого дня та інші прикрі обставини. Соціальні мережі доволі громіздкі. Наша платформа поважає ваш час та зекономить його.</div>
    
                    </div>
                    
    
    
                    <div class="thirdBlockSubPart">
                        
                        <i class="fa fa-solid fa-crosshairs"></i>
                        <div class="SubPartTitle">Націленість</div>
    
                        <div class="SubPartText">Mitra - платформа націлена на творчих людей. Ми ставимо перед нами задачу створити дружню екосистему, у якій музиканти можуть без обмежень кооперувати та творити.  </div>
                    </div>
    
                    <div class="thirdBlockSubPart">
                       
                        <i class="fa fa-rocket"></i>
                        <div class="SubPartTitle">Ефективність</div>
    
                        <div class="SubPartText">Проста та ефективна система фільтрації анкет Mitra допоможе вам швидко знайти потрібну вам людину до вашого музичного гурту або для простого приємного спілкування.</div>
                    </div>   
                </div>
            </div>
        </div>
    </div>

    <!-- FOURTH BLOCK  -->

    <div class="fourthBlock" id="blockContact">
        <div class="container">
            <div class="fourthBlockInner">
    
                <div class="fourthBlockTitle">Наші Контакти</div>
       
                <div class="fourthBlockSub">
    
                    
                    <div class="fourthBlockSubPart">
                        <div class="contact-left">
                            <i class="fa fa-telegram" id="fourth-fa"></i>
                        </div>

                        <div class="contact-right">
                            <div class="fourthSubPartText">@MitraApp</div>
                        </div>
                    </div>
    
                    <div class="fourthBlockSubPart">
                        <div class="contact-left">
                        <i class="fa fa-phone" id="fourth-fa"></i>
                        </div>
                        <div class="contact-right">
                        <div class="fourthSubPartText">+380 50 274 65 11<br>+380 50 274 65 11</div>
                        </div>
                    </div>
    
                    <div class="fourthBlockSubPart">
                        <div class="contact-left">
                        <i class="fa fa-envelope-o" id="fourth-fa"></i>
                        </div>
                        <div class="contact-right">
                        <div class="fourthSubPartText">Mitra@mitra.com<br>Mitra@gmail.com</div>
                        </div>
                    </div>  
                </div>
            </div>
        </div>
    </div>








    <!-- FOOTER  -->

    <footer class="footer">

        <div class="container">
            <div class="rights">
                All rights reserved by Mitra
            </div>


        </div>

    </footer>




    



    

    <script src="${pageContext.request.contextPath}/resources/js/scriptLanding.js"></script>
</body>
</html>