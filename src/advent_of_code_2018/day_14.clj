(ns advent-of-code-2018.day-14
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(def initial-recipes {0 3, 1 7})
(def initial-currents [0 1])

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
    [new-coll new-idx new-recipes]))

(defn recipes
  ([n] (recipes initial-recipes initial-currents n))

  ([recipes currents n]
   (loop [coll recipes
          currents currents]
     (if (= (count coll) n)
       coll
       (let [[new-coll new-currents] (combine-recipes coll currents)]
         (recur new-coll new-currents))))))

(defn compare-new-and-remaining [all-expected remaining new-recipes found]
  (loop [remaining remaining
         new-recipes new-recipes
         found found]
    (let [[first-remaining & other-remaining] remaining
          [first-new & other-new] new-recipes]
      (cond
        (empty? remaining) [remaining found]
        (empty? new-recipes) [remaining found]
        (= first-remaining first-new) (recur other-remaining other-new true)
        (and (not found) (not (empty? other-new))) (recur remaining other-new false)
        found (recur all-expected new-recipes false)
        :else (recur all-expected other-new false)))))

(defn next-10-recipes-after [n]
  (let [recipes (recipes (+ n 10))]
    (str/join (last-n-recipes recipes 10))))

(defn recipes-num-before [s]
  (let [size (count s)
        expected (parse s)]
    (loop [coll initial-recipes
           currents initial-currents
           remaining expected
           found false]
      (if (empty? remaining)
        (if (= (last-n-recipes coll size) expected)
          (- (count coll) size)
          (- (count coll) size 1))
        (let [[new-coll new-currents new-recipes] (combine-recipes coll currents)
              [new-remaining new-found] (compare-new-and-remaining expected remaining new-recipes found)]
          (recur new-coll new-currents new-remaining new-found))))))

(defn solve-part-1 []
  (-> "day_14.txt"
      input/read-raw
      Integer/parseInt
      next-10-recipes-after))

(defn solve-part-2 []
  (-> "day_14.txt"
      input/read-raw
      recipes-num-before))
