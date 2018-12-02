(ns advent-of-code-2018.day-2-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-2 :refer :all]))

(def ids '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))

(deftest checksum-test
  (testing "Returns so-called checksum of the list"
    (is (= (checksum ids) 12))))
