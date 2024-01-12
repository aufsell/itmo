const canvas = document.getElementById("graph")


    ctx = canvas.getContext('2d');
    ctx.imageSmoothingEnabled = false;
    canvas.width = 442
    canvas.height = 442
    let w = canvas.width,
        h = canvas.height;

    const hatchWidth = 20 / 2;
    const hatchGap = 56;
    let rValue = 'R';

    function redrawGraph(r) {
        ctx.clearRect(0, 0, w, h);

        ctx.lineWidth = 2;
        ctx.strokeStyle = 'black';

        // y axis
        ctx.beginPath();
        ctx.moveTo(w / 2, 0);
        ctx.lineTo(w / 2 - 10, 15);
        ctx.moveTo(w / 2, 0);
        ctx.lineTo(w / 2 + 10, 15);
        ctx.moveTo(w / 2, 0);
        ctx.lineTo(w / 2, h);
        ctx.stroke();
        ctx.closePath();

        // x axis
        ctx.beginPath();
        ctx.moveTo(w, h / 2);
        ctx.lineTo(w - 15, h / 2 - 10);
        ctx.moveTo(w, h / 2);
        ctx.lineTo(w - 15, h / 2 + 10);
        ctx.moveTo(w, h / 2);
        ctx.lineTo(0, h / 2);
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(w / 2 - hatchWidth, h / 2 - hatchGap);
        ctx.lineTo(w / 2 + hatchWidth, h / 2 - hatchGap);
        ctx.moveTo(w / 2 - hatchWidth, h / 2 - hatchGap * 2);
        ctx.lineTo(w / 2 + hatchWidth, h / 2 - hatchGap * 2);
        ctx.moveTo(w / 2 - hatchWidth, h / 2 + hatchGap);
        ctx.lineTo(w / 2 + hatchWidth, h / 2 + hatchGap);
        ctx.moveTo(w / 2 - hatchWidth, h / 2 + hatchGap * 2);
        ctx.lineTo(w / 2 + hatchWidth, h / 2 + hatchGap * 2);
        ctx.moveTo(w / 2 - hatchGap, h / 2 - hatchWidth);
        ctx.lineTo(w / 2 - hatchGap, h / 2 + hatchWidth);
        ctx.moveTo(w / 2 - hatchGap * 2, h / 2 - hatchWidth);
        ctx.lineTo(w / 2 - hatchGap * 2, h / 2 + hatchWidth);
        ctx.moveTo(w / 2 + hatchGap, h / 2 - hatchWidth);
        ctx.lineTo(w / 2 + hatchGap, h / 2 + hatchWidth);
        ctx.moveTo(w / 2 + hatchGap * 2, h / 2 - hatchWidth);
        ctx.lineTo(w / 2 + hatchGap * 2, h / 2 + hatchWidth);
        ctx.stroke();
        ctx.closePath();


        ctx.fillStyle = '#99999930';
        ctx.beginPath();
        ctx.moveTo(w / 2, h / 2);
        ctx.lineTo(w / 2, h / 2 + hatchGap * 2);
        ctx.lineTo(w / 2 + hatchGap, h / 2);
        ctx.lineTo(w / 2, h / 2);
        ctx.lineTo(w / 2 + hatchGap * 2, h / 2);
        ctx.lineTo(w / 2 + hatchGap * 2, h / 2 - hatchGap);
        ctx.lineTo(w / 2, h / 2 - hatchGap);
        ctx.lineTo(w / 2, h / 2);
        ctx.lineTo(w / 2 - hatchGap, h / 2);
        ctx.arc(w / 2, h / 2, hatchGap * 2, Math.PI, - 1 / 2 * Math.PI, false);
        ctx.lineTo(w / 2, h / 2);

        ctx.fill();
        ctx.strokeStyle = '#black'
        ctx.stroke();
        ctx.closePath();

        const fontSize = hatchGap / 3.5
        ctx.fillStyle = 'black'
        ctx.font = `500 ${fontSize * 1.4}px Gilroy`;

        ctx.fillText('Y', w / 2 - hatchWidth * 2.8, 15)
        ctx.fillText('x', w - 20, h / 2 - hatchWidth * 2.4)

        let label1, label2;
        if (isNaN(r)) {
            label1 = r + '/2'
            label2 = r
        } else {
            label1 = r / 2
            label2 = r
        }
        rValue = r;

        ctx.font = `500 ${fontSize}px Gilroy`;
        ctx.fillText(label1, w / 2 + hatchGap - 3, h / 2 + hatchWidth * 2.8);
        ctx.fillText(label2, w / 2 + hatchGap * 2 - 3, h / 2 + hatchWidth * 2.8);
        ctx.fillText('-' + label1, w / 2 - hatchGap - 18, h / 2 + hatchWidth * 2.8);
        ctx.fillText('-' + label2, w / 2 - hatchGap * 2 - 7, h / 2 + hatchWidth * 2.8);

        ctx.fillText(label1, w / 2 - hatchWidth * 2 - 20, h / 2 - hatchGap + 3);
        ctx.fillText(label2, w / 2 + hatchWidth * 2, h / 2 - hatchGap * 2 + 3);
        ctx.fillText('-' + label1, w / 2 + hatchWidth * 2 - 65, h / 2 + hatchGap + 3);
        ctx.fillText('-' + label2, w / 2 + hatchWidth * 2, h / 2 + hatchGap * 2 + 3);


        const table = document.getElementById("resultsTable");
        if ((table)&&(canvas)) {
            processTable(table, r);
        }

    }
function processTable(table, r) {
    for (let item of table.rows) {
        let x = parseFloat(item.getAttribute('data-x'));
        let y = parseFloat(item.getAttribute('data-y'));


        if (isNaN(x) || isNaN(y)) continue;
        let result = dotInGraph(x,y,r)
        printDotOnGraph(x, y, result);

    }
}
redrawGraph('R');
    function printDotOnGraph(xCenter, yCenter, isHit) {

        ctx.fillStyle = isHit ? '#00ff00' : '#ff0000'

        const x = w / 2 + xCenter * hatchGap * (2 / rValue) - 3, y = h / 2 - yCenter * hatchGap * (2 / rValue) - 3;
        ctx.fillRect(x, y, 6, 6);
    }

function dotInGraph(x,y,r){
    if(((x >= 0 )&&(x <= r/2)&&(0 >= y)&& (y>= 2*x-r)) || ((x <= 0)&& (y >= 0)&& (x*x+y*y <= r*r)) || ((r/2 >= y)&&(x>=0)&&(y >= 0)&&(x <= r))) {
        return true;
    }
    else return false;
}



canvas.addEventListener("click", function(event) {
    if(r !== undefined) {


        let rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        const xMouse =   +(((x - 220)*r)/110).toFixed(3);
        const yMouse =  -(((y - 220)*r)/110).toFixed(3);

        const form = $('<form>', {
            action: "controller",
            method: "post"
        });

        const args = { action: "submit", x: xMouse, y: yMouse, r: r };
        Object.entries(args).forEach(entry => {
            const [key, value] = entry;
            $('<input>').attr({
                type: "hidden",
                name: key,
                value: value
            }).appendTo(form);
        });

        form.appendTo('body').submit();
    }
    else{
        alert('введите r')
    }
});
