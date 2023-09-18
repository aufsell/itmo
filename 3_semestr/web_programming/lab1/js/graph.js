const canvas = document.getElementById("graph"),
    ctx = canvas.getContext('2d');

canvas.height = canvas.width;
let w = canvas.width, h = canvas.height;

const hatchWidth = 20 / 2;
const hatchGap = 56;

var center_x = w/2;
var center_y = h/2;
var Rad = hatchGap;

let rValue = 'R';
function redrawGraph(r) {
    ctx.clearRect(0, 0, w, h);

    ctx.lineWidth = 2;
    ctx.strokeStyle = 'white';

    // y axis
    ctx.beginPath();
    ctx.moveTo(center_x, 0);
    ctx.lineTo(center_x - 10, 15);
    ctx.moveTo(center_x, 0);
    ctx.lineTo(center_x + 10, 15);
    ctx.moveTo(center_x, 0);
    ctx.lineTo(center_x, h);
    ctx.stroke();
    ctx.closePath();

    // x axis
    ctx.beginPath();
    ctx.moveTo(w, center_y);
    ctx.lineTo(w - 15, center_y - 10);
    ctx.moveTo(w, center_y);
    ctx.lineTo(w - 15, center_y + 10);
    ctx.moveTo(w, center_y);
    ctx.lineTo(0, center_y);
    ctx.stroke();
    ctx.closePath();

    ctx.beginPath();
    ctx.moveTo(center_x - hatchWidth, center_y - hatchGap);
    ctx.lineTo(center_x + hatchWidth, center_y - hatchGap);
    ctx.moveTo(center_x - hatchWidth, center_y - hatchGap * 2);
    ctx.lineTo(center_x + hatchWidth, center_y - hatchGap * 2);
    ctx.moveTo(center_x - hatchWidth, center_y + hatchGap);
    ctx.lineTo(center_x + hatchWidth, center_y + hatchGap);
    ctx.moveTo(center_x - hatchWidth, center_y + hatchGap * 2);
    ctx.lineTo(center_x + hatchWidth, center_y + hatchGap * 2);
    ctx.moveTo(center_x - hatchGap, center_y - hatchWidth);
    ctx.lineTo(center_x - hatchGap, center_y + hatchWidth);
    ctx.moveTo(center_x - hatchGap * 2, center_y - hatchWidth);
    ctx.lineTo(center_x - hatchGap * 2, center_y + hatchWidth);
    ctx.moveTo(center_x + hatchGap, center_y - hatchWidth);
    ctx.lineTo(center_x + hatchGap, center_y + hatchWidth);
    ctx.moveTo(center_x + hatchGap * 2, center_y - hatchWidth);
    ctx.lineTo(center_x + hatchGap * 2, center_y + hatchWidth);
    ctx.stroke();
    ctx.closePath();


    //цвет заливки
    var gradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
    gradient.addColorStop(0, '#21FF52');
    gradient.addColorStop(0.3638, 'rgba(21, 255, 138, 0.65)');
    gradient.addColorStop(0.991, '#1BE8DB');


    //рисуем области


    ctx.fillStyle = "rgba(255,255,255,0.1)";
    ctx.beginPath();
    ctx.moveTo(center_x,  center_y + hatchGap );
    ctx.lineTo(center_x - hatchGap , center_y );
    ctx.lineTo(center_x,center_y);

    ctx.moveTo(center_x,center_y);
    ctx.arc(center_x,center_y, hatchGap, - Math.PI / 2, 0);
    ctx.closePath();
    ctx.stroke();
    ctx.fill();

    ctx.beginPath(); 
    ctx.moveTo(center_x, center_y); 
    ctx.lineTo(center_x, center_y - Rad * 2); 
    ctx.lineTo(center_x - Rad * 2,center_y - Rad * 2)
    ctx.lineTo(center_x - Rad * 2,center_y )
    ctx.lineTo(center_x,center_y)
    ctx.stroke(); 
    ctx.fill();
    ctx.closePath();

    const fontSize = hatchGap / 3.5
    ctx.fillStyle = "rgba(255,255,255,0.5)";

    ctx.font = `500 ${fontSize * 1.4}px Poppins`;
    ctx.fillText('y', center_x - hatchWidth * 2.8, 15)
    ctx.fillText('x', w - 20, center_y - hatchWidth * 2.4)

    let label1, label2;
    if (isNaN(r)) {
        label1 = r + '/2'
        label2 = r
    } else {
        label1 = r / 2
        label2 = r
    }
    rValue = label2

    ctx.font = `800 ${fontSize}px Poppins`;
    ctx.fillText(label1, center_x + hatchGap - 3, center_y + hatchWidth * 2.8);
    ctx.fillText(label2, center_x + hatchGap * 2 - 3, center_y + hatchWidth * 2.8);
    ctx.fillText('-' + label1, center_x - hatchGap - 7, center_y + hatchWidth * 2.8);
    ctx.fillText('-' + label2, center_x - hatchGap * 2 - 7, center_y + hatchWidth * 2.8);

    ctx.fillText(label1, center_x + hatchWidth * 2, center_y - hatchGap + 3);
    ctx.fillText(label2, center_x + hatchWidth * 2, center_y - hatchGap * 2 + 3);
    ctx.fillText('-' + label1, center_x + hatchWidth * 2, center_y + hatchGap + 3);
    ctx.fillText('-' + label2, center_x + hatchWidth * 2, center_y + hatchGap * 2 + 3);
}



// draw graph with standard label
redrawGraph(rValue);

function printDotOnGraph(xCenter, yCenter) {
    redrawGraph(rValue);
    ctx.fillStyle = 'black'
    let x = center_x + xCenter * hatchGap * (2 / rValue) - 3, y = center_y - yCenter * hatchGap * (2 / rValue) - 3;
    ctx.fillRect(x, y, 6, 6);
}
