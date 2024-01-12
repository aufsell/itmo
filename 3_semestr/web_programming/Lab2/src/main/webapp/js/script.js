document.addEventListener("DOMContentLoaded", function () {
    initCheckboxGroup(".checkbox-input-R");
    initCheckboxGroup(".checkbox-input-X");
    initY(".y-value-input");
    submit(".verify-button");
});
let x;
let y;
let r;

function initCheckboxGroup(selector) {
    const checkboxes = document.querySelectorAll(selector);

    checkboxes.forEach(function (checkbox) {
        const customCheckbox = checkbox.nextElementSibling;

        customCheckbox.addEventListener("click", ()=> {
            const className = selector.replace('.','');
            if (className == 'checkbox-input-R'){
                r = parseInt(customCheckbox.querySelector('.checkbox-text').textContent);
                redrawGraph(r);
            } else {
                x = parseInt(customCheckbox.querySelector('.checkbox-text').textContent);
            }
            checkboxes.forEach(function (otherCheckbox) {
                const otherCustomCheckbox = otherCheckbox.nextElementSibling;
                if (checkbox !== otherCheckbox) {
                    otherCustomCheckbox.classList.remove("selected");
                    otherCheckbox.checked = false;
                }
            });

            checkbox.checked = !checkbox.checked;
            updateCheckboxStyle();
            a(className);
        });

        function updateCheckboxStyle() {
            if (checkbox.checked) {
                customCheckbox.classList.add("selected");
            } else {
                customCheckbox.classList.remove("selected");
            }
        }
        //функция делает r и x неопределенными если с чекбокса снимаем выбор. Да, эта штука работает =/
        function a(className){
            const anySelected = Array.from(checkboxes).some((cb) => cb.checked);
            if (className == 'checkbox-input-R' && !anySelected) {
                r = undefined;
            }
            if (className != 'checkbox-input-R' && !anySelected) {
                x = undefined;
            }
        }
    });
}


function initY(selector){
    let inp = document.querySelector(selector);
    inp.addEventListener('input', handleInput)


    function handleInput(event) {
        let inputValue = event.target.value;
        event.target.value = inputValue.replace(/[^0-9,-]/g, '');
        let q = event.target.value.split(',');
        if(q.length>1) {
            if (q[1].length > 8) {
                event.target.value = q[0] + ',' + q[1].slice(0, 8);
            }
        }
        if (parseFloat(event.target.value.replace(',', '.')) < -3 || parseFloat(event.target.value.replace(',', '.')) > 3){
            inp.classList.add('incorrectY')
            event.target.value = '';
            y = undefined
        }
        else{
            y = parseFloat(event.target.value.replace(',', '.'))
            inp.classList.remove('incorrectY')
        }
    }
}

function submit(selector){
    const btn = document.querySelector(selector);
    btn.addEventListener(
        'mouseenter',
        () => {
            if (x === undefined){
                const elements = document.querySelectorAll(".checkbox-input-X");
                elements.forEach(el => {
                    el.nextElementSibling.classList.add('noSelected')
                    btn.classList.add('disabledBtn');
                });
            }
            if (r === undefined){
                const elements = document.querySelectorAll(".checkbox-input-R");
                elements.forEach(el => {
                    el.nextElementSibling.classList.add('noSelected');
                    btn.classList.add('disabledBtn');
                });
            }
            if (y === undefined || isNaN(y)){
                const element = document.querySelector(".y-value-input");
                element.classList.add('noSelected');
                btn.classList.add('disabledBtn');

            }
        }
    );

    btn.addEventListener(
        'mouseleave',
        () => {

                let elements = document.querySelectorAll(".checkbox-input-X");
                elements.forEach(el => {
                    el.nextElementSibling.classList.remove('noSelected')
                });


                elements = document.querySelectorAll(".checkbox-input-R");

                elements.forEach(el => {
                    el.nextElementSibling.classList.remove('noSelected')
                });


                const element = document.querySelector(".y-value-input");
                element.classList.remove('noSelected');

            btn.classList.remove('disabledBtn');

        }
    );
    btn.addEventListener(
        'click',
        () => {
            console.log(x,y,r)
            const checkUndefined = (...values) => values.some(value => value === undefined);
            if (checkUndefined(x,y,r)) {
                alert('Заполните все необходимые поля!')
            }
            if(x != undefined && !isNaN(x) && y != undefined && !isNaN(y) && r != undefined && !isNaN(r)) {

                const form = $('<form>', {
                    action: "controller",
                    method: "post"
                });

                const args = { action: "submit", x: x, y: y, r: r };
                Object.entries(args).forEach(entry => {
                    const [key, value] = entry;
                    $('<input>').attr({
                        type: "hidden",
                        name: key,
                        value: value
                    }).appendTo(form);
                });
                //
                form.appendTo('body').submit();

            }
            printDotOnGraph(x,y,true);
            console.log('print s scr');
        });
}





