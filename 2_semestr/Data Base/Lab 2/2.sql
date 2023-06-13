-- ******************************** 2 ********************************
-- Сделать запрос для получения атрибутов из указанных таблиц, применив фильтры по указанным условиям:
-- Таблицы: Н_ЛЮДИ, Н_ВЕДОМОСТИ, Н_СЕССИЯ.
-- Вывести атрибуты: Н_ЛЮДИ.ФАМИЛИЯ, Н_ВЕДОМОСТИ.ДАТА, Н_СЕССИЯ.ИД.
-- Фильтры (AND):
-- a) Н_ЛЮДИ.ОТЧЕСТВО < Александрович.
-- b) Н_ВЕДОМОСТИ.ДАТА < 2022-06-08.
-- c) Н_СЕССИЯ.ЧЛВК_ИД > 106059.
-- Вид соединения: RIGHT JOIN.

SELECT "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ВЕДОМОСТИ"."ДАТА", "Н_СЕССИЯ"."ИД"
FROM "Н_ЛЮДИ"
RIGHT JOIN "Н_ВЕДОМОСТИ" ON "Н_ЛЮДИ"."ИД" = "Н_ВЕДОМОСТИ"."ЧЛВК_ИД"
RIGHT JOIN "Н_СЕССИЯ" ON "Н_ЛЮДИ"."ИД" = "Н_СЕССИЯ"."ЧЛВК_ИД"
WHERE "Н_ЛЮДИ"."ОТЧЕСТВО" < 'Александрович'
    AND "Н_ВЕДОМОСТИ"."ДАТА" < '2022-06-08'
    AND "Н_СЕССИЯ"."ЧЛВК_ИД" > 106059;

-- Данный запрос ничего не выведет, так как в исходных таблицах нет подходящих значений. Внесем корректировку
SELECT "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ВЕДОМОСТИ"."ДАТА", "Н_СЕССИЯ"."ИД"
FROM "Н_ЛЮДИ"
RIGHT JOIN "Н_ВЕДОМОСТИ" ON "Н_ЛЮДИ"."ИД" = "Н_ВЕДОМОСТИ"."ЧЛВК_ИД"
RIGHT JOIN "Н_СЕССИЯ" ON "Н_ЛЮДИ"."ИД" = "Н_СЕССИЯ"."ЧЛВК_ИД"
WHERE "Н_ЛЮДИ"."ОТЧЕСТВО" < 'Юрьевич'
    AND "Н_ВЕДОМОСТИ"."ДАТА" < '2022-06-08'
    AND "Н_СЕССИЯ"."ЧЛВК_ИД" > 106059;
