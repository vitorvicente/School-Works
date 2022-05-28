(* #use "pa4.ml";; *)
(* interpreter "input.txt" "output.txt";; *)

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
		 | Fun of (string * string)
		 | FunEnd
		 | Call
		 | Return
		 | InOutFun of (string * string)

type value = Var of string | Func of (string * com list * env) | InOutFunc of (string * com list * env)

and env = (string * value) list

let rec fetchProper (v : string) (e : env) : value option =
	match e with
	| [] -> None
	| (v', i) :: rest ->  if v = v' then Some i else (fetchProper v rest)

let rec fetch (v : string) (e : env) (es : env list) : value option = 
	let rec fetchAux (s : string) (ls : env list) : value option =
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
			 | Some Var i -> checkIntAux (explode i)
			 | Some _ -> false)
	   else checkIntAux (explode s))

let checkString (s : string) (e : env) (es : env list) : bool =
	let rec checkStringAux (ls : char list) : bool =
		match ls with
		| [] -> false
		| x :: xs -> if (x == '"') then true else false
	in if checkName s 
	   then (match fetch s e es with
			 | Some Var s1 -> checkStringAux (explode s1)
			 | Some _ -> false
			 | None    -> checkStringAux (explode s))
	   else checkStringAux (explode s)

let checkBool (s : string) (e : env) (es : env list) : bool =
	match s with
	| "<true>" -> true
	| "<false>" -> true
	| _ -> (match (fetch s e es) with
			| Some Var s -> (match s with
							 | "<true>" -> true
							 | "<false>" -> true
							 | _ -> false)
			| Some _ -> false
			| None -> false)

let checkUnit (s : string) (e : env) (es : env list) : bool =
	match s with
	| "<unit>" -> true
	| _ -> (match (fetch s e es) with
			| Some Var s -> ( match s with
							  | "<unit>" -> true
							  | _        -> false )
			| Some _ -> false
			| None -> false)


(* Getter Functions *)
let getStack (cur : string list) (prev : string list) : string list =
	match cur with
	| [] -> prev
	| x :: rest -> x :: prev

let getInt (s : string) (e : env) (es : env list) : int option =
	if checkName s 
	then (match (fetch s e es) with
		  | Some Var i -> Some (int_of_string i)
		  | _ -> None)
	else Some (int_of_string s)

let getBool (s : string) (e : env) (es : env list) : string option = 
	match s with
	| "<true>" -> Some "<true>"
	| "<false>" -> Some "<false>"
	| _ -> (match (fetch s e es) with
			| Some Var s -> ( match s with
							  | "<true>"  -> Some "<true>"
                              | "<false>" -> Some "<false>"
							  | _ -> None)
			| Some _ -> None
			| None -> None)

let getString (s : string) (e : env) (es : env list) : string option = 
	if checkName s 
	then (match fetch s e es with
		  | Some Var s1 -> Some s1
		  | _ -> Some s)
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

let rec set (v : string) (i : value) (e : env) : env = (v,i) :: e

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
	
let rec parseFunction (lst : com list) : com list = 
	match lst with
	| [] -> []
	| FunEnd :: xs -> []
	| x :: xs -> x :: (parseFunction xs)
	
let rec getRest (lst : com list) : com list =
	match lst with
	| [] -> []
	| FunEnd :: xs -> xs
	| _ :: xs -> getRest xs
	
(*let rec runFunc (argName : string) (cmdList : com list) (e' : env) (y : string) (rest : string list) (xs : com list) (stack : string list) (stacks : string list list) (es : env list) (e : env) : string list * env =
	match ( eval cmdList [] [] [] (set argName (Var y) e') ) with
	| s, e'' -> ( match s with
				  | [] -> eval xs stack stacks es e
				  | top :: ys -> (if checkName top 
								  then (match (fetch top e' []) with
								        | None -> eval xs (top :: rest) stacks es e
										| Some t -> eval xs (t :: rest) stacks es e) 
								  else eval xs (top :: rest) stacks es e)
				  | top -> (if checkName top 
						    then (match (fetch top e' []) with
							      | None -> eval xs (top :: rest) stacks es e
							      | Some t -> eval xs (t :: rest) stacks es e) 
						    else eval xs (top :: rest) stacks es e))
	| _, _ -> eval xs ("<error>" :: stack) stacks es e*)


(*

	type fun = (string * string * com list * env list)
	
	let rec evalFunc (f : fun) (a : arg) : string = 
		match f with 
		| (function, arg, exp, el) -> match ( el ) with
									  | x :: rest -> match ( eval (exp) [] [] [] (set arg a e) ) with
													 | x :: rest -> x
													 | x -> x
													 | [] -> "<error>"
									  | x -> match ( eval (exp) [] [] [] (set arg a e) ) with
													 | x :: rest -> x
													 | x -> x
													 | [] -> "<error>"
									  | [] -> match ( eval (exp) [] [] [] (set arg a e) ) with
													 | x :: rest -> x
													 | x -> x
													 | [] -> "<error>"
 
*)

(* Interpreter *)
let rec eval (exp : com list) (stack : string list) (stacks : string list list) (es : env list) (e : env) : string list * env =
	match exp with
	| []             -> (stack, e)
	| Quit :: xs     -> (stack, e)
	| PushI i :: xs  -> eval xs (string_of_int i :: stack) stacks es e
	| PushB b :: xs  -> eval xs (b :: stack) stacks es e
	| PushS s :: xs  -> eval xs ((s) :: stack) stacks es e
	| PushN n :: xs  -> eval xs (n :: stack) stacks es e
	| Push :: xs     -> eval xs ("<unit>" :: stack) stacks es e
	| Bind :: xs     -> (match stack with
					 	 | x :: y :: rest -> (if checkName x 
											  then ( if ( (checkInt y e es) || (checkBool y e es) || (checkString y e es) || (checkUnit y e es) ) 
													then eval xs ("<unit>" :: rest) stacks es (set x (Var y) e)
													else ( match (fetch y e es) with
														   | Some i -> eval xs ("<unit>" :: rest) stacks es (set x (Var y) e) 
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
	| Fun (funName, argName) :: xs -> eval (getRest xs) ("<unit>" :: stack) stacks es ( set funName ( Func (argName, (parseFunction xs), e) ) e)
	| InOutFun (funName, argName) :: xs -> eval (getRest xs) ("<unit>" :: stack) stacks es ( set funName ( InOutFunc (argName, (parseFunction xs), e) ) e)
	| Call :: xs     -> (match (stack) with 
						 | y :: x :: rest -> ( if checkName x
											   then ( match (fetch x e es) with
													  | None -> eval xs ("<error>" :: stack) stacks es e
													  | Some t -> ( match t with
													                | Var z -> ( if checkName z 
															                     then (match (fetch z e es) with
																				       | None -> eval xs ("<error>" :: stack) stacks es e
																					   | Some g -> (match g with
																									| Var _ -> eval xs ("<error>" :: stack) stacks es e
																					                | Func (argName, cmdList, e') -> ( if checkName y
																																	   then ( match (fetch y e es) with
																																			  | Some z -> (match (eval cmdList [] [] [] (set argName (z) e')) with 
																																						   | (ys, e'') -> ( match ys with
																																											| [] -> eval xs ("<error>" :: stack) stacks es e
																																											| top :: rest' -> ( if checkName top
																																																then ( match (fetch top e'' []) with
																																																	   | None -> eval xs (top :: rest) stacks es e
																																																	   | Some Var s -> eval xs (s :: rest) stacks es e
																																																	   | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																																	   | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																																else eval xs (top :: rest) stacks es e  )))
																																			  | None -> (match (eval cmdList [] [] [] (set argName (Var y) e')) with 
																																						 | (ys, e'') -> ( match ys with
																																										  | [] -> eval xs ("<error>" :: stack) stacks es e
																																										  | top :: rest' -> ( if checkName top
																																															  then ( match (fetch top e'' []) with
																																																	 | None -> eval xs (top :: rest) stacks es e
																																																	 | Some Var s -> eval xs (s :: rest) stacks es e
																																																	 | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																																	 | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																															  else eval xs (top :: rest) stacks es e  ))))
																																	   else (match (eval cmdList [] [] [] (set argName (Var y) e')) with 
																																			 | (ys, e'') -> ( match ys with
																																							  | [] -> eval xs ("<error>" :: stack) stacks es e
																																							  | top :: rest' -> ( if checkName top
																																												  then ( match (fetch top e'' []) with
																																														 | None -> eval xs (top :: rest) stacks es e
																																														 | Some Var s -> eval xs (s :: rest) stacks es e
																																														 | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																														 | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																												   else eval xs (top :: rest) stacks es e  ))) )
																	                                | InOutFunc (argName, cmdList, e') -> ( if checkName y
																											                                then ( match (eval cmdList [] [] [] (set argName (Var y) e')) with
																												                                   | (ys, e'') -> ( match (fetch y e'' []) with
																												                                                    | Some Var s -> eval xs stack stacks es (set y (Var s) e)
																																	                                | _ -> eval xs ("<error>" :: stack) stacks es e  ) )
																											                                else eval xs ("<error>" :: stack) stacks es e ) ) )
															                     else eval xs ("<error>" :: stack) stacks es e )
																	| Func (argName, cmdList, e') -> ( if checkName y
																									   then ( match (fetch y e es) with
																											  | Some z -> (match (eval cmdList [] [] [] (set argName (z) e')) with 
																														   | (ys, e'') -> ( match ys with
																																			| [] -> eval xs ("<error>" :: stack) stacks es e
																																			| top :: rest' -> ( if checkName top
																																							    then ( match (fetch top e'' []) with
																																									   | None -> eval xs (top :: rest) stacks es e
																																									   | Some Var s -> eval xs (s :: rest) stacks es e
																																									   | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																									   | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																								else eval xs (top :: rest) stacks es e  )))
																											  | None -> (match (eval cmdList [] [] [] (set argName (Var y) e')) with 
																														 | (ys, e'') -> ( match ys with
																																		  | [] -> eval xs ("<error>" :: stack) stacks es e
																																		  | top :: rest' -> ( if checkName top
																																							  then ( match (fetch top e'' []) with
																																									 | None -> eval xs (top :: rest) stacks es e
																																									 | Some Var s -> eval xs (s :: rest) stacks es e
																																									 | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																									 | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																							  else eval xs (top :: rest) stacks es e  ))))
																									   else (match (eval cmdList [] [] [] (set argName (Var y) e')) with 
																							                 | (ys, e'') -> ( match ys with
																														      | [] -> eval xs ("<error>" :: stack) stacks es e
  																							                                  | top :: rest' -> ( if checkName top
																																			      then ( match (fetch top e'' []) with
																																			             | None -> eval xs (top :: rest) stacks es e
																																			             | Some Var s -> eval xs (s :: rest) stacks es e
																																				         | Some Func (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) 
																																				         | Some InOutFunc (argName', cmdList', e''') -> eval xs (top :: rest) stacks es ( set top ( Func (argName', (cmdList'), e''') ) e) )
																																			      else eval xs (top :: rest) stacks es e  ))) )
																	| InOutFunc (argName, cmdList, e') -> ( if checkName y
																											then ( match (eval cmdList [] [] [] (set argName (Var y) e')) with
																												   | (ys, e'') -> ( match (fetch y e'' []) with
																												                    | Some Var s -> eval xs stack stacks es (set y (Var s) e)
																																	| _ -> eval xs ("<error>" :: stack) stacks es e  ) )
																											else eval xs ("<error>" :: stack) stacks es e ) ) )
											   else eval xs ("<error>" :: stack) stacks es e )
						 | x :: _ -> eval xs ("<error>" :: stack) stacks es e
						 | [] -> eval xs ("<error>" :: stack) stacks es e)
	| Return :: xs -> (stack, e)
	| FunEnd :: xs -> (stack, e);;

	
let rec parse (inCmds : string list) : com list =
	match inCmds with
	| [] -> []
	| x :: xs -> match (explodeS x) with
				| "PushI" :: i :: _ -> (PushI (int_of_string i)) :: (parse xs)
				| "PushB" :: b :: _ -> PushB b :: (parse xs)
				| "PushS" :: ls -> (PushS (String.concat " " ls)) :: (parse xs)
				| "PushN" :: n :: _ -> PushN n :: (parse xs)
				| "Push"  :: u :: _ -> Push :: (parse xs)
				| "Fun" :: funName :: argName :: _ -> (Fun (funName, argName)) :: (parse xs)
				| "InOutFun" :: funName :: argName :: _ -> (InOutFun (funName, argName)) :: (parse xs)
				| "Return" :: _ -> Return :: (parse xs)
				| "FunEnd" :: _ -> FunEnd :: (parse xs)
				| "Call"   :: _ -> Call :: (parse xs)
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
				| _ -> parse xs;;

let runPrompt (cmds : string list) : string list =
	match parse cmds with
	| [] -> []
	| exp -> match (eval exp [] [] [] []) with
			 | (ls, e) -> ls;;

let interpreter (input : string) (output : string ) : unit =
	let commands = readlines input in
	  let answers = runPrompt commands in
	    write output (remQuotesTotal answers);;

(*
let comparing (input1 : string) (output1 : string) (output2 : string) : bool =
	interpreter input1 output1;
	let answers1 = readlines output1 in
	let answers2 = readlines output2 in
	let rec aux (s1 : string list) (s2 : string list) : bool =
			match s1, s2 with
			| [], [] -> true
			| x :: xs, y :: ys -> (if (String.equal x y) then aux xs ys else false )
			| _, _ -> false
	in aux answers1 answers2;;
	
	
comparing "./input/input1.txt" "output.txt" "./output/output1.txt";;
comparing "./input/input2.txt" "output.txt" "./output/output2.txt";;
comparing "./input/input3.txt" "output.txt" "./output/output3.txt";;
comparing "./input/input4.txt" "output.txt" "./output/output4.txt";;
comparing "./input/input5.txt" "output.txt" "./output/output5.txt";;
comparing "./input/input6.txt" "output.txt" "./output/output6.txt";;
comparing "./input/input7.txt" "output.txt" "./output/output7.txt";;
comparing "./input/input8.txt" "output.txt" "./output/output8.txt";;
comparing "./input/input9.txt" "output.txt" "./output/output9.txt";;
comparing "./input/input10.txt" "output.txt" "./output/output10.txt";;
comparing "./input/input11.txt" "output.txt" "./output/output11.txt";;
comparing "./input/input12.txt" "output.txt" "./output/output12.txt";;
comparing "./input/input13.txt" "output.txt" "./output/output13.txt";;
comparing "./input/input14.txt" "output.txt" "./output/output14.txt";;
comparing "./input/input15.txt" "output.txt" "./output/output15.txt";;
comparing "./input/input16.txt" "output.txt" "./output/output16.txt";;
comparing "./input/input17.txt" "output.txt" "./output/output17.txt";;
comparing "./input/input18.txt" "output.txt" "./output/output18.txt";;
comparing "./input/input19.txt" "output.txt" "./output/output19.txt";;
comparing "./input/input20.txt" "output.txt" "./output/output20.txt";;*)