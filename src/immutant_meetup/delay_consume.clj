(ns immutant-meetup.delay-consume
  (:require [immutant.messaging :as msg]))

(comment

(msg/start "queue/odd-println")

(msg/start "queue/error")

(defn odd-println
  [data]
  (if (odd? data)
    (println (str "odd: " data))
    (msg/publish "queue/error" data)))

(msg/listen "queue/odd-println" odd-println)

(doseq [x (range 4)]
  (msg/publish "queue/odd-println" x))

(msg/listen "queue/error" #(println (str "error" %)))

)
