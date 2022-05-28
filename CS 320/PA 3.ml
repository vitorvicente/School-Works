open Printf

let checkPangram ( input:string ) : string =
	if (not (String.contains input 'a')) then "false"
	else if (not (String.contains input 'b')) then "false"
	else if (not (String.contains input 'c')) then "false"
	else if (not (String.contains input 'd')) then "false"
	else if (not (String.contains input 'e')) then "false"
	else if (not (String.contains input 'f')) then "false"
	else if (not (String.contains input 'g')) then "false"
	else if (not (String.contains input 'h')) then "false"
	else if (not (String.contains input 'i')) then "false"
	else if (not (String.contains input 'j')) then "false"
	else if (not (String.contains input 'k')) then "false"
	else if (not (String.contains input 'l')) then "false"
	else if (not (String.contains input 'm')) then "false"
	else if (not (String.contains input 'n')) then "false"
	else if (not (String.contains input 'o')) then "false"
	else if (not (String.contains input 'p')) then "false"
	else if (not (String.contains input 'q')) then "false"
	else if (not (String.contains input 'r')) then "false"
	else if (not (String.contains input 's')) then "false"
	else if (not (String.contains input 't')) then "false"
	else if (not (String.contains input 'u')) then "false"
	else if (not (String.contains input 'v')) then "false"
	else if (not (String.contains input 'w')) then "false"
	else if (not (String.contains input 'x')) then "false"
	else if (not (String.contains input 'y')) then "false"
	else if (not (String.contains input 'z')) then "false"
	else "true";;

let rec write (path : string) (ls : string list ) : unit =
  let rec loop (ch) (ls : string list ) : unit =
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
	
let rec getresponses (inputText : string list) : string list =
  let rec loop (ls : string list ) : string list =
    match ls with
      | [] -> []
	  | x :: xs -> (checkPangram x) :: loop xs
  in
  let response = loop inputText in
	response;;
	
let pangram (inputChannel, outputChannel) =
	let lines = readlines inputChannel in
		let responses = getresponses lines in
			write outputChannel responses;;
  
  
  
  
  
  
