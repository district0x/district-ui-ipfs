(ns district.ui.ipfs.queries
  (:require [cljs-ipfs-api.core :as ipfs-api]))

(defn ipfs [db]
  @ipfs-api/*ipfs-instance*)
