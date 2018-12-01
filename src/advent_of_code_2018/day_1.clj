(ns advent-of-code-2018.day-1
  (:require [clojure.java.io :as io]))

(defn read-input [filename]
  (with-open [r (io/reader (io/resource filename))]
    (doall (line-seq r))))

(defn parse [coll] (map #(Integer/parseInt %) coll))

(defn sum [coll] (reduce + coll))

(defn first-repeated-frequency
  [coll]
  (loop [in coll
         original-input coll
         current-frequency 0
         found-frequencies #{0}]
    (let [[change & remaining] in
          next-frequency (and change (+ current-frequency change))]
      (cond
        (nil? next-frequency) (recur original-input original-input current-frequency found-frequencies)
        (contains? found-frequencies next-frequency) next-frequency
        :else (recur remaining original-input next-frequency (conj found-frequencies next-frequency))))))

(defn solve-part-1
  []
  (sum (parse (read-input "day_1.txt"))))

(defn solve-part-2
  []
  (first-repeated-frequency (parse (read-input "day_1.txt"))))
