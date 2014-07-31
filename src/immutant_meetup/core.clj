(ns immutant-meetup.core
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [immutant.messaging :as msg]))

(def updates (atom 0))

(defn publish-my-topic
  [text]
  (msg/publish "topic/println" text)
  (str "<h1>Topic</h1><br>Published: " text))

(defroutes app
  (GET "/" [] (str "<h1>Hello World</h1><br>Updated: " @updates " times"))
  (GET "/topic/:text" [text] (publish-my-topic text))
  (route/not-found "<h1>Page not found</h1>"))
