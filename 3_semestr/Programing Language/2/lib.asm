; lib.asm
%define SYS_EXIT        60
%define SYS_WRITE       1
%define SPACE     0x20
%define TAB       0x9
%define NEW_LINE  0xA
%define MINUS '-'
%define ZERO '0'
%define NINE '9'
%define PLUS '+'
%define STD_OUT 1

section .text
  
; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, SYS_EXIT
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
    mov rdi, SYS_WRITE
    mov rax, SYS_WRITE
    syscall
    ret


; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, NEW_LINE


; Принимает код символа и выводит его в stdout
print_char:
    xor rax, rax
    push rdi
    mov rsi, rsp
    mov rdi, STD_OUT
    mov rax, SYS_WRITE
    mov rdx, 1
    syscall
    pop rax
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
    test rdi, rdi
  jge .print
  push rdi
  mov rdi, MINUS
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
    test dl,dl
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
  push 0
    xor rax, rax
  xor rdi, rdi
  mov rsi, rsp
  mov rdx, 1
  syscall
    test rax, rax
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
  cmp rax, SPACE
  je .search_space
  cmp rax, TAB
  je .search_space
  cmp rax, NEW_LINE
  je .search_space
  test rax, rax
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
  test rcx, rcx
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
        cmp r8b, ZERO
        jb .exit
        cmp r8b, NINE
        ja .exit
        sub r8b, ZERO
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
    cmp byte [rdi], MINUS
    je .process_minus
    cmp byte [rdi], PLUS
    je .process_plus
    jne parse_uint


  .process_plus:
    inc rdi                 
    call parse_uint         
    inc rdx                 
    ret

  .process_minus:
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
  test cl, cl
  je .end
  jmp .loop
.fail:  
  xor rax, rax
.end:
  ret