(ns advent-of-code-2018.day-3
  (:require [advent-of-code-2018.input :as input]))

(defn parse [s]
  (let [[_ id x y w h] (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s)]
    {:id id
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     :w (Integer/parseInt w)
     :h (Integer/parseInt h)}))

(defn update-point [old-set id]
  (if old-set (conj old-set id) #{id}))

(defn add-square-to-points [points {:keys [id x y w h]}]
  (let [changes (for [x (range x (+ x w))
                      y (range y (+ y h))]
                  [x y])]
    (reduce #(update %1 %2 update-point id) points changes)))

(defn points [coll]
  (loop [[s & remaining] coll
         points {}]
    (if s
      (let [square (parse s)
            new-points (add-square-to-points points square)]
        (recur remaining new-points))
      points)))

(defn overlapping-area [coll]
  (count
   (filter
    #(> (count %) 1)
    (vals (points coll)))))

(defn solve-part-1 []
  (-> "day_3.txt"
      input/read-lines
      overlapping-area))
