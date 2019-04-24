(ns tests.all
  (:require
    [cljs.spec.alpha :as s]
    [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
    [day8.re-frame.test :refer [run-test-async wait-for]]
    [district.ui.ipfs.subs :as subs]
    [district.ui.ipfs]
    [mount.core :as mount]
    [re-frame.core :refer [reg-event-fx dispatch subscribe reg-cofx trim-v]]))

(s/check-asserts true)

(def interceptors [trim-v])

(use-fixtures
  :each
  {:after
   (fn []
     (mount/stop))})


(reg-event-fx
  ::on-list-files-success
  interceptors
  (fn [_ [response]]
    (println response)))


(reg-event-fx
  ::error
  interceptors
  (fn [_ [error]]
    (println error)))


(reg-event-fx
  ::list-files
  interceptors
  (fn [_ [url]]
    {:ipfs/call {:func "ls"
                 :args [url]
                 :on-success [::on-list-files-success]
                 :on-error [::error]}}))


(deftest tests
  (run-test-async
    (let [ipfs (subscribe [::subs/ipfs])]

      (-> (mount/with-args
            {:ipfs {:host "https://ipfs.io" :endpoint "/api/v0"}})
        (mount/start))

      (dispatch [::list-files "/ipfs/QmYwAPJzv5CZsnA625s3Xf2nemtYgPpHdWEz79ojWnPbdG/"])

      (wait-for [::on-list-files-success ::error]
        (is (= @ipfs {:host "https://ipfs.io" :endpoint "/api/v0"}))))))
