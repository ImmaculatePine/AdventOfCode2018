(ns advent-of-code-2018.day-4
  (:require [advent-of-code-2018.input :as input]))

(def ts-format (java.text.SimpleDateFormat. "yyyy-MM-dd hh:mm"))
(def shift-start-format #"Guard #(\d+) begins shift")
(def fall-asleep-format "falls asleep")
(def wake-up-format "wakes up")

(defn parse-timestamp [s] (.parse ts-format s))

(defn last-day? [month day]
  (= day ([0 31 28 31 30 31 30 31 31 30 31 30 31] month)))

(defn next-day [month day]
  (if (last-day? month day) 1 (+ day 1)))

(defn next-month [month day]
  (if (last-day? month day) (+ month 1) month))

(defn max-by [coll]
  (key (apply max-key val coll)))

(defn parse-minute [s]
  (let [[_ _ smonth sday shour sminute] (re-matches #"(\d+)-(\d+)-(\d+) (\d+):(\d+)" s)
        month (Integer/parseInt smonth)
        day (Integer/parseInt sday)
        hour (Integer/parseInt shour)
        minute (Integer/parseInt sminute)]
    (if (= hour 23)
      [(str (next-month month day) "-" (next-day month day)) 0]
      [(str month "-" day) minute])))

(defn parse-action [s]
  (cond
    (= s fall-asleep-format) [:fall-asleep]
    (= s wake-up-format) [:wake-up]
    :else (let [[_ sid] (re-matches shift-start-format s)
                id (Integer/parseInt sid)] [:start-shift id])))

(defn parse [s]
  (let [[_ raw-timestamp raw-action] (re-matches #"\[(.+)\] (.+)" s)
        timestamp (parse-timestamp raw-timestamp)
        [date minute] (parse-minute raw-timestamp)
        [action id] (parse-action raw-action)]
    {:timestamp timestamp
     :date date
     :minute minute
     :action action
     :id id}))

(defn sortByTimestamp [coll]
  (sort-by :timestamp coll))

(defn get-guard [guards id]
  (or (guards id) {}))

(defn get-shift [guard date]
  (or (guard date) []))

(defn update-shift [shift minute]
  (conj shift minute))

(defn update-guard [guard event]
  (let [{:keys [date minute]} event
        shift (get-shift guard date)]
    (conj guard [date (update-shift shift minute)])))

(defn update-guards [guards event prev-id]
  (let [{id :id} event
        current-id (or id prev-id)
        current-guard (get-guard guards current-id)
        updated-guard (update-guard current-guard event)]
    [(conj guards [current-id updated-guard]) current-id]))

(defn events->schedule [coll]
  (loop [[event & remaining] coll
         guards {}
         prev-id nil]
    (if event
      (let [[new-guards new-id] (update-guards guards event prev-id)]
        (recur remaining new-guards new-id))
      guards)))

(defn timestamps->by-minutes [timestamps]
  (let [[a b] timestamps
        [f & other] timestamps
        initial-state (if (= a b 0) true nil)
        initial-timestamps (if initial-state other timestamps)]
    (loop [[minute & remaining-minutes] (range 60)
           [ts & remaining-tss :as tss] initial-timestamps
           awake initial-state ; nil - not started, true - awake, false - sleeping
           out []]
      (if minute
        (if (= minute ts)
          (recur remaining-minutes remaining-tss (not awake) (conj out (not awake)))
          (recur remaining-minutes tss awake (conj out awake)))
        out))))

(defn shifts->by-minutes [shifts]
  (into
   {}
   (map (fn [[date timestamps]] [date (timestamps->by-minutes timestamps)]) shifts)))

(defn schedule->by-minutes [schedule]
  (into
   {}
   (map (fn [[id shifts]] [id (shifts->by-minutes shifts)]) schedule)))

(defn minutes->asleep [minutes]
  (count (filter #(= % false) minutes)))

(defn shifts->minutes-asleep [shifts]
  (reduce +
          (map
           (fn [[_ minutes]] (minutes->asleep minutes))
           shifts)))

(defn sleeping-rate-by-minutes [shifts]
  (let [data (vals shifts)]
    (reduce
     (fn [out, i]
       (update
        out
        i
        (fn [val]
          (+ (or val 0) (count (filter #(= (% i) false) data))))))

     {}
     (range 60))))

(defn most-sleepy-minute [shifts]
  (let [rate (sleeping-rate-by-minutes shifts)
        minute (max-by rate)
        times (rate minute)]
    [minute, times]))

(defn schedule->minutes-asleep [schedule]
  (into {}
        (map
         (fn [[id shifts]] [id (shifts->minutes-asleep shifts)])
         schedule)))

(defn most-sleeping-guard [schedule]
  (let [data (schedule->minutes-asleep schedule)
        sorted (sort-by last data)
        [id _] (last sorted)
        shifts (schedule id)]
    [id shifts]))

(defn input->schedule [coll]
  (events->schedule (sortByTimestamp (map parse coll))))

(defn strategy-1 [coll]
  (let [schedule (schedule->by-minutes (input->schedule coll))
        [id shifts] (most-sleeping-guard schedule)
        [minute _] (most-sleepy-minute shifts)]
    (* id minute)))

(defn strategy-2 [coll]
  (let [schedule (schedule->by-minutes (input->schedule coll))
        all (map (fn [[id shifts]] [id (most-sleepy-minute shifts)]) schedule)
        sorted (sort-by (fn [[_ [_ times]]] times) all)
        [id [minute _]] (last sorted)]
    (* id minute)))

(defn solve-part-1 []
  (-> "day_4.txt"
      input/read-lines
      strategy-1))

(defn solve-part-2 []
  (-> "day_4.txt"
      input/read-lines
      strategy-2))
