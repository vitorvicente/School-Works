(* #use "pa4.ml";; *)
(* interpreter "./input/input11.txt" "output.txt";; *)


(* Helper Functions *)
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
	
let is_alpha (c:char): bool = 
  (Char.code 'a' <= Char.code c && Char.code c <= Char.code 'z')
  || (Char.code 'A' <= Char.code c && Char.code c <= Char.code 'Z')

let is_digit (c:char): bool = 
 Char.code '0' <= Char.code c && Char.code c <= Char.code '9'


(*
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
		 | Concat 
		 | And | Or | Not
		 | Equal | LessThan
		 | If
		 | Bind
		 | Begin | End
		 | Quit

type env = (string * string) list

let rec fetchProper (v : string) (e : env) : string option =
	match e with
	| [] -> None
	| (v', i) :: rest ->  if v = v' then Some i else (fetchProper v rest)

let rec fetch (v : string) (e : env) (es : env list) : string option = 
	let rec fetchAux (s : string) (ls : env list) : string option =
		match ls with
		| [] -> None
		| x :: rest -> (match (fetchProper s x) with
					    | Some i -> Some i
					    | None -> fetchAux s rest)
	in fetchAux v (e :: es)
	
let rec remNames (s : string list) (e : env) : string list =
	match s with
	| [] -> []
	| x :: rest -> (match fetchProper x e with
					| None -> x :: (remNames rest e) 
					| Some a -> (remNames rest e))


(* Checker Functions *)
let checkName (s : string) : bool = 
	if String.length s > 0 && ( let c = (String.get s 0) in is_alpha c ||  c = '_')
	then true
	else false

let checkInt (s : string) (e : env) (es : env list) : bool =
	let rec checkIntAux (ls : char list) : bool =
		match ls with
		| [] -> true
		| x :: xs -> if ((is_digit x) || (x == '-')) then checkIntAux xs else false
	in (if checkName s 
	   then (match fetch s e es with
	         | None   -> false
			 | Some i -> checkIntAux (explode i))
	   else checkIntAux (explode s))

let checkString (s : string) (e : env) (es : env list) : bool =
	let rec checkStringAux (ls : char list) : bool =
		match ls with
		| [] -> false
		| x :: xs -> if (x == '"') then true else false
	in if checkName s 
	   then (match fetch s e es with
		  	 | None    -> checkStringAux (explode s)
			 | Some s1 -> checkStringAux (explode s1))
	   else checkStringAux (explode s)

let checkBool (s : string) (e : env) (es : env list) : bool =
	match s with
	| "<true>" -> true
	| "<false>" -> true
	| _ -> (match (fetch s e es) with
			| Some "<true>" -> true
			| Some "<false>" -> true
			| Some _ -> false
			| None -> false)

let checkUnit (s : string) (e : env) (es : env list) : bool =
	match s with
	| "<unit>" -> true
	| _ -> (match (fetch s e es) with
			| Some "<unit>" -> true
			| Some _ -> false
			| None -> false)


(* Getter Functions *)

(*

let greaterGetInt (s : string) (e : env) (es : env list) : int option =
	let rec auxGGI (s1 : string) (ls : env list) : int option =
		match ls with
		| [] -> None
		| x :: rest -> (match (getInt s1 x) with
					    | Some i -> Some i
					    | None -> auxGGI s1 rest)
	in auxGGI s (e :: es)

let greaterGetBool (s : string) (e : env) (es : env list) : int option =
	let rec auxGGI (s1 : string) (ls : env list) : int option =
		match ls with
		| [] -> None
		| x :: rest -> (match (getBool s1 x) with
					    | Some i -> Some i
					    | None -> auxGGI s1 rest)
	in auxGGI s (e :: es)
	
let greaterGetString (s : string) (e : env) (es : env list) : int option =
	let rec auxGGI (s : string) (ls : env list) : int option =
		match ls with
		| [] -> None
		| x :: rest -> (match (getBool s x) with
					    | Some i -> Some i
					    | None -> auxGGI s rest)
	in auxGGI s (e :: es)
	
*)

let getStack (cur : string list) (prev : string list) : string list =
	match cur with
	| [] -> prev
	| x :: rest -> x :: prev

let getInt (s : string) (e : env) (es : env list) : int option =
	if checkName s 
	then (match (fetch s e es) with
		  | Some i -> Some (int_of_string i)
		  | None -> None)
	else Some (int_of_string s)

let getBool (s : string) (e : env) (es : env list) : string option = 
	match s with
	| "<true>" -> Some "<true>"
	| "<false>" -> Some "<false>"
	| _ -> (match (fetch s e es) with
			| Some "<true>" -> Some "<true>"
			| Some "<false>" -> Some "<false>"
			| Some _ -> None
			| None -> None)

let getString (s : string) (e : env) (es : env list) : string option = 
	if checkName s 
	then (match fetch s e es with
		  | Some s1 -> Some s1
		  | None -> Some s)
	else Some s


(* Language Functions *)
let boolAnd (b1 : string) (b2 : string) : string =
	match b1, b2 with
	| "<true>", "<true>" -> "<true>"
	| _, _ -> "<false>"
	
let boolOr (b1 : string) (b2 : string) : string =
	match b1, b2 with
	| "<true>", _ -> "<true>"
	| _, "<true>" -> "<true>"
	| _, _ -> "<false>"
	
let boolNot (b1 : string) : string =
	match b1 with
	| "<true>" -> "<false>"
	| "<false>" -> "<true>"
	| _ -> "<error>"
	
let intEqual (i1 : int) (i2 : int) : string =
	if (i1 == i2) then "<true>" else "<false>"
	
let intLessThan (i1 : int) (i2 : int) : string =
	if (i2 >= i1) then "<true>" else "<false>"

let boolIf (b : string) (s1 : string) (s2 : string) : string =
	match b with
	| "<true>" -> s1
	| "<false>" -> s2
	| _ -> "<error>"

let rec set (v : string) (i : string) (e : env) : env = (v,i) :: e

let addInt (stack : string list) (e : env)  (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt x e es), (getInt y e es) with
								| Some a, Some b -> ( string_of_int ( a + b ) ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack
	
let subInt (stack : string list) (e : env)  (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt x e es), (getInt y e es) with
								| Some a, Some b -> ( string_of_int ( a - b ) ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let mulInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt x e es), (getInt y e es) with
								| Some a, Some b -> ( string_of_int ( a * b ) ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let divInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt y e es) with
								| Some 0 -> "<error>" :: stack 
								| None   -> "<error>" :: stack 
								| Some c -> ( match (getInt x e es), (getInt y e es) with
									          | Some a, Some b -> ( string_of_int ( a / b ) ) :: rest
									          | _, _ -> "<error>" :: stack ))
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let remInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match getInt y e es with
								| Some 0 -> "<error>" :: stack 
								| None   -> "<error>" :: stack 
								| Some c -> ( match (getInt x e es), (getInt y e es) with
									          | Some a, Some b -> ( string_of_int ( a mod b ) ) :: rest
									          | _, _ -> "<error>" :: stack ))
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let negInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: rest -> (if ((checkInt x e es)) 
						 then ( match (getInt x e es) with
								| Some a -> ( string_of_int ( -a ) ) :: rest
								| None -> "<error>" :: stack )
						 else "<error>" :: stack )
	| []        -> "<error>" :: stack

let concatStr (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkString x e es) && (checkString y e es)) 
						 then ( match (getString x e es), (getString y e es) with
								| Some a, Some b -> ( a ^ b ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let andBool (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkBool x e es) && (checkBool y e es)) 
						 then ( match (getBool x e es), (getBool y e es) with
								| Some a, Some b -> ( boolAnd a b ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let orBool (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkBool x e es) && (checkBool y e es)) 
						 then ( match (getBool x e es), (getBool y e es) with
								| Some a, Some b -> ( boolOr a b ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let notBool (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: rest -> (if (checkBool x e es) 
				    then ( match (getBool x e es) with
						   | Some a -> ( boolNot a ) :: rest
						   | _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| []             -> "<error>" :: stack

let equalInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt x e es), (getInt y e es) with
								| Some a, Some b -> ( intEqual a b ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let lessThanInt (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: rest -> (if ((checkInt x e es) && (checkInt y e es)) 
						 then ( match (getInt x e es), (getInt y e es) with
								| Some a, Some b -> ( intLessThan a b ) :: rest
								| _, _ -> "<error>" :: stack )
						 else "<error>" :: stack )
	| x :: _         -> "<error>" :: stack
	| []             -> "<error>" :: stack

let ifBool (stack : string list) (e : env) (es : env list) : string list =
	match stack with
	| x :: y :: z :: rest -> (if (checkBool z e es) 
				              then ( match (getBool z e es) with
						             | Some a -> ( boolIf a y x ) :: rest
						             | _ -> "<error>" :: stack )
						      else "<error>" :: stack )
	| x :: _ :: _         -> "<error>" :: stack
	| x :: _              -> "<error>" :: stack
	| []                  -> "<error>" :: stack

(* Interpreter *)
let rec eval (exp : com list) (stack : string list) (stacks : string list list) (es : env list) (e : env) : string list =
	match exp with
	| []             -> stack
	| Quit :: xs     -> stack
	| PushI i :: xs  -> eval xs (string_of_int i :: stack) stacks es e
	| PushB b :: xs  -> eval xs (b :: stack) stacks es e
	| PushS s :: xs  -> eval xs ((s) :: stack) stacks es e
	| PushN n :: xs  -> eval xs (n :: stack) stacks es e
	| Push :: xs     -> eval xs ("<unit>" :: stack) stacks es e
	| Bind :: xs     -> (match stack with
					 	 | x :: y :: rest -> (if checkName x 
											  then ( if ( (checkInt y e es) || (checkBool y e es) || (checkString y e es) || (checkUnit y e es) ) 
													then eval xs ("<unit>" :: rest) stacks es (set x y e)
													else ( match (fetch y e es) with
														   | Some i -> eval xs ("<unit>" :: rest) stacks es (set x y e) 
														   | None -> eval xs ("<error>" :: stack) stacks es e ) )
											  else eval xs ("<error>" :: stack) stacks es e)
						 | x :: _ -> eval xs ("<error>" :: stack) stacks es e
					     | [] -> eval xs ("<error>" :: stack) stacks es e)
	| Begin :: xs    -> eval xs [] (stack :: stacks) (e :: es) []
	| End :: xs      -> (match es with
						 | x :: rest -> (match stacks with
										 | y :: rem -> eval xs (getStack stack y) rem rest x
										 | _ -> eval xs ("<error>" :: stack) stacks es e)
						 | _ -> eval xs ("<error>" :: stack) stacks es e)
	| Add :: xs      -> eval xs (addInt stack e es) stacks es e
	| Sub :: xs      -> eval xs (subInt stack e es) stacks es e
	| Mul :: xs      -> eval xs (mulInt stack e es) stacks es e
	| Div :: xs      -> eval xs (divInt stack e es) stacks es e
	| Rem :: xs      -> eval xs (remInt stack e es) stacks es e
	| Neg :: xs      -> eval xs (negInt stack e es) stacks es e
	| Swap :: xs     -> (match stack with
					     | x :: y :: rest -> eval xs (y :: x :: rest) stacks es e
					     | x :: _ -> eval xs ("<error>" :: stack) stacks es e
					     | [] -> eval xs ("<error>" :: stack) stacks es e)
	| Pop :: xs      -> (match stack with
					     | x :: rest -> eval xs (rest) stacks es e
					     | [] -> eval xs ("<error>" :: stack) stacks es e)
	| Concat :: xs   -> eval xs (concatStr stack e es) stacks es e
	| And :: xs      -> eval xs (andBool stack e es) stacks es e
	| Or :: xs       -> eval xs (orBool stack e es) stacks es e
	| Not :: xs      -> eval xs (notBool stack e es) stacks es e
	| Equal :: xs    -> eval xs (equalInt stack e es) stacks es e
	| LessThan :: xs -> eval xs (lessThanInt stack e es) stacks es e
	| If :: xs       -> eval xs (ifBool stack e es) stacks es e

let rec parse (inCmds : string list) : com list =
	match inCmds with
	| [] -> []
	| x :: xs -> match (explodeS x) with
				| "PushI" :: i :: _ -> (PushI (int_of_string i)) :: (parse xs)
				| "PushB" :: b :: _ -> PushB b :: (parse xs)
				| "PushS" :: ls -> (PushS (String.concat " " ls)) :: (parse xs)
				| "PushN" :: n :: _ -> PushN n :: (parse xs)
				| "Push"  :: u :: _ -> Push :: (parse xs)
				| "Add"   :: _ -> Add :: (parse xs)
				| "Sub"   :: _ -> Sub :: (parse xs)
				| "Mul"   :: _ -> Mul :: (parse xs)
				| "Div"   :: _ -> Div :: (parse xs)
				| "Rem"   :: _ -> Rem :: (parse xs)
				| "Neg"   :: _ -> Neg :: (parse xs)
				| "Swap"  :: _ -> Swap :: (parse xs)
				| "Pop"   :: _ -> Pop :: (parse xs)
				| "Concat" :: _ -> Concat :: (parse xs)
				| "And" :: _ -> And :: (parse xs)
				| "Or" :: _ -> Or :: (parse xs)
				| "Not" :: _ -> Not :: (parse xs)
				| "Equal" :: _ -> Equal :: (parse xs)
				| "LessThan" :: _ -> LessThan :: (parse xs)
				| "If" :: _ -> If :: (parse xs)
				| "Begin" :: _ -> Begin :: (parse xs)
				| "End" :: _ -> End :: (parse xs)
				| "Bind" :: _ -> Bind :: (parse xs)
				| "Quit" :: _ -> Quit :: (parse xs)
				| _ -> parse xs

let runPrompt (cmds : string list) : string list =
	match parse cmds with
	| [] -> []
	| exp -> eval exp [] [] [] [];;

let interpreter (input : string) (output : string ) : unit =
	let commands = readlines input in
	  let answers = runPrompt commands in
	    write output (remQuotesTotal answers);;