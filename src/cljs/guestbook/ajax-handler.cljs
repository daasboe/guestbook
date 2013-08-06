(ns guestbook.ajax-handler
  (:require [ajax.core :refer [POST GET]]
            [domina :refer [by-id value]]
            [domina.events :refer [listen!]]))

(defn handler [response]
  (.log js/console (str response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))


(defn send-message []
  (POST "/send-message"
      {:params {:message "Hello World"
                :user    "Bob"}
      :handler handler
      :error-handler error-handler}))

(defn ^:export init []
  (listen! (by-id "btn-send") :click send-message))

