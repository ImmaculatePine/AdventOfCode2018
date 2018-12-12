(ns advent-of-code-2018.day-12-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-12 :refer :all]))

(def rules {"...##" "#"
            "..#.." "#"
            ".#..." "#"
            ".#.#." "#"
            ".#.##" "#"
            ".##.." "#"
            ".####" "#"
            "#.#.#" "#"
            "#.###" "#"
            "##.#." "#"
            "##.##" "#"
            "###.." "#"
            "###.#" "#"
            "####." "#"})

(deftest next-generation-test
  (is (=
       (state->string (next-generation (string->state "#..#.#..##......###...###") rules))
       "#...#....#.....#..#..#..#"))
  (is (=
       (state->string (next-generation (string->state "#...#....#.....#..#..#..#") rules))
       "##..##...##....#..#..#..##")))

(deftest emulate-test
  (let [[s n] (emulate (string->state "#..#.#..##......###...###") rules 20)]
    (is (= (state->string s) "#....##....#####...#######....#.#..##"))
    (is (= n 325))))
