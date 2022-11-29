# Reverse Game

## Критерии

По критериям программа сделана на 10 баллов. Реализованы все дополнительные функции:

- Игра в легком режиме
- Игра в сложном режиме
- Игрок против игрока
- Меню для запуска в разных режимах
- Возможность отмена хода (на 1 ход назад)
- Вывод всех вариантов хода игрока
- Визуализация ходов для игрока

## Функционал

### Главное меню

![Главное меню](pictures/mainMenu.png)

Тут можно выбрать действие. Нужно ввести в консоль число от 1 до 4. При других данных будет выведена ошибка

### Уровень сложности

![Уровень](pictures/level.png)

Тут можно выбрать уровень компьютера. Нужно ввести в консоль число от 1 до 2. При других данных будет выведена ошибка

### Как ходить

![Ходы](pictures/coords.png)

Нужно ввести в консоль координаты, например, <3 4> (показано на фото). Координата <0 0> в левом нижнем углу

![Ходы](pictures/possibleMoves.png)

На поле можно видеть 4 формата фишек:

- ![#f03c15](https://placehold.co/15x15/8993a3/8993a3.png) `0 - Пустые клетки`
- ![#f03c15](https://placehold.co/15x15/00ff26/00ff26.png) `0 - Возможные ходы для игрока`
- ![#f03c15](https://placehold.co/15x15/ae00ff/ae00ff.png) `X - Фишки игрока`
- ![#f03c15](https://placehold.co/15x15/ff0040/ff0040.png) `Y - Фишки компьютера`

После каждого хода отображается количество очков

![Очки](pictures/points.png)

### Отмена хода

После вашего хода и хода компьютера можно отменить ход

![Отмена хода](pictures/cancel.png)

Для этого напишите в консоль команду /cancel. Чтобы не отменять ход, напишите любое другое слово/символ. 

### Конец игры

![Конец](pictures/end.png)

В конце игры пишется, кто выиграл. Также снова показывается меню, то есть программа не завершается.
