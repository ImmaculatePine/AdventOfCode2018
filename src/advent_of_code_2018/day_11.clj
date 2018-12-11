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

(defn cell [grid tlx tly z]
  (for [x (range tlx (+ tlx z))
        y (range tly (+ tly z))]
    (grid [x y])))

(defn powers-1x1 [grid]
  (into {}
        (for [x (range 1 301)
              y (range 1 301)]
          [[x y 1] (grid [x y])])))

(defn powers-3x3 [grid]
  (into {}
        (for [x (range 1 299)
              y (range 1 299)]
          [[x y] (reduce + (cell grid x y 3))])))

(defn power-even [grid powers x y z]
  (let [k [x y z]
        half-z (/ z 2)
        top-left (powers [x y half-z])
        top-right (powers [(+ x half-z) y half-z])
        bottom-left (powers [x (+ y half-z) half-z])
        bottom-right (powers [(+ x half-z) (+ y half-z) half-z])
        v (+ top-left top-right bottom-left bottom-right)]
    [k v]))

(defn power-odd [grid powers x y z]
  (let [k [x y z]
        prev-z (dec z)
        vertical-x (+ x prev-z)
        horizontal-y (+ y prev-z)
        prev-power (powers [x y prev-z])
        horizontal (for [a (range x (+ x prev-z))] (grid [a horizontal-y]))
        vertical (for [a (range y (+ y prev-z))] (grid [vertical-x a]))
        bottom-right (grid [vertical-x horizontal-y])
        v (+ prev-power (reduce + horizontal) (reduce + vertical) bottom-right)]
    [k v]))

(defn powers-zxz [grid powers z]
  (let [f (if (even? z) power-even power-odd)]
    (into {}
          (for [x (range 1 (- 302 z))
                y (range 1 (- 302 z))]
            (f grid powers x y z)))))

(defn add-powers [grid powers z]
  (merge powers (powers-zxz grid powers z)))

(defn largest-total-power-3x3-cell [n]
  (let [grid (grid n)
        powers (powers-3x3 grid)]
    (key (apply max-key val powers))))

(defn largest-total-power-cell [n]
  (let [grid (grid n)]
    (loop [powers (powers-1x1 grid)
           z 2]
      (if (= z 301)
        (key (apply max-key val powers))
        (recur (add-powers grid powers z) (inc z))))))

(defn solve-part-1 []
  (-> "day_11.txt"
      input/read-raw
      Integer/parseInt
      largest-total-power-3x3-cell))

(defn solve-part-2 []
  (-> "day_11.txt"
      input/read-raw
      Integer/parseInt
      largest-total-power-cell))
