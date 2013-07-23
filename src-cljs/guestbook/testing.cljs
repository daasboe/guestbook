(ns guestbook.testing
  (:require [domina :refer [by-id value]]
            [domina.events :refer [listen!]]
            [guestbook.message.validators :refer [validate-message validate-name validate-model]]))

(defn validate-message-form [name message]
  (.log js/console (str (validate-model (value (by-id "name")) (value (by-id "message"))))))

(defn validate-name-input [name]
  (.log js/console (str (validate-name (value name)))))

(defn validate-message-input [message]
  (.log js/console (str (validate-message (value message)))))

(defn ^:export init [] 
  (if (and js/document
           (aget js/document "getElementById"))
    (let [name    (by-id "name")
          message (by-id "message")]
      (listen! (by-id "btn-test") :click validate-message-form)
      (listen! name :blur (fn [] (validate-name-input name)))
      (listen! message :blur (fn [] (validate-message-input message))))))
