(ns guestbook.message.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present? max-length]]))

(defn validate-name
  [name]
  (validate {:name name}
            [:name present? "Name cant be empty."]
            [:name (max-length 10) "Name must be less that 10 chars"]))

(defn validate-message
  [message]
  (validate {:message message}
            [:message present? "No message?"]
            [:message (max-length 200) "Too large message"]))

(defn validate-model
  [name message]
  (merge (validate-name name)
         (validate-message message)))
