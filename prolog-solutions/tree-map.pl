size(Node, 0) :- number(Node), !.
size(node(_, _, _, _, Size), Size).
less(Lef, Rig) :- size(Lef, LefSize), size(Rig, RigSize), LefSize < RigSize.
max(Num1, Num2, Num2) :- Num1 < Num2, !.
max(Num1, Num2, Num1).
nodeCr(Key, Value, LefKid, RigKid, node(Key, Value, LefKid, RigKid, Size)) :- size(LefKid, LefSize), size(RigKid, RigSize), max(LefSize, RigSize, MaxSize), Size is MaxSize + 1.

map_get(node(Key, TVal, _, _, _), Key, TVal) :- !.
map_get(node(TKey, _, LefKid, _, _), Key, Val) :- Key < TKey, !, map_get(LefKid, Key, Val).
map_get(node(_, _, _, RigKid, _), Key, Val) :- map_get(RigKid, Key, Val).

create(node(TKey, TVal, _, _, _), node(LKey, LVal, _, _, _), node(RKey, RVal, _, _, _), [A, B, C, D], R)
		:- nodeCr(LKey, LVal, A, B, Left), nodeCr(RKey, RVal, C, D, Right), nodeCr(TKey, TVal, Left, Right, R).
turn(Node, Node) :- Node = node(Key, Val, Lef, Rig, _), size(Lef, LefSize), size(Rig, RigSize), Dif is RigSize - LefSize, Dif < 2, Dif > -2, !.
turn(node(Key, Value, LefKid, RigKid, _), Top) :- less(LefKid, RigKid), RigKid = node(RKey, RVal, RLKid, RRKid, _), less(RLKid, RRKid), !,
																									nodeCr(Key, Value, LefKid, RLKid, Lef), nodeCr(RKey, RVal, Lef, RRKid, Top).
turn(node(Key, Value, LefKid, RigKid, _), Top) :- less(LefKid, RigKid), RigKid = node(_, _, RLKid, RRKid, _), \+ less(RLKid, RRKid), !,
																									RLKid = node(_, _, RLL, RLR, _), create(RLKid, node(Key, Value, 0, 0, 0), RigKid, [LefKid, RLL, RLR, RRKid], Top).
turn(node(Key, Value, LefKid, RigKid, _), Top) :- \+ less(LefKid, RigKid), LefKid = node(_, _, LLKid, LRKid, _), less(LLKid, LRKid), !,
																									LRKid = node(_, _, LRL, LRR, _), create(LRKid, LefKid, node(Key, Value, 0, 0, 0), [LLKid, LRL, LRR, RigKid], Top).
turn(node(Key, Value, LefKid, RigKid, _), Top) :- \+ less(LefKid, RigKid), LefKid = node(LKey, LVal, LLKid, LRKid, _), \+ less(LLKid, LRKid), !,
																									nodeCr(Key, Value, LRKid, RigKid, Rig), nodeCr(LKey, LVal, LLKid, Rig, Top).

map_put(0, Key, Value, Node) :- !, nodeCr(Key, Value, 0, 0, Node).
map_put(node(Key, _, LefKid, RigKid, _), Key, Value, Result) :- !, nodeCr(Key, Value, LefKid, RigKid, Result).
map_put(node(TKey, TVal, LefKid, RigKid, _), Key, Value, Result) :- Key < TKey, !, map_put(LefKid, Key, Value, TmpRes), nodeCr(TKey, TVal, TmpRes, RigKid, Top), turn(Top, Result).
map_put(node(TKey, TVal, LefKid, RigKid, _), Key, Value, Result) :- map_put(RigKid, Key, Value, TmpRes), nodeCr(TKey, TVal, LefKid, TmpRes, Top), turn(Top, Result).

delLeft(node(Key, Val, LefKid, RigKid, _), RigKid, Key, Val) :- number(LefKid), !.
delLeft(node(TKey, TVal, LefKid, RigKid, _), Result, ClKey, ClVal) :- delLeft(LefKid, TmpRes, ClKey, ClVal), nodeCr(TKey, TVal, TmpRes, RigKid, Top), turn(Top, Result).

map_remove(0, Key, 0) :- !.
map_remove(node(Key, Val, LefKid, RigKid, _), Key, LefKid) :- number(RigKid), !.
map_remove(node(Key, Val, LefKid, RigKid, _), Key, RigKid) :- number(LefKid), !.
map_remove(TreeMap, Key, Result) :- TreeMap = node(Key, _, LefKid, RigKid, _), !, delLeft(RigKid, TmpRes, ClKey, ClVal), nodeCr(ClKey, ClVal, LefKid, TmpRes, Top), turn(Top, Result).
map_remove(node(TKey, TVal, LefKid, RigKid, _), Key, Result) :- Key < TKey, !, map_remove(LefKid, Key, TmpRes), nodeCr(TKey, TVal, TmpRes, RigKid, Top), turn(Top, Result).
map_remove(node(TKey, TVal, LefKid, RigKid, _), Key, Result) :- map_remove(RigKid, Key, TmpRes), nodeCr(TKey, TVal, LefKid, TmpRes, Top), turn(Top, Result).


map_build([], 0) :- !.
map_build([(Key, Value) | T], SufAns) :- map_build(T, CurAns), map_put(CurAns, Key, Value, SufAns).

map_getCeiling(node(TKey, TVal, _, _, _), TKey, TVal) :- !.
map_getCeiling(node(TKey, _, _, RigKid, _), Key, Value) :- Key > TKey, !, map_getCeiling(RigKid, Key, Value).
map_getCeiling(node(TKey, _, LefKid, _, _), Key, Value) :- map_getCeiling(LefKid, Key, Value), !.
map_getCeiling(node(Tkey, TVal, _, _, _), Key, TVal).

putCeiling(node(TKey, TVal, LefKid, RigKid, _), TKey, Value, Result) :- !, nodeCr(TKey, Value, LefKid, RigKid, Result).
putCeiling(node(TKey, TVal, LefKid, RigKid, _), Key, Value, Result) :- Key > TKey, !, putCeiling(RigKid, Key, Value, TmpRes), nodeCr(TKey, TVal, LefKid, TmpRes, Result).
putCeiling(node(TKey, TVal, LefKid, RigKid, _), Key, Value, Result) :- putCeiling(LefKid, Key, Value, TmpRes), !, nodeCr(TKey, TVal, TmpRes, RigKid, Result).
putCeiling(node(TKey, TVal, LefKid, RigKid, _), Key, Value, Result) :- nodeCr(TKey, Value, LefKid, RigKid, Result).

map_putCeiling(Map, Key, Value, Result) :- putCeiling(Map, Key, Value, Result), !.
map_putCeiling(Map, Key, Value, Map).