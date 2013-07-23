(ns guestbook.routes.home
  (:use compojure.core)
  (:require [guestbook.views.layout :as layout]
            [guestbook.util :as util]
            [guestbook.models.db :as db]
            [noir.response :as response]))

(defn home-page [& [name message error]]
  (layout/render "home.html"
                 {:error error
                  :name name
                  :message message
                  :messages (db/get-messages)}))

(defn save-message [name message]
  (cond 

    (empty? name)
    (home-page name message "Somebody forgot to leave a name")
    
    (empty? message)
    (home-page name message "Dont you have anything to say?")
    :else
    (do
      (db/save-message name message)
      (home-page))))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/" [name message] (save-message name message))
  (GET "/about" [] (about-page))
  (POST "/send-message" [message user] (response/edn "yeee")))
