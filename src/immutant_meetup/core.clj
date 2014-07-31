(ns immutant-meetup.core
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [immutant.messaging :as msg]))

(defn publish-my-topic
  [text]
  (msg/publish "topic/println" text)
  (str "<h1>Topic</h1><br>Published: " text))

(defroutes app
  (GET "/" [] "<h1>Hello World</h1>")
  (GET "/topic/:text" [text] (publish-my-topic text))
  (route/not-found "<h1>Page not found</h1>"))
