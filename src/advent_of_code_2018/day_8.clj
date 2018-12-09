(ns advent-of-code-2018.day-8
  (:require [advent-of-code-2018.input :as input]))

(defn parse
  ([[c m & remaining]]
   (parse [] c m remaining))

  ([acc c m [v & remaining :as input]]
   (cond
     (and (zero? c) (zero? m)) [acc input]
     (zero? c) (parse (conj acc v) 0 (- m 1) remaining)
     :else (let [[children remaining] (parse input)]
             (parse (conj acc children) (- c 1) m remaining)))))

(defn score [coll]
  (let [children (filter vector? coll)
        meta (filter integer? coll)
        valuable-children (map #(nth children (- % 1) []) meta)]
    (if (empty? children)
      (reduce + meta)
      (reduce + (map score valuable-children)))))

(defn sum-metadata [coll]
  (let [[tree _] (parse coll)
        metadata (flatten tree)]
    (reduce + metadata)))

(defn root-node-value [coll]
  (let [[tree _] (parse coll)]
    (score tree)))

(defn ->integers [coll]
  (doall (map #(Integer/parseInt %) coll)))

(defn solve-part-1 []
  (-> "day_8.txt"
      input/read-words
      ->integers
      sum-metadata))

(defn solve-part-2 []
  (-> "day_8.txt"
      input/read-words
      ->integers
      root-node-value))
