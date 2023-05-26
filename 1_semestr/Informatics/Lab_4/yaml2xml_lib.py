import time
from dict2xml import *
from benedict import *

NUMBER_CHARS = [str(i) for i in range(0,10)]
WORDS = 'day,time,room,subject,lesson,teacher,location,parity,format,weeks'.split(',')
t_start = time.perf_counter()

def match_lesson(s):
    word = ''
    for j in range(len(s)):
            word = word + s[j]
            if word == 'lesson' and s[j+1] in NUMBER_CHARS:
                return True
    return False

def copy_field(key, a, b):
    a[key] = b[key]

def file_to_xml(file_name):
    yaml_object = benedict.from_yaml(file_name)
    time_table = dict()
    inf = {"timetable": time_table }
    #выбираем нужные поля
    for key in yaml_object["timetable"]:
        if "day" in key:
            time_table[key] = yaml_object["timetable"][key]
        elif match_lesson(key):
            lesson = dict()
            for key_ in yaml_object["timetable"][key]:
                if key_ in WORDS:
                    lesson[key_] = yaml_object["timetable"][key][key_]
            time_table[key] = lesson
        
    xml = dict2xml(inf)
    return xml

def convert_file(input_file_name, output_file_name, show = False):
    input_file_name = input_file_name + ".yml"
    output_file = open(output_file_name + ".xml",'w', encoding = "utf-8")
    xml = file_to_xml(input_file_name)
    output_file.write(xml)
    output_file.close()
    if show:
        print(xml)

convert_file("timetable","timetable",True)

###
print("time: ",time.perf_counter() - t_start)