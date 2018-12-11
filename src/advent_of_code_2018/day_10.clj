(ns advent-of-code-2018.day-10
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(defn parse [s]
  (let [[_ x y vx vy] (re-matches #"position=<(.+), (.+)> velocity=<(.+), (.+)>" s)]
    [[(Integer/parseInt (str/trim x)) (Integer/parseInt (str/trim y))]
     [(Integer/parseInt (str/trim vx)) (Integer/parseInt (str/trim vy))]]))

(defn points [coll]
  (map parse coll))

(defn move-all [coll]
  (map
   (fn [[[x y] [vx vy :as velocity]]]
     [[(+ x vx) (+ y vy)]
      velocity])
   coll))

(defn plane-corners [coordinates]
  (let [xs (map (fn [[x _]] x) coordinates)
        ys (map (fn [[_ y]] y) coordinates)
        min-x (apply min xs)
        min-y (apply min ys)
        max-x (apply max xs)
        max-y (apply max ys)]
    [[min-x min-y] [max-x max-y]]))

(defn exists? [coordinate points]
  (contains? points coordinate))

(defn plane [points]
  (let [coordinates (into #{} (map #(first %) points))
        [[min-x min-y] [max-x max-y]] (plane-corners coordinates)]
    (if (> (- max-x min-x) 100)
      []
      (for [x (range min-x (inc max-x))
            y (range min-y (inc max-y))]
        [x
         y
         (if (contains? coordinates [x y]) "#" ".")]))))

(defn plane->lines [plane]
  (let [lines (group-by (fn [[_ y _]] y) plane)
        sorted-by-y (vals (sort-by (fn [[y _]] y) lines))]
    (map
     (fn [line] (sort-by (fn [[x _ _]] x) line))
     sorted-by-y)))

(defn line->string [line]
  (str/join (map (fn [[_ _ s]] s) line)))

(defn draw [coll seconds]
  (let [plane (plane coll)
        lines (plane->lines plane)
        strings (map line->string lines)]
    (if (not (empty? lines)) (println (str "After " seconds " seconds")))
    (doall (map println strings))))

(defn emulate [coll steps]
  (let [points (points coll)]
    (loop [points points
           n 1]
      (if (= n steps)
        n
        (let [new-points (move-all points)
              _ (draw new-points n)]
          (recur new-points (inc n)))))))

(defn solve-both []
  (-> "day_10.txt"
      input/read-lines
      (emulate 11000)))
