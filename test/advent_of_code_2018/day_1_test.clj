(ns advent-of-code-2018.day-1-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-1 :refer :all]))

(deftest parse-test
  (testing "Returns list of integers"
    (is (= (parse '("+1" "+1" "+1")) '(1 1 1)))
    (is (= (parse '("+1" "+1" "-2")) '(1 1 -2)))
    (is (= (parse '("-1" "-2" "-3")) '(-1 -2 -3)))))

(deftest sum-test
  (testing "Returns sum of all elements"
    (is (= (sum '(1 1 1)) 3))
    (is (= (sum '(1 1 -2)) 0))
    (is (= (sum '(-1 -2 -3)) -6))))

(deftest first-repeated-frequency-test
  (testing "Returns the first repeated frequency"
    (is (= (first-repeated-frequency '(1 -1)) 0))
    (is (= (first-repeated-frequency '(3 3 4 -2 -4)) 10))
    (is (= (first-repeated-frequency '(-6 3 8 5 -6)) 5))
    (is (= (first-repeated-frequency '(7 7 -2 -7 -4)) 14))))
