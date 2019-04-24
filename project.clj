(defproject district0x/district-ui-ipfs "1.0.0-SNAPSHOT"
  :description "district UI module for setting up IPFS"
  :url "https://github.com/district0x/district-ui-ipfs"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[district0x.re-frame/ipfs-fx "1.0.0"]
                 [mount "0.1.11"]
                 [org.clojure/clojurescript "1.10.439"]
                 [re-frame "0.10.2"]]

  :doo {:paths {:karma "./node_modules/karma/bin/karma"}}

  :npm {:devDependencies [[karma "1.7.1"]
                          [karma-chrome-launcher "2.2.0"]
                          [karma-cli "1.0.1"]
                          [karma-cljs-test "0.1.0"]]}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0"]
                                  [cider/piggieback "0.4.0"]
                                  [org.clojure/tools.reader "1.3.0"]
                                  [day8.re-frame/test "0.1.5"]]
                   :plugins [[lein-cljsbuild "1.1.7"]
                             [lein-doo "0.1.8"]
                             [lein-npm "0.6.2"]]}}

  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "tests-output/tests.js"
                                   :output-dir "tests-output"
                                   :main "tests.runner"
                                   :optimizations :none}}]})