(ns immutant-meetup.hq
  (:require [immutant.messaging :as msg]))

(comment
  "More documentaiton about HornetQ integration here:
http://docs.jboss.org/hornetq/2.3.0.Final/docs/user-manual/html/management.html#management.jms
http://immutant.org/documentation/current/apidoc/immutant.messaging.hornetq.html")


(comment
(msg/start "queue/hq")
(msg/publish "queue/hq" (with-meta [1 2 3] {:error "something went wrong"}))

(def msg-ctrl (immutant.messaging.hornetq/destination-controller "queue/hq"))

(doseq [message (.listMessages msg-ctrl "")]
  (println (zipmap (.keySet message) (.values message))))

(.countMessages msg-ctrl "")
)
