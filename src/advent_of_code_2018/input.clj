(ns advent-of-code-2018.input
  (:require [clojure.java.io :as io]))

(defn read-lines [filename]
  (with-open [r (io/reader (io/resource filename))]
    (doall (line-seq r))))
