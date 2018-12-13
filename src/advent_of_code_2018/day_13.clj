(ns advent-of-code-2018.day-13
  (:require [advent-of-code-2018.input :as input])
  (:require [clojure.string :as str]))

(def cart-symbols #{"^" "v" "<" ">"})

(defn sanitize-route [v]
  (case v
    "^" "|"
    "v" "|"
    "<" "-"
    ">" "-"
    v))

(defn build-cart [x y v]
  {:x x
   :y y
   :dir v
   :last-turn nil})

(defn find-carts [points]
  (filter (fn [[_ v]] (contains? cart-symbols v)) points))

(defn parse-line [[y s]]
  (let [points (str/split s #"")
        indexed-points (zipmap (range) points)
        valuable-points (filter (fn [[_ v]] (not (= v " "))) indexed-points)
        routes (reduce (fn [acc [x v]] (assoc acc [x y] (sanitize-route v))) {} valuable-points)
        carts (map (fn [[x v]] (build-cart x y v)) (find-carts valuable-points))]
    [routes carts]))

(defn parse [s]
  (let [lines (str/split s #"\n")
        indexed-lines (zipmap (range) lines)
        parsed-lines (map parse-line indexed-lines)]
    (reduce
     (fn [[routes-acc carts-acc] [routes carts]] [(merge routes-acc routes) (concat carts-acc carts)])
     [{} []]
     parsed-lines)))

(defn next-turn [last-turn]
  (case last-turn
    nil :left
    :left :straight
    :straight :right
    :right :left))

(defn turn [{:keys [x y dir last-turn] :as cart}]
  (let [next-turn (next-turn last-turn)
        cart (assoc cart :last-turn next-turn)]

    (case [dir next-turn]
      ["^" :left] (assoc cart :x (dec x) :dir "<")
      ["^" :straight] (assoc cart :y (dec y))
      ["^" :right] (assoc cart :x (inc x) :dir ">")

      ["v" :left] (assoc cart :x (inc x) :dir ">")
      ["v" :straight] (assoc cart :y (inc y))
      ["v" :right] (assoc cart :x (dec x) :dir "<")

      ["<" :left] (assoc cart :y (inc y) :dir "v")
      ["<" :straight] (assoc cart :x (dec x))
      ["<" :right] (assoc cart :y (dec y) :dir "^")

      [">" :left] (assoc cart :y (dec y) :dir "^")
      [">" :straight] (assoc cart :x (inc x))
      [">" :right] (assoc cart :y (inc y) :dir "v"))))

(defn tick [routes {:keys [x y dir] :as cart}]
  (case [(routes [x y]) dir]
    ["|" "^"] (assoc cart :y (dec y))
    ["|" "v"] (assoc cart :y (inc y))

    ["-" "<"] (assoc cart :x (dec x))
    ["-" ">"] (assoc cart :x (inc x))

    ["/" "^"] (assoc cart :x (inc x) :dir ">")
    ["/" "v"] (assoc cart :x (dec x) :dir "<")
    ["/" ">"] (assoc cart :y (dec y) :dir "^")
    ["/" "<"] (assoc cart :y (inc y) :dir "v")

    ["\\" "^"] (assoc cart :x (dec x) :dir "<")
    ["\\" "v"] (assoc cart :x (inc x) :dir ">")
    ["\\" ">"] (assoc cart :y (inc y) :dir "v")
    ["\\" "<"] (assoc cart :y (dec y) :dir "^")

    (turn cart)))

(defn tick-all [routes carts]
  (let [sorted-carts (sort-by (juxt :y :x) carts)]
    (map #(tick routes %) carts)))

(defn crashes [carts]
  (let [grouped (group-by (fn [{:keys [x y]}] [x y]) carts)
        crashed (filter (fn [[_ coll]] (> (count coll) 1)) grouped)]
    (keys crashed)))

(defn first-crash-location [s]
  (let [[routes carts] (parse s)]
    (loop [carts carts]
      (let [new-carts (tick-all routes carts)
            crashes (crashes new-carts)]
        (if (empty? crashes)
          (recur new-carts)
          crashes)))))

(defn solve-part-1 []
  (-> "day_13.txt"
      input/read-raw
      first-crash-location))
