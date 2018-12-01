(defproject advent-of-code-2018 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2018 solutions in Clojure"
  :url "https://github.com/ImmaculatePine/AdventOfCode2018"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :plugins [[lein-cljfmt "0.6.2"]]
  :main ^:skip-aot advent-of-code-2018.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
