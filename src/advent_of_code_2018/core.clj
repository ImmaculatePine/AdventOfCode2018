(ns advent-of-code-2018.core
  (:gen-class)
  (:require advent-of-code-2018.day-1))

(defn -main
  [& args]
  (let [[puzzle] args]
    (println
     (case puzzle
       "1-1" (advent-of-code-2018.day-1/solve-part-1)
       "1-2" (advent-of-code-2018.day-1/solve-part-2)
       nil "Please, select the puzzle"
       "Unknown puzzle number"))))
