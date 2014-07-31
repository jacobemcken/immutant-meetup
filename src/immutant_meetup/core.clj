(ns immutant-meetup.core
  (:use [compojure.core])
  (:require [compojure.route :as route]))

;; This handler is copy-pasted directly from compojures README.md
(defroutes app
  (GET "/" [] "<h1>Hello World</h1>")
  (route/not-found "<h1>Page not found</h1>"))
