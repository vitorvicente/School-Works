(* #use "pa4-proper.ml";; *)
(* interpreter "./input/input11.txt" "output.txt";; *)

let rec write (path : string) (ls : string list) : unit =
  let rec loop (ch) (ls : string list) : unit =
    match ls with
      | [] -> ()
	  | x :: xs -> let _ = Printf.fprintf ch "%s\n" x in loop ch xs
  in 
  let ch = open_out path in
  let ()  = loop ch ls in
  let () = close_out ch in
  ();;
			
let rec readlines (path : string) : string list =
  let rec loop (ch : in_channel) : string list =
    match input_line ch with 
    | str -> str :: loop ch
    | exception  End_of_file -> []
  in 
  let ch = open_in path in
  let lines = loop ch in
  let () = close_in ch in
  lines;;
  
let explodeS (s : string) : string list = String.split_on_char ' ' s

let explode (s:string) : char list =
  let rec expl i l =
    if i < 0 
    then l
    else expl (i - 1) (String.get s i :: l)
  in expl (String.length s - 1) []
  
let implode (cl:char list) : string = 
  String.concat "" (List.map (String.make 1) cl)

let is_digit (c:char): bool = 
 Char.code '0' <= Char.code c && Char.code c <= Char.code '9'

let checkInt (s : string) : bool =
	let rec checkIntAux (ls : char list) : bool =
		match ls with
		| [] -> true
		| x :: xs -> if ((is_digit x) || (x == '-')) then checkIntAux xs else false
	in checkIntAux (explode s)

let remQuotes (s : string) : string = 
	let rec remQuotesAux (ls : char list) : char list =
		match ls with
		| [] -> []
		| x :: xs -> if (x == '"') then remQuotesAux xs else x :: (remQuotesAux xs)
	in implode (remQuotesAux (explode s))

let rec remQuotesTotal (ls : string list) : string list =
	match ls with
	| [] -> []
	| x :: xs -> (remQuotes x) :: (remQuotesTotal xs)

(*
write an interperter that evaluates arithmetic in the standard way

digit ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
letter ::= a | b | ... | z | A | B | ... | Z
const ::= int | bool | error | string | name | unit

int ::= [−] digit { digit }
bool ::= <true> | <false>
error ::= <error>
unit ::= <unit>
string ::= "{ simpleASCII }"
name ::= {_} letter {letter | digit | _}
*)

type com = PushI of int | PushB of string | PushS of string | PushN of string | Push 
		 | Add | Sub | Mul | Div | Rem | Neg
		 | Swap | Pop

let rec eval (exp : com list) (stack : string list) : string list =
	match exp with
	| [] -> stack
	| PushI i :: xs -> eval xs (string_of_int i :: stack)
	| PushB b :: xs -> eval xs (b :: stack)
	| PushS s :: xs -> eval xs ((s) :: stack)
	| PushN n :: xs -> eval xs (n :: stack)
	| Push :: xs    -> eval xs ("<unit>" :: stack)
	| Add :: xs     -> (match stack with
					   | x :: y :: rest -> if ((checkInt x) && (checkInt y)) then eval xs (string_of_int ((int_of_string x) + (int_of_string y)) :: rest) else eval xs ("<error>" :: stack)
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Sub :: xs     -> (match stack with
					   | x :: y :: rest -> if checkInt x && checkInt y then eval xs (string_of_int (int_of_string x - int_of_string y) :: rest) else eval xs ("<error>" :: stack)
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Mul :: xs     -> (match stack with
					   | x :: y :: rest -> if checkInt x && checkInt y then eval xs (string_of_int (int_of_string x * int_of_string y) :: rest) else eval xs ("<error>" :: stack)
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Div :: xs     -> (match stack with
					   | x :: y :: rest -> if checkInt x && checkInt y then
							if (int_of_string y) == 0 then eval xs ("<error>" :: stack)
							else eval xs (string_of_int (int_of_string x / int_of_string y) :: rest) else eval xs ("<error>" :: stack)
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Rem :: xs     -> (match stack with
					   | x :: y :: rest -> if checkInt x && checkInt y then 
							if (int_of_string y) == 0 then eval xs ("<error>" :: stack)
							else eval xs (string_of_int (int_of_string x mod int_of_string y) :: rest) else eval xs ("<error>" :: stack)
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Neg :: xs     -> (match stack with
					   | x :: rest -> if checkInt x then eval xs (string_of_int (0 - int_of_string x) :: rest) else eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Swap :: xs    -> (match stack with
					   | x :: y :: rest -> eval xs (y :: x :: rest) 
					   | x :: _ -> eval xs ("<error>" :: stack)
					   | [] -> eval xs ("<error>" :: stack))
	| Pop :: xs     -> (match stack with
					   | x :: rest -> eval xs (rest)
					   | [] -> eval xs ("<error>" :: stack))


let rec parse (inCmds : string list) : com list =
	match inCmds with
	| [] -> []
	| x :: xs -> match (explodeS x) with
				| "PushI" :: i :: [] -> (PushI (int_of_string i)) :: (parse xs)
				| "PushB" :: b :: [] -> PushB b :: (parse xs)
				| "PushS" :: ls -> (PushS (String.concat " " ls)) :: (parse xs)
				| "PushN" :: n :: [] -> PushN n :: (parse xs)
				| "Push"  :: u :: [] -> Push :: (parse xs)
				| "Add"   :: [] -> Add :: (parse xs)
				| "Sub"   :: [] -> Sub :: (parse xs)
				| "Mul"   :: [] -> Mul :: (parse xs)
				| "Div"   :: [] -> Div :: (parse xs)
				| "Rem"   :: [] -> Rem :: (parse xs)
				| "Neg"   :: [] -> Neg :: (parse xs)
				| "Swap"  :: [] -> Swap :: (parse xs)
				| "Pop"   :: [] -> Pop :: (parse xs)
				| _ -> (parse xs)

let runPrompt (cmds : string list) : string list =
	match parse cmds with
	| [] -> []
	| exp -> eval exp [];;

let interpreter (input : string) (output : string ) : unit =
	let commands = readlines input in
	  let answers = runPrompt commands in
	    write output (remQuotesTotal answers);;