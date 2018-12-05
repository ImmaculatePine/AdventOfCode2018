(ns advent-of-code-2018.day-5-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-5 :refer :all]))

(def input "dabAcCaCBAcCcaDA")

(deftest opposite-polarity-shrink-test
  (testing "Returns new polymer when all units with opposite polarities are removed"
    (is (= (opposite-polarity-shrink input) "dabCBAcaDA"))))
