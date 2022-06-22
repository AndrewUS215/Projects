    function a() {
        var amount = document.querySelectorAll('.count_form');
        var count = document.querySelectorAll('.calc_summa');
        var value = document.querySelectorAll("span[class='calc_summa']");
        for(let i = 0; i < amount.length; i++) {
            if(amount[i].value == 0) {
                count[i].innerText = 0;
            } else if(amount[i].value >= 1) {
                count[i].innerText = value[i].getAttribute('value') * amount[i].value;
            }
        }
        change();
    }

    window.onload = function() {
        a();
        change();
    };

    function change() {
        var items = document.querySelectorAll('.calc_summa'),
        summa = 0;
        for(let i = 0; i < items.length; i++) {
            summa += parseInt(items[i].textContent);
        }
        document.querySelector('.amount_result').innerText = summa;
    }


    function anc() {
        const anchors = document.querySelectorAll('a[href*="#"]')
        for (let anchor of anchors) {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const blockID = anchor.getAttribute('href').substr(1);
            document.getElementById(blockID).scrollIntoView({
            behavior: 'smooth',
            block: 'start'
            })
        })
    }
}
    
