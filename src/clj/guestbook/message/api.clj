(ns guestbook.message.api
  (:require [shoreleave.middleware.rpc :refer [defremote]]
            [guestbook.message.validators :refer [validate-model]]))

(defremote testapi [name message]
  (if-let [res (seq (validate-model name message))]
    (str res)
    "save"))
