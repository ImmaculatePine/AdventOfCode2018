(ns advent-of-code-2018.day-13-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-13 :refer :all]))

(def input-1 (slurp "test/advent_of_code_2018/fixtures/day_13_input_1.txt"))

(def input-2 (slurp "test/advent_of_code_2018/fixtures/day_13_input_2.txt"))

(deftest first-crash-location-test
  (is (=
       '([0 3])
       (first-crash-location input-1)))
  (is (=
       '([7 3])
       (first-crash-location input-2))))
