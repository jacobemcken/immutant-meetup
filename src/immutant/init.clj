(ns immutant.init
  (:use immutant-meetup.core)
  (:require [immutant.web :as web]))

;; web/start takes a ring handler as argument
;; for non-ring handlers take a look at web/start-handler
(web/start app)
