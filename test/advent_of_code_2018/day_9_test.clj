(ns advent-of-code-2018.day-9-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-9 :refer :all]))

(deftest winning-score-test
  (testing "Returns high score"
    (is (= 32 (winning-score 9 25)))
    (is (= 8317 (winning-score 10 1618)))))
