(ns advent-of-code-2018.day-2
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str])
  (:require [clojure.data :as data]))

;; Part 1

(defn id->checksum [id]
  (let [freqs (frequencies id)
        values (vals freqs)
        has-duplets (some #(= % 2) values)
        has-triplets (some #(= % 3) values)]
    [(if has-duplets 1 0) (if has-triplets 1 0)]))

(defn sum [[sum-a sum-b] [a b]]
  [(+ sum-a a) (+ sum-b b)])

(defn mult [[a b]]
  (* a b))

(defn checksum [ids]
  (->> ids
       (map id->checksum)
       (reduce sum)
       mult))

(defn solve-part-1 []
  (-> "day_2.txt"
      input/read-lines
      checksum))

;; Part 2

(defn string->vector [s]
  (str/split s #""))

(defn distance [a b]
  (loop [a a
         b b
         distance 0]
    (let [[a-head & a-tail] a
          [b-head & b-tail] b]
      (if (and a-head b-head)
        (recur a-tail b-tail (if (= a-head b-head) distance (+ 1 distance)))
        distance))))

(defn similar? [a b]
  (= 1 (distance a b)))

(defn similar [ids]
  (for [id ids
        other-id ids
        :when (similar? id other-id)]
    id))

(defn common-letters [a b]
  (let [a-letters (string->vector a)
        b-letters (string->vector b)
        [_ _ diff] (data/diff a-letters b-letters)
        common (remove nil? diff)]
    (str/join "" common)))

(defn common-letters-between-similar-ids [ids]
  (let [[a b] (similar ids)]
    (common-letters a b)))

(defn solve-part-2 []
  (-> "day_2.txt"
      input/read-lines
      common-letters-between-similar-ids))
