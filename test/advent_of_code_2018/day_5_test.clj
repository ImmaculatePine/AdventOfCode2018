(ns advent-of-code-2018.day-5-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-5 :refer :all]))

(def input "dabAcCaCBAcCcaDA")

(deftest shrink-test
  (testing "Removes units with the opposite polarities"
    (is (= (shrink input) "dabCBAcaDA"))))

(deftest remove-and-shrink-test
  (testing "Removes particular letter and shrinks the resulting string"
    (is (= (remove-and-shrink input "a") "dbCBcD"))
    (is (= (remove-and-shrink input "b") "daCAcaDA"))
    (is (= (remove-and-shrink input "c") "daDA"))
    (is (= (remove-and-shrink input "d") "abCBAc"))))

(deftest shortest-structure-test
  (testing "Finds the shortest possible structure"
    (is (=
         (shortest-structure input)
         ["c" 4 "daDA"]))))
