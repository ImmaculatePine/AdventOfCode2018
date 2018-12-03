(ns advent-of-code-2018.day-3
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.set :as set]))

(defn parse [s]
  (let [[_ id x y w h] (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s)]
    {:id id
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     :w (Integer/parseInt w)
     :h (Integer/parseInt h)}))

(defn claim->changes [{:keys [id x y w h]}]
  (for [x (range x (+ x w))
        y (range y (+ y h))]
    [x y]))

(defn apply-change [points change id overlaps]
  (let [old-val (points change)
        new-val (if old-val (conj old-val id) #{id})
        new-points (conj points [change new-val])
        new-overlaps (set/union overlaps (if old-val new-val #{}))]
    [new-points new-overlaps]))

(defn add-claim-to-points [points claim]
  (let [changes (claim->changes claim)
        {id :id} claim]
    (reduce
     (fn [[pts overlaps] change] (apply-change pts change id overlaps))
     [points #{}]
     changes)))

(defn ->data [coll]
  (loop [[s & remaining] coll
         points {}
         claims #{}
         overlapping-claims #{}]
    (if s
      (let [claim (parse s)
            {id :id} claim
            [new-points added-overlaps] (add-claim-to-points points claim)
            new-claims (conj claims id)
            new-overlapping-claims (set/union overlapping-claims added-overlaps)]
        (recur remaining new-points new-claims new-overlapping-claims))
      [points claims overlapping-claims])))

(defn overlapping-area [coll]
  (let [[points _ _] (->data coll)]
    (count
     (filter
      #(> (count %) 1)
      (vals points)))))

(defn not-overlapping [coll]
  (let [[_ claims overlapping-claims] (->data coll)
        not-overlapping-claims (set/difference claims overlapping-claims)]
    (first not-overlapping-claims)))

(defn solve-part-1 []
  (-> "day_3.txt"
      input/read-lines
      overlapping-area))

(defn solve-part-2 []
  (-> "day_3.txt"
      input/read-lines
      not-overlapping))
