(defproject
  guestbook
  "0.1.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "1.5.1"]
   [lib-noir "0.6.6"]
   [compojure "1.1.5"]
   [ring-server "0.2.8"]
   [clabango "0.5"]
   [com.taoensso/timbre "2.1.2"]
   [com.postspectacular/rotor "0.1.0"]
   [com.taoensso/tower "1.7.1"]
   [markdown-clj "0.9.28"]
   [com.h2database/h2 "1.3.172"]
   [korma "0.3.0-RC5"]
   [domina "1.0.2-SNAPSHOT"]
   [shoreleave/shoreleave-remote "0.3.0"]
   [shoreleave/shoreleave-remote-ring "0.3.0"]
   [com.cemerick/piggieback "0.0.5"]
   [cljs-ajax "0.1.4"]
   [com.cemerick/valip "0.3.2"]
   [org.clojure/google-closure-library-third-party "0.0-2029"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]]
  :ring
  {:handler guestbook.handler/war-handler,
   :init guestbook.handler/init,
   :destroy guestbook.handler/destroy}
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.1.8"]]}}
  :url
  "http://example.com/FIXME"
  :plugins
  [[lein-ring "0.8.5"]
   [lein-cljsbuild "0.3.0"]]
  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]
  :cljsbuild {:crossovers [valip.core valip.predicates guestbook.message.validators]
              :builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/guestbook.js"
                           :optimization :whitespace}}]}
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :description
  "FIXME: write description"
  :min-lein-version "2.0.0")
