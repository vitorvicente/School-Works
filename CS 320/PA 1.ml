(* #use "streams.ml";;*)


type 'a stream = Nil | Cons of 'a * (unit -> 'a stream)

let rec take (n : int)  (s : 'a stream) : 'a list = 
  if n >= 0
  then
    match s with 
      | Nil -> []
	  | Cons (a, f ) -> a :: take (n-1) (f ())
  else []

let rec countFrom (i: int) : int stream =
  Cons (i , fun () -> countFrom (i + 1))

let rec nats : int stream = countFrom 0



(* PART A *)
let read (s : 'a stream) (default : 'a) : ('a * 'a stream) =
	match s with
		| Nil -> (default, Nil)
		| Cons (valueB, fB) -> (valueB, (fB ()))

(* PART B *)
let rec skip (s : 'a stream) (p : 'a -> bool) : 'a stream =
	match s with
		| Nil -> (Nil)
		| Cons (valueB, fB) ->
			if (p valueB) then Cons (valueB, fun () -> skip (fB ()) p) else skip (fB ()) p

(* PART C *)
let rec mergeS (s : 'a stream) (t : 'a stream) : 'a stream =
	match s, t with
		| Nil, Cons(valC, fC) -> t
		| Cons(valC, fC), Nil -> s
		| Nil, Nil -> Nil
		| Cons(valC, fC), Cons(valD, fD) -> Cons(valC, fun () ->(mergeS t (fC ())))

(* PART D *)
let rec twoseq (s : 'a stream) (t: 'a stream) : 'a stream =
	match s, t with
		| Nil, Cons(valC, fC) -> t
		| Cons(valC, fC), Nil -> s
		| Nil, Nil -> Nil
		| Cons(valC, fC), Cons(valD, fD) -> Cons(valC, fun () -> (twoseq (fC ()) t))

(* PART E *)
let rec dupk (x : 'a) (k : int) (s : 'a stream) : 'a stream =
	match k with
		| 0 -> s
		| y -> Cons(x, fun () -> dupk x (k-1) s)

(* PART F *)
let rec repeatk (k : int) (s : 'a stream) : 'a stream =
	let rec repeatkaux (j : int) (l : 'a stream) : 'a stream =
		match j, l with
			| _, Nil -> Nil
			| 0, Cons(valA, fA) -> repeatkaux k (fA ())
			| x, Cons(valA, fA) -> Cons(valA, fun () -> repeatkaux (x-1) l)
	in repeatkaux k s


(* PART G *)
let rec addAdjacent (s : int stream) : int stream = 
	match s with
		| Nil -> Nil
		| Cons(valA, fA) -> match (fA ()) with
							| Nil -> Cons(valA, fun () -> Nil)
							| Cons(valB, fB) -> Cons((valA + valB), fun () -> (addAdjacent (fB ())))

(* PART H *)
let rec addAdjacentk (k : int) (s : int stream) : int stream = Nil
(*	let rec addAdjacentkAux (i : int) (t : int stream) : int stream =
		match i, t with 
			| _, Nil -> Nil
			| 0, Cons(valA, fA) -> addAdjacentkAux k t 
			| x, Cons(valA, fA) -> match (fA()) with
									| Nil -> Nil
									| Cons(valB, fB) -> addAdjacentkAux (i-1) (Cons((valA + valB), fB))
	in addAdjacentkAux k s *)


(* PART I *)
let rec binOpSeq (f : 'a -> 'b -> 'c) (s : 'a stream) (t : 'b stream) : 'c stream =
	match s, t with
		| Nil, _ -> Nil
		| _, Nil -> Nil
		| Cons(valA, fA), Cons(valB, fB) -> Cons((f valA valB), fun () -> (binOpSeq f (fA ()) (fB ()) ))

(* PART J *)
let addSeq (ls : int stream)  (rs : int stream) : int stream = binOpSeq (fun i j -> i + j) ls rs
let mulSeq (ls : int stream)  (rs : int stream) : int stream = binOpSeq (fun i j -> i * j) ls rs

(* PART K *)
let zipS (s : 'a stream) (t : 'b stream) : ('a * 'b) stream = binOpSeq (fun i j -> (i, j)) s t

(* PART L *)
let unzipS (s : ('a * 'b) stream) : ('a stream * 'b stream) =
	let rec unzipSAux1 (s : ('a * 'b) stream) : ('a stream) = 
		match s with
			| Nil -> Nil
			| Cons(valC, fC) -> match valC with
									| x, y -> Cons (x, fun () -> (unzipSAux1 (fC ()) ))
	in let rec unzipSAux2 (s : ('a * 'b) stream) : ('a stream) = 
		match s with
			| Nil -> Nil
			| Cons(valC, fC) -> match valC with
									| x, y -> Cons (y, fun () -> (unzipSAux2 (fC ()) ))
	in match s with
		| Nil -> (Nil, Nil)
		| Cons(valC, fC) -> (unzipSAux1 s, unzipSAux2 s)
