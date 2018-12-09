(ns advent-of-code-2018.day-7-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day-7 :refer :all]))

(def input '("Step C must be finished before step A can begin."
             "Step C must be finished before step F can begin."
             "Step A must be finished before step B can begin."
             "Step A must be finished before step D can begin."
             "Step B must be finished before step E can begin."
             "Step D must be finished before step E can begin."
             "Step F must be finished before step E can begin."))

(deftest graph-test
  (testing "Returns nodes and transitions"
    (is (=
         [["A" "B" "C" "D" "E" "F"] [["C" "A"] ["C" "F"] ["A" "B"] ["A" "D"] ["B" "E"] ["D" "E"] ["F" "E"]]]
         (graph input)))))

(deftest deps-test
  (testing "Returns dependencies between steps"
    (let [[nodes transitions] (graph input)]
      (is (=
           {"A" #{"C"}
            "B" #{"A"}
            "C" #{}
            "D" #{"A"}
            "E" #{"B" "D" "F"}
            "F" #{"C"}}
           (deps nodes transitions))))))

(deftest order-test
  (testing "Returns order of steps to be completed in"
    (is (=
         "CABDFE"
         (order input)))))
