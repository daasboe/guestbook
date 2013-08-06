(ns guestbook.handler  
  (:require [compojure.core :refer [defroutes]]            
            [compojure.route :as route]
            [compojure.handler :as handler]
            [noir.util.middleware :as middleware]
            [taoensso.timbre :as timbre]
            [com.postspectacular.rotor :as rotor]
            [guestbook.routes.home :refer [home-routes]]
            [guestbook.models.schema :as schema]
            [shoreleave.middleware.rpc :refer [wrap-rpc defremote remote-ns]]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn destroy []
  (timbre/info "picture-gallery is shutting down"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (timbre/set-config!
    [:appenders :rotor]
    {:min-level :info
     :enabled? true
     :async? false ; should be always false for rotor
     :max-message-per-msecs nil
     :fn rotor/append})
  
  (timbre/set-config!
    [:shared-appender-config :rotor]
    {:path "guestbook.log" :max-size (* 512 1024) :backlog 10})
  
  ;;initialize if needed
  (if-not (schema/initialized?) (schema/create-tables))
  
  (timbre/info "guestbook started successfully"))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (timbre/info "guestbook is shutting down..."))

(remote-ns 'guestbook.message.api :as "api")
(def app (middleware/app-handler
           ;;add your application routes here
           [home-routes app-routes]
           ;;add custom middleware here           
           :middleware [wrap-rpc handler/site]
           ;;add access rules here
           ;;each rule should be a vector
           :access-rules []))

(def war-handler (middleware/war-handler app))

