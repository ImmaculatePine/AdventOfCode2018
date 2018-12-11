(ns advent-of-code-2018.day-9
  (:require [advent-of-code-2018.input :as input]))

; Circle structure is
; [{number [prev-number next-number] ...} current-number]

(defn parse [s]
  (let [[_ players points] (re-matches #"(\d+) players; last marble is worth (\d+) points" s)]
    [(Integer/parseInt players) (Integer/parseInt points)]))

(defn cw [n [coll current]]
  (loop [n n
         current current]
    (if (zero? n)
      current
      (let [[_ next-marble] (coll current)]
        (recur (dec n) next-marble)))))

(defn ccw [n [coll current]]
  (loop [n n
         current current]
    (if (zero? n)
      current
      (let [[prev-marble _] (coll current)]
        (recur (dec n) prev-marble)))))

(defn link [coll a b]
  (let [[<-a _] (coll a)
        [_ b->] (coll b)]
    (if (= a b)
      (assoc coll a [a a])
      (assoc coll a [<-a b] b [a b->]))))

(defn put-marble [[coll current :as circle] val]
  (let [before (cw 1 circle)
        after (cw 2 circle)]
    [(-> coll
         (link before val)
         (link val after)) val]))

(defn remove-marble [[coll current :as circle]]
  (let [removed-marble (ccw 7 circle)
        prev-marble (ccw 1 [coll removed-marble])
        next-marble (cw 1 [coll removed-marble])]
    [[(-> coll
          (dissoc removed-marble)
          (link prev-marble next-marble)) next-marble] removed-marble]))

(defn next-player [current-player max-players]
  (if (>= current-player max-players) 1 (inc current-player)))

(defn add-score [scores player val]
  (update scores player #(+ %1 %2) val))

(defn high-score [scores]
  (apply max (vals scores)))

(defn winning-score [max-players last-marble]
  (loop [circle [{0 [0 0]} 0]
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

(defn solve-part-2 []
  (let [[max-players last-marble] (parse (input/read-raw "day_9.txt"))]
    (winning-score max-players (* last-marble 100))))
