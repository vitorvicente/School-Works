(* #use "PS2.ml";; *)
(* PROBLEM 1 *) 

let rec tetra1 (n : int) : int =
	match n with
		| 0 -> 0
		| 1 -> 1
		| 2 -> 1
		| 3 -> 2
		| x -> tetra1 (x-1) + tetra1 (x-2) + tetra1 (x-3) + tetra1 (x-4);;


(* PROBLEM 2 *)

(*
   PART A: This function works as specified on lists of
   length greater than 4.
*)

let sum_top_4 (xs : int list) : int list =
	match xs with
		| [] -> []
		| x :: y :: z :: w :: ls -> (x+y+z+w) :: xs
		| _ -> xs;;

(* PART B *)

let rec ascending (n : int) : int list =
	let a = 0 in
	let b = n in 
    let rec ascending_aux a b =
      if a > b then [] else a :: ascending_aux (a+1) b in ascending_aux a b;;

(* PART C *)

let tetra2 (n : int) : int =
	if (n == 0) then 0
	else if (n == 1) then 1
	else if (n == 2) then 1
	else if (n == 3) then 2
	else
	let rec tetra2_aux (n : int) (xs : int list) : int list =
		match n with
			| 3 -> xs
			| x -> tetra2_aux (n-1) (sum_top_4 xs)
	in let y = tetra2_aux n [2; 1; 1; 0]
	in match y with
			| [] -> 0
			| x :: xs -> x;;


(* PROBLEM 3 *)

let rec sum_top_k (k : int) (xs : int list) : int = 
  if (List.length xs >= k) then
	let rec sum_top_k_aux (k : int) (xs : int list) (sum : int) : int =
	match k, xs with
		| _, [] -> 0
		| 0, _ -> sum
		| x, first :: ls -> sum_top_k_aux (k-1) ls (sum+first)
	in sum_top_k_aux k xs 0
  else 0;;

let fib_k_step (k : int) (n : int) : int = 0;;

(* PROBLEM 4 *)

type 'a binTree =
   | Leaf
   | Node of 'a * ('a binTree) * ('a binTree)


let rec mapT (f : 'a -> 'b) (t : 'a binTree) : 'b binTree =
 match t with
 | Leaf -> Leaf
 | Node (k, left, right) -> Node((f k), (mapT f left), (mapT f right)) 


let rec foldT (f: 'a -> 'b -> 'b -> 'b) (t: 'a binTree) (base: 'b) : 'b = 
 match t with
 | Leaf -> base
 | Node (k, left, right) -> f k (foldT f left base) (foldT f right base)

(* PROBLEM 5 *) 

let leafCount (t : 'a binTree) : int = foldT (fun _ l r -> l + r) t 1

let nodeCount (t : 'a binTree) : int = foldT (fun _ l r -> 1 + l + r) t 0

let height (t : 'a binTree) : int = foldT (fun _ l r -> 1 + max l r) t 0

(* PROBLEM 6 *) 

let perfect (t : 'a binTree) : bool =
  let x = int_of_float((2. ** float_of_int(height t)) -. 1.) in
    if (x == (nodeCount t)) then true else false

let degenerate (t : 'a binTree) : bool =
  if ((height t) == (nodeCount t)) then true else false
  
let treeToList (t : 'a binTree) : 'a list option = 
  match (degenerate t) with
  | false -> None
  | true -> Some (foldT (fun k l r -> k :: List.append l r) t [])



