from math import log as l
from prettytable import PrettyTable as PT
import matplotlib.pyplot as plt

# Вариант 8
dataset = [1.07, 1.59, -1.49, -0.10, 0.11, 1.18, 0.35, -0.73, 1.07, 0.31,
        -0.26, -1.20, -0.35, 0.73, 1.01, -0.12, 0.28, -1.32, -1.10, -0.26]

print("Исходные данные:")
print(dataset)

print("Вариационный ряд:")
sorted_values = sorted(dataset)
print(sorted_values)

print("Первая и последняя порядковая статистика:", sorted_values[0], ";", sorted_values[-1])
print("Размах:", round(sorted_values[-1] - sorted_values[0], 2))

print("Статистический ряд:")
counter_dict = {}
for val in sorted_values:
    if counter_dict and val == list(counter_dict.keys())[-1]:
        counter_dict[val] += 1
    else:
        counter_dict[val] = 1

table = PT()
table.field_names = ["x(i)", *counter_dict.keys()]
table.add_row(["n(i)", *counter_dict.values()])
print(table)

average_value = sum(dataset) / len(dataset)
print("Выборочное среднее:", average_value)

variance = 0
for val in dataset:
    variance += ((val - average_value) ** 2) * counter_dict[val]/len(dataset)
print("Дисперсия:",variance )

print("СКО:", variance ** 0.5)

print("Эмпирическая функция:")
plt.subplot(5, 1, 1)
plt.title("График эмпирической функции распределения")
num_values = len(counter_dict)
keys_values = list(counter_dict.keys())
y_value = 0
print(f'\t\t/ {round(y_value, 2)}, при x <= {keys_values[0]}')
for i in range(num_values - 1):
    y_value += counter_dict[keys_values[i]] / num_values if i < num_values else 0
    left_value = "F*(x) =         " if i == num_values / 2 else "\t\t"
    print(f'{left_value}| {round(y_value, 2)}, при {keys_values[i]} < x <= {keys_values[i + 1]}')
    plt.plot([keys_values[i], keys_values[i + 1]], [y_value, y_value], c='black')
print(f'\t\t\\ {round(y_value, 2)}, при {keys_values[-1]} < x')

print("Интервальное статистическое распределение:")
interval_size = round((sorted_values[-1] - sorted_values[0]) / (1 + round(l(num_values, 2))), 2)
curr_val = round(sorted_values[0] - interval_size / 2, 2)
next_val = round(curr_val + interval_size, 2)
grouped_values = {curr_val: 0}
for val in sorted_values:
    if val < next_val:
        grouped_values[curr_val] += 1 / num_values
    else:
        grouped_values[next_val] = 1 / num_values
        curr_val = next_val
        next_val = round(next_val + interval_size, 2)
table = PT()
table.field_names = (f'[{round(val, 2)}; {round(val + interval_size, 2)})' for val in grouped_values.keys())
table.add_row(list(round(val, 2) for val in grouped_values.values()))
print(table)

plt.subplot(5, 1, 3)
plt.title("Полигон частот")
plt.plot(list(grouped_values.keys()), list(grouped_values.values()), c='black')

plt.subplot(5, 1, 5)
plt.title("Гистограмма частот")
plt.bar(list(map(lambda val: val + interval_size / 2, grouped_values.keys())), list(grouped_values.values()), width=interval_size)
xticks_values = list(grouped_values.keys()) + [round(list(grouped_values.keys())[-1] + interval_size, 2)]
plt.xticks(xticks_values, xticks_values)
plt.show()
