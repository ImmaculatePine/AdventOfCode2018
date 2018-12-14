(ns advent-of-code-2018.day-14
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(def initial-recipes {0 3, 1 7})

(defn parse [s]
  (let [coll (str/split s #"")
        integers (map #(Integer/parseInt %) coll)]
    integers))

(defn add-recipes [recipes-map new-recipes-list]
  (let [size (count recipes-map)
        idxs (range size (+ size (count new-recipes-list)))
        new-recipes-map (zipmap idxs new-recipes-list)]
    (merge recipes-map new-recipes-map)))

(defn last-n-recipes [recipes n]
  (let [size (count recipes)
        idxs (range (- size n) size)]
    (map (fn [idx] (recipes idx)) idxs)))

(defn adjust-idx [idx size]
  (cond
    (zero? size) 0
    (>= idx size) (adjust-idx (- idx size) size)
    (< idx 0) (adjust-idx (+ idx size) size)
    :else idx))

(defn combine-recipes [coll [idx1 idx2]]
  (let [val1 (coll idx1)
        val2 (coll idx2)
        new-recipes (parse (str (+ val1 val2)))
        new-coll (add-recipes coll new-recipes)
        size (count new-coll)
        new-idx [(adjust-idx (+ 1 val1 idx1) size) (adjust-idx (+ 1 val2 idx2) size)]]
    [new-coll new-idx]))

(defn recipes
  ([n] (recipes initial-recipes [0 1] n))

  ([recipes currents n]
   (loop [coll recipes
          currents currents]
     (if (= (count coll) n)
       coll
       (let [[new-coll new-currents] (combine-recipes coll currents)]
         (recur new-coll new-currents))))))

(defn next-10-recipes-after [n]
  (let [recipes (recipes (+ n 10))]
    (str/join (last-n-recipes recipes 10))))

(defn solve-part-1 []
  (-> "day_14.txt"
      input/read-raw
      Integer/parseInt
      next-10-recipes-after))
