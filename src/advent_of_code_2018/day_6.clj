(ns advent-of-code-2018.day-6
  (:require [advent-of-code-2018.input :as input]))

(defn parse [s]
  (let [[_ xs ys] (re-matches #"(\d+), (\d+)" s)
        x (Integer/parseInt xs)
        y (Integer/parseInt ys)]
    [x y]))

(defn min-x [points]
  (apply min (map (fn [[x _]] x) points)))

(defn min-y [points]
  (apply min (map (fn [[_ y]] y) points)))

(defn max-x [points]
  (apply max (map (fn [[x _]] x) points)))

(defn max-y [points]
  (apply max (map (fn [[_ y]] y) points)))

(defn plane-corners [points]
  (let [min-x (min-x points)
        min-y (min-y points)
        max-x (max-x points)
        max-y (max-y points)]
    [[min-x min-y] [max-x max-y]]))

(defn plane [[[min-x min-y] [max-x max-y]]]
  (for [x (range min-x (+ max-x 1))
        y (range min-y (+ max-y 1))]
    [x y]))

(defn abs [n] (max n (- n)))

(defn distance [[x1 y1] [x2 y2]]
  (+ (abs (- x1 x2)) (abs (- y1 y2))))

(defn distances-from-centers [point centers]
  (map
   (fn [center] [center (distance point center)])
   centers))

(defn closest-center [point centers]
  (let [distances (distances-from-centers point centers)
        distances-vals (map (fn [[_ val]] val) distances)
        min-distance (apply min distances-vals)
        [matched-center & similar-centers] (filter (fn [[_ v]] (= v min-distance)) distances)
        [matched-center-coords _] matched-center]
    (if (empty? similar-centers) matched-center-coords nil)))

(defn distribute [points centers]
  (group-by
   #(closest-center % centers)
   points))

(defn border? [[px py] [[min-x min-y] [max-x max-y]]]
  (or
   (= px min-x)
   (= px max-x)
   (= py min-y)
   (= py max-y)))

(defn infinite? [points corners]
  (some #(border? % corners)  points))

(defn largest-finite-area [coll]
  (let [centers (map parse coll)
        corners (plane-corners centers)
        points (plane corners)
        distribution (distribute points centers)
        finite-areas (remove (fn [[_ points]] (infinite? points corners)) distribution)
        sorted-areas (sort-by (fn [[_ v]] (count v)) finite-areas)]
    (count (val (last sorted-areas)))))

(defn solve-part-1 []
  (-> "day_6.txt"
      input/read-lines
      largest-finite-area))
