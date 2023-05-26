#Задание 1. Смайлики.
# =-O smile
#Количество смайликов подсчитанное в первом тесте вручную : 8 
#Количество смайликов подсчитанное во втором тесте вручную : 14 
#Количество смайликов подсчитанное в третьем тесте вручную : 16
#Количество смайликов подсчитанное в четвертом тесте вручную : 20
#Количество смайликов подсчитанное в пятом тесте вручную : 10
from re import *
print("---------------------- 1 Задание-----------------------")
tests_smile = ["first_test_for_smile.txt","second_test_for_smile.txt","third_test_for_smile.txt","fourth_test_for_smile.txt","fifth_test_for_smile.txt"]
for x in tests_smile:
    with open (x, "r") as f:
        num_test = int(f.readline())
        amount_smile_in_test = len(findall(r'=-O', f.read()))
        print("Количество смайлов в тесте", num_test, ":", amount_smile_in_test)
f.close()
#--------------------------------------------------------------------------------------------------
print("---------------------- 2 Задание-----------------------")
tests_time = ["first_test_for_time.txt","second_test_for_time.txt","third_test_for_time.txt","fourth_test_for_time.txt","fifth_test_for_time.txt"]
tests_time_after_replace = ["first_test_time_after_replace.txt","second_test_time_after_replace.txt","third_test_time_after_replace.txt","fourth_test_time_after_replace.txt","fifth_test_time_after_replace.txt"]

for x in tests_time:
    with open (x, 'r') as f:
        num_test = int(f.readline())
        old_data = f.read()
        amount_time_in_test = len(findall(r'(\s|")([0-1][0-9]|2[0-4])\:[0-5][0-9](")?(\:[0-9][0-9])?(")?', old_data))
    new_data = sub(r'(\s|")([0-1][0-9]|2[0-4])\:[0-5][0-9](")?(\:[0-9][0-9])?(")?','TBD', old_data)
    with open (tests_time_after_replace[num_test-1], 'w') as f:
        f.write(new_data)
        f.close()
    print("В тесте", num_test, "заменено", amount_time_in_test, "вхождений(я)")
print("---------------------- 3 Задание-----------------------")
#[А-Я]+[а-я]+(\-*[А-Я]*+[а-я]+)*\s([А-Я])\.\2\sP3118
tests_time = ["first_test_for_surname.txt","second_test_for_surname.txt","third_test_for_surname.txt","fourth_test_for_surname.txt","fifth_test_for_surname.txt"]
tests_time_after_replace = ["first_test_surname_after_replace.txt","second_test_surname_after_replace.txt","third_test_surname_after_replace.txt","fourth_test_surname_after_replace.txt","fifth_test_surname_after_replace.txt"]

for x in tests_time:
    with open (x, 'r') as f:
        num_test = int(f.readline())
        old_data = f.read()
        amount_time_in_test = len(findall(r'[А-Я]+[а-я]+(\-*[А-Я][а-я]+)*\s([А-Я])\.*\-*([А-Я])*\.\2\sP3118', old_data))
    new_data = sub(r'[А-Я]+[а-я]+(\-*[А-Я][а-я]+)*\s([А-Я])\.*\-*([А-Я])*\.\2\sP3118','', old_data)
    with open (tests_time_after_replace[num_test-1], 'w') as f:
        f.write(new_data)
        f.close()
    print("В тесте", num_test, "будут удалены", amount_time_in_test, "строк(а)")

