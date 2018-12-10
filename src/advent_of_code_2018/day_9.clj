(ns advent-of-code-2018.day-9
  (:require [advent-of-code-2018.input :as input]))

(defn parse [s]
  (let [[_ players points] (re-matches #"(\d+) players; last marble is worth (\d+) points" s)]
    [(Integer/parseInt players) (Integer/parseInt points)]))

(defn adjust-pos [pos size]
  (cond
    (zero? size) 0
    (>= pos size) (adjust-pos (- pos size) size)
    (< pos 0) (adjust-pos (+ pos size) size)
    :else pos))

(defn cw [n [coll current :as circle]]
  (adjust-pos (+ current n) (count coll)))

(defn ccw [n [coll current :as circle]]
  (adjust-pos (- current n) (count coll)))

(defn put-marble [[coll current :as circle] val]
  (let [pos (cw 1 circle)
        new-current (adjust-pos (inc pos) (inc (count coll)))]
    [(apply vector (concat (subvec coll 0 (inc pos)) [val] (subvec coll (inc pos)))), new-current]))

(defn remove-marble [[coll current :as circle]]
  (let [pos (ccw 7 circle)
        val (coll pos)
        new-current (adjust-pos pos (dec (count coll)))]
    [[(apply vector (concat (subvec coll 0 pos) (subvec coll (inc pos)))) new-current] val]))

(defn next-player [current-player max-players]
  (if (>= current-player max-players) 1 (inc current-player)))

(defn add-score [scores player val]
  (update scores player #(+ %1 %2) val))

(defn high-score [scores]
  (apply max (vals scores)))

(defn winning-score [max-players last-marble]
  (loop [circle [[0] 0]
         player 1
         next-marble 1
         scores (into {} (for [n (range 1 (+ max-players 1))] [n 0]))]
    (if (< next-marble last-marble)
      (if (zero? (rem next-marble 23))
        (let [[new-circle removed-marble] (remove-marble circle)]
          (recur
           new-circle
           (next-player player max-players)
           (+ next-marble 1)
           (add-score scores player (+ next-marble removed-marble))))
        (recur
         (put-marble circle next-marble)
         (next-player player max-players)
         (+ next-marble 1)
         scores))
      (high-score scores))))

(defn solve-part-1 []
  (let [[max-players last-marble] (parse (input/read-raw "day_9.txt"))]
    (winning-score max-players last-marble)))
