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

(defn sum-metadata [coll]
  (let [[tree _] (parse coll)
        metadata (flatten tree)]
    (reduce + metadata)))

(defn ->integers [coll]
  (doall (map #(Integer/parseInt %) coll)))

(defn solve-part-1 []
  (-> "day_8.txt"
      input/read-words
      ->integers
      sum-metadata))
