(ns advent-of-code-2018.day-7
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.set :as set])
  (:require [clojure.string :as str]))

(defn duration [node delay]
  (+ (- (int (first (seq (chars (char-array node))))) 64) delay))

(defn parse [s]
  (let [[_ a b] (re-matches #"Step ([A-Z]) must be finished before step ([A-Z]) can begin\." s)]
    [a b]))

(defn sort-transitions [transitions]
  (sort-by (fn [[_ to]] to) transitions))

(defn node-> [node transitions]
  (filter (fn [[from _]] (= from node)) transitions))

(defn ->node [node transitions]
  (filter (fn [[_ to]] (= to node)) transitions))

(defn ->nodes [nodes transitions]
  (if (empty? nodes)
    transitions
    (reduce concat (map #(->node % transitions) nodes))))

(defn nodes-> [nodes transitions]
  (if (empty? nodes)
    transitions
    (reduce concat (map #(node-> % transitions) nodes))))

(defn ->nodes-> [froms tos transitions]
  (let [a (set (nodes-> froms transitions))
        b (set (->nodes tos transitions))
        matched (set/intersection a b)]
    matched))

(defn nodes [transitions]
  (let [froms (set (map first transitions))
        tos (set (map last transitions))]
    (sort (set/union froms tos))))

(defn deps [nodes transitions]
  (into {}
        (map
         (fn [to]
           [to (set (map first (->node to transitions)))])
         nodes)))

(defn entry-points [transitions]
  (let [froms (set (map first transitions))
        tos (set (map last transitions))]
    (set/difference froms tos)))

(defn graph [coll]
  (let [transitions (map parse coll)
        nodes (nodes transitions)]
    [nodes transitions]))

(defn extended-graph [coll]
  (let [[nodes transitions] (graph coll)
        entry-points (entry-points transitions)
        entry-transitions (map #(vector nil %) entry-points)
        extended-transitions (concat transitions entry-transitions)
        deps (deps nodes transitions)]
    [nodes extended-transitions deps]))

(defn remove-node [which nodes]
  (remove #(= % which) nodes))

(defn remove-dep [[from to] deps]
  (into {}
        (map
         (fn [[k coll]] [k (disj coll from to)])
         deps)))

(defn apply-deps [deps transitions]
  (let [ready-nodes (keys (filter (fn [[_ v]] (empty? v)) deps))
        allowed (filter (fn [[_ to]] (some #{to} ready-nodes)) transitions)]
    allowed))

(defn available-transitions [froms tos transitions deps]
  (sort-transitions
   (apply-deps deps
               (->nodes-> froms tos transitions))))

(defn select-transition [froms tos transitions deps]
  (first (available-transitions froms tos transitions deps)))

(defn take-transitions [n done to-do transitions deps]
  (if (empty? to-do)
    [[] []]
    (loop [n n
           to-do to-do
           selected []]
      (if (zero? n)
        [to-do selected]
        (let [used-nodes (map (fn [[from to]] to) selected)
              all (available-transitions done to-do transitions deps)
              useful (remove (fn [[from to]] (some #{to} used-nodes)) all)
              [used] useful
              [from to] used]
          (if used
            (recur (- n 1) (remove-node to to-do) (conj selected used))
            [to-do selected]))))))

(defn order [coll]
  (let [[nodes transitions deps] (extended-graph coll)]
    (loop [to-do nodes
           done [nil]
           deps deps]
      (if (empty? to-do)
        (str/join done)
        (let [[from to] (select-transition done to-do transitions deps)
              new-to-do (remove-node to to-do)
              new-done (conj done to)
              new-deps (remove-dep [from to] deps)]
          (recur
           new-to-do
           new-done
           new-deps))))))

(defn free? [worker]
  (= worker [nil 0]))

(defn tick [[task seconds :as worker]]
  (if (free? worker)
    [[] worker]
    (let [new-seconds (- seconds 1)]
      (if (zero? new-seconds)
        [[task] [nil 0]]
        [[] [task new-seconds]]))))

(defn tick-all [workers]
  (reduce
   (fn [[done-acc workers-acc] [done worker]]
     [(concat done-acc done) (conj workers-acc worker)])
   [[] []]
   (map tick workers)))

(defn parallel-duration [coll workers-num delay]
  (let [[nodes transitions deps] (extended-graph coll)
        workers (for [n (range workers-num)] [nil 0])]
    (loop [to-do nodes
           done [nil]
           deps deps
           workers workers
           timer 0]
      (if (and (empty? to-do) (every? free? workers))
        (- timer 1)
        (let [[finished updated-workers] (tick-all workers)
              free-workers (filter free? updated-workers)
              busy-workers (remove free? updated-workers)
              new-done (concat done finished)
              new-deps (reduce #(remove-dep [nil %2] %1) deps finished)
              [new-to-do selected-transitions] (take-transitions (count free-workers) new-done to-do transitions new-deps)
              added-workers (map (fn [[_ to]] [to (duration to delay)]) selected-transitions)
              new-workers (concat busy-workers added-workers (repeat (- workers-num (count busy-workers) (count added-workers)) [nil 0]))]
          (recur new-to-do new-done new-deps new-workers (+ timer 1)))))))

(defn solve-part-1 []
  (-> "day_7.txt"
      input/read-lines
      order))

(defn solve-part-2 []
  (-> "day_7.txt"
      input/read-lines
      (parallel-duration 5 60)))
