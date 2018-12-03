(ns advent-of-code-2018.day-3-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-3 :refer :all]))

(deftest parse-test
  (testing "Parses input string into map"
    (let [input "#1 @ 604,100: 17x27"
          expected {:id "1" :x 604 :y 100 :w 17 :h 27}]
      (is (= expected (parse input))))))

(deftest apply-change-test
  (testing "Returns new points and list of encountered overlaps"
    (is (=
         [{[1 1] #{"1"}} #{}]
         (apply-change {} [1 1] "1" #{})))
    (is (=
         [{[1 1] #{"1"}} #{"2"}]
         (apply-change {} [1 1] "1" #{"2"})))
    (is (=
         [{[1 1] #{"1" "2"}} #{"1" "2"}]
         (apply-change {[1 1] #{"2"}} [1 1] "1" #{})))
    (is (=
         [{[1 1] #{"1" "2"}} #{"1" "2"}]
         (apply-change {[1 1] #{"2"}} [1 1] "1" #{"2"})))))

(deftest ->data-test
  (let [input '("#1 @ 1,1: 1x1" "#2 @ 1,1: 2x2")
        [points claims overlapping-claims] (->data input)]
    (testing "Returns a collection of all taken points"
      (is (=
           points
           {[1 1] #{"1" "2"} [1 2] #{"2"} [2 1] #{"2"} [2 2] #{"2"}})))
    (testing "Returns a collection of all claims"
      (is (=
           claims
           #{"1" "2"})))
    (testing "Returns a collection of overlapping claims"
      (is (=
           overlapping-claims
           #{"1" "2"})))))

(def input '("#1 @ 1,3: 4x4" "#2 @ 3,1: 4x4" "#3 @ 5,5: 2x2"))

(deftest overlapping-area-test
  (testing "Returns number of points within two or more claims"
    (is (=
         4
         (overlapping-area input)))))

(deftest not-overlapping-test
  (testing "Returns id of the claim not overlapping with any other"
    (is (=
         "3"
         (not-overlapping input)))))
