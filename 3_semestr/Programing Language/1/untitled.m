section .text
  
; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, 60
    syscall 

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
     xor rax, rax
    .loop:
    cmp byte [rdi+rax], 0
    je .end
    inc rax
    jmp .loop
 .end:
    ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi
    call string_length
    pop rsi
    mov rdx, rax
    mov rdi, 1
    mov rax, 1
    syscall
    ret


; Принимает код символа и выводит его в stdout
print_char:
    xor rax, rax
    push rdi
    mov rsi, rsp
    mov rdi, 1
    mov rax, 1 
    mov rdx, 1
    syscall
    pop rax
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    xor rax, rax
    mov rdi, 0xA
    call print_char
    ret


; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov rax, rdi
    mov rdi, rsp
    dec rdi
    mov rcx, 10
    sub rsp, 32
    mov byte[rdi], 0
    .loop:
        xor rdx, rdx
        div rcx
        add dl, '0'
        dec rdi
        mov [rdi], dl
        cmp rax, 0
        jz .end
        jmp .loop
    .end:
        call print_string
        add rsp, 32
        ret


; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    xor rax, rax
    cmp rdi, 0
  jge .print
  push rdi
  mov rdi, '-'
  call print_char
  pop rdi
  neg rdi
 .print:
  jmp print_uint

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе

string_equals:
.loop:
    mov dl, byte[rdi]
    cmp dl, byte[rsi]
    jne .neq
    inc rsi
    inc rdi
    cmp dl, 0
    je .eq
    jmp .loop
.neq:
    xor rax, rax
    ret
.eq:
    mov rax, 1
    ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
   read_char:
    xor rax, rax
  push 0
    xor rax, rax
  xor rdi, rdi
  mov rsi, rsp
  mov rdx, 1
  syscall
    cmp rax, 0 
    cmp rax, 0
    js .end
    pop rax
 .end:
    ret 

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор
read_word:
    push r12
    push r13
    push r14
    xor rcx, rcx
 .loop:
  mov r12, rcx
  mov r13, rdi
  mov r14, rsi
  call read_char
  mov rcx, r12
  mov rdi, r13
  mov rsi, r14
  cmp rax, 0x20
  je .search_space
  cmp rax, 0x9
  je .search_space
  cmp rax, 0xA
  je .search_space
  cmp rax, 0
  jz .end
  cmp rsi, rcx
  je .exit
  mov byte[rdi+rcx], al
  inc rcx
  jmp .loop
 .exit:
    pop r14
    pop r13
    pop r12
  xor rax, rax
  ret
 .search_space:
  cmp rcx, 0
  je .loop
 .end:
  mov byte[rdi+rcx], 0
  mov rdx, rcx
    mov rax, rdi
    pop r14
    pop r13
    pop r12
  ret
 

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rax, rax
    xor rcx, rcx
    xor r8, r8
    mov r9, 10
    .loop:
        mov r8b, byte[rdi + rcx]
        cmp r8b, '0'
        jb .exit
        cmp r8b, '9'
        ja .exit
        sub r8b, '0'
        inc rcx
        mul r9
        add rax, r8
        jmp .loop
    .exit:
        mov rdx, rcx
        ret




; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    xor rax, rax
    cmp byte [rdi], '-'
    jne parse_uint
    inc rdi
    call parse_uint
    inc rdx
    neg rax
    ret 

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0

string_copy:
  xor rax, rax
.loop:
  cmp rax, rdx
  jge .fail
  mov cl, byte[rdi + rax]
  mov byte[rsi + rax], cl
  inc rax
  cmp cl, 0
  je .end
  jmp .loop
.fail:  
  xor rax, rax
.end:
  ret 