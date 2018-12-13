(ns advent-of-code-2018.day-12
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(defn state->string [state]
  (let [coll (vals (sort-by key state))
        s (str/join coll)
        [_ middle] (re-matches #"\.*#(.+)#\.*" s)]
    (str "#" middle "#")))

(defn string->state
  ([s]
   (string->state s 0))

  ([s n]
   (let [coll (str/split s #"")
         size (count coll)]
     (zipmap (range n (+ n size)) coll))))

(defn parse-initial-state [s]
  (let [[_ state] (re-matches #"initial state: (.+)" s)]
    (string->state state)))

(defn parse-rule [s]
  (let [[_ from to] (re-matches #"(.+) => (.)" s)]
    [from to]))

(defn parse-input [coll]
  (let [[initial-state _ & rules] coll]
    [(parse-initial-state initial-state)
     (into {} (map parse-rule rules))]))

(defn borders [state]
  (let [keys (keys state)]
    [(apply min keys)
     (apply max keys)]))

(defn extend-state [state]
  (let [[from to] (borders state)]
    (assoc
     state
     (- from 1) "." (- from 2) "." (- from 3) "." (- from 4) "."
     (+ to 1) "." (+ to 2) "." (+ to 3) "." (+ to 4) ".")))

(defn trim-left [state from]
  (loop [i from
         out state]
    (let [v (state i)]
      (if (= v ".")
        (recur (inc i) (dissoc out i))
        out))))

(defn trim-right [state to]
  (loop [i to
         out state]
    (let [v (state i)]
      (if (= v ".")
        (recur (dec i) (dissoc out i))
        out))))

(defn trim-state [state]
  (let [[from to] (borders state)
        trimmed-left (trim-left state from)]
    (trim-right trimmed-left to)))

(defn pattern [n state]
  (str/join (reduce
             (fn [acc i] (conj acc (or (state i) ".")))
             []
             (range (- n 2) (+ n 3)))))

(defn transform [state rules n]
  (let [pattern (pattern n state)]
    (or (rules pattern) ".")))

(defn next-generation [state rules]
  (let [state (extend-state state)
        [from to] (borders state)]
    (loop [n from
           out {}]
      (if (= n to)
        (trim-state out)
        (recur (inc n) (assoc out n (transform state rules n)))))))

(defn total-alive-numbers [state]
  (let [alive (filter (fn [[_ v]] (= v "#")) state)]
    (reduce + (keys alive))))

(defn emulate [state rules n]
  (loop [i 0
         history #{(state->string state)}
         out state]
    (if (= i n)
      [out (total-alive-numbers out)]
      (let [new-state (next-generation out rules)
            new-state-string (state->string new-state)
            already-happen (contains? history new-state-string)
            new-history (if already-happen history (conj history new-state-string))]
        (if already-happen
          (let [[from _] (borders new-state)
                steps-left (- n i 1)
                final-from (+ from steps-left)
                final-state (string->state (state->string new-state) final-from)]
            [final-state (total-alive-numbers final-state)])
          (recur (inc i) new-history new-state))))))

(defn solve-part-1 []
  (let [input (input/read-lines "day_12.txt")
        [state rules] (parse-input input)
        [_ count] (emulate state rules 20)]
    count))

(defn solve-part-2 []
  (let [input (input/read-lines "day_12.txt")
        [state rules] (parse-input input)
        [_ count] (emulate state rules 50000000000)]
    count))
