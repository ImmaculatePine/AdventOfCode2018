(ns advent-of-code-2018.core
  (:require advent-of-code-2018.day-1)
  (:require advent-of-code-2018.day-2)
  (:require advent-of-code-2018.day-3)
  (:require advent-of-code-2018.day-4)
  (:require advent-of-code-2018.day-5)
  (:require advent-of-code-2018.day-6))

(defn -main [& args]
  (let [[puzzle] args]
    (println
     (case puzzle
       "1-1" (advent-of-code-2018.day-1/solve-part-1)
       "1-2" (advent-of-code-2018.day-1/solve-part-2)
       "2-1" (advent-of-code-2018.day-2/solve-part-1)
       "2-2" (advent-of-code-2018.day-2/solve-part-2)
       "3-1" (advent-of-code-2018.day-3/solve-part-1)
       "3-2" (advent-of-code-2018.day-3/solve-part-2)
       "4-1" (advent-of-code-2018.day-4/solve-part-1)
       "4-2" (advent-of-code-2018.day-4/solve-part-2)
       "5-1" (advent-of-code-2018.day-5/solve-part-1)
       "5-2" (advent-of-code-2018.day-5/solve-part-2)
       "6-1" (advent-of-code-2018.day-6/solve-part-1)
       nil "Please, select the puzzle"
       "Unknown puzzle number"))))
