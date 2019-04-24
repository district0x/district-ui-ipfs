(ns district.ui.ipfs.subs
  (:require
    [district.ui.ipfs.queries :as queries]
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  ::ipfs
  queries/ipfs)