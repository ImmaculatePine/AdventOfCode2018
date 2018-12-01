(ns advent-of-code-2018.core
  (:gen-class)
  (:require advent-of-code-2018.day-1))

(defn -main
  [& args]
  (println
   (case args
     '("1") (advent-of-code-2018.day-1/solve-part-1)
     "Please, select the puzzle")))
