<?php
session_start();

// Получаем параметры из POST-запроса
$radius = isset($_POST['radius']) ? floatval($_POST['radius']) : 0;
$xCoordinate = isset($_POST['xCoordinate']) ? floatval($_POST['xCoordinate']) : 0;
$yCoordinate = isset($_POST['yCoordinate']) ? floatval($_POST['yCoordinate']) : 0;


function validate_values($xCoordinate, $yCoordinate, $radius)
{
    return in_array($xCoordinate, [-3, -2, -1, 0, 1, 2, 3, 4, 5])
        and (is_numeric($yCoordinate) and $yCoordinate >= -5 and $yCoordinate <= 3)
        and in_array($radius, [1, 1.5, 2, 2.5, 3]);
}

// Валидация данных
if (!is_numeric($radius) || !is_numeric($yCoordinate) || !is_numeric($xCoordinate)) {
    echo '<tr><td colspan="6">Ошибка: Некорректные данные</td></tr>';
} else {
    // Проверяем X-координату на попадание в область
    $inArea = checkPointInArea($radius, $xCoordinate, $yCoordinate);

    // Время выполнения скрипта
    $executionTime = microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'];
    date_default_timezone_set('Europe/Moscow');
    $current_time = date("H:i:s");

    // Формируем результат в виде строки HTML
    $resultHtml = '<tr>';
    $resultHtml .= "<td>{$radius}</td>";
    $resultHtml .= "<td>{$xCoordinate}</td>";
    $resultHtml .= "<td>{$yCoordinate}</td>";
    $resultHtml .= "<td>{$inArea}</td>";
    $resultHtml .= "<td>{$current_time}</td>";
    $resultHtml .= "<td>" . number_format($executionTime * 1000, 2) . " ms</td>";
    $resultHtml .= '</tr>';

    // Добавляем результат в сессию
    $_SESSION['results'][] = $resultHtml;

    // Возвращаем результат
    echo $resultHtml;
}

function checkPointInArea($r, $x, $y) {
    // Проверка попадания точки в левую верхнюю область
    $area1 = (((-($r)) <= ($x)) && (($x) <= (0))) && (((0) <= ($y)) && (($y) <= ($r)));

    // Првоерка попадания в правую верхнюю область
    $area2 = ($x >= 0) && ($y >= 0) && ((($x ** 2) + ($y ** 2)) <= ((($r) / 2)**2));

    // Проверка попадания точки в левую нижнюю область
    $area3 = (((-($r / 2)) <= ($x))  &&  (($x) <= (0))) && (((-($r / 2)) <= ($y))  &&  (($y) <= (0))) && ($y >= (-($x + ($r / 2))));

    if ($area1 || $area2 || $area3){
        return 'Success';}
    else{
        return 'Failed';
    } 
}
?>

