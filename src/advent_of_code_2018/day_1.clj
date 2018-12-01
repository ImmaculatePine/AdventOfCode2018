(ns advent-of-code-2018.day-1
  (:require [clojure.java.io :as io]))

(defn read-input [filename]
  (with-open [r (io/reader (io/resource filename))]
    (doall (line-seq r))))

(defn sum
  [coll]
  (reduce + (map #(Integer/parseInt %) coll)))

(defn solve-part-1
  []
  (sum (read-input "day_1.txt")))
