(ns advent-of-code-2018.core
  (:require advent-of-code-2018.day-1)
  (:require advent-of-code-2018.day-2)
  (:require advent-of-code-2018.day-3)
  (:require advent-of-code-2018.day-4)
  (:require advent-of-code-2018.day-5)
  (:require advent-of-code-2018.day-6)
  (:require advent-of-code-2018.day-7)
  (:require advent-of-code-2018.day-8)
  (:require advent-of-code-2018.day-9)
  (:require advent-of-code-2018.day-10))

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
       "6-2" (advent-of-code-2018.day-6/solve-part-2)
       "7-1" (advent-of-code-2018.day-7/solve-part-1)
       "7-2" (advent-of-code-2018.day-7/solve-part-2)
       "8-1" (advent-of-code-2018.day-8/solve-part-1)
       "8-2" (advent-of-code-2018.day-8/solve-part-2)
       "9-1" (advent-of-code-2018.day-9/solve-part-1)
       "9-2" (advent-of-code-2018.day-9/solve-part-2)
       "10-1" (advent-of-code-2018.day-10/solve-both)
       "10-2" (advent-of-code-2018.day-10/solve-both)
       nil "Please, select the puzzle"
       "Unknown puzzle number"))))
