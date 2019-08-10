(ns district.ui.ipfs.events
  (:require
    [district0x.re-frame.ipfs-fx]
    [re-frame.core :refer [reg-event-fx trim-v]]))

(def interceptors [trim-v])

(reg-event-fx
  ::start
  interceptors
  (fn [_ [opts]]
    {:ipfs/init opts}))

(reg-event-fx
  ::stop
  interceptors
  ::stopped)
