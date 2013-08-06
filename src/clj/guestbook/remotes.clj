(ns guestbook.remotes
  (:require [shoreleave.middleware.rpc :refer [defremote]]
            [guestbook.core]))

(defremote testsend [name message]
  "remote hei")

