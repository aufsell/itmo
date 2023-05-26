import time

NUMBER_CHARS = [str(i) for i in range(0,10)]
WORDS = 'day,time,room,subject,lesson,teacher,location,parity,format,weeks,#'.split(',')
t_start = time.perf_counter()

def match_lesson(s):
    word = ''
    for j in range(len(s)):
            word = word + s[j]
            if word == 'lesson' and s[j+1] in NUMBER_CHARS:
                return True
    return False

class Timetable:
    def __init__(self):
        self.lessons = []
        self.day = ""

def parse_line(line):
    line = line.replace("  ", "").replace('\n','')
    if "#" in line:
        key, word = line.split(" ", 1)
    else:
        key, word = line.split(":", 1)
    return key, word

def parse_file(file):
    lines = file.readlines()
    schedule = Timetable()
    lesson = dict()
    timetable_start = False
    for line in lines:
        if timetable_start:
            if line.count("  ", 0, 2) == 0: #проверяем не закончилась ли таблица
                break
            else:
                line = line.replace("\n", "").replace("  ", "").replace(": ", ":")
                if "day" in line:
                    key, word = parse_line(line)
                    schedule.day = word
                elif match_lesson(line) and len(lesson)!=0:
                    schedule.lessons.append(lesson)
                    lesson = dict()
                else:
                    key, word = parse_line(line)
                    if key in WORDS:
                        lesson[key] = word
        if "timetable" in line:
            timetable_start = True

    schedule.lessons.append(lesson)
    return schedule

def file_to_xml(file):
    schedule = parse_file(file)
    xml = "<timetable>\n"
    xml+=f"\t<day>{schedule.day}</day>\n"
    for i in range(len(schedule.lessons)):
        lesson = schedule.lessons[i]
        xml += f"\t<lesson{i+1}>\n"
        for p in lesson:
            if p == "#":
                xml += f"\t\t<!--{p}{lesson[p]}-->\n"
            else:
                xml += f"\t\t<{p}>{lesson[p]}</{p}>\n"
        xml += f"\t</lesson{i+1}>\n"
    xml += "</timetable>"
    return xml

def convert_file(input_file_name, output_file_name, show = False):
    input_file = open(input_file_name + ".yml", 'r', encoding='utf-8') 
    output_file = open(output_file_name + ".xml",'w', encoding = "utf-8")
    xml = file_to_xml(input_file)
    output_file.write(xml)
    input_file.close()
    output_file.close()
    if show:
        print(xml)

convert_file("timetable","timetable",True)
print("time: ",time.perf_counter() - t_start)