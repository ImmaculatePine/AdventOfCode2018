(ns advent-of-code-2018.day-2
  (:require [advent-of-code-2018.input :as input]))

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
