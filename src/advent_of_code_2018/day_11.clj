(ns advent-of-code-2018.day-11
  (:require [advent-of-code-2018.input :as input]))

(defn hundreds [n]
  (/ (- (rem n 1000) (rem n 100)) 100))

(defn power [x y n]
  (let [rack-id (+ x 10)]
    (-> rack-id
        (* y)
        (+ n)
        (* rack-id)
        hundreds
        (- 5))))

(defn grid [n]
  (into {}
        (for [x (range 1 301)
              y (range 1 301)]
          [[x y] (power x y n)])))

(defn cell [grid tlx tly]
  (for [x (range tlx (+ tlx 3))
        y (range tly (+ tly 3))]
    (grid [x y])))

(defn cell-power [grid x y]
  (reduce + (cell grid x y)))

(defn powers [grid]
  (into {}
        (for [x (range 1 299)
              y (range 1 299)]
          [[x y] (cell-power grid x y)])))

(defn largest-total-power-cell [n]
  (let [grid (grid n)
        powers (powers grid)]
    (key (apply max-key val powers))))

(defn solve-part-1 []
  (-> "day_11.txt"
      input/read-raw
      Integer/parseInt
      largest-total-power-cell))
