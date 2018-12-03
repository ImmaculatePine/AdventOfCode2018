(ns advent-of-code-2018.day-3-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-3 :refer :all]))

(deftest parse-test
  (testing "Parses input string into map"
    (let [input "#1 @ 604,100: 17x27"
          expected {:id "1" :x 604 :y 100 :w 17 :h 27}]
      (is (= expected (parse input))))))

(deftest update-point-test
  (testing "Adds new value to the set or initializes a new set"
    (is (= #{"1"} (update-point nil "1")))
    (is (= #{"1"} (update-point #{} "1")))
    (is (= #{"1"} (update-point #{"1"} "1")))
    (is (= #{"1" "2"} (update-point #{"1"} "2")))))

(deftest add-square-to-points-test
  (testing "Adds square id to the existing points"
    (is (=
         {[1 1] #{"1"} [1 2] #{"1"} [2 1] #{"1"} [2 2] #{"1"}}
         (add-square-to-points {} {:id "1" :x 1 :y 1 :w 2 :h 2}))
        (is (=
             {[1 1] #{"1" "2"} [1 2] #{"2"} [2 1] #{"2"} [2 2] #{"2"}}
             (add-square-to-points {[1 1] #{"1"}} {:id "2" :x 1 :y 1 :w 2 :h 2}))))))

(deftest points-test
  (testing "Parses input and returns a collection of all taken points"
    (is (=
         {[1 1] #{"1" "2"} [1 2] #{"2"} [2 1] #{"2"} [2 2] #{"2"}}
         (points '("#1 @ 1,1: 1x1" "#2 @ 1,1: 2x2"))))))

(deftest overlapping-area-test
  (testing "Returns number of points within two or more squares"
    (is (=
         4
         (overlapping-area '("#1 @ 1,3: 4x4" "#2 @ 3,1: 4x4" "#3 @ 5,5: 2x2"))))))
