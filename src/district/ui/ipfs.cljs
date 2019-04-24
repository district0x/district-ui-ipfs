(ns district.ui.ipfs
  (:require
    [cljs.spec.alpha :as s]
    [district.ui.ipfs.events :as events]
    [district0x.re-frame.ipfs-fx]
    [mount.core :as mount :refer [defstate]]
    [re-frame.core :refer [dispatch-sync]]))

(declare start)
(declare stop)


(defstate ipfs
  :start (start (:ipfs (mount/args)))
  :stop (stop))


(s/def ::host string?)
(s/def ::endpoint string?)
(s/def ::opts (s/keys :req-un [::host]
                      :opt-un [::endpoint]))

(defn start [opts]
  (s/assert ::opts opts)
  (dispatch-sync [::events/start opts])
  opts)


(defn stop []
  (dispatch-sync [::events/stop]))


