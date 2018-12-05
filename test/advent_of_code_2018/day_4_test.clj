(ns advent-of-code-2018.day-4-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-4 :refer :all]))

(def input
  '("[1518-11-01 00:05] falls asleep"
    "[1518-11-01 00:00] Guard #10 begins shift"
    "[1518-11-01 00:25] wakes up"
    "[1518-11-03 00:24] falls asleep"
    "[1518-11-01 00:55] wakes up"
    "[1518-11-01 23:58] Guard #99 begins shift"
    "[1518-11-01 00:30] falls asleep"
    "[1518-11-02 00:40] falls asleep"
    "[1518-11-03 00:05] Guard #10 begins shift"
    "[1518-11-05 00:45] falls asleep"
    "[1518-11-02 00:50] wakes up"
    "[1518-11-03 00:29] wakes up"
    "[1518-11-04 00:02] Guard #99 begins shift"
    "[1518-11-05 00:03] Guard #99 begins shift"
    "[1518-11-05 00:55] wakes up"
    "[1518-11-04 00:36] falls asleep"
    "[1518-11-04 00:46] wakes up"))

(def schedule
  {10 {"11-1" [0 5 25 30 55] "11-3" [5 24 29]}
   99 {"11-2" [0 40 50] "11-4" [2 36 46] "11-5" [3 45 55]}})

(def schedule-by-minutes (schedule->by-minutes schedule))

(deftest input->schedule-test
  (testing "Parses input and builds a schedule"
    (is (= schedule (input->schedule input)))))

(deftest shifts->minutes-asleep-test
  (testing "Calculates how many minutes a single guard was sleeping during all shifts"
    (is (= (shifts->minutes-asleep (schedule-by-minutes 10)) 50))
    (is (= (shifts->minutes-asleep (schedule-by-minutes 99)) 30))))

(deftest most-sleeping-guard-test
  (testing "Returns ID and shifts of the most sleepy guard"
    (let [[id _] (most-sleeping-guard schedule-by-minutes)]
      (is (= id 10)))))

(deftest strategy-1-test
  (testing "ID of the most sleepy guard * the most sleepy minute"
    (is (= 240 (strategy-1 input)))))
