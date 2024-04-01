divides(A, B) :- 0 is mod(A, B).

every([], _).
every([H | T], F) :- G =.. [F, H], call(G), every(T, F).

every_between_two([], _, _).
every_between_two([H | T], Arg, F) :- G =.. [F, Arg, H], call(G), every_between_two(T, H, F).

less_or_equal(A, B) :- \+ A > B.

foldLeft([], Z, _, Z).
foldLeft([H | T], Z, F, R) :- G =.. [F, Z, H, RH], call(G), foldLeft(T, RH, F, R).

mult(A, B, R) :- R is A * B.

composite(1).
set_composite(MaxLen, _, Cur) :- Cur > MaxLen, !.
set_composite(MaxLen, Prime, Cur) :- assert(composite(Cur)), NewCur is Cur + Prime, set_composite(MaxLen, Prime, NewCur).
erath(MaxLen, St) :- Square is St * St, Square > MaxLen, !.
erath(MaxLen, St) :- composite(St), !, NewSt is St + 1, erath(MaxLen, NewSt).
erath(MaxLen, St) :- Square is St * St, set_composite(MaxLen, St, Square), NewSt is St + 1, erath(MaxLen, NewSt).

prime(N) :- \+ composite(N).

get_primes(Cur, MaxLen, []) :- Square is Cur * Cur, MaxLen < Square, !.
get_primes(Cur, MaxLen, [Cur | R]) :- prime(Cur), !, NewCur is Cur + 1, get_primes(NewCur, MaxLen, R).
get_primes(Cur, MaxLen, R) :- NewCur is Cur + 1, get_primes(NewCur, MaxLen, R).

init(MaxLen) :- erath(MaxLen, 2), get_primes(1, MaxLen, R), assert(primes(R)).

get_divisors(1, _, []) :- !.
get_divisors(Num, [], R) :- \+ Num = 1, R = [Num], !.
get_divisors(Num, [Prime | Rest], [Prime | R]) :- divides(Num, Prime), !, NewN is Num / Prime, get_divisors(NewN, [Prime | Rest], R).
get_divisors(Num, [Prime | Rest], R) :- get_divisors(Num, Rest, R).

prime_divisors(1, []) :- !.
prime_divisors(Num, R) :- number(Num), !, primes(Primes), get_divisors(Num, Primes, R).
prime_divisors(Num, R) :- every(R, prime), R = [H | T], every_between_two(T, H, less_or_equal), foldLeft(R, 1, mult, Num).

%get_divs_until_root(St, Num, []) :- Square is St * St, Square > Num, !.
%get_divs_until_root(St, Num, [Div]) :- Num is St * St, prime_divisors(St, Div), !.
%get_divs_until_root(St, Num, R) :- \+ divides(Num, St), !, NewSt is St + 1, get_divs_until_root(NewSt, Num, R).
%get_divs_until_root(St, Num, [Div1 | [Div2 | R]]) :- prime_divisors(St, Div1), Divisor is Num / St, prime_divisors(Divisor, Div2), NewSt is St + 1, get_divs_until_root(NewSt, Num, R).
%divisors_divisors(1, [[]]) :- !.
%divisors_divisors(Num, R) :- get_divs_until_root(1, Num, R).
close(Prev, [], [[Prev, CurNum]], CurNum) :- !.
close(Prev, [H | Rest], R, CurNum) :- H = Prev, !, NewCurNum is CurNum + 1, close(Prev, Rest, R, NewCurNum).
close(Prev, [H | Rest], [[Prev, CurNum] | R], CurNum) :- close(H, Rest, R, 1).

unclose([], []) :- !.
unclose([[Elem, Times] | Rest], R) :- Times = 0, !, unclose(Rest, R).
unclose([[Elem, Times] | Rest], [Elem | R]) :- NewTimes is Times - 1, unclose([[Elem, NewTimes] | Rest], R).

unclose_every([], []) :- !.
unclose_every([Elem | Rest], [UnclosedR | R]) :- unclose(Elem, UnclosedR), unclose_every(Rest, R).

concat([], []) :- !.
concat([[] | Oth], R) :- One = [], !, concat(Oth, R).
concat([[Elem | Rest] | Oth], [Elem | R]) :- concat([Rest | Oth], R).

push_every(_, [], []) :- !.
push_every(Elem, [Cur | Rest], [[Elem | Cur] | R]) :- push_every(Elem, Rest, R).

for_by_times(_, St, Times, _, []) :- St > Times, !.
for_by_times(Elem, St, Times, CurR, [PushedR | R]) :- push_every([Elem, St], CurR, PushedR), NewSt is St + 1, for_by_times(Elem, NewSt, Times, CurR, R).

get_parts([], [[]]) :- !.
get_parts([[Elem, Times] | Rest], R) :- get_parts(Rest, TmpR), for_by_times(Elem, 0, Times, TmpR, UnConcatR), concat(UnConcatR, R).

divisors_divisors(1, [[]]) :- !.
divisors_divisors(Num, R) :- prime_divisors(Num, [H | Rest]), close(H, Rest, ClosedP, 1), get_parts(ClosedP, Parts), unclose_every(Parts, R).