document.addEventListener('DOMContentLoaded', function () {
  const pointForm = document.getElementById('section__input__form');
  const resultsTable = document.getElementById('section__table__results');
  const clearButton = document.getElementById('clearButton');
  const checkboxes = document.querySelectorAll('input[type="checkbox"]');
  const radiusRadios = document.querySelectorAll('input[name="radius"]');
  




  pointForm.addEventListener('submit', function (event) {
      event.preventDefault();

      const selectedRadius = document.querySelector('input[name="radius"]:checked');
      const xCheckbox = document.querySelector('input[name="xCheckbox"]:checked');
      const yCoordinateInput = document.getElementById('yCoordinate');
      
      if (!selectedRadius) {
          alert('Пожалуйста, выберите радиус.');
          return;
      }

      if (!xCheckbox) {
          alert('Пожалуйста, выберите X-координату.');
          return;
      }

      const yCoordinate = parseFloat(yCoordinateInput.value);

      if (isNaN(yCoordinate) || yCoordinate < -5 || yCoordinate > 3 || Math.abs(yCoordinate-3)<0.0000000001) {
          alert('Пожалуйста, введите корректное числовое значение Y-координаты в диапазоне от -5 до 3.');
          return;
      }
      const xCoordinate = parseFloat(xCheckbox.value);
      const radius = selectedRadius.value;

      const validRadRValue = ['1', '1.5', '2', '2.5', '3'];

      if (!validRadRValue.includes(radius)) {
          alert('Выбран недопустимый радиус.');
      }

      // Проверяем, что X находится в списке допустимых значений
      const validXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
      if (!validXValues.includes(xCoordinate)) {
          alert('Выбрано недопустимое значение X-координаты.');
          return;
      }


      printDotOnGraph(xCoordinate, yCoordinate);


      // Отправляем данные на сервер для проверки точки в области
      $.ajax({
          url: 'check_point.php',
          method: 'POST',
          data: { radius, xCoordinate, yCoordinate },
          success: function (response) {
              // Добавляем результат в таблицу
              resultsTable.querySelector('tbody').innerHTML += response;

          },
          error: function () {
              alert('Произошла ошибка при отправке данных.');
          }
      });
  });

clearButton.addEventListener('click', function () {
    // Очищаем таблицу результатов
    const tbody = resultsTable.querySelector('tbody');

    tbody.innerHTML = ''; // Очищаем все строки внутри tbody
});


  // Снимаем выбор с остальных чекбоксов если был выбран новый
checkboxes.forEach(checkbox => {
      checkbox.addEventListener('change', function () {
          if (this.checked) {
              checkboxes.forEach(cb => {
                  if (cb !== this) {
                      cb.checked = false;
                  }
              });
          }
      });
  });

  // Обработчик события change для радио-кнопок радиуса и валидации R
  radiusRadios.forEach(radio => {
      radio.addEventListener('change', function () {
          const selectedRadius = this.value;
          redrawGraph(selectedRadius);
      });
  });
});
