(ns advent-of-code-2018.day-7
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.set :as set])
  (:require [clojure.string :as str]))

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

(defn select-transition [froms tos transitions deps]
  (first
   (sort-transitions
    (apply-deps deps
                (->nodes-> froms tos transitions)))))

(defn order [coll]
  (let [[nodes transitions] (graph coll)
        entry-points (entry-points transitions)
        entry-transitions (map #(vector nil %) entry-points)
        extended-transitions (concat transitions entry-transitions)]
    (loop [to-do nodes
           done [nil]
           deps (deps nodes transitions)]

      (if (empty? to-do)
        (str/join done)
        (let [[from to] (select-transition done to-do extended-transitions deps)
              new-to-do (remove-node to to-do)
              new-done (conj done to)
              new-deps (remove-dep [from to] deps)]
          (recur
           new-to-do
           new-done
           new-deps))))))

(defn solve-part-1 []
  (-> "day_7.txt"
      input/read-lines
      order))
