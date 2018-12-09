(ns advent-of-code-2018.day-8-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-8 :refer :all]))

(def input [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])

(deftest sum-metadata-test
  (testing "Returns sum of nodes metadata"
    (is (= 138 (sum-metadata input)))))
