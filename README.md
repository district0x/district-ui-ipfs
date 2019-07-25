# district-ui-ipfs

[![Build Status](https://travis-ci.org/district0x/district-ui-ipfs.svg?branch=master)](https://travis-ci.org/district0x/district-ui-ipfs)

Clojurescript [re-mount](https://github.com/district0x/d0x-INFRA/blob/master/re-mount.md) module, that takes care of setting up IPFS instance for working with [re-frame-ipfs-fx](https://github.com/district0x/re-frame-ipfs-fx). 

## Installation
Add <br>

[![Clojars Project](https://img.shields.io/clojars/v/district0x/district-ui-ipfs.svg)](https://clojars.org/district0x/district-ui-ipfs)

into your project.clj  
Include `[district.ui.ipfs]` in your CLJS file, where you use `mount/start`

## API Overview

**Warning:** district0x modules are still in early stages, therefore API can change in a future.

- [district.ui.ipfs](#districtuiipfs)
- [district.ui.ipfs.subs](#districtuiipfssubs)
  - [::ipfs](#ipfs-sub)
- [district.ui.ipfs.events](#districtuiipfsevents)
  - [::start](#start)
- [district.ui.ipfs.queries](#districtuiipfsqueries)
  - [ipfs](#ipfs)

## district.ui.ipfs
This namespace contains ipfs [mount](https://github.com/tolitius/mount) module. Once you start mount, it'll take care of ipfs
initialisation and will put results into re-frame db.

You can pass following args to initiate this module: 
* `:host` IPFS host that will be used for IPFS calls. 
* `:endpoint` Endpoint at host for IPFS calls

```clojure
(ns my-district.core
  (:require [mount.core :as mount]
            [re-frame.core :refer [reg-event-fx dispatch trim-v]]
            [district.ui.ipfs]))

(-> (mount/with-args
      {:ipfs {:host "https://ipfs.io" :endpoint "/api/v0"}})
  (mount/start))
    
(reg-event-fx
  ::list-files
  [trim-v]
  (fn [_ [url]]
    {:ipfs/call {:func "ls"
                 :args [url]
                 :on-success [::on-list-files-success]
                 :on-error [::error]}}))
                 
(dispatch [::list-files "/ipfs/QmYwAPJzv5CZsnA625s3Xf2nemtYgPpHdWEz79ojWnPbdG/"])                 

```

## district.ui.ipfs.subs
re-frame subscriptions provided by this module:

#### <a name="ipfs-sub">`::ipfs`
Returns IPFS configuration.

```clojure
(ns my-district.home-page
  (:require [district.ui.ipfs.subs :as ipfs-subs]
            [re-frame.core :refer [subscribe]]))

(defn home-page []
  (let [ipfs (subscribe [::ipfs-subs/ipfs])]
    (fn []
        [:div "Using IPFS host: " (:host @ipfs) " endpoint: " (:endpoint @ipfs)])))
```

## district.ui.ipfs.events
re-frame events provided by this module:

#### <a name="start">`::start [opts]`
Initiates IPFS instance. Same as passing config options to mount. 

## district.ui.ipfs.queries
DB queries provided by this module:  
*You should use them in your events, instead of trying to get this module's 
data directly with `get-in` into re-frame db.*

#### <a name="ipfs">`ipfs [db]`
Returns IPFS configuration.

```clojure
(ns my-district.events
    (:require [district.ui.ipfs.queries :as ipfs-queries]
              [re-frame.core :refer [reg-event-fx]]))

(reg-event-fx
  ::my-event
  (fn [{:keys [:db]}]
    (if (= "http://127.0.0.1:5001" (:host (ipfs-queries/ipfs db))
      {:dispatch [::do-something]}
      {:dispatch [::do-other-thing]}))))
```

## Development
```bash
lein deps

# To run tests and rerun on changes
lein doo chrome tests
```
