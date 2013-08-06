(ns guestbook.testing
  (:require [domina :refer [by-id value]]
            [domina.events :refer [listen!]]
            [guestbook.message.validators :refer [validate-message validate-name validate-model]]
            [ajax.core :refer [GET POST]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(defn validate-message-form
  [name message]
  (.log js/console (str (validate-model (value (by-id "name")) (value (by-id "message"))))))

(defn validate-message-input
  [message]
  (.log js/console (str (validate-message (value message)))))

(defn validate-name-input
  [name]
  (.log js/console (str (validate-name (value name)))))

(defn handler [response]
  (.log js/console (str response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn send-test [name message]
  (remote-callback :api/testapi [(value name) (value message)] #(js/alert %)))

(defn send-message [name message]
  (POST "/"
      {:params {:message message
                :name name}
      :handler handler
      :error-handler error-handler}))

(defn ^:export init
  [] 
  (if (and js/document
           (aget js/document "getElementById"))
    (let [name    (by-id "name")
          message (by-id "message")]
      (listen! (by-id "btn-test") :click (fn [] (send-test name message)))
      (listen! name :blur (fn [] (validate-name-input name)))
      (listen! message :blur (fn [] (validate-message-input message))))))
