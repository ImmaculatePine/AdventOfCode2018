(ns advent-of-code-2018.core
  (:require advent-of-code-2018.day-1)
  (:require advent-of-code-2018.day-2))

(defn -main [& args]
  (let [[puzzle] args]
    (println
     (case puzzle
       "1-1" (advent-of-code-2018.day-1/solve-part-1)
       "1-2" (advent-of-code-2018.day-1/solve-part-2)
       "2-1" (advent-of-code-2018.day-2/solve-part-1)
       nil "Please, select the puzzle"
       "Unknown puzzle number"))))
