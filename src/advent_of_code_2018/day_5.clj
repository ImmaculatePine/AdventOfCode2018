(ns advent-of-code-2018.day-5
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(def types (map #(str (char %)) (range 97 123)))

(defn opposite? [a b]
  (and
   (not (= a b))
   (= (str/lower-case a) (str/lower-case b))))

(defn out->remaining [out remaining]
  (let [s (peek out)]
    (if s (into [s] remaining) remaining)))

(defn out->void [out]
  (if (empty? out) [] (pop out)))

(defn shrink [s]
  (loop [[a b & remaining] (str/split s #"")
         out []]
    (cond
      (and a b) (if (opposite? a b)
                  (recur (out->remaining out remaining) (out->void out))
                  (recur (into [b] remaining) (conj out a)))
      (and a (not b)) (recur remaining (conj out a))
      :else (str/join out))))

(defn remove-and-shrink [s removed]
  (let [regexp (re-pattern (str "(?i)" removed))
        reduced (str/replace s regexp "")]
    (shrink reduced)))

(defn possible-structures [s]
  (for [type types]
    (let [shrinked (remove-and-shrink s type)
          size (count shrinked)]
      [type size shrinked])))

(defn shortest-structure [s]
  (first (sort-by (fn [[_ size _]] size) (possible-structures s))))

(defn solve-part-1 []
  (-> "day_5.txt"
      input/read-raw
      shrink
      count))

(defn solve-part-2 []
  (let [[_ n _] (-> "day_5.txt" input/read-raw shortest-structure)] n))
