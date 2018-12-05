(ns advent-of-code-2018.input
  (:require [clojure.java.io :as io]))

(defn read-raw [filename]
  (slurp (io/resource filename)))

(defn read-lines [filename]
  (with-open [r (io/reader (io/resource filename))]
    (doall (line-seq r))))
