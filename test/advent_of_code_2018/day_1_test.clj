(ns advent-of-code-2018.day-1-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-1 :refer :all]))

(deftest sum-test
  (testing "Returns sum of all elements"
    (is (= (sum '("+1" "+1" "+1")) 3))
    (is (= (sum '("+1" "+1" "-2")) 0))
    (is (= (sum '("-1" "-2" "-3")) -6))))
