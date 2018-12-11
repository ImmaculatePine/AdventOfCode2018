(ns advent-of-code-2018.day-11-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-11 :refer :all]))

(deftest power-test
  (is (= -5 (power 122 79 57)))
  (is (= 0 (power 217 196 39)))
  (is (= 4 (power 101 153 71))))
