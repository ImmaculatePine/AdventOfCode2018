(ns advent-of-code-2018.day-5
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(defn opposite? [a b]
  (and
   (not (= a b))
   (= (str/upper-case a) (str/upper-case b))))

(defn out->remaining [out remaining]
  (let [s (peek out)]
    (if s (into [s] remaining) remaining)))

(defn out->void [out]
  (if (empty? out) [] (pop out)))

(defn opposite-polarity-shrink [s]
  (loop [[a b & remaining] (str/split s #"")
         out []]
    (cond
      (and a b) (if (opposite? a b)
                  (recur (out->remaining out remaining) (out->void out))
                  (recur (into [b] remaining) (conj out a)))
      (and a (not b)) (recur remaining (conj out a))
      :else (str/join out))))

(defn solve-part-1 []
  (-> "day_5.txt"
      input/read-raw
      opposite-polarity-shrink
      count))
