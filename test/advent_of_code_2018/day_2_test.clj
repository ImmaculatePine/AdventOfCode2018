(ns advent-of-code-2018.day-2-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-2 :refer :all]))

;; Part 1

(deftest checksum-test
  (let [ids '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab")]
    (testing "Returns so-called checksum of the list"
      (is (= 12 (checksum ids))))))

;; Part 2

(def ids '("abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"))

(deftest distance-test
  (testing "Returns number of different characters on similar positions"
    (is (= 1 (distance "fghij" "fguij")))
    (is (= 2 (distance "fghik" "fguij")))))

(deftest similar?-test
  (testing "true if only 1 character is different"
    (is (similar? "fghij" "fguij")))
  (testing "false otherwise"
    (is (not (similar? "fghik" "fguij")))
    (is (not (similar? "fghik" "fghik")))))

(deftest similar-test
  (testing "Returns list of similar ids"
    (is (= '("fghij" "fguij") (similar ids)))))

(deftest common-letters-test
  (testing "Returns common letters between two strings"
    (is (= "fgij" (common-letters "fghij" "fguij")))
    (is (= "1234" (common-letters "12345" "12340")))))

(deftest common-letters-between-similar-ids-test
  (testing "Finds two similar ids and calculates their common letters"
    (is (= "fgij" (common-letters-between-similar-ids ids)))))
