(ns advent-of-code-2018.day-6-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-6 :refer :all]))

(def input '("1, 1"
             "1, 6"
             "8, 3"
             "3, 4"
             "5, 5"
             "8, 9"))

(def centers (map parse input))

(deftest closest-center-test
  (testing "Returns center with the shortest Manhattan distance"
    (is (=
         (closest-center [0 0] centers)
         [1 1]))
    (is (=
         (closest-center [2 2] centers)
         [1 1])))
  (testing "Returns nil when few centers with the same Manhattan distance found"
    (is (=
         (closest-center [1 4] centers)
         nil))))

(deftest sum-distances-test
  (testing "Returns sum of all distances from point to the centers"
    (is (=
         30
         (sum-distances [4 3] centers)))))

(deftest largest-finite-area-test
  (testing "Returns size of the largest finite area after distribution"
    (is (=
         (largest-finite-area input)
         17))))

(deftest safe-region-test
  (testing "Return size of the region of points close enough to all the centers"
    (is (=
         16
         (safe-region input 32)))))
